package qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 该类提供和消息相关的服务方法
 */

public class MessageClientService
{

    public void sendMessageToOne(String content,String senderId,String getterId) throws IOException
    {
        Message message = new Message();
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSendTime(new java.util.Date().toString());
        System.out.println(senderId+" 对"+getterId + "说"+content);
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
        oos.writeObject(message);
    }
}
