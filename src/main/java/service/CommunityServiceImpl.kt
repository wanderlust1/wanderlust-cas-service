package service

import dao.CommunityDao
import entity.Community
import event.CommunityEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import utils.LoginType

@Service
class CommunityServiceImpl: CommunityService {

    @Autowired
    lateinit var mCommunityDao: CommunityDao

    override fun searchCommunity(keywords: String, id: String, type: Int): List<Community> {
        return if (type == LoginType.USER) {
            mCommunityDao.queryCommunityByKeywords(keywords, id)
        } else if (type == LoginType.ADMIN) {
            mCommunityDao.queryAdminCommunityByKeywords(keywords, id)
        } else emptyList()
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

}