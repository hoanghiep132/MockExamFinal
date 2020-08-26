package fa.cpl_java_05.dao.IMPL;

import fa.cpl_java_05.dao.IBookCaseDAO;
import fa.cpl_java_05.mapper.BookCaseMapper;
import fa.cpl_java_05.model.book.BookCaseModel;



import java.sql.SQLException;
import java.util.List;


public class BookCaseDAO extends AbstractDAO<BookCaseModel> implements IBookCaseDAO {

    @Override
    public int add(BookCaseModel bookCaseModelModel) {
        String sql="INSERT INTO BookCase (book_case_name) VALUES(?)";
        return insert(sql, bookCaseModelModel.getBook_case_name());
    }

    @Override
    public void update(BookCaseModel bookCaseModelUpdate) {
        String sql = "UPDATE BookCase SET book_case_name = ? WHERE book_case_id = ?";
        update(sql, bookCaseModelUpdate.getBook_case_name());
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "update BookCase set deleted = true where book_case_id = ?";
        update(sql, new BookCaseMapper(), id);
    }

    @Override
    public List<BookCaseModel> findBookAll() {
        String sql="SELECT * FROM BookCase WHERE deleted = false";
        return query(sql, new BookCaseMapper());
    }

    @Override
    public List<BookCaseModel> findById(int id) {
        String sql = "SELECT * FROM BookCase WHERE book_case_id=? and deleted = false";
        return query(sql, new BookCaseMapper(), id);
    }

    @Override
    public List<BookCaseModel> findByBookCaseName(String bookCaseName) {
        String sql = "SELECT * FROM BookCase WHERE upper(book_case_name) LIKE upper(concat('%',?,'%,)) AND deleted = false";
        return query(sql, new BookCaseMapper(), bookCaseName);
    }
}

