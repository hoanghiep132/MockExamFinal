package fa.cpl_java_05.service;



import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fa.cpl_java_05.entities.book.Book;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;


public class CreateData {

    public static void main(String[] args) throws IOException {
        JsonParser parser = new JsonParser();

        Object obj =  parser.parse(new FileReader("data.json"));
        JsonArray jsonArray = (JsonArray) obj;
        for(Object o : jsonArray){
            JsonObject jsonObject = (JsonObject) o;
            System.out.println(jsonObject.get("name"));
        }

    }
}
