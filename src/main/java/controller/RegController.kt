package controller

import com.google.gson.Gson
import event.RegEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import service.RegService
import javax.servlet.http.HttpServletResponse

@Controller
class RegController {

    @Autowired
    lateinit var mRegService: RegService

    @PostMapping("/getTemperRecord")
    fun getTemperRecord(@RequestBody request: RegEvent.GetTemperRecordReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = RegEvent.GetTemperRecordRsp(mRegService.getTemperRecord(request.userId))
        println(result)
        response.writer.write(Gson().toJson(result))
    }

    @PostMapping("/getOutsideRecord")
    fun getOutsideRecord(@RequestBody request: RegEvent.GetOutSideRecordReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = RegEvent.GetOutSideRecordRsp(mRegService.getOutsideRecord(request.userId))
        println(result)
        response.writer.write(Gson().toJson(result))
    }

    @PostMapping("/addTemperReg")
    fun addTemperReg(@RequestBody request: RegEvent.AddTemperRecordReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = when (mRegService.addTemperReg(request.record)) {
            RegEvent.SUCC ->
                RegEvent.AddTemperRecordRsp(RegEvent.SUCC, "体温记录已提交")
            RegEvent.FAIL ->
                RegEvent.AddTemperRecordRsp(RegEvent.FAIL, "提交失败，请重试")
            else ->
                RegEvent.AddTemperRecordRsp(RegEvent.FAIL, "提交失败，请重试")
        }
        println(result)
        response.writer.write(Gson().toJson(result))
    }

    @PostMapping("/addOutsideReg")
    fun addOutsideReg(@RequestBody request: RegEvent.AddOutsideRecordReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = when (mRegService.addOutsideReg(request.record)) {
            RegEvent.SUCC ->
                RegEvent.AddOutsideRecordRsp(RegEvent.SUCC, "外出记录已提交")
            RegEvent.FAIL ->
                RegEvent.AddOutsideRecordRsp(RegEvent.FAIL, "提交失败，请重试")
            else ->
                RegEvent.AddOutsideRecordRsp(RegEvent.FAIL, "提交失败，请重试")
        }
        println(result)
        response.writer.write(Gson().toJson(result))
    }

}