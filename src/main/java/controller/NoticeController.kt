package controller

import com.google.gson.Gson
import entity.Notice
import event.NoticeEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import service.NoticeService
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletResponse
import kotlin.random.Random
import kotlin.random.nextInt

@Controller
class NoticeController {

    @Autowired
    lateinit var mNoticeService: NoticeService

    @PostMapping("/addNotice")
    fun addNotice(@RequestBody request: NoticeEvent.AddNoticeReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val resultCode = mNoticeService.addNotice(request.notice.apply {
            id = buildString {
                for (i in 0..9) append(Random.nextInt(0..9))
            }
            date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Calendar.getInstance().time)
            if (title.isEmpty() && type == Notice.NOTICE_ALERT) {
                title = "社区管理员提醒您请及时登记健康信息"
            }
        })
        val message = if (resultCode == NoticeEvent.SUCC) "已成功发布公告" else "操作失败"
        println("result code = $resultCode, msg = $message")
        response.writer.write(Gson().toJson(NoticeEvent.AddNoticeRsp(resultCode, message)))
    }

    @PostMapping("/getNoticesList")
    fun getNoticesList(@RequestBody request: NoticeEvent.GetNoticesListReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = mNoticeService.getNoticesList(request.userId, request.communityId, request.type)
        println(result)
        response.writer.write(Gson().toJson(NoticeEvent.GetNoticesListRsp(result)))
    }

    @PostMapping("/getNoReadCount")
    fun getNoReadCount(@RequestBody request: NoticeEvent.GetNoReadCountReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = mNoticeService.getNoReadCount(request.userId, request.communityId)
        response.writer.write(Gson().toJson(NoticeEvent.GetNoReadCountRsp(result)))
    }

    @PostMapping("/setNoticeRead")
    fun setNoticeRead(@RequestBody request: NoticeEvent.SetNoticeReadReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val resultCode = mNoticeService.setNoticeRead(request.userId, request.noticeId)
        val noReadCount = mNoticeService.getNoReadCount(request.userId, request.communityId)
        val message = if (resultCode == NoticeEvent.SUCC) "已将此公告标记为已读" else "操作失败"
        val result = NoticeEvent.SetNoticeReadRsp(noReadCount, resultCode, message)
        println(result)
        response.writer.write(Gson().toJson(result))
    }


}