package service

import entity.Notice

interface NoticeService {

    fun addNotice(notice: Notice): Int

    fun getNoticesList(userId: String, communityId: String, type: Int): List<Notice>

    fun getNoReadCount(userId: String, communityId: String): Int

    fun setNoticeRead(userId: String, noticeId: String): Int

    fun deleteNotice(noticeId: String): Int

}