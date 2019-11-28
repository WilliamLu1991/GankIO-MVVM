package com.williamlu.toolslib

/**
 * @Author: WilliamLu
 * @Date: 2019/11/28
 * @Description:
 */
object EmojiUtil {
    /**
     * 是否包含表情
     *
     * @return
     */
    fun containsEmoji(source: String): Boolean {
        val len = source.length
        for (i in 0 until len) {
            val codePoint = source[i]
            if (!isNotEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true
            }
        }
        return false
    }

    private fun isNotEmojiCharacter(codePoint: Char): Boolean {
        return (codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD
                || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF || codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD
                || codePoint.toInt() >= 0x10000 && codePoint.toInt() <= 0x10FFFF)
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    fun filterEmoji(source: String): String? {
        if (!containsEmoji(source)) {
            return source
        }
        val buf = StringBuilder(source.length)
        val len = source.length
        for (i in 0 until len) {
            var codePoint = source[i]
            if (isNotEmojiCharacter(codePoint)) {
                buf.append(codePoint)
            } else { //过滤并替换
                buf.append("")
            }
        }
        return buf.toString()
    }
}