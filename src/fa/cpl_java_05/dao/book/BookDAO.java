package fa.cpl_java_05.dao.book;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 

public class BookDAO {
    public JSONArray getDataFromOtherSourceInJsonType(){
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("path_to_json_file"))
        {
           
            Object obj = jsonParser.parse(reader);
 
            JSONArray bookList = (JSONArray) obj;
            System.out.println(bookList);
             
            bookList.forEach( emp -> this.parseBookObject( (JSONObject) emp ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bookList;
    }
    }

    private void parseBookObject(JSONObject employee) 
    {
       
        JSONObject BookObject = (JSONObject) employee.get("employee");
         
        String id = (String) BookObject.get("_id");    
         
        String brand = (String) BookObject.get("brand");  
         
        String name = (String) BookObject.get("name");    
       
        long price = (long) BookObject.get("price");    
        long price_max = (long) BookObject.get("price_max"); 
        long price_min = (long) BookObject.get("price_min");    
        
        String shop_location = (String) BookObject.get("shop_location");  
        String currency = (String) BookObject.get("currency");  
        String image = (String) BookObject.get("image");  
    }
}
