package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Alex", "Alekseev", (byte) 25);
        userService.saveUser("Anna", "Alekseeva", (byte) 20);
        userService.saveUser("Anton", "Pavlov", (byte) 30);
        userService.saveUser("Pavel", "Ivanov", (byte) 40);

        userService.getAllUsers();

        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.closeConnection();
    }
}
