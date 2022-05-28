package Server.Model;

public class Content {
    public String type;
    public byte[] data;

    public Content(String type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }


    public String getData() {
        return new String(data);
    }


    public int getLength() {
        return data.length;
    }
}
