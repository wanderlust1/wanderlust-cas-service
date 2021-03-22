package service

import dao.RegDao
import entity.OutSideReg
import entity.TemperReg
import event.RegEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class RegServiceImpl: RegService {

    @Autowired
    lateinit var mRegDao: RegDao

    override fun getTemperRecord(userId: String): List<TemperReg> {
        return mRegDao.queryTemperRecord(userId).sortedWith { temperReg1: TemperReg, temperReg2: TemperReg ->
            val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.CHINA)
            val date1 = format.parse(temperReg1.date)
            val date2 = format.parse(temperReg2.date)
            if (date1.after(date2)) -1 else 1
        }.onEach {
            it.isDanger = it.temper.toFloat() > 37.4 || !it.approach.contains("未") || !it.status.contains("正常")
        }
    }

    override fun getOutsideRecord(userId: String): List<OutSideReg> {
        return mRegDao.queryOutsideRecord(userId).sortedWith { outsideReg1: OutSideReg, outsideReg2: OutSideReg ->
            val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.CHINA)
            val date1 = format.parse(outsideReg1.date)
            val date2 = format.parse(outsideReg2.date)
            if (date1.after(date2)) -1 else 1
        }
    }

    override fun addTemperReg(record: TemperReg): Int {
        return if (mRegDao.insertTemper(record) == 1) {
            RegEvent.SUCC
        } else {
            RegEvent.FAIL
        }
    }

    override fun addOutsideReg(record: OutSideReg): Int {
        return if (mRegDao.insertOutside(record) == 1) {
            RegEvent.SUCC
        } else {
            RegEvent.FAIL
        }
    }

}