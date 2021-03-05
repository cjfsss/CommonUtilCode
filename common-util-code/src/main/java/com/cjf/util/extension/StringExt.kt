package com.cjf.util.extension

import android.util.Base64
import com.blankj.utilcode.util.EncryptUtils
import com.cjf.util.utils.IntegerUtils
import com.cjf.util.utils.LongUtils
import com.cjf.util.utils.StringUtils
import java.io.File

/**
 * <p>Title: String </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/3 13:56
 * @version : 1.0
 */

fun String?.toStringZero(): String {
    return StringUtils.toStringZero(this)
}

fun String?.toLongZero(): Long {
    return StringUtils.toLongZero(this)
}

fun String?.toIntZero(): Int {
    return StringUtils.toIntZero(this)
}

fun String?.toFloatZero(): Float {
    return StringUtils.toFloatZero(this)
}

fun String?.toDoubleZero(): Double {
    return StringUtils.toDoubleZero(this)
}

fun Int?.toIntZero(): Int {
    return IntegerUtils.toIntZero(this)
}

fun Long?.toLongZero(): Long {
    return LongUtils.toLongZero(this)
}

fun String?.trimNR(): String {
//    if (TextUtils.isEmpty(toNULL())) {
//        return ""
//    }
//    return this?.replace("\n", "")?.replace("\r", "")?.trim().toString()
    return StringUtils.trimNR(this)
}

fun String?.noData(): String {
    return StringUtils.noData(this)
}

fun String?.noDataTime(): String {
    return StringUtils.noDataTime(this)
}

fun String?.noDataTimeN(): String {
    return StringUtils.noDataTimeN(this)
}

fun String?.toTime(): String {
    return StringUtils.toTime(this)
}

fun String?.toTimeN(): String {
    return StringUtils.toTimeN(this)
}

fun String?.toNULL(): String? {
    return StringUtils.toNULL(this)
}

fun String?.empty(): String {
    return StringUtils.toEmpty(this)
}

fun String?.deleteEndZero(): String {
    var str = empty()
    if (str.indexOf(".") > 0) {
        // 去掉多余的0
        str = str.replace(Regex("0+?\$"), "")
        // 如最后一位是.则去掉
        str = str.replace(Regex("[.]\$"), "")
    }
    return str
}

/**
 * 对字符串进行编码
 *
 * @param target 目标
 * @return
 */
fun String?.toBase64Encoder(): String {
    return StringUtils.toBase64Encoder(this)
}

/**
 * 对字符串进行解密
 *
 * @param target 目标值
 * @return
 */
fun String?.toBase64Decoder(): String {
    return StringUtils.toBase64Decoder(this)
}

/**
 * 对字符串进行编码
 *
 * @param target 目标
 * @return
 */
fun String?.toBase64UrlEncoder(): String {
    return StringUtils.toBase64UrlEncoder(this)
}

/**
 * 对字符串进行解密
 *
 * @param target 目标值
 * @return
 */
fun String?.toUrlBase64Decoder(): String {
    return StringUtils.toUrlBase64Decoder(this)
}

fun File.md5() = EncryptUtils.encryptMD5File2String(this)

fun String.md5File() = EncryptUtils.encryptMD5File2String(this)

fun String.md5() = EncryptUtils.encryptMD5ToString(this)

fun String.sha1() = EncryptUtils.encryptSHA1ToString(this)

fun String.sha256() = EncryptUtils.encryptSHA256ToString(this)

fun String.sha512() = EncryptUtils.encryptSHA512ToString(this)

/**
 * 随机数增强的md5算法
 * @param salt 加盐的值
 */
fun String.md5Hmac(salt: String) = EncryptUtils.encryptHmacMD5ToString(this, salt)

fun String.sha1Hmac(salt: String) = EncryptUtils.encryptHmacSHA1ToString(this, salt)

fun String.sha256Hmac(salt: String) = EncryptUtils.encryptHmacSHA256ToString(this, salt)

/**
 * DES对称加密
 * @param key 长度必须是8位
 */
fun String.encryptDES(key: String) =
        Base64.encodeToString(
                EncryptUtils.encryptDES(this.toByteArray(), key.toByteArray(), "DES/CBC/PKCS5Padding", null),
                Base64.NO_WRAP
        )


/**
 * DES对称解密
 * @param key 长度必须是8位
 */
fun String.decryptDES(key: String) = String(EncryptUtils.decryptDES(Base64.decode(this, Base64.NO_WRAP), key.toByteArray(), "DES/CBC/PKCS5Padding", null))

/**
 * AES对称加密
 * @param key 长度必须是16位
 */
fun String.encryptAES(key: String) = Base64.encodeToString(
        EncryptUtils.encryptAES(this.toByteArray(), key.toByteArray(), "AES/ECB/PKCS5Padding", null),
        Base64.NO_WRAP
)

/**
 * AES对称解密
 * @param key 长度必须是16位
 */
fun String.decryptAES(key: String) = String(EncryptUtils.decryptAES(Base64.decode(this, Base64.NO_WRAP), key.toByteArray(), "AES/ECB/PKCS5Padding", null))

