# yuanbaotu

#### 介绍
元宝兔服务

#### 软件架构
软件架构说明

### 服务列表
1. gateway 网关 所有服务入口
2. auth   用户登录注册，Token 相关操作
3. admin  后台管理
4. product  首页，商品列表，商品推荐，商品分享
5. order   订单列表，订单返佣计算
6. user   会员成长，收益报表，粉丝邀请
7. wallet  佣金到账，余额提现  
8. message 消息中心，推送，短信
9. file 文件服务
10. mall-connector 电商对接 整合电商平台的api，对其余服务提供统一格式的接口，不处理业务逻辑

#### 安装教程

#### 本地安装Nacos

1. 下载nacos2.5

   https://download.nacos.io/nacos-server/nacos-server-2.5.0.zip?spm=5238cd80.6a33be36.0.0.5b841e5dWX5js9&file=nacos-server-2.5.0.zip

2. 修改nacos目录中conf/application.properties配置文件

   ```mysql
   # db mysql
   spring.datasource.platform=mysql
   db.num=1
   db.url.0=jdbc:mysql://47.96.40.74:23306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai
   db.user=nacos
   db.password=8I%+f~lM7fh2(PLB
   ```

3. 单机模式启动nacos，加载mysql中的配置

   ##### Linux/Unix/Mac

   ```shell
   # Standalone means it is non-cluster Mode.
   $ sh startup.sh -m standalone
   ```

   ##### Windows

   ```shell
   # Standalone means it is non-cluster Mode.
   $ cmd startup.cmd -m standalone
   ```

4. 访问nacos，测试是否启动成功

```
http://localhost:8848/nacos
```



#### **安装lib下的jar包**

```
mvn install:install-file   -Dfile=D:\Projects\yuanbaotu\lib\open-api-sdk-2.0-2025-02-28.jar    -DgroupId=com.jd   -DartifactId=jd-api-sdk    -Dversion=2.0    -Dpackaging=jar

```





#### 本地启动sentinel控制台(可选)

1.下载sentinel-dashboard包

2.启动

```sh
java -Dserver.port=8718 -Dcsp.sentinel.dashboard.server=localhost:8718 -Dproject.name=sentinel-dashboard -Dcsp.sentinel.api.port=8719 -jar D:\sentinel-dashboard-1.8.8.jar
```





#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
