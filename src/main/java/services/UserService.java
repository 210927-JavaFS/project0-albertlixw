package services;

import controllers.MenuController;
import controllers.UserController;
import daos.UserDAO;
import daos.UserDAOImpl;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Scanner;

public class UserService {

//    private AccountDAO  accountDao = new AccountDAOImpl();
    private static UserDAO userDao = new UserDAOImpl();

    private static UserController userController = new UserController();
//    private static MenuController menuController = new MenuController();

//    private static AccountService accountService = new AccountService();

    private static Scanner sc = new Scanner(System.in);

    public static HashMap<Integer, User> userList = getUserList();
//    private static HashMap<Integer, Account> accountList = accountService.findAllAccount();

    private static Logger log = LoggerFactory.getLogger(MenuController.class);





    public static HashMap<Integer, User> getUserList() {
        return userDao.findAll();
    }


    public void deleteUser(int id){
//        boolean bool = userController.approve();
            userDao.deleteMapping(id);
            userDao.deleteUser(id);
            userList.remove(id);
            System.out.println("userid: " + id + " deleted successfully");
    }








    public boolean login(User user, String username, String pwd) {
        if(username.equals(user.getUsername())&&pwd.equals(user.getPwd()))
            return true;
        else return false;
    }


//    private static UserDAO playerDAO = new UserDAO();

//    public User createNewUser(String id, String role, int balance){
//        User user = new User(id, role, balance);
//        return user;
//    }

}
