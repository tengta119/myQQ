package qqserver;

import qqcommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 该类对应的对象和某个客户端保持t
 */


public class ServerConnectClientThread extends Thread
{
    private Socket socket;
    private String userId;//连接到服务端的用户Id

    public ServerConnectClientThread(Socket socket, String userId)
    {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run()//线程处于run状态，可以发送/接收消息
    {
        while (true)
        {

            try
            {
                System.out.println("服务端和客服端保持通讯，读取数据。。。。"+userId);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message o = (Message) ois.readObject();

            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
