package service

import entity.Community

interface CommunityService {

    fun searchCommunity(keywords: String, userId: String): List<Community>

    fun joinCommunity(userId: String, communityId: String): Int

    fun changeCommunity(userId: String, oldCommunityId: String, newCommunityId: String): Int

}