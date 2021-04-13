package dao

import entity.Community

interface CommunityDao {

    fun queryCommunityByAdminId(adminId: String): Community?

    fun queryCommunityByKeywords(keywords: String, userId: String): List<Community>

    fun queryCommunityById(communityId: String): Community?

    fun queryAdminCommunityByKeywords(keywords: String, adminId: String): List<Community>

    fun insertJoinCommunity(userId: String, communityId: String): Int

    fun deleteJoinCommunity(userId: String, communityId: String): Int

    fun insertCommunity(community: Community): Int

    fun updateAdminCommunity(adminId: String, communityId: String): Int

}