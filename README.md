# project-interface
毕设项目中的接口项目

其中人脸识别的调用在utils.java中，由于face_recognition库打包时一直提示我找不到该模块，所以若想使用该功能请自行创建python项目，导入opencv、dlib、face_recognition，
所调用的python脚本文件内容地址：https://blog.csdn.net/qq_41563868/article/details/117447552

另外数据库使用的是MySQL，代码地址：https://blog.csdn.net/qq_41563868/article/details/117787600

在后台搭建完成之后，请修改Android中com.example.project_android.utils包下的ProjectStatic类中的SERVICE_PATH的值为服务端的ip。
