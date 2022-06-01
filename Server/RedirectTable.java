package Server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RedirectTable {
    private static final RedirectTable table=new RedirectTable();
    private final Map<String,String> temporaryTable=new HashMap<>();
    private final Map<String,String> permanentTable=new HashMap<>();
    public static final int TEMPORARY_MOVED=1;
    public static final int PERMANENT_MOVED=2;
    public static final int NOT_MOVED=0;

    private RedirectTable(){
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("Redirect.txt")));
            String s;
            while ((s=reader.readLine())!=null){
                String[] tokens=s.split("->");
                if(tokens.length==2){
                    temporaryTable.put(tokens[0],tokens[1]);
                }else {
                    tokens=s.split("=>");
                    if(tokens.length==2){
                        permanentTable.put(tokens[0],tokens[1]);
                    }
                }
            }
            System.out.println("Redirect table loaded");
        }catch (IOException e){
            System.err.println("Failed to load Redirect.txt");
            e.printStackTrace();
        }
    }

    public static RedirectTable getTable(){
        return table;
    }

    public int getStatus(String uri){
        if(permanentTable.containsKey(uri)) return PERMANENT_MOVED;
        if (temporaryTable.containsKey(uri)) return TEMPORARY_MOVED;
        return NOT_MOVED;
    }

    public Optional<String> getRedirection(String uri){
        if(permanentTable.containsKey(uri)) return Optional.of(permanentTable.get(uri));
        if(temporaryTable.containsKey(uri)) return Optional.of(temporaryTable.get(uri));
        return Optional.empty();
    }

}
