package service

import entity.OutSideReg
import entity.TemperReg

interface RegService {

    fun getTemperRecord(userId: String): List<TemperReg>

    fun getOutsideRecord(userId: String): List<OutSideReg>

    fun addTemperReg(record: TemperReg): Int

    fun addOutsideReg(record: OutSideReg): Int

}