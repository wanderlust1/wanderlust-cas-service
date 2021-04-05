package dao

import entity.Community

interface CommunityDao {

    fun queryCommunityByKeywords(keywords: String, userId: String): List<Community>

    fun queryAdminCommunityByKeywords(keywords: String, adminId: String): List<Community>

    fun insertJoinCommunity(userId: String, communityId: String): Int

    fun deleteJoinCommunity(userId: String, communityId: String): Int

    fun insertCommunity(community: Community): Int

    fun updateAdminCommunity(adminId: String, communityId: String): Int

}