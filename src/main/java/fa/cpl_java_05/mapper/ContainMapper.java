package fa.cpl_java_05.mapper;


import fa.cpl_java_05.model.book.ContainModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:14 PM
 */
public class ContainMapper implements RowMapper<ContainModel> {
    @Override
    public ContainModel mapRow(ResultSet rs) {
        try {
            ContainModel containModel = new ContainModel();
            containModel.setBookCaseId(rs.getInt("book_case_id"));
            containModel.setBookId(rs.getInt("book_id"));
            containModel.setCreat_date(rs.getDate("create_date"));

            return  containModel;
        }catch (SQLException e){
            return null;
        }
    }
}
