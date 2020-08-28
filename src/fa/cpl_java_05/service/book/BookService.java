package fa.cpl_java_05.service.book;

import fa.cpl_java_05.dao.IMPL.BookDAO;
import fa.cpl_java_05.model.book.BookModel;



import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BookService {

    private static final Logger LOGGER = java.util.logging.Logger.getLogger(BookService.class.getName());

    private final BookDAO bookDAO;

    public BookService() {
        bookDAO = new BookDAO();
    }

    public List<BookModel> findBookAll() {
        try {
            return bookDAO.findBookAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"find-all-book-error : " + ex);
            return null;
        }
    }

    public List<BookModel> findBookAllDeleted() {
        try {
            return bookDAO.findBookAllDeleted();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"find-all-book-error : " + ex);
            return null;
        }
    }

    public BookModel findById(int id){
        try{
            return bookDAO.findById(id).get(0);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"find-by-id-book-error : " + ex);
            return null;
        }
    }

    public List<BookModel> search(String text){
        try{
            return bookDAO.search(text);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"search-book-error : " + ex);
            return null;
        }
    }

    public List<BookModel> searchBookDeleted(String text){
        try{
            return bookDAO.searchBookDeleted(text);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"search-book-error : " + ex);
            return null;
        }
    }

    public boolean save(BookModel bookModel){
        try{
            bookDAO.save(bookModel);
            return true;
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"save-book-error : " + ex);
            return false;
        }
    }

    public boolean update(BookModel bookModel){
        try{
            return bookDAO.update(bookModel);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"update-book-error : " + ex);
            return false;
        }
    }

    public boolean deleted(int id){
        try{
            return bookDAO.delete(id);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,"delete-book-error : " + ex);
            return false;
        }
    }
}
