package Server.Content;

import java.io.IOException;

public abstract class Content {
    public String type;

    public String getType() {
        return type;
    }

    public abstract int getLength();

    public abstract byte[] readData() throws IOException;

    public abstract byte[] readData(int len) throws IOException;
}
