package dao

import entity.Admin
import entity.User

interface UserDao {

    fun queryUser(user: User): User?

    fun queryUserById(userId: String): User?

    fun insertUser(user: User): Int

    fun queryAdmin(admin: Admin): Admin?

    fun insertAdmin(admin: Admin): Int

    fun queryCommunityUsers(communityId: String): List<User>

    fun deleteUserFromCommunity(userId: String, communityId: String): Int

}