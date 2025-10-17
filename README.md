# SpringBoot+Vue餐厅点餐系统

#### 介绍
整合SpringBoot+Vue
本系统被划分为两个子系统平台，管理员子系统和顾客子系统。管理员子系统作为后台管理平台，主要负责菜品信息的录入与更新、订单数据的监控与统计，以及系统公告的发布和维护。管理员可以根据经营需求对菜品进行分类管理，调整库存状态，并通过订单数据分析来优化运营策略。顾客子系统面向终端用户，支持用户注册、登录及个人信息维护，同时提供菜品浏览、下单、在线客服和订单状态查询等功能

#### 软件架构
![软件功能架构](https://foruda.gitee.com/images/1760685606749392680/c612eab5_11397283.png "屏幕截图")


#### 安装教程

1. 使用Navicat或其它工具，在mysql中创建对应sql文件名称的数据库，并导入项目的sql文件；
2. 使用IDEA导入项目配置tomcat即可运行；
3. 将项目中src/main/resources/jdbc.properties配置文件中的数据库配置改为自己的配置

前端后台导入vscode后使用以下命令启动项目：
```
npm i
npm run serve
```

**后端：**
-  SpringBoot 2.X
-  Mysql 5.7
-  MybatisPlus 3.4.0

 **前端：** 
- Vue 2.6.X
- Router 3.5.1
- Axios 1.7.9
- ElementUI 2.15.14
- Echarts 5.6.0
- WebSocket

#### 界面图
![登录页面](https://foruda.gitee.com/images/1760685691557491021/55280308_11397283.png "屏幕截图")
![首页](https://foruda.gitee.com/images/1760685697778428562/41d13265_11397283.png "屏幕截图")
![美食广场](https://foruda.gitee.com/images/1760685709411843962/0f7fa7bd_11397283.png "屏幕截图")
![我的订单](https://foruda.gitee.com/images/1760685723046048671/177a4a5d_11397283.png "屏幕截图")
![在线催单](https://foruda.gitee.com/images/1760685736622334388/9b773f00_11397283.png "屏幕截图")
![后台首页](https://foruda.gitee.com/images/1760685747447609723/5f5dff1c_11397283.png "屏幕截图")
![菜品管理](https://foruda.gitee.com/images/1760685765140521887/472bd644_11397283.png "屏幕截图")
![订单管理](https://foruda.gitee.com/images/1760685776926318023/c86771a2_11397283.png "屏幕截图")

####问题咨询
 
获取完整项目请联系作者

QQ：2975612823

WX：HwttzzZ
