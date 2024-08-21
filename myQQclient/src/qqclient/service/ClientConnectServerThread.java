package qqclient.service;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread
{
    //该线程持有socket
    private Socket socket;
    //接收一个socket对象
    public ClientConnectServerThread(Socket socket)
    {
        this.socket = socket;
    }
    public Socket getSocket()
    {
        return socket;
    }
    public void run()
    {
        //因为Thread需要在后台和服务器通讯，因此我们while循环
        while (true)
        {
            System.out.println("客户端线程，等待从读取服务器段发送的消息");
            try
            {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送Message，线程会阻塞
                Message message = (Message) ois.readObject();
                //判断这个message类型，做相应的业务处理
                if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIED))
                {
                    String onlineUsers = message.getContent();
                    System.out.println("========当前在线用户列表==========");
                    System.out.println(onlineUsers);
                }
                else
                {
                    System.out.println("其他类型message");
                }
            }
            catch (Exception e)
            {

            }
        }

    }


}
