package entity

import com.google.gson.annotations.SerializedName

/**
 * 用户实体类
 */
data class User (

    @SerializedName("user_id")
    var userId: String = "",

    var password: String = "",

    @SerializedName("user_name")
    var userName: String = "",

    var cid: String = ""

)