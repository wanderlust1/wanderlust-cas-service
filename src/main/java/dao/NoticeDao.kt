package dao

import entity.Notice

interface NoticeDao {

    fun insertNotice(notice: Notice): Int

    fun queryNoticesUser(userId: String, communityId: String): List<Notice>

    fun queryNoticesAdmin(communityId: String): List<Notice>

    fun queryNoReadCount(userId: String, communityId: String): Int

    fun insertNoticeRead(userId: String, noticeId: String): Int

    fun deleteNotice(noticeId: String): Int

}