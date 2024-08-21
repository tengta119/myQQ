package qqclient.service;


import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//完成用户登录验证和用户注册
public class UserClientService
{
    private User u = new User();//可能在其他地方使用usr信息
    //因为socket在其他地方也可能使用
    private Socket socket;

    //根据userId，pwd到服务器验证
    public boolean checkUser(String userId,String pwd) throws IOException, ClassNotFoundException
    {
        boolean b = false;
        u.setUserId(userId);
        u.setPasswd(pwd);
        //连接服务器，发送u对象
        socket = new Socket(InetAddress.getLocalHost(), 9999);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(u);//发送User

        //读取信息
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message ms = (Message) ois.readObject();
        if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED))
        {
            b = true;
            //登录成功
            //创建一个和服务器段保持通讯的线程
            ClientConnectServerThread ClientConnectServerThread = new ClientConnectServerThread(socket);
            //启动客户端线程
            ClientConnectServerThread.start();
            //为了客服端扩展，将线程添加到集合中
            ManageClientConnectServerThread.addClientConnectServerThread(u.getUserId(),ClientConnectServerThread);
        }
        else
        {
            //如果登录失败，关闭socket
            socket.close();
        }
        return b;
    }

    //向服务器段请求在线用户列表
    public void onlineFriendList() throws IOException
    {
        //发送一个Message，类型MESSAGE_GET_ONLINE_FRIED
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIED);
        message.setSender(u.getUserId());
        //发送给服务器
        //应该得到当前线程的Socket对应的ObjectOutputStream
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
        oos.writeObject(message);//发送一个message对象,向服务段要在线用户列表
    }
}
