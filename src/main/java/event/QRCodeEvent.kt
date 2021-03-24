package event

import entity.QRContent

interface QRCodeEvent {

    data class QRContentReq(val userId: String)

    data class QRContentRsp(val qrContent: QRContent)

}