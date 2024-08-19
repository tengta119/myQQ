package qqserver;

import java.util.HashMap;

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
}
