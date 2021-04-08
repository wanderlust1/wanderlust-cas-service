package dao

import entity.Notice
import org.springframework.stereotype.Repository

@Repository("NoticeDaoImpl")
class NoticeDaoImpl: BaseDao(), NoticeDao {

    override fun insertNotice(notice: Notice): Int {
        return insert("dao.NoticeDaoImpl.insertNotice", notice)
    }

    override fun queryNoticesUser(userId: String, communityId: String): List<Notice> {
        val params = mapOf("user_id" to userId, "community_id" to communityId)
        return query("dao.NoticeDaoImpl.queryNoticesUser", params)
    }

    override fun queryNoticesAdmin(communityId: String): List<Notice> {
        val params = mapOf("community_id" to communityId)
        return query("dao.NoticeDaoImpl.queryNoticesAdmin", params)
    }

    override fun queryNoReadCount(userId: String, communityId: String): Int {
        val params = mapOf("user_id" to userId, "community_id" to communityId)
        return query<Int>("dao.NoticeDaoImpl.queryNoReadCount", params)[0]
    }

    override fun insertNoticeRead(userId: String, noticeId: String): Int {
        val params = mapOf("user_id" to userId, "notice_id" to noticeId)
        return insert("dao.NoticeDaoImpl.insertNoticeRead", params)
    }

    override fun deleteNotice(noticeId: String): Int {
        val params = mapOf("notice_id" to noticeId)
        return insert("dao.NoticeDaoImpl.deleteNotice", params)
    }

}