package qqserver;
import qqcommon.Message;
import qqcommon.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

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

    public Socket getSocket()
    {
        return socket;
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
                Message message = (Message) ois.readObject();
                //根据message的类型，做相应的业务处理
                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIED))
                {
                    System.out.println(message.getSender()+"想要在线用户列表");
                    String onlineUser = ManagerClientThreads.getOnlineUser();
                    System.out.println(onlineUser);
                    //构建一个message，返回给客户端
                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIED);
                    message2.setContent(onlineUser);
                    message2.setGetter(message.getSender());
                    //返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                }
                else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT))
                {
                    System.out.println(message.getSender()+"退出系统");
                    ManagerClientThreads.removeServerConnectClientThread(message.getSender());
                    socket.close();
                    break;
                }
                else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES))//转发消息
                {
                    System.out.println(message.getSender() + "想对"+message.getGetter() + "说:" + message.getContent());
                    ServerConnectClientThread serverConnectClientThread = ManagerClientThreads.getServerConnectClientThread(message.getGetter());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);//转发，如果客户不在线，可以保存到数据库
                }
                else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES))
                {
                    //遍历ManagerClientThreads
                    System.out.println(message.getSender() + " 相对大家说: "+message.getContent());
                    HashMap<String, ServerConnectClientThread> hm = ManagerClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext())
                    {
                        //取出在线用户的Id
                        String onLineUserId = iterator.next().toString();
                        if(onLineUserId.equals(message.getSender()))
                            continue;
                        ObjectOutputStream oos = new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                        oos.writeObject(message);

                    }
                }
                else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES))
                {
                    ServerConnectClientThread serverConnectClientThread = ManagerClientThreads.getServerConnectClientThread(message.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                }

            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
