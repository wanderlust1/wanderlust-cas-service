package utils

object CheckUtils {

    private const val REG_MOBILE = "^[1][3,4,5,7,8][0-9]{9}$"
    private const val REG_PHONE1 = "^[0][1-9]{2,3}-[0-9]{5,10}$"
    private const val REG_PHONE2 = "^[1-9]{1}[0-9]{5,8}$"
    private const val REG_CID =
        "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)"
    private const val REG_NAME = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*"

    fun String.checkPhone(): Boolean {
        return isNotBlank() && (matches(Regex(REG_MOBILE)) || if (length > 9) {
            matches(Regex(REG_PHONE1))
        } else {
            matches(Regex(REG_PHONE2))
        })
    }

    fun String.checkCID(): Boolean {
        val matches = isNotBlank() && matches(Regex(REG_CID))
        if (matches && length == 18) {
            return try {
                val charArray = toCharArray()
                val idCardWi = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
                val idCardY = arrayOf("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2")
                var sum = 0
                for (i in idCardWi.indices) {
                    val current = charArray[i].toString().toInt()
                    val count = current * idCardWi[i]
                    sum += count
                }
                val idCardLast = charArray[17]
                val idCardMod = sum % 11
                idCardY[idCardMod].equals(idCardLast.toString(), true)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        return matches
    }

    fun String.checkUsername(): Boolean {
        return isNotBlank() && matches(Regex(REG_NAME))
    }

}