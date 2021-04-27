package fa.cpl_java_05.mapper;


import java.sql.ResultSet;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:00 PM
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs);
}
