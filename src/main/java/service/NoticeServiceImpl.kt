package service

import dao.NoticeDao
import entity.Notice
import event.NoticeEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import utils.LoginType
import java.text.SimpleDateFormat
import java.util.*

@Service
class NoticeServiceImpl: NoticeService {

    @Autowired
    lateinit var mNoticeDao: NoticeDao

    override fun addNotice(notice: Notice): Int {
        return if (mNoticeDao.insertNotice(notice) == 1) NoticeEvent.SUCC else NoticeEvent.FAIL
    }

    override fun getNoticesList(userId: String, communityId: String, type: Int): List<Notice> {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return if (type == LoginType.USER) {
            mNoticeDao.queryNoticesUser(userId, communityId).sortedWith { notice1, notice2 ->
                if (format.parse(notice1.date).after(format.parse(notice2.date))) -1 else 1
            }
        } else if (type == LoginType.ADMIN) {
            mNoticeDao.queryNoticesAdmin(communityId).sortedWith { notice1, notice2 ->
                if (format.parse(notice1.date).after(format.parse(notice2.date))) -1 else 1
            }
        } else {
            emptyList()
        }
    }

    override fun getNoReadCount(userId: String, communityId: String): Int {
        return mNoticeDao.queryNoReadCount(userId, communityId)
    }

    override fun setNoticeRead(userId: String, noticeId: String): Int {
        return if (mNoticeDao.insertNoticeRead(userId, noticeId) == 1) NoticeEvent.SUCC else NoticeEvent.FAIL
    }

    override fun deleteNotice(noticeId: String): Int {
        return if (mNoticeDao.deleteNotice(noticeId) == 1) NoticeEvent.SUCC else NoticeEvent.FAIL
    }

}