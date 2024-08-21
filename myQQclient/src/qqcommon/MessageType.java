package qqcommon;

//消息类型
public interface MessageType
{
    String MESSAGE_LOGIN_SUCCEED = "1";//登录成功
    String MESSAGE_LOGIN_FAIL = "2";//登录失败
    String MESSAGE_COMM_MES = "3";
    String MESSAGE_GET_ONLINE_FRIED = "4";//要求返回用户列表
    String MESSAGE_RET_ONLINE_FRIED = "5";//返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6";//客户端请求退出


}
