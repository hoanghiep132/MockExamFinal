package fa.cpl_java_05.dao.IMPL;


import fa.cpl_java_05.dao.IBookDAO;
import fa.cpl_java_05.dao.IMPL.AbstractDAO;
import fa.cpl_java_05.dao.jdbc.GetConnection;
import fa.cpl_java_05.mapper.BookMapper;
import fa.cpl_java_05.model.book.BookModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:23 PM
 */
public class BookDAO extends AbstractDAO<BookModel> implements IBookDAO {

    @Override
    public List<BookModel> findBookAll() {
        String sql="SELECT * FROM Book WHERE deleted = false";
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
                "and (" +
                " upper(book_title) like upper(concat('%','" + text + "','%')) " +
                " or upper(author) like  upper(concat('%','" + text + "','%')) " +
                " or upper(publisher) like  upper(concat('%','" + text + "','%')) " +
                " or upper(brief) like upper(concat('%','" + text + "','%')) " +
                " or upper(category) like upper(concat('%','" + text + "','%')) " +
                ")";
        Connection con = GetConnection.getConnection();
        Statement ps = null;
        List<BookModel> list = new ArrayList<>();
        try {
            ps = con.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()){
                BookModel bookModel = new BookModel();
                bookModel.setBookId(rs.getInt("book_id"));
                bookModel.setBookTitle(rs.getString("book_title"));
                bookModel.setAuthor(rs.getString("author"));
                bookModel.setPublisher(rs.getString("publisher"));
                bookModel.setCategory(rs.getString("category"));
                bookModel.setContent(rs.getString("content"));
                bookModel.setBrief(rs.getString("brief"));
                bookModel.setDelete(rs.getBoolean("deleted"));
                list.add(bookModel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
//        return query(sql,new BookMapper(), text);
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
        sql.append("brief = ?, publisher = ?, content = ? , category = ? WHERE book_id = ?");
        try {
            update(sql.toString(), bookModelUpdate.getBookTitle(), bookModelUpdate.getAuthor(), bookModelUpdate.getBrief(), bookModelUpdate.getPublisher()
                    , bookModelUpdate.getContent(), bookModelUpdate.getCategory(), bookModelUpdate.getBookId());
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "update Book set deleted = 1 where book_id = ?";
        try{
            update(sql, new BookMapper(), id);
            return true;
        }catch (Exception ex){
            return false;
        }

    }
}
