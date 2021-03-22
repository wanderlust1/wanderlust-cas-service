package entity

import com.google.gson.annotations.SerializedName

/**
 * 用户实体类
 */
data class User (

    @SerializedName("user_id")
    var userId: String = "", //用户id

    var password: String = "", //密码

    @SerializedName("user_name")
    var userName: String = "", //真实姓名

    var cid: String = "", //身份证号

    var phone: String = "", //手机号

    var communityId: String = "", //所在社区

    var communityName: String = ""

)