package qqclient;

import qqclient.service.UserClientService;
import qqclient.utils.Utility;
import java.io.IOException;

public class QQview
{
    private boolean loop = true;//是否显示菜单
    private String key = "";
    private UserClientService userClientService = new UserClientService();//用来登录服务器
    //显示主菜单
    private void mainMenu() throws IOException, ClassNotFoundException
    {
        while (loop)
        {
            System.out.println("=============欢迎登录网络通讯系统================");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 2 退出系统");
            System.out.println("请输入你的选择:");
            key = Utility.readString(1);

            //根据用户的输入，来处理不同的逻辑
            switch (key)
            {
                case "1":
                    System.out.println("请输入用户号:");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密码:");
                    String pwd = Utility.readString(50);
                    //到服务段验证
                    //UserClientService[用户登录/注册]
                    if(userClientService.checkUser(userId,pwd))
                    {
                        System.out.println("============欢迎"+userId+"===========");
                        while (loop)
                        {
                            System.out.println("============欢迎来到二级菜单"+userId+"===========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送消息");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择:");
                            key = Utility.readString(1);
                            switch (key)
                            {
                                case "1":
                                    System.out.println("显示在线用户列表");
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送消息");
                                    break;
                                case "9":
                                    //调用一个方法,给服务器发送一个退出系统的message
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    }
                    else
                    {
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        QQview my = new QQview();
        my.mainMenu();
        System.out.println("客服端退出");
    }
}
