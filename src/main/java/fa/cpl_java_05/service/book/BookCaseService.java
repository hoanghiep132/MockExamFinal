package fa.cpl_java_05.service.book;

import fa.cpl_java_05.dao.IMPL.BookCaseDAO;
import fa.cpl_java_05.model.book.BookCaseModel;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookCaseService {
    private static final Logger LOGGER = Logger.getLogger(BookCaseService.class.getName());

    private final BookCaseDAO bookCaseDAO;

    public BookCaseService() {
        bookCaseDAO = new BookCaseDAO();
    }

    public boolean add(BookCaseModel bookCaseModel) {
        try {
            bookCaseDAO.add(bookCaseModel);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"add-false");
            return false;
        }
    }

    public boolean update(int id, String name) {
        try {
            bookCaseDAO.update(id,name);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"update - false");
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            bookCaseDAO.delete(id);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"delete-false");
            return false;
        }
    }

    public List<BookCaseModel> findBookAll(){
        try {
            return bookCaseDAO.findBookAll();
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"not-found-bookcase");
            return null;
        }
    }

    public List<BookCaseModel> findByID(int ID){
        try {
            return bookCaseDAO.findById(ID);
        } catch (Exception ex){
            LOGGER.log(Level.SEVERE,"not-found-bookcase");
            return null;
        }
    }

    public List<BookCaseModel> findByBookCaseName(String bookCaseName){
        try {
            return bookCaseDAO.findByBookCaseName(bookCaseName);
        } catch (Exception ex){
            LOGGER.log(Level.SEVERE,"not-found-bookcase");
            return null;
        }
    }


    public BookCaseModel findByUserId(int id){
        try{
            return bookCaseDAO.findByUserId(id);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"find-by-user-id err : " +ex);
            return null;
        }
    }
}
