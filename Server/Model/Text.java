package Server.Model;

public class Text extends Content{
    public String type;
    public byte[] data;

    public Text(String type, byte[] data) {
        super(type, data);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getData() {
        return new String(data);
    }

    @Override
    public int getLength() {
        return data.length;
    }
}
