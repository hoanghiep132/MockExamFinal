package fa.cpl_java_05.service.book;

import fa.cpl_java_05.dao.IMPL.BookDAO;
import fa.cpl_java_05.model.book.BookModel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    private BookDAO bookDAO;

    public BookService() {
        bookDAO = new BookDAO();
    }

    public List<BookModel> findBookAll() {
        try {
            return bookDAO.findBookAll();
        } catch (Exception ex) {
            LOGGER.error("find-all-book-error : " + ex);
            return null;
        }
    }

    public BookModel findById(int id){
        try{
            return bookDAO.findById(id).get(0);
        }catch (Exception ex){
            return null;
        }
    }

    public List<BookModel> search(String text){
        try{
            return bookDAO.search(text);
        }catch (Exception ex){
            return null;
        }
    }

    public boolean save(BookModel bookModel){
        try{
            bookDAO.save(bookModel);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean update(BookModel bookModel){
        try{
            return bookDAO.update(bookModel);
        }catch (Exception ex){
            return false;
        }
    }

    public boolean deleted(int id){
        try{
            return bookDAO.delete(id);
        }catch(Exception ex){
            return false;
        }
    }
}
