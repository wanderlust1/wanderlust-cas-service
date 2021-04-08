package service

import entity.User
import event.UserEvent

interface UserService {

    fun login(req: UserEvent.LoginReq): UserEvent.LoginQueryRsp

    fun register(req: UserEvent.RegisterReq): Int

    fun getCommunityUsers(communityId: String): List<User>

    fun removeUserFromCommunity(userId: String, communityId: String): Int

}