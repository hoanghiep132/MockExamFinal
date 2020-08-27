package fa.cpl_java_05.dao;

import fa.cpl_java_05.model.book.BookModel;
import fa.cpl_java_05.model.book.ContainModel;

import java.sql.Date;
import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:21 PM
 */
public interface IContainDAO extends GenericDAO<ContainModel> {

    List<BookModel> findByBookCaseId(int id);
    int save(int bookCaseId, int bookId, Date date);
    int deleted(int book_case_id,int book_id);
}
