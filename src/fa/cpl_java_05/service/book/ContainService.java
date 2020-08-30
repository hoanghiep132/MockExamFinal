package fa.cpl_java_05.service.book;

import fa.cpl_java_05.dao.IMPL.ContainDAO;
import fa.cpl_java_05.model.book.BookModel;

import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainService {

    private static final Logger LOGGER = java.util.logging.Logger.getLogger(ContainService.class.getName());

    private final ContainDAO containDAO;

    public ContainService() {
        containDAO = new ContainDAO();
    }

    public List<BookModel> findByBookCaseId(int id){
        try{
            return containDAO.findByBookCaseId(id);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"find-by-book-case-error");
            return null;
        }
    }
    public List<BookModel> search(int bookCaseId, String text) {
        try{
            return containDAO.search(bookCaseId,text);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"search-by-book-case-error");
            return null;
        }
    }

    public Boolean save(int bookCaseId, int bookId, Date date){
        try{
            return containDAO.save(bookCaseId,bookId,date) == 0 ? true : false;
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"save-error " + ex);
            return null;
        }
    }

    public boolean deleted(int book_case_id, int book_id){
        try{
            return containDAO.deleted(book_case_id,book_id) > 0 ? true : false;
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"delete-error");
            return false;
        }
    }

    public Boolean changeDeleted(int bookCaseId, int bookId){
        try{
            return containDAO.changeDeleted(bookCaseId,bookId) > 0 ? true : false;
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"restore-book-error : " + ex);
            return null;
        }
    }

    public Boolean findContainDeleted(int bookCaseId, int bookId){
        try{
            return containDAO.findContainDeleted(bookCaseId,bookId);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"find-contain-deleted-error : " + ex);
            return null;
        }
    }
}
