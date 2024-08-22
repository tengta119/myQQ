package qqserver;
import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 这是服务端，监听9999，等待客户端连接
 */
public class QQServer
{
    private ServerSocket ss = null;
    //创建一个集合，存放多个用户，如果这些用户登录，就任务合法
    private static HashMap<String,User> validUsers = new HashMap<>();

    static
    {
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("300",new User("300","123456"));
        validUsers.put("A",new User("A","123456"));
    }

    //验证用户是否有效
    private boolean checkUser(String userId,String passwd)
    {
        User user = validUsers.get(userId);
        if(user==null)
        {
            //不存在
            return false;
        }
        if(user.getPasswd().equals(passwd))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public QQServer() throws IOException, ClassNotFoundException
    {
        //端口可以写在配置文件
        System.out.println("服务器在9999端口监听");
        new Thread(new SenNewsToAllService()).start();
        ss = new ServerSocket(9999);
        while (true)
        {
            //当和某个客户端连接后，会继续监听
            Socket socket = ss.accept();//如果没有客户端连接，就会阻塞在这里
            //得到socket关联的对象输入流输出liu
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            User u = (User) ois.readObject();//读取
            Message message = new Message();
            if(checkUser(u.getUserId(),u.getPasswd()))
            {
                message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                oos.writeObject(message);
                //创建一个线程和客户端保持通讯，该线程需要持有socket
                ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserId());
                serverConnectClientThread.start();
                //将线程看作一个对象放入到一个集合中，进行管
                ManagerClientThreads.aadClientThread(u.getUserId(),serverConnectClientThread);
            }
            else
            {
                System.out.println("用户"+u.getUserId()+"登录失败");
                message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                oos.writeObject(message);
                socket.close();
            }
        }


    }

}
