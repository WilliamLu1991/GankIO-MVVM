package com.williamlu.toolslib

import java.security.MessageDigest


/**
 * @Author: WilliamLu
 * @Date: 2019/8/12
 * @Description:
 */
object MD5Utils {
    /**
     * Title: MD5加密 生成32位md5码
     * @param inStr
     * @return 返回32位md5码
     * @throws Exception
     */
    @Throws(Exception::class)
    fun md5Encode(inStr: String): String {
        var md5: MessageDigest? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
        } catch (e: Exception) {
            println(e.toString())
            e.printStackTrace()
            return ""
        }

        val byteArray = inStr.toByteArray(charset("UTF-8"))
        val md5Bytes = md5!!.digest(byteArray)
        val hexValue = StringBuffer()
        for (i in md5Bytes.indices) {
            val v = md5Bytes[i].toInt() and 0xff
            if (v < 16) {
                hexValue.append("0")
            }
            hexValue.append(Integer.toHexString(v))
        }
        return hexValue.toString()
    }

}