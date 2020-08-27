package fa.cpl_java_05.dao;


import fa.cpl_java_05.model.book.BookCaseModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:20 PM
 */
public interface IBookCaseDAO extends GenericDAO<BookCaseModel> {
    int add(BookCaseModel bookCaseModelModel);
    void update(int id, String name);
    public void delete(int id) throws SQLException;
    List<BookCaseModel> findBookAll();
    List<BookCaseModel> findById(int id);
    List<BookCaseModel> findByBookCaseName(String bookCaseName);
}
