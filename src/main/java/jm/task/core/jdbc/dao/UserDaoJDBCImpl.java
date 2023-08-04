package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();

    private final String CREATE_SQL_QUERY = """
            CREATE TABLE IF NOT EXISTS users (
            id INTEGER AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255),
            last_name VARCHAR(255),
            age SMALLINT);
            """;

    private final String DROP_SQL_QUERY = """
            DROP TABLE IF EXISTS users;
            """;

    private final String CLEAN_SQL_QUERY = """
            TRUNCATE TABLE users;
            """;

    private final String PERSIST_SQL_QUERY = """
            INSERT INTO users (name, last_name, age) VALUES (?,?,?);
            """;

    private final String DELETE_SQL_QUERY = """
            DELETE FROM users WHERE id = ?;
            """;

    private final String SELECT_SQL_QUERY = """
            SELECT * FROM users;
            """;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL_QUERY)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблицы", e);
        }
    }

    public void dropUsersTable() {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DROP_SQL_QUERY)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(PERSIST_SQL_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении пользователя", e);
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении пользователя", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                users.add(new User(id, name, lastName, age));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении пользователя", e);
        }
    }

    public void cleanUsersTable() {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_SQL_QUERY)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при очистки таблицы", e);
        }
    }
}
