package fa.cpl_java_05.dao.IMPL;


import fa.cpl_java_05.dao.IBookDAO;
import fa.cpl_java_05.dao.IMPL.AbstractDAO;
import fa.cpl_java_05.mapper.BookMapper;
import fa.cpl_java_05.model.book.BookModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:23 PM
 */
public class BookDAO extends AbstractDAO<BookModel> implements IBookDAO {

    @Override
    public List<BookModel> findBookAll() {
        String sql="SELECT * FROM BookCase WHERE deleted = false";
        return query(sql, new BookMapper());
    }

    @Override
    public List<BookModel> findById(int id) {
        String sql = "SELECT * FROM Book WHERE book_id=? and deleted = false";
        return query(sql, new BookMapper(), id);
    }

    @Override
    public List<BookModel> findByBookTitle(String bookTitle) {
       String sql = "SELECT * FROM Book WHERE book_title LIKE ? AND deleted = false";
       return query(sql, new BookMapper(), bookTitle);
    }

    public List<BookModel> search(String text){
        String sql = "select * from Book where deleted = false " +
                "and (upper(book_title)  LIKE upper(concat('%',?,'%,) " +
                "or upper(author)  LIKE upper(concat('%',?,'%,) " +
                "or upper(publisher)  LIKE upper(concat('%',?,'%,) " +
                "or upper(brief)  LIKE upper(concat('%',?,'%,) " +
                ")";
        return query(sql,new BookMapper(), text);
    }

    @Override
    public List<BookModel> findByCategory(String category) {
        String sql = "SELECT * FROM Book WHERE category LIKE ? AND deleted = false";
        return query(sql, new BookMapper(), category);
    }

    @Override
    public int save(BookModel bookModel) {
        String sql="INSERT INTO Book (book_title, author, brief, publisher, content, category, deleted) VALUES(?,?,?,?,?,?,false)";
        return insert(sql, bookModel.getBookTitle(), bookModel.getAuthor(), bookModel.getBrief(), bookModel.getPublisher()
        , bookModel.getContent(), bookModel.getCategory(), bookModel.getDelete());
    }

    @Override
    public boolean update(BookModel bookModelUpdate) {
        StringBuilder sql = new StringBuilder("UPDATE Book SET book_title = ?, author = ?,");
        sql.append("brief = ?, publisher = ?, content = ? , category = ?, deleted = ? WHERE book_id = ?");
        try {
            update(sql.toString(), bookModelUpdate.getBookTitle(), bookModelUpdate.getAuthor(), bookModelUpdate.getBrief(), bookModelUpdate.getPublisher()
                    , bookModelUpdate.getContent(), bookModelUpdate.getCategory(), bookModelUpdate.getDelete(), bookModelUpdate.getBookId());
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "update Book set deleted = true where book_id = ?";
        try{
            update(sql, new BookMapper(), id);
            return true;
        }catch (Exception ex){
            return false;
        }

    }
}
