package com.williamlu.testmvvm.model

data class GankBean(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class GankDailyData(
    val Android: List<Android>,
    val App: List<App>,
    val iOS: List<IOS>,
    val 休息视频: List<休息视频>,
    val 前端: List<前端>,
    val 拓展资源: List<拓展资源>,
    val 瞎推荐: List<瞎推荐>,
    val 福利: List<福利>
)

data class Android(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class App(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class IOS(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class 休息视频(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class 前端(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class 拓展资源(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class 瞎推荐(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class 福利(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)