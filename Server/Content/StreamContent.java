package Server.Content;

import java.io.IOException;
import java.io.InputStream;

public class StreamContent extends Content{
    public InputStream data;

    public StreamContent(String type, InputStream data) {
        this.type = type;
        this.data = data;
    }

    public byte[] readData() throws IOException {
        return readData(4096);
    }

    public byte[] readData(int len) throws IOException {
        return data.readNBytes(len);
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
