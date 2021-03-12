package service

import dao.UserDao
import entity.User
import event.UserEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import utils.LoginType

@Service
class UserServiceImpl: UserService {

    @Autowired
    lateinit var mUserDao: UserDao

    override fun getUserData(id: String, password: String): User? {
        return mUserDao.queryUser(User(userId = id, password = password))
    }

    override fun login(req: UserEvent.LoginReq): UserEvent.LoginQueryRsp {
        if (req.loginType == LoginType.USER && req.user != null) {
            val queryUser = mUserDao.queryUser(req.user)
            if (queryUser?.userId == req.user.userId) {
                return UserEvent.LoginQueryRsp(queryUser, code = UserEvent.SUCC)
            }
        } else if (req.loginType == LoginType.ADMIN && req.admin != null) {
            val queryAdmin = mUserDao.queryAdmin(req.admin)
            if (queryAdmin?.adminId == req.admin.adminId) {
                return UserEvent.LoginQueryRsp(admin = queryAdmin, code = UserEvent.SUCC)
            }
        }
        return UserEvent.LoginQueryRsp(null, code = UserEvent.FAIL)
    }

    override fun register(req: UserEvent.RegisterReq): Int {
        val result = if (req.loginType == LoginType.USER && req.user != null) {
            mUserDao.insertUser(req.user)
        } else if (req.loginType == LoginType.ADMIN && req.admin != null) {
            mUserDao.insertAdmin(req.admin)
        } else 0
        return when (result) {
            1    -> UserEvent.SUCC
            -2   -> UserEvent.EXISTED
            else -> UserEvent.FAIL
        }
    }

}