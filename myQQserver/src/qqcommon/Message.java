package qqcommon;

import java.io.Serializable;

public class Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String sender;//发送者
    private String getter;//接收这；
    private String content;//内容
    private String sendTime;//发送时间
    private String mesType;//消息类型[可以在接口定义消息类型】
    private byte[] fileBytes;
    private int fileLen = 0;
    private String dest;//将文件传输到那
    private String src;

    public String getSrc()
    {
        return src;
    }

    public void setSrc(String src)
    {
        this.src = src;
    }

    public byte[] getFileBytes()
    {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes)
    {
        this.fileBytes = fileBytes;
    }

    public int getFileLen()
    {
        return fileLen;
    }

    public void setFileLen(int fileLen)
    {
        this.fileLen = fileLen;
    }

    public String getDest()
    {
        return dest;
    }

    public void setDest(String dest)
    {
        this.dest = dest;
    }



    public String getMesType()
    {
        return mesType;
    }

    public void setMesType(String mesType)
    {
        this.mesType = mesType;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getGetter()
    {
        return getter;
    }

    public void setGetter(String getter)
    {
        this.getter = getter;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }
}
