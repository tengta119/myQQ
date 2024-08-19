package qqclient.service;

import java.util.HashMap;

public class ManageClientConnectServerThread
{
    //我们把多个线程放入一个HashMap集合，key就是用户id，value线程
    private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();

    //将某个线程加入到线程
    public static void addClientConnectServerThread(String userId,ClientConnectServerThread clientConnectServerThread)
    {
        hm.put(userId, clientConnectServerThread);
    }
    //通过一个方法userId，得到对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId)
    {
        return hm.get(userId);
    }
}
