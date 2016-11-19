# [Tunnel Vision](https://github.com/WaylanPunch/Tunnel-Vision)

[Shields.io: Quality metadata badges for open source projects](http://shields.io/)

[![Awesome LeanCloud](https://img.shields.io/badge/Awesome-LeanCloud-2c97e8.svg)](http://leancloud.sexy) [![Crates.io](https://img.shields.io/crates/dv/rustc-serialize.svg?style=flat-square)](http://127.0.0.1) [![Twitter URL](https://img.shields.io/twitter/url/http/shields.io.svg?style=social?style=flat-square)](http://127.0.0.1)
[![Crates.io](https://img.shields.io/crates/l/rustc-serialize.svg?style=flat-square)](http://127.0.0.1)

---

## 1.项目简介

这是一个新闻阅读应用，新闻来源采用网易新闻API，包含头条、汽车、笑话、NBA、足球、娱乐、体育、财经、科技、电影、游戏等二十几个频道。同时包含图片浏览和天气预报功能。

---

## 2.使用的开源项目

---

- [MaterialViewPager](https://github.com/florent37/MaterialViewPager)
- [PrettyTime](https://github.com/ocpsoft/prettytime)
- [TencentBugly](http://bugly.qq.com/)
- [XRecyclerView](https://github.com/jianghejie/XRecyclerView)
- [jsoup](https://jsoup.org/)
- [greenDAO](https://github.com/greenrobot/greenDAO)
- [Glide](https://github.com/bumptech/glide)
- [Gson](https://github.com/google/gson)
- [OkHttp](https://github.com/square/okhttp)
- [Youmi广告](https://www.youmi.net/)
- [ShareSDK社会化分享](http://www.mob.com/)

### 2.1.MaterialViewPager数据存储和实时通信服务

 Material Design ViewPager easy to use library. 一个美观的Material Design风格ViewPager控件，支持Header动态背景。 
![MaterialViewPager](https://raw.githubusercontent.com/florent37/MaterialViewPager/master/screenshots/screenshot_2_small.png)

### 2.2.PrettyTime时间格式

*当你希望能够将时间格式成易于用户阅读的格式，如"12分钟前"、"2天前"、"至今3个月"等，那么你可以用 PrettyTime 来处理。*

*PrettyTime 支持多语言，可以在构造 PrettyTime 的时候传递一个 Locale 参数即可。*

### 2.3.TencentBugly异常监测

*腾讯Bugly，为移动开发者提供专业的异常上报，运营统计和内测分发解决方案，帮助开发者快速发现并解决异常，同时掌握产品运营动态，及时跟进用户反馈。专业、全面的异常监控和解决方案，可以让您及时发现应用的异常，并通

过丰富的现场信息帮您快速定位和解决问题。*

### 2.4.XRecyclerView第三方RecyclerView控件

a RecyclerView that implements pullrefresh , loadingmore and header featrues.you can use it like a standard RecyclerView. you don't need to implement a special adapter . 实现了下拉刷新

和上拉加载功能以及添加Header属性，还可以更改刷新或加载样式，可以像原生RecyclerView控件一样使用。

![XRecyclerView](https://raw.githubusercontent.com/jianghejie/XRecyclerView/master/art/demo.gif)
![XRecyclerView](https://raw.githubusercontent.com/jianghejie/XRecyclerView/master/art/ballpulse.gif)

### 2.5.jsoup Java HTML Parser

jsoup is a Java library for working with real-world HTML. It provides a very convenient API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods. 

jsoup是一个用于解析HTML文件的Java库。它提供了一系列的接口，利用文档对象模型（DOM）、层叠样式表（CSS）和类似jquery的方法抽取和操作数据。

Example：
获取Wikipedia主页，转换成DOM对象，从新闻区选取标题信息存入Elements对象中：

	Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
	Elements newsHeadlines = doc.select("#mp-itn b a");

### 2.6.greenDAO Android对象关系映射（ORM）库

greenDAO is a light & fast ORM for Android that maps objects to SQLite databases. Being highly optimized for Android, greenDAO offers great performance and consumes minimal 

memory. greenDAO是一款可以轻便快速的映射对象到SQLite数据库的Android对象关系映射（ORM）库。 greenDAO针对Android作了高度优化，有非常好的性能和极少的内存消耗。

![greenDAO-orm](http://greenrobot.org/wordpress/wp-content/uploads/greenDAO-orm-320.png)

优点：
- 应用广泛: greenDAO自2011已来已经应用在无数的APP上
- 使用简单: 简明的API和详尽的注释
- 占内存小: 整个库至多150K的大小的jar文件
- 快速存储: 可能是目前最快的Android ORM工具
- 安全查询: QueryBuilder使用常量属性避免错误输出
- 功能强大: 可以通过复杂关系查询数据
- 类型灵活: 在实体类中可以使用自定义类或者枚举表示数据
- 支持加密: 支持用SQLCipher加密数据库

### 2.7.Glide图片加载

*Glide 是一个 android 平台上的快速和高效的开源的多媒体资源管理库,提供 多媒体文件的压缩,内存和磁盘缓存, 资源池的接口。*

*它可以最大性能地在 Android 设备上读取、解码、显示图片和视频。Glide 可以将远程的图片、视频、动画图片等缓存在设备本地便于提高用户浏览图片的流畅体验。*

![greenDAO-orm](https://raw.githubusercontent.com/bumptech/glide/master/static/glide_logo.png)

Gradle：

	repositories {
  mavenCentral() // jcenter() works as well because it pulls from Maven Central
}

dependencies {
  compile 'com.github.bumptech.glide:glide:3.7.0'
  compile 'com.android.support:support-v4:19.1.0'
}

Example：

Glide.with(context)
    .load(“/user/profile/photo/path”)
    .asBitmap()
    .toBytes()
    .centerCrop()
    .into(new SimpleTarget<byte[]>(250, 250) {
        @Override
        public void onResourceReady(byte[] data, GlideAnimation anim) {
            // Post your bytes to a background thread and upload them here.
        }
    });

### 2.8.Gson Json格式转换

*GSON是Google开发的Java API，用于转换Java对象和Json对象。*

### 2.9.OkHttp Http工具类

*OkHttp是 Square 开源的 http 工具类。一款优秀的HTTP框架，它支持get请求和post请求，支持基于Http的文件上传和下载，支持加载图片，支持下载文件透明的GZIP压缩，支持响应缓存避免重复的网络请求，支持使用连接池

来降低响应延迟问题。*

### 2.10.Youmi广告

有米广告平台最大的特色在于能够为企业广告主提供精准的广告投放，让广告按广告主设定的投放目标在合适的时间到达合适地点的受众手中，并让广告在手机上变得生动有趣，引人关注。平台还提供详实的广告统计功能，广告数据清晰透明，方便广告主了解广告效果、开发者了解收入情况。

### 2.11.ShareSDK社会化分享

ShareSDK是一种社会化分享组件，为Android APP提供社会化功能，集成了一些常用的类库和接口，缩短开发者的开发时间，还有社会化统计分析管理后台。

---

## 3.项目结构

- adapter 存放适配器
- base 存放全局类
- entity 数据模型
- onekeyshare 一键分享操作类
- ui 存放activity和fragment
 - base 存放activitiy和fragment基类
 - activities
 - fragments
 - widget 自定义控件
- utils 工具类

---

## 4.APP界面

### 4.1.Splash界面

![Splash界面](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/112648564643300708.jpg)

### 4.2.初始页广告展示

![初始页广告展示](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/349239068554531780.jpg)

### 4.3.首页

![首页](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/412889527546827541.jpg)

### 4.4.图片浏览

![图片浏览](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/75238494617429956.jpg)

### 4.5.天气预报

![天气预报](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/759809882621560941.jpg)

### 4.5.收藏

![收藏](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/149819844487775666.jpg)

### 4.6.设置

![设置](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/429251707539539048.jpg)

### 4.7.分享

![分享](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/30937442612302617.jpg)

---

## 5.打包上传

将文件上传到[fir.im](http://fir.im/)内测托管平台，详细地址为[http://fir.im/lad8](http://fir.im/lad8)。

---

## 6.最后

作为第一个个人应用，都是花费下班或者周末时间完成，既是为了填充个人的空闲时间，也是为了学习新技术新框架，是自己保持不断更新的状态。但是其中肯定有许多不足之处，希望自己在将来的项目中可以有效避免。

![Sea](https://raw.githubusercontent.com/WaylanPunch/Tunnel-Vision/master/Images/initpintu.png)