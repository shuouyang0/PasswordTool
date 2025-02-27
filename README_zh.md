# 一个基于 Kotlin / Compose Mutiplatform 的密码记录工具

[English Doc](./README.md)

> 偏向于练手，目前 Android 与 Desktop 端能够成功编译运行，IOS 上因为 [KSP 的问题](https://issuetracker.google.com/issues/398414973)待解决

<figure>
<img src="./img/HomePage.png" width=200/>
<img src="./img/DetailPage.png" width=200/>
</figure>



## 使用到的依赖项有

- Coil : 图片加载  -  https://coil-kt.github.io/coil/compose/
- Ktor : Http 客户端  - https://ktor.io/docs/client-create-multiplatform-application.html#android-activity
- Koin : 控制反转 - https://insert-koin.io/docs/quickstart/cmp/
- Room : 数据库 - https://developer.android.com/kotlin/multiplatform/room?hl=zh-cn
- Paging: 分页库 - https://github.com/cashapp/multiplatform-paging
- Hash :  Hash编码库 - https://github.com/KotlinCrypto/hash/
- Serialization ： 序列化 - https://github.com/Kotlin/kotlinx.serialization
- navigation : 导航 - https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html



##  计划功能

- 添加 / 修改 / 搜索 / 删除 网站记录    ✔️
- 基于私钥生成密码的算法 ✔️
- 密码自动复制剪切板 ✔️
- 基于网站显示 icon ✔️ （可优化）
- Android / IOS 扫码添加网站 



## 存在的问题

IOS 编译始终不同过，参考该 [KSP 的问题](https://issuetracker.google.com/issues/398414973) ，等待官方解决，再做更新

