package qqserver;

import qqcommon.Message;
import qqcommon.MessageType;
import utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class SenNewsToAllService implements Runnable
{



    @Override
    public void run()
    {
        while (true)
        {
            System.out.println("请输入服务器推送的消息,\n输入exit退出推送服务");
            String news = Utility.readString(100);
            if(news.equals("exit"))
                break;
            Message message = new Message();
            message.setSender("服务器");
            message.setContent(news);
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setDest(new Date().toString());
            System.out.println("服务器推送给所有人:" + news);

            HashMap<String, ServerConnectClientThread> hm = ManagerClientThreads.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext())
            {
                String onLineUserId = iterator.next().toString();
                ServerConnectClientThread serverConnectClientThread = hm.get(onLineUserId);
                try
                {
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
