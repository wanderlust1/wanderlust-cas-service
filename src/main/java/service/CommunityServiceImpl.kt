package service

import dao.CommunityDao
import dao.RegDao
import entity.Community
import entity.CommunityStatistics
import entity.OutSideReg
import entity.TemperReg
import event.CommunityEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import utils.HealthType
import utils.LoginType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Service
class CommunityServiceImpl: CommunityService {

    @Autowired
    lateinit var mCommunityDao: CommunityDao

    @Autowired
    lateinit var mRegDao: RegDao

    override fun getMyCommunity(adminId: String): Community? {
        return mCommunityDao.queryCommunityByAdminId(adminId)
    }

    override fun searchCommunity(keywords: String, id: String, type: Int): List<Community> {
        return if (type == LoginType.USER) {
            mCommunityDao.queryCommunityByKeywords(keywords, id)
        } else if (type == LoginType.ADMIN) {
            mCommunityDao.queryAdminCommunityByKeywords(keywords, id)
        } else emptyList()
    }

    override fun getCommunityById(communityId: String): Community? {
        return mCommunityDao.queryCommunityById(communityId)
    }

    override fun joinCommunity(userId: String, communityId: String): Int {
        return if (mCommunityDao.insertJoinCommunity(userId, communityId) == 1) {
            CommunityEvent.SUCC
        } else {
            CommunityEvent.FAIL
        }
    }

    override fun changeCommunity(userId: String, oldCommunityId: String, newCommunityId: String): Int {
        return if (mCommunityDao.deleteJoinCommunity(userId, oldCommunityId) == 1) {
            if (mCommunityDao.insertJoinCommunity(userId, newCommunityId) == 1) {
                CommunityEvent.SUCC
            } else {
                CommunityEvent.FAIL
            }
        } else {
            CommunityEvent.FAIL
        }
    }

    override fun createCommunity(community: Community, adminId: String): Int {
        return if (mCommunityDao.insertCommunity(community) == 1) {
            bindCommunity(adminId, community.id)
        } else {
            CommunityEvent.FAIL
        }
    }

    override fun bindCommunity(adminId: String, communityId: String): Int {
        return if (mCommunityDao.updateAdminCommunity(adminId, communityId) == 1) {
            CommunityEvent.SUCC
        } else {
            CommunityEvent.FAIL
        }
    }

    override fun getCommunityStatistics(communityId: String, totalUserCount: Int): CommunityStatistics {
        val result = CommunityStatistics()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        HashMap<String, TemperReg>().apply { //获取用户提交的健康记录（每个用户只获取最新的一次记录）
            mRegDao.queryCommunityTemperRegs(communityId).forEach {
                it.dateFormat = format.parse(it.date)
                if ((!containsKey(it.userId)) || it.dateFormat?.after(get(it.userId)?.dateFormat) == true) {
                    set(it.userId, it)
                }
            }
        }.filter { //筛选最近一个月的记录
            val curr     = Calendar.getInstance(Locale.CHINA).time
            val monthAgo = Calendar.getInstance(Locale.CHINA).apply {
                add(Calendar.MONTH, -1)
            }.time
            monthAgo.before(it.value.dateFormat) && curr.after(it.value.dateFormat)
        }.apply { //统计健康记录提交人数
            result.total = totalUserCount
            result.submit = size
            result.noneSubmit = totalUserCount - size
        }.forEach { //统计健康记录内容
            var health = true
            if (it.value.diagnose == HealthType.DIAGNOSE) {
                result.diagnose++
                health = false
            }
            if (it.value.diagnose == HealthType.SUSPECT) {
                result.suspect++
                health = false
            }
            if (it.value.approach == HealthType.APPROACH_DIAGNOSE
                || it.value.approach == HealthType.APPROACH_SUSPECT) {
                result.approach++
                health = false
            }
            if (it.value.temper.toFloat() > 37.4f) {
                result.highTemper++
                health = false
            }
            if (health) result.health++
        }
        val outsideUserSet = HashSet<String>()
        result.outsideRegs = mRegDao.queryCommunityOutsideRegs(communityId).filter { //筛选最近一个月的记录
            outsideUserSet.add(it.userId)
            val start    = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(it.startTime)
            val end      = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(it.endTime)
            val curr     = Calendar.getInstance(Locale.CHINA).time
            val monthAgo = Calendar.getInstance(Locale.CHINA).apply {
                add(Calendar.MONTH, -1)
            }.time
            monthAgo.before(start) && curr.after(start) ||
                monthAgo.before(end) && curr.after(end) || monthAgo.after(start) && curr.before(end)
        }
        result.outside = outsideUserSet.size
        return result
    }

}