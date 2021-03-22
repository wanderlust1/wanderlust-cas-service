package service

import dao.CommunityDao
import entity.Community
import event.CommunityEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service 

@Service
class CommunityServiceImpl: CommunityService {

    @Autowired
    lateinit var mCommunityDao: CommunityDao

    override fun searchCommunity(keywords: String, userId: String): List<Community> {
        return mCommunityDao.queryCommunityByKeywords(keywords, userId)
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

}