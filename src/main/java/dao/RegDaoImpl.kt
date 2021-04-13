package dao

import entity.OutSideReg
import entity.TemperReg
import org.springframework.stereotype.Repository

@Repository("RegDaoImpl")
class RegDaoImpl: BaseDao(), RegDao {

    override fun queryTemperRecord(userId: String): List<TemperReg> {
        return query("dao.RegDaoImpl.queryTemperRecord", userId)
    }

    override fun queryOutsideRecord(userId: String): List<OutSideReg> {
        return query("dao.RegDaoImpl.queryOutsideRecord", userId)
    }

    override fun insertTemper(record: TemperReg): Int {
        return insert("dao.RegDaoImpl.insertTemper", record)
    }

    override fun insertOutside(record: OutSideReg): Int {
        return insert("dao.RegDaoImpl.insertOutside", record)
    }

    override fun queryCommunityTemperRegs(communityId: String): List<TemperReg> {
        return query("dao.RegDaoImpl.queryCommunityTemperRegs", communityId)
    }

    override fun queryCommunityOutsideRegs(communityId: String): List<OutSideReg> {
        return query("dao.RegDaoImpl.queryCommunityOutsideRegs", communityId)
    }

}