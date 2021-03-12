package controller

import com.google.gson.Gson
import event.UserEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.multipart.MultipartFile
import service.UserService
import java.util.*
import java.util.concurrent.ThreadPoolExecutor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * @author Wanderlust 2020.12.11
 */
@Controller
class UserController {

    @Autowired
    lateinit var mUserService: UserService

    @RequestMapping("/getUser")
    fun getUser(req: HttpServletRequest, rsp: HttpServletResponse) {
        rsp.contentType = "text/html;charset=UTF-8"
        val result = mUserService.getUserData(req.getParameter("id"), req.getParameter("pw"))
        rsp.writer.write(Gson().toJson(result))

    }

}