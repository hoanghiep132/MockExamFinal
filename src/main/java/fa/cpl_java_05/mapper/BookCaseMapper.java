package fa.cpl_java_05.mapper;


import fa.cpl_java_05.model.book.BookCaseModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:17 PM
 */
public class BookCaseMapper implements RowMapper<BookCaseModel> {
    @Override
    public BookCaseModel mapRow(ResultSet rs) {
        try {
            BookCaseModel bookCaseModel = new BookCaseModel();
            bookCaseModel.setBook_case_id(rs.getInt("book_case_id"));
            bookCaseModel.setBook_case_name(rs.getString("book_case_id"));

            return bookCaseModel;
        }catch (SQLException e){
            return null;
        }
    }
}
