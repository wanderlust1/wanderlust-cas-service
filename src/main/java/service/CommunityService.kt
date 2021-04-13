package service

import entity.Community
import entity.CommunityStatistics

interface CommunityService {

    fun getMyCommunity(adminId: String): Community?

    fun searchCommunity(keywords: String, id: String, type: Int): List<Community>

    fun getCommunityById(communityId: String): Community?

    fun joinCommunity(userId: String, communityId: String): Int

    fun changeCommunity(userId: String, oldCommunityId: String, newCommunityId: String): Int

    fun createCommunity(community: Community, adminId: String): Int

    fun bindCommunity(adminId: String, communityId: String): Int

    fun getCommunityStatistics(communityId: String, totalUserCount: Int): CommunityStatistics

}