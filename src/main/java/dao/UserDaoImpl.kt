package dao

import entity.Admin
import entity.User
import org.springframework.stereotype.Repository

@Repository("UserDaoImpl")
class UserDaoImpl: UserDao, BaseDao() {

    override fun queryUser(user: User): User? {
        val result = query<User>("dao.UserDaoImpl.queryUser", user)
        return if (result.isNotEmpty()) result[0] else null
    }

    override fun queryUserById(userId: String): User? {
        val result = query<User>("dao.UserDaoImpl.queryUserById", userId)
        return if (result.isNotEmpty()) result[0] else null
    }

    override fun insertUser(user: User): Int {
        return insert("dao.UserDaoImpl.insertUser", user)
    }

    override fun queryAdmin(admin: Admin): Admin? {
        val result = query<Admin>("dao.UserDaoImpl.queryAdmin", admin)
        return if (result.isNotEmpty()) result[0] else null
    }

    override fun insertAdmin(admin: Admin): Int {
        return insert("dao.UserDaoImpl.insertAdmin", admin)
    }

    override fun queryCommunityUsers(communityId: String): List<User> {
        return query("dao.UserDaoImpl.queryCommunityUsers", communityId)
    }

    override fun deleteUserFromCommunity(userId: String, communityId: String): Int {
        val params = mapOf("user_id" to userId, "community_id" to communityId)
        return delete("dao.UserDaoImpl.deleteUserFromCommunity", params)
    }

}