package service

import entity.Community

interface CommunityService {

    fun searchCommunity(keywords: String, id: String, type: Int): List<Community>

    fun joinCommunity(userId: String, communityId: String): Int

    fun changeCommunity(userId: String, oldCommunityId: String, newCommunityId: String): Int

    fun createCommunity(community: Community, adminId: String): Int

    fun bindCommunity(adminId: String, communityId: String): Int

}