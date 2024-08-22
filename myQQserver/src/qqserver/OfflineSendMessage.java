package qqserver;
import qqcommon.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Stack;

public class OfflineSendMessage
{
    private static HashMap<String, Stack<Message>> hm = new HashMap<>();

    public  HashMap<String, Stack<Message>> getHm()
    {
        return hm;
    }

    public void setHm(HashMap<String, Stack<Message>> hm)
    {
        this.hm = hm;
    }

    public static void Addhm(String getter,Message message)
    {
        if(!hm.containsKey(getter))
        {
            hm.put(getter,new Stack<>());
        }
        hm.get(getter).push(message);
    }

    public static void sendMessageOffine(String getter) throws IOException
    {
        ServerConnectClientThread serverConnectClientThread = ManagerClientThreads.getServerConnectClientThread(getter);
        if(serverConnectClientThread==null)
        {
            System.out.println("没有离线信息可以发送");
            return;
        }
        ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
        Stack<Message> messages = hm.get(getter);
        if(messages == null)
            return;
        while (!messages.empty())
        {
            Message message = messages.pop();
            String content = message.getContent();
            content += " 离线消息";
            message.setContent(content);
            oos.writeObject(message);
        }
        return;
    }
}
