package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.FormatFlagsConversionMismatchException;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

public class UserDao {
    
    public User getUserByUsername(String username){
        try (Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from users where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User();
            user.setId(rs.getInt(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));
            return user;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return new User();
        }
    }

    public User createUser(UsernamePasswordAuthentication registerRequest){
        try(Connection connection = ConnectionUtil.createConnection()){
            //1. craft the query
            String sql = "insert into users values(default,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //2. provider relevatn information
            ps.setString(1, registerRequest.getUsername());
            ps.setString(2, registerRequest.getPassword());
            //3. execute query
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            //4. handle the result
            User newUser = new User();
            rs.next();
            int newId = rs.getInt("id");
            newUser.setId(newId);
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            return newUser;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return new User();
        }
    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();
        
        UsernamePasswordAuthentication newUser = new UsernamePasswordAuthentication();
        newUser.setUsername("javalin");
        newUser.setPassword("I want fly");
        System.out.println(dao.createUser(newUser).getId());

        // System.out.println(dao.getUserByUsername("java"));

    }
}
