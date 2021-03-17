package dao

import entity.Community
import org.springframework.stereotype.Repository

@Repository("CommunityDaoImpl")
class CommunityDaoImpl: BaseDao(), CommunityDao {

    override fun queryCommunityByKeywords(keywords: String, userId: String): List<Community> {
        val params = mapOf(Pair("user_id", userId), Pair("keywords", keywords))
        return query("dao.CommunityDaoImpl.queryCommunityByKeywords", params)
    }

    override fun insertJoinCommunity(userId: String, communityId: String): Int {
        val params = mapOf(Pair("user_id", userId), Pair("community_id", communityId))
        return insert("dao.CommunityDaoImpl.insertJoinCommunity", params)
    }

    override fun deleteJoinCommunity(userId: String, communityId: String): Int {
        val params = mapOf(Pair("user_id", userId), Pair("community_id", communityId))
        return delete("dao.CommunityDaoImpl.deleteJoinCommunity", params)
    }

}