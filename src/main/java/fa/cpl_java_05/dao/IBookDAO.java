package fa.cpl_java_05.dao;

import fa.cpl_java_05.model.book.BookModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:20 PM
 */
public interface IBookDAO extends GenericDAO<BookModel> {

    List<BookModel> findBookAll();
    List<BookModel> findById(int id);
    List<BookModel> findByBookTitle(String bookTitle);
    List<BookModel> findByCategory(String category);
    int save(BookModel bookModel);
    boolean update(BookModel bookModelUpdate);
    public boolean delete(int id) throws SQLException;
}
