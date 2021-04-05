package service

import dao.RegDao
import dao.UserDao
import entity.Content
import entity.QRContent
import entity.TemperReg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import utils.HealthType
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadPoolExecutor

@Service
class QRCodeServiceImpl : QRCodeService {

    @Autowired
    lateinit var mRegDao: RegDao

    @Autowired
    lateinit var mUserDao: UserDao

    override fun getQRContent(userId: String): QRContent {
        val result = QRContent()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val temperReg = mRegDao.queryTemperRecord(userId).sortedWith { temperReg1: TemperReg, temperReg2: TemperReg ->
            val date1 = format.parse(temperReg1.date)
            val date2 = format.parse(temperReg2.date)
            if (date1.after(date2)) -1 else 1
        }.let {
            if (it.isEmpty()) TemperReg(approach = HealthType.APPROACH_NONE, diagnose = HealthType.NONE) else it[0]
        }
        result.temperature = temperReg.temper
        result.approach    = temperReg.approach
        result.diagnose    = temperReg.diagnose
        result.outside     = mutableListOf()
        mRegDao.queryOutsideRecord(userId).filter {
            val start    = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(it.startTime)
            val end      = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(it.endTime)
            val curr     = Calendar.getInstance(Locale.CHINA).time
            val monthAgo = Calendar.getInstance(Locale.CHINA).apply {
                add(Calendar.MONTH, -1)
            }.time
            monthAgo.before(start) && curr.after(start)
                || monthAgo.before(end) && curr.after(end)
                || monthAgo.after(start) && curr.before(end)
        }.forEach {
            result.outside.add(it.city)
        }
        result.content = Content().apply {
            val user = mUserDao.queryUserById(userId) ?: return@apply
            name   = user.userName.replaceRange(1 until user.userName.length, "**")
            phone  = user.phone.replaceRange(3..7, "****")
            cid    = user.cid.replaceRange(2..15, "**************")
            label  = "Wanderlust 社区疫情防控系统"
            time   = format.format(Calendar.getInstance().time)
            encode = "$label$cid$name$phone$time".computeMD5()
        }
        return result
    }

    private fun String.computeMD5(): String {
        return try {
            val bytes = MessageDigest.getInstance("MD5").let {
                it.update(toByteArray())
                it.digest()
            }
            var result = ""
            for (byte in bytes) {
                result += Integer.toHexString(0x000000ff and byte.toInt() or -0x100).substring(6)
            }
            result
        } catch (e: NoSuchAlgorithmException) {
            ""
        }
    }

}