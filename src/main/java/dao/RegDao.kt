package dao

import entity.OutSideReg
import entity.TemperReg

interface RegDao {

    fun queryTemperRecord(userId: String): List<TemperReg>

    fun queryOutsideRecord(userId: String): List<OutSideReg>

    fun insertTemper(record: TemperReg): Int

    fun insertOutside(record: OutSideReg): Int

}