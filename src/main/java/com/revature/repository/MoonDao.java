package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.models.User;
import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from moons";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Moon> moons = new ArrayList<>();
			while(rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
			 	moons.add(moon);
			}
			return moons;
		}catch(SQLException e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
	}

	public Moon getMoonByName(String username, String moonName) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1,moonName);
			ResultSet rs = ps.executeQuery();
            rs.next();
			Moon moon = new Moon();
			moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));
			return moon;
		}catch(SQLException e){
            System.out.println(e.getMessage());
            return new Moon();
        }
	}

	public Moon getMoonById(String username, int moonId) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,moonId);
			ResultSet rs = ps.executeQuery();
            rs.next();
			Moon moon = new Moon();
            moon.setId(rs.getInt(1));
            moon.setName(rs.getString(2));
            moon.setMyPlanetId(rs.getInt(3));
			return moon;
		}catch(SQLException e){
            System.out.println(e.getMessage());
            return new Moon();
        }
	}

	public Moon createMoon(String username, Moon m) {
		try(Connection connection = ConnectionUtil.createConnection()){
			
            //1. craft the query
            String sql = "insert into moons values(default,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //2. provider relevatn information
            ps.setString(1, m.getName());
            ps.setInt(2, m.getMyPlanetId());
            //3. execute query
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            //4. handle the result
			Moon moon = new Moon();
			rs.next();
			int newId = rs.getInt("id");
            moon.setId(newId);
            moon.setName(m.getName());
            moon.setMyPlanetId(m.getMyPlanetId());
			return moon;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return new Moon();
        }
	}

	public void deleteMoonById(int moonId) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "delete from moons where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons where myPlanetId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();

			List<Moon> moons = new ArrayList<>();
			while(rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
			 	moons.add(moon);
			}
			return moons;
		}catch(SQLException e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
	}

	public static void main(String[] args) {
		MoonDao moonDao = new MoonDao();
		// System.out.println(moonDao.getMoonsFromPlanet(10));
		
		// PlanetDao planetDao = new PlanetDao();


		Moon moon5 = new Moon();
		moon5.setName("moon6");
		moon5.setMyPlanetId(10);
		System.out.println(moonDao.createMoon("sql", moon5));

		// System.out.println(moonDao.getMoonById("lombok",8));

	}
}
