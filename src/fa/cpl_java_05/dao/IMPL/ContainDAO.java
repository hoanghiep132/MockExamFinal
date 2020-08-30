package fa.cpl_java_05.dao.IMPL;

import fa.cpl_java_05.dao.IContainDAO;
import fa.cpl_java_05.dao.jdbc.GetConnection;
import fa.cpl_java_05.mapper.BookMapper;
import fa.cpl_java_05.mapper.ContainMapper;
import fa.cpl_java_05.model.book.BookModel;
import fa.cpl_java_05.model.book.ContainModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:23 PM
 */
public class ContainDAO extends AbstractDAO<ContainModel> implements IContainDAO {


    @Override
    public List<BookModel> findByBookCaseId(int id) {
        String sql = "select b.book_id,b.book_title,b.author,b.category,b.publisher,b.brief,b.content, b.deleted from Contain c " +
                " join Book b on b.book_id = c.book_id and b.deleted = false " +
                " join BookCase bc on bc.book_case_id = c.book_case_id and bc.book_case_id = ? " +
                " and c.deleted = false";
        return query(sql,new BookMapper(),id);
    }

    public List<BookModel> search(int bookCaseId, String text) {
        String sql = "select distinct b.book_id,b.book_title,b.author,b.category,b.publisher,b.brief,b.content, b.deleted from Contain c " +
                " join Book b on b.book_id = c.book_id and b.deleted = false " +
                " join BookCase bc on bc.book_case_id = c.book_case_id and bc.book_case_id = ? " +
                " and c.deleted = false and ";
        sql  += "(" +
                " upper(b.book_title) like upper(concat('%','" + text + "','%')) " +
                " or upper(b.author) like  upper(concat('%','" + text + "','%')) " +
                " or upper(b.publisher) like  upper(concat('%','" + text + "','%')) " +
                " or upper(b.brief) like upper(concat('%','" + text + "','%')) " +
                " or upper(b.category) like upper(concat('%','" + text + "','%')) " +
                ")";
        Connection con = GetConnection.getConnection();
        PreparedStatement ps = null;
        List<BookModel> list = new ArrayList<>();
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,bookCaseId);
            ResultSet rs = ps.executeQuery();
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
    }

    public int changeDeleted(int bookCaseId, int bookId){
        String sql = "update Contain set deleted = 0 where " +
                " book_case_id = ? and book_id = ?";
        try{
            update(sql,bookCaseId,bookId );
            return 1;
        }catch (Exception ex){
            return -1;
        }
    }

    public Boolean findContainDeleted(int bookCaseId, int bookId){
        String sql = "select * from Contain where " +
                "book_case_id = ? and book_id = ? and deleted = true";
        try{
            List<ContainModel> containModels = query(sql,new ContainMapper(),bookCaseId,bookId);
            if(containModels.isEmpty()){
                return false;
            }else {
                return true;
            }
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public int save(int bookCaseId, int bookId, Date date) {
        String sql = "insert into Contain(book_case_id,book_id,create_date,deleted) values(?,?,?,false)";
        return insert(sql,bookCaseId,bookId,date);
    }

    @Override
    public int deleted(int book_case_id, int book_id) {
        String sql = "update Contain set deleted = 1 where " +
                " book_case_id = ? and book_id = ?";
        try{
            update(sql,book_case_id,book_id );
            return 1;
        }catch (Exception ex){
            return -1;
        }
    }
}
