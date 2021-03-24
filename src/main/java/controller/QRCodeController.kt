package controller

import com.google.gson.Gson
import event.QRCodeEvent
import event.RegEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import service.QRCodeService
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.servlet.http.HttpServletResponse

@Controller
class QRCodeController {

    @Autowired
    lateinit var mQRCodeService: QRCodeService

    @PostMapping("/getQRContent")
    fun getQRContent(@RequestBody request: QRCodeEvent.QRContentReq, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        println(request)
        val result = QRCodeEvent.QRContentRsp(mQRCodeService.getQRContent(request.userId))
        println(result)
        response.writer.write(Gson().toJson(result))
    }

}