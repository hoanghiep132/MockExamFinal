package fa.cpl_java_05.dao;


import fa.cpl_java_05.mapper.RowMapper;

import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:19 PM
 */
public interface GenericDAO<T> {

    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);
    void update(String sql, Object... parameters);
    int insert(String sql, Object... parameters);
}
