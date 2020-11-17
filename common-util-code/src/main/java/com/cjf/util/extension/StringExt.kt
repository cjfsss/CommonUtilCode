package com.cjf.util.extension

import android.text.TextUtils
import android.util.Base64
import com.blankj.utilcode.util.EncryptUtils
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
    if (TextUtils.isEmpty(toNULL())) {
        return "0"
    }
    return StringUtils.toStringZero(this)
}

fun String?.toLongZero(): Long {
    if (TextUtils.isEmpty(toNULL())) {
        return 0L
    }
    return this!!.toLong()
}

fun String?.toIntZero(): Int {
    if (TextUtils.isEmpty(toNULL())) {
        return 0
    }
    return this!!.toInt()
}

fun String?.toFloatZero(): Float {
    if (TextUtils.isEmpty(toNULL())) {
        return 0f
    }
    return this!!.toFloat()
}

fun String?.toDoubleZero(): Double {
    if (TextUtils.isEmpty(toNULL())) {
        return 0.0
    }
    return this!!.toDouble()
}

fun Int?.toIntZero(): Int {
    if (this == null) {
        return 0
    }
    return this
}

fun Long?.toLongZero(): Long {
    if (this == null) {
        return 0L
    }
    return this
}

fun String?.trimNR(): String {
    if (TextUtils.isEmpty(toNULL())) {
        return ""
    }
    return this?.replace("\n", "")?.replace("\r", "")?.trim().toString()
}

fun String?.noData(): String {
    if (TextUtils.isEmpty(toNULL())) {
        return "暂无"
    }
    return this.toString()
}

fun String?.toTime(): String {
    if (TextUtils.isEmpty(toNULL())) {
        return "暂无"
    }
    return StringUtils.toTime(this)
}

fun String?.toTimeN(): String {
    if (TextUtils.isEmpty(toNULL())) {
        return "暂无"
    }
    return StringUtils.toTimeN(this)
}

fun String?.toNULL(): String? {
    if (TextUtils.isEmpty(this) || TextUtils.equals(this, "null")
            || TextUtils.equals(this, "NULL")
    ) {
        return null
    }
    return this.toString()
}

fun String?.empty(): String {
    if (TextUtils.isEmpty(this) || TextUtils.equals(this, "null")
            || TextUtils.equals(this, "NULL")
    ) {
        return ""
    }
    return this.toString()
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

