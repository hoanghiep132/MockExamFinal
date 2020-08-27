package fa.cpl_java_05.dao.init_data;



import com.google.gson.*;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class BookData {
    public JsonArray getDataFromOtherSourceInJsonType(){
        Gson gson = new GsonBuilder().setLenient().create();
        JsonParser jsonParser = new JsonParser();
        JsonArray bookList = null;

        try (FileReader reader = new FileReader("data.json"))
        {

            Object obj = jsonParser.parse(reader);

            bookList = (JsonArray) obj;
            System.out.println(bookList);

            bookList.forEach( emp -> this.parseBookObject( (JsonObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    private void parseBookObject(JsonObject employee)
    {

        JsonObject BookObject = (JsonObject) employee.get("employee");

        String brand =  BookObject.get("brand").toString();

        String name = BookObject.get("name").toString();

    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        new BookData().getDataFromOtherSourceInJsonType();
    }
}
