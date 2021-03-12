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

}