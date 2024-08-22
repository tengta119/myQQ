package qqclient.service;
import qqcommon.Message;
import qqcommon.MessageType;
import java.io.*;

/**
 * 文件传输
 */


public class FileClientService
{
    /**
     *
     * @param src 源文件
     * @param dest  目标文件
     * @param senderId  发送者
     * @param getterId  接收者
     */
    public void sendFileToOne(String src,String dest,String senderId,String getterId) throws IOException
    {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);
        FileInputStream fileInputStream = null;
        byte[] file = new byte[(int)new File(src).length()];
        fileInputStream = new FileInputStream(src);
        fileInputStream.read(file);
        message.setFileBytes(file);
        if(fileInputStream!=null)
                fileInputStream.close();
        System.out.println(senderId + " 给 "+getterId + " 发送了 "+ src + " 到 " + dest);
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
        oos.writeObject(message);
    }
}
