package com.williamlu.toolslib

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File




/**
 * @Author: WilliamLu
 * @Data: 2019/4/1
 * @Description:
 */
object ShareUtils {

    private const val PACKAGE_WECHAT: String = "com.tencent.mm"
    private const val PACKAGE_MOBILE_QQ: String = "com.tencent.mobileqq"

    //分享文本
    fun shareText(context: Context, title: String, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, title))
    }

    //分享图片
    fun shareImage(context: Context, uri: Uri, title: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(intent, title))
    }

    //分享多张图片
    fun shareMoreImage(context: Context, imageUris: ArrayList<Uri>, title: String) {
        val mulIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        mulIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
        mulIntent.type = "image/jpeg"
        context.startActivity(Intent.createChooser(mulIntent, "多图文件分享"))
    }

    /**
     * 分享文本到微信好友
     *
     * @param context context
     * @param content 需要分享的文本
     */
    fun shareTextToWechatFriend(context: Context, content: String) {
        if (hasAPP(context, PACKAGE_WECHAT)) {
            val intent = Intent()
            val cop = ComponentName(PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareImgUI")
            intent.component = cop
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.putExtra("Kdescription", "shareTextToWechatFriend")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } else {
            ToastUtils.showToast("请先安装微信客户端")
        }
    }

    /**
     * 分享单张图片到微信好友
     *
     * @param context context
     * @param picFile 要分享的图片文件
     */
    fun sharePictureToWechatFriend(context: Context, picFile: File?) {
        if (hasAPP(context, PACKAGE_WECHAT)) {
            val intent = Intent()
            val cop = ComponentName(PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareImgUI")
            intent.component = cop
            intent.action = Intent.ACTION_SEND
            intent.type = "image/*"
            if (picFile != null) {
                if (picFile!!.isFile() && picFile!!.exists()) {
                    val uri = Uri.fromFile(picFile)
                    intent.putExtra(Intent.EXTRA_STREAM, uri)
                }
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(Intent.createChooser(intent, "sharePictureToWechatFriend"))
        } else {
            ToastUtils.showToast("请先安装微信客户端")
        }
    }


    /**
     * 分享单张图片到QQ好友
     *
     * @param context conrtext
     * @param picFile 要分享的图片文件
     */
    fun sharePictureToQQFriend(context: Context, picFile: File) {
        if (hasAPP(context, PACKAGE_MOBILE_QQ)) {
            val shareIntent = Intent()
            val componentName = ComponentName(PACKAGE_MOBILE_QQ, "com.tencent.mobileqq.activity.JumpActivity")
            shareIntent.component = componentName
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "image/*"
            val uri = Uri.fromFile(picFile)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            // 遍历所有支持发送图片的应用。找到需要的应用
            context.startActivity(Intent.createChooser(shareIntent, "shareImageToQQFriend"))
        } else {
            ToastUtils.showToast("请先安装QQ客户端")
        }
    }

    /**
     * 分享文本到QQ好友
     *
     * @param context context
     * @param content 文本
     */
    fun shareTextToQQFriend(context: Context, content: String) {
        if (hasAPP(context, PACKAGE_MOBILE_QQ)) {
            val intent = Intent("android.intent.action.SEND")
            intent.component = ComponentName(PACKAGE_MOBILE_QQ, "com.tencent.mobileqq.activity.JumpActivity")
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } else {
            ToastUtils.showToast("请先安装QQ客户端")
        }
    }

    /**
     * 分享单张图片到朋友圈
     *
     * @param context context
     * @param picFile 图片文件
     */
    fun sharePictureToTimeLine(context: Context, picFile: File) {
        if (hasAPP(context, PACKAGE_WECHAT)) {
            val intent = Intent()
            val comp = ComponentName(PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareToTimeLineUI")
            intent.component = comp
            intent.action = Intent.ACTION_SEND
            intent.type = "image/*"
            val uri = Uri.fromFile(picFile)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra("Kdescription", "sharePictureToTimeLine")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } else {
            ToastUtils.showToast("请先安装微信客户端")
        }
    }

    /**
     * 分享多张图片到朋友圈
     *
     * @param context context
     * @param files   图片集合
     */
    fun shareMultiplePictureToTimeLine(context: Context, files: List<File>) {
        if (hasAPP(context, PACKAGE_WECHAT)) {
            val intent = Intent()
            val comp = ComponentName(PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareToTimeLineUI")
            intent.component = comp
            intent.action = Intent.ACTION_SEND_MULTIPLE
            intent.type = "image/*"

            val imageUris = ArrayList<Uri>()
            for (f in files) {
                imageUris.add(Uri.fromFile(f))
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
            intent.putExtra("Kdescription", "shareMultiplePictureToTimeLine")
            context.startActivity(intent)
        } else {
            ToastUtils.showToast("请先安装微信客户端")
        }
    }

    fun hasAPP(context: Context, packageName: String): Boolean {
        val packageManager = context.packageManager // 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == packageName) {
                    return true
                }
            }
        }
        return false
    }

}