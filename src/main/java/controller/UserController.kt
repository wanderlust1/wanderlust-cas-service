package controller

import com.google.gson.Gson
import event.UserEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import service.UserService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Wanderlust 2020.12.11
 */
@Controller
class UserController {

    @Autowired
    lateinit var mUserService: UserService

    @PostMapping("/getCommunityUsers")
    fun getCommunityUsers(@RequestBody request: UserEvent.GetCommunityUsersReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = UserEvent.GetCommunityUsersRsp(mUserService.getCommunityUsers(request.communityId))
        println(result)
        response.writer.write(Gson().toJson(result))
    }

    @PostMapping("/kickUser")
    fun kickUser(@RequestBody request: UserEvent.KickUserReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val resultCode = mUserService.removeUserFromCommunity(request.userId, request.communityId)
        val message = if (resultCode == UserEvent.SUCC) "已移除该用户" else "操作失败"
        val result = UserEvent.KickUserRsp(resultCode, message)
        println(result)
        response.writer.write(Gson().toJson(result))
    }

}