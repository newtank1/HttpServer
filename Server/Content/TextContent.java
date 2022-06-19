package Server.Content;

import java.nio.charset.StandardCharsets;

/**
* 纯文本Content
* */

public class TextContent extends Content{
    String text;

    public TextContent(String text) {
        this.text = text;
    }

    @Override
    public int getLength() {
        return text.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public byte[] readData(){
        byte[] bytes=text.getBytes(StandardCharsets.UTF_8);
        byte[] ret=new byte[bytes.length];
        System.arraycopy(bytes,0,ret,0,bytes.length);
        return ret;
    }

    @Override
    public byte[] readData(int len){
        byte[] bytes=text.getBytes(StandardCharsets.UTF_8);
        byte[] ret=new byte[len];
        System.arraycopy(bytes,0,ret,0,len);
        return ret;
    }
    public String getType() {
        return "text/plain";
    }
}
