package service

import entity.QRContent

interface QRCodeService {

    fun getQRContent(userId: String): QRContent

}