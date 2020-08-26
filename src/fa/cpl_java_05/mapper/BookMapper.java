package fa.cpl_java_05.mapper;


import fa.cpl_java_05.model.book.BookModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:01 PM
 */
public class BookMapper implements RowMapper<BookModel> {
    @Override
    public BookModel mapRow(ResultSet rs) {
        try {
             BookModel bookModel = new BookModel();
             bookModel.setBookId(rs.getInt("book_id"));
             bookModel.setBookTitle(rs.getString("book_title"));
             bookModel.setAuthor(rs.getString("author"));
             bookModel.setBrief(rs.getString("brief"));
             bookModel.setPublisher(rs.getString("publisher"));
             bookModel.setContent(rs.getString("content"));
             bookModel.setCategory(rs.getString("category"));
             bookModel.setDelete(rs.getBoolean("deleted"));

             return bookModel;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
