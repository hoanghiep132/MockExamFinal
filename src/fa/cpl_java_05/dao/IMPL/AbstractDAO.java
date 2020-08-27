package fa.cpl_java_05.dao.IMPL;

import fa.cpl_java_05.dao.GenericDAO;
import fa.cpl_java_05.dao.jdbc.GetConnection;
import fa.cpl_java_05.mapper.RowMapper;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:23 PM
 */
public class AbstractDAO<T> implements GenericDAO<T> {

    public Connection getConnectionJDBC(){
        return GetConnection.getConnection();
    }


    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnectionJDBC();
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            rs = statement.executeQuery();
            while (rs.next()) {
                results.add(rowMapper.mapRow(rs));
            }

            return results;
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                return null;
            }
        }
    }


    @Override
    public void update(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnectionJDBC();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {

            }
        }
    }

    @Override
    public int insert(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int id = 0;
            connection = getConnectionJDBC();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(statement, parameters);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            connection.commit();
            return id;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
       return -1;
    }

    private void setParameter(PreparedStatement statement, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                int index = i + 1;
                if (parameter instanceof Long) {
                    statement.setLong(index, (Long) parameter);
                } else if (parameter instanceof String) {
                    statement.setString(index, (String) parameter);
                } else if (parameter instanceof Integer) {
                    statement.setInt(index, (Integer) parameter);
                } else if (parameter instanceof Date) {
                    statement.setDate(index, (java.sql.Date) parameter);
                }else if(parameter instanceof Boolean){
                    statement.setBoolean(index,(Boolean) parameter);
                }
            }
        } catch (SQLException e) {
        }
    }
}
