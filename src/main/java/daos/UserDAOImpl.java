package daos;

import controllers.MenuController;
import models.Home;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class UserDAOImpl implements UserDAO{

    private HomeDAO homeDao = new HomeDAOImpl();
    private static Logger log = LoggerFactory.getLogger(MenuController.class);

    @Override
    public HashMap<Integer, User> findAll() {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Users;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            HashMap<Integer, User> userList = new HashMap<Integer, User>();
            //ResultSets have a cursor like Scanner

            while(result.next()){
                User user = new User(
                        result.getInt("user_level"),
                        result.getString("username"),
                        result.getString("pwd").substring(0, result.getString("pwd").length() - 1),
                        result.getString("keyword")
                );
                int userId = result.getInt("userId");
                user.setId(userId);
                String homeName = result.getString("home");
                if(homeName!=null){
                    Home home = homeDao.findByName(homeName);
                    user.setHome(home);

                }
                userList.put(user.getId(), user);
            }
            return userList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    @Override
    public User findUserByUsername(String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * from users WHERE username = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            User user = new User();

            if(result.next()){
                user = new User(
                        result.getInt("user_level"),
                        result.getString("username"),
                        result.getString("pwd").substring(0, result.getString("pwd").length() - 1),
                        result.getString("keyword")
                );
                int userId = result.getInt("userId");
                user.setId(userId);
                String homeName = result.getString("home");
                if(homeName!=null){
                    Home home = homeDao.findByName(homeName);
                    user.setHome(home);
                }
            }
        return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteMapping(int userId) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "DELETE FROM map_users_accounts WHERE userid = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "DELETE FROM users WHERE userid = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            log.info("Deleted user id: " + id);
            return true;
        } catch (SQLException e) {
            log.warn("failed deleting user id: " + id);
            log.warn(e.getMessage());

            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findUserById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Users WHERE userid = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            HashMap<Integer, User> userList = new HashMap<Integer, User>();
            //ResultSets have a cursor like Scanner
            User user = new User();
            if(result.next()){
                user = new User(
                        result.getInt("user_level"),
                        result.getString("username"),
                        result.getString("pwd").substring(0, result.getString("pwd").length() - 1),
                        result.getString("keyword")
                );
                int userId = result.getInt("userId");
                user.setId(userId);
                String homeName = result.getString("home");
                if(homeName!=null){
                    Home home = homeDao.findByName(homeName);
                    user.setHome(home);

                }
            }
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO users(user_level, username, pwd, keyword) VALUES (?, ?, ?, ?);";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user.getLevel());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPwd() + "*");
            statement.setString(4, user.getKeyword());

//            if(user.getHome()!=null){
//                statement.setString(5, user.getHome().getName());
//            }
            statement.execute();

            return true;

        }   catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE users SET user_level = ?, username = ?, pwd = ?, keyword = ? WHERE userId = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user.getLevel());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPwd() + "*");
            statement.setString(4, user.getKeyword());
            statement.setInt(5, user.getId());
            statement.execute();
            return true;

        }   catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }


}
