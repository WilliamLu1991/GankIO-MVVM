package com.williamlu.testmvvm

import com.williamlu.toolslib.SpUtils
import com.williamlu.testmvvm.api.BaseIApi
import com.williamlu.testmvvm.api.RetrofitHelper

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description:
 */
object AppConstant {
    private val mSpUtils = SpUtils.getInstance(AppConstant.SpConstant.CONFIG_INFO)

    fun getBaseUrl(): String {
        return mSpUtils.getString(AppConstant.SpConstant.CONFIG_BASEURL)
    }

    fun getBaseApiService(): BaseIApi {
        return RetrofitHelper.getApiService(AppConstant.getBaseUrl(), BaseIApi::class.java)
    }


    object UrlConstant {
        const val URL_BASE_PRODUCT = "http://gank.io/api/"
        const val URL_BASE_TEST = "http://gank.io/api/"
    }

    object ConfigConstant {
        const val BUGLY_APPID = ""
        const val TINKER_NAME = ""
        const val UMENG_APPKEY = ""

        //融云
        const val RONGYUN_KEY_PRODUCT = ""
        const val RONGYUN_KEY_TEST = ""

        //微信、QQ、微博相关设置
        const val WEIXIN_APPID = ""
        const val WEIXIN_APPSECRET = ""
        const val QQ_APPID = ""
        const val QQ_APPSECRET = ""
        const val WEIBO_APPID = ""
        const val WEIBO_APPSECRET = ""
        const val WEIBO_SHARE_URL = ""

        //翻页数量
        const val SERVICE_PAGE_SIZE = "10"
        //验证码长度
        const val SECURITY_CODE_LENGTH = 6
        //获取验证码间隔时长
        const val GET_SECURITY_CODE_TIME = 60
        //手机号长度
        const val PHONE_LENGTH = 11
        //密码最小长度
        const val PASSWORD_MIN_LENGTH = 6
        //身份证号码长度
        const val ID_CARD_LENGTH = 18
        //客服电话
        const val CUSTOMER_SERVICE_TELEPHONE = ""
        //上传图片最大数量
        const val UPLOAD_PIC_MAX_NUM = 5
        //昵称最少字符
        const val PETNAME_MIN_LENGTH = 2
        //金钱种类
        const val MONEY_TYPE = "¥ "
    }

    object ErrorCodeConstant {
        const val ERROR_CODE_401 = 401  //token失效
    }

    object ToastConstant {
        const val EXIT_APP = "再次点击将退出应用"
        const val NO_INPUT_PHONE = "您还未填写手机号码哦～"
        const val ERROR_INPUT_PHONE = "您填写的手机号码不准确哦~"
        const val ERROR_GET_USER_PHONE = "获取用户手机号码失败"
        const val NO_SELECTED_AGREEMENT = "您还未签署《平台服务与协议》哦~"
        const val NO_INPUT_SECCODE = "您还未填写验证码哦～"
        const val NO_INPUT_PWD = "您还未填写密码哦～"
        const val NO_INPUT_NAME = "您还未填写姓名哦～"
        const val NO_INPUT_PETNAME = "您还未填写昵称哦～"
        const val NO_INPUT_ID_CARD = "您还未填写身份证号哦～"
        const val ERROR_INPUT_ID_CARD = "您填写的身份证号不准确哦～"
        const val NO_INPUT_BANK_NUMBER = "您还未填写银行卡号哦～"
        const val NO_INPUT_BANK_NAME = "银行名称不准确，请您填写正确名称"
        const val ERROR_INPUT_PWD = "请填写6-16位的密码哦~"
        const val SUCCESS_LOGIN = "登录成功"
        const val SUCCESS_SEND = "发送成功"
        const val SUCCESS_GET = "获取成功"
        const val SUCCESS_UPLOAD = "上传成功"
        const val SUCCESS_REGISTER = "注册成功"
        const val SUCCESS_SET = "设置成功"
        const val SUCCESS_CONFIRM = "验证成功"
        const val SUCCESS_CHANGE = "修改成功"
        const val SUCCESS_PUT = "提交成功"
        const val SUCCESS_SAVE = "保存成功"
        const val NO_INPUT_NEW_PWD = "您还未填写新密码哦～"
        const val NO_INPUT_OLD_PWD = "您还未填写原登录密码哦～"
        const val NO_INPUT_SUGGEST = "您还未填写任何意见哦～"
        const val NO_EMPTY = "输入地址不能为空哦~"
        const val ERROR_REQUEST = "请求失败"
        const val ERROR_INPUT_TWO_PWD = "您两次输入的密码不一致哦~"
        const val ERROR_UPLOAD = "上传失败"
        const val ERROR_LOGIN_INVALID = "登录失效，请重新登录"
        const val SUCCESS_BIND = "绑定成功"
        const val ALREADY_BIND_WEIXIN = "您已绑定过微信"
        const val ALREADY_BIND_QQ = "您已绑定过QQ"
        const val ERROR_UPLOAD_PIC_MAX_NUM = "最多只能上传3张图片哦~"
        const val ERROR_PETNAME_MIN_LENGTH = "昵称最少要两个字符哦~"
        const val ERROR_VER = "请确认验证码是否正确~"
        const val ERROR_WX_INFO = "信息获取失败，请稍后再试~"
    }

    object DialogConstant {
        const val LOGIN_LOADING = "登录中..."
        const val LOADING = "加载中..."
    }

    object SpConstant {
        const val CONFIG_INFO = "CONFIG_INFO"
        const val CONFIG_BASEURL = "config_baseurl"
        const val CONFIG_URL_H5 = "config_url_h5"

        const val USER_INFO = "USER_INFO"
        const val USER_SEARCH_HISTORY = "user_search_history"
        const val USER_IS_LOGIN = "user_is_login"
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
        const val USER_PASSWORD = "user_password"
        const val USER_ID = "user_id"
        const val USER_HEAD_PIC_URL = "user_head_pic_url"
        const val USER_PETNAME = "user_petname"
        const val USER_PHONE = "user_phone"
        const val USER_IS_FIRST_START_APP = "user_is_first_start_app"
    }

}