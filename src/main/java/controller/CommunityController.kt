package controller

import com.google.gson.Gson
import event.CommunityEvent
import event.UserEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import service.CommunityService
import utils.LoginType
import javax.servlet.http.HttpServletResponse
import kotlin.random.Random
import kotlin.random.nextInt

@Controller
class CommunityController {

    @Autowired
    lateinit var mCommunityService: CommunityService

    @PostMapping("/searchCommunity")
    fun searchCommunity(@RequestBody request: CommunityEvent.SearchReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = CommunityEvent.SearchRsp(CommunityEvent.SUCC, mCommunityService.searchCommunity(
            request.keywords, request.id, request.loginType
        ))
        println(result)
        response.writer.write(Gson().toJson(result))
    }

    @PostMapping("/joinCommunity")
    fun joinCommunity(@RequestBody request: CommunityEvent.JoinReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val resultCode = if (request.type == CommunityEvent.NEW_JOIN) {
            mCommunityService.joinCommunity(request.userId, request.newCommunityId)
        } else if (request.type == CommunityEvent.CHANGE) {
            mCommunityService.changeCommunity(request.userId, request.oldCommunityId, request.newCommunityId)
        } else {
            CommunityEvent.FAIL
        }
        val message = if (resultCode == CommunityEvent.SUCC) {
            if (request.type == CommunityEvent.NEW_JOIN) "已成功加入社区" else "已成功修改社区"
        } else {
            "操作失败"
        }
        println("result code = $resultCode")
        response.writer.write(Gson().toJson(CommunityEvent.JoinRsp(resultCode, message)))
    }

    @PostMapping("/createCommunity")
    fun createCommunity(@RequestBody request: CommunityEvent.CreateCommunityReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val generatedId = buildString {
            for (i in 0..9) append(Random.nextInt(0..9))
        }
        request.community.id = generatedId
        request.community.count = 0
        val resultCode = mCommunityService.createCommunity(request.community, request.adminId)
        val message = if (resultCode == CommunityEvent.SUCC) "已成功创建并绑定社区" else "操作失败"
        println("result code = $resultCode, msg = $message")
        response.writer.write(Gson().toJson(CommunityEvent.CreateCommunityRsp(generatedId, resultCode, message)))
    }

    @PostMapping("/adminBindCommunity")
    fun adminBindCommunity(@RequestBody request: CommunityEvent.AdminBindCommunityReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val resultCode = if (request.adminId.isNotBlank()) {
            mCommunityService.bindCommunity(request.adminId, request.newCommunityId)
        }else {
            CommunityEvent.FAIL
        }
        val message = if (resultCode == CommunityEvent.SUCC) "已成功绑定社区" else "操作失败"
        println("result code = $resultCode, msg = $message")
        response.writer.write(Gson().toJson(CommunityEvent.AdminBindCommunityRsp(resultCode, message)))
    }

}