package Server.Content;

import java.io.IOException;
import java.io.InputStream;

/**
* 主要用于文件读取
* */
public class StreamContent extends Content{
    private final InputStream data;
    private final long lastModified;

    public StreamContent(String type, InputStream data, long lastModified) {
        this.type = type;
        this.data = data;
        this.lastModified=lastModified;
    }

    public byte[] readData() throws IOException {
        return readData(4096);
    }

    public byte[] readData(int len) throws IOException {
        return data.readNBytes(len);
    }

    public long getLastModified() {
        return lastModified;
    }

    public int getLength(){
        try {
            return data.available();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
