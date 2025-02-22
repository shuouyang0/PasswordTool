package org.shu.tool.password.base.module

import com.ctrip.sqllin.dsl.annotation.DBRow
import io.ktor.http.Url
import kotlinx.serialization.Serializable
import org.shu.tool.password.util.isEmail
import org.shu.tool.password.util.isPhoneNumber
import org.shu.tool.password.util.maskEmail
import org.shu.tool.password.util.maskPhoneNumber

//records
@DBRow("password_record")
@Serializable
data class PasswordRecord(
    val id:Long? = null,
    /**
     * 网站链接 必须
     */
    val websiteLink:String,
    /**
     * 注册账号 必须
     */
    val account:String,
    /**
     * 账号类型 必须
     * - 0 - 邮箱
     * - 1 - 电话号码
     */
    val accountType:Int = 0,
    /**
     * 密码类型 必须
     * - 1 - 简单类型 ：数字，大写字母，小写字母 组合
     * - 0 - 加强类型 ：数字，大写字母，小写字母，特殊字符组合
     */
    val passwordType:Int,
    /**
     * 私钥md5 必须
     */
    val cipher:String,
    /**
     * 注册日期 必须
     */
    val registerDate:Long,
    /**
     * 该条记录的昵称 非必须
     */
    val nickname:String = "",
    /**
     * 用户名 非必须
     */
    val username:String = "",
    /**
     * 修改日期 非必须
     */
    val modifyDate:Long = -1,

    /**
     * 备注
     */
    val remark:String = ""
){
    companion object{
        const val ACCOUNT_TYPE_EMAIL = 0
        const val ACCOUNT_TYPE_PHONE = 1

        const val PASSWORD_TYPE_STRONG = 0
        const val PASSWORD_TYPE_SIMPLE = 1
    }
    fun obtainIconLink():String{
        return "$websiteLink/favicon.ico"
    }

    fun obtainAccountType():String{
        return when(accountType){
            ACCOUNT_TYPE_PHONE -> "电话"
            ACCOUNT_TYPE_EMAIL -> "邮箱"
            else -> ""
        }
    }
    fun obtainMaskAccount():String{
        return if (account.isPhoneNumber()){
            account.maskPhoneNumber()
        }else if (account.isEmail()){
            account.maskEmail()
        }else{
            account
        }
    }
    fun obtainWebsiteUrl():Url{
        return Url(websiteLink)
    }
}
