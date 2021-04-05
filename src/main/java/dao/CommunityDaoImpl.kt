package dao

import entity.Community
import org.springframework.stereotype.Repository

@Repository("CommunityDaoImpl")
class CommunityDaoImpl: BaseDao(), CommunityDao {

    override fun queryCommunityByKeywords(keywords: String, userId: String): List<Community> {
        val params = mapOf("user_id" to userId, "keywords" to keywords)
        return query("dao.CommunityDaoImpl.queryCommunityByKeywords", params)
    }

    override fun queryAdminCommunityByKeywords(keywords: String, adminId: String): List<Community> {
        val params = mapOf("admin_id" to adminId, "keywords" to keywords)
        return query("dao.CommunityDaoImpl.queryAdminCommunityByKeywords", params)
    }

    override fun insertJoinCommunity(userId: String, communityId: String): Int {
        val params = mapOf("user_id" to userId, "community_id" to communityId)
        return insert("dao.CommunityDaoImpl.insertJoinCommunity", params)
    }

    override fun deleteJoinCommunity(userId: String, communityId: String): Int {
        val params = mapOf("user_id" to userId, "community_id" to communityId)
        return delete("dao.CommunityDaoImpl.deleteJoinCommunity", params)
    }

    override fun insertCommunity(community: Community): Int {
        return insert("dao.CommunityDaoImpl.insertCommunity", community)
    }

    override fun updateAdminCommunity(adminId: String, communityId: String): Int {
        val params = mapOf("admin_id" to adminId, "community_id" to communityId)
        return update("dao.CommunityDaoImpl.updateAdminCommunity", params)
    }

}