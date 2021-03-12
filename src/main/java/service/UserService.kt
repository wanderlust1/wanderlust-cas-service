package service

import entity.User
import event.UserEvent

interface UserService {

    fun getUserData(id: String, password: String): User?

    fun login(req: UserEvent.LoginReq): UserEvent.LoginQueryRsp

    fun register(req: UserEvent.RegisterReq): Int

}