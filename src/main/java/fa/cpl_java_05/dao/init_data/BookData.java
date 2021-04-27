package fa.cpl_java_05.dao.init_data;



import com.google.gson.*;
import fa.cpl_java_05.model.book.BookModel;
import fa.cpl_java_05.service.book.BookService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class BookData {

    private String creatPublisher(){
        int rand = new Random().nextInt(4) ;
        switch (rand){
            case 0 :
                return "NXB Tiền Phong";
            case 1:
                return "NXB Nhi Đồng";
            case 2 :
                return "NXB Thanh Niên";
            case 3:
                return "NXB Kim đồng";
        }
        return "";
    }

    private String createCate(){
        int rand = new Random().nextInt(4) ;
        switch (rand) {
            case 0 :
                return "Báo chí";
            case 1:
                return "Thiếu nhi";
            case 2:
                return "Gíao dục";
            case 3:
                return "Văn học";
        }
        return "";
    }

    public void getDataFromOtherSourceInJsonType(){
        JsonParser jsonParser = new JsonParser();
        JsonArray bookList;

        try (FileReader reader = new FileReader("data.json")) {
            Object obj = jsonParser.parse(reader);

            bookList = (JsonArray) obj;
            String name = "";
            String author = "";
            BookService bookService = new BookService();
            for(JsonElement o : bookList){
                try {
                    BookModel bookModel = new BookModel();
                    name =  ((JsonObject) o).get("name").toString();
                    bookModel.setBookTitle(name);
                    author = ((JsonObject) o).get("brand").toString();
                    if(author.equals("") || author.equals("\"0\"") || author.equals("null")){
                        author = "Nhiều tác giả";
                    }
                    bookModel.setAuthor(author);
                    bookModel.setCategory(createCate());
                    bookModel.setPublisher(creatPublisher());
                    bookModel.setDelete(false);
                    bookModel.setBrief("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
                    bookModel.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \nLorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \nIt has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \nIt was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
                    bookService.save(bookModel);
                }catch (NullPointerException ex){

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BookData().getDataFromOtherSourceInJsonType();
    }
}
