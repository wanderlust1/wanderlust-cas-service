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

@Controller
class CommunityController {

    @Autowired
    lateinit var mCommunityService: CommunityService

    @PostMapping("/searchCommunity")
    fun searchCommunity(@RequestBody request: CommunityEvent.SearchReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = CommunityEvent.SearchRsp(CommunityEvent.SUCC, mCommunityService.searchCommunity(
            request.keywords, request.userId
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

}