package controller

import com.google.gson.Gson
import entity.User
import event.UserEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import service.UserService
import utils.LoginType
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class LoginController {

    @Autowired
    lateinit var mUserService: UserService

    @PostMapping("/login")
    fun login(@RequestBody request: UserEvent.LoginReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val queryRsp = mUserService.login(request)
        val msg = when (queryRsp.code) {
            UserEvent.SUCC -> "登录成功"
            UserEvent.FAIL -> "登录失败，${if (request.loginType == LoginType.USER) "用户" else "管理员"}账号或密码错误"
            else           -> "登录失败，未知错误"
        }
        val result = if (request.loginType == LoginType.USER) {
            UserEvent.LoginRsp(queryRsp.code, msg, queryRsp.user)
        } else if (request.loginType == LoginType.ADMIN)  {
            UserEvent.LoginRsp(queryRsp.code, msg, admin = queryRsp.admin)
        } else {
            UserEvent.LoginRsp(queryRsp.code, msg)
        }
        response.writer.write(Gson().toJson(result))
    }

    @PostMapping("/register")
    fun register(@RequestBody request: UserEvent.RegisterReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = when (mUserService.register(request)) {
            UserEvent.SUCC ->
                UserEvent.RegisterRsp(UserEvent.SUCC, "注册成功")
            UserEvent.EXISTED ->
                UserEvent.RegisterRsp(UserEvent.EXISTED,
                    "注册失败，该${if (request.loginType == LoginType.USER) "用户" else "管理员"}账号已存在")
            else ->
                UserEvent.RegisterRsp(UserEvent.FAIL, "注册失败，未知错误")
        }
        response.writer.write(Gson().toJson(result))
    }

}