package qqserver;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 该类用于管理和客户端通讯的线程
 */

public class ManagerClientThreads
{
    private static HashMap<String,ServerConnectClientThread> hm = new HashMap<>();
    public static void  aadClientThread(String userId,ServerConnectClientThread serverConnectClientThread)
    {
        hm.put(userId, serverConnectClientThread);
    }
    public static ServerConnectClientThread getServerConnectClientThread(String userId)
    {
        return hm.get(userId);
    }

    public static HashMap<String, ServerConnectClientThread> getHm()
    {
        return hm;
    }

    //返回在线用户列表
    public static String getOnlineUser()
    {
        Iterator<String> iterator = hm.keySet().iterator();
        StringBuilder onlineUserList = new StringBuilder();
        while (iterator.hasNext()) {
            String UserId = iterator.next();
            System.out.println(UserId + " ManagerClientThreads");
            onlineUserList.append(UserId);
            onlineUserList.append(" ");
        }
        return onlineUserList.toString();
    }

    //从集合删除掉某个线程
    public static void removeServerConnectClientThread(String userId)
    {
        hm.remove(userId);
    }
}
