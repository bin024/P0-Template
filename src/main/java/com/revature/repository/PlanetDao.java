package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.SourceDataLine;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

import ch.qos.logback.core.net.SyslogOutputStream;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from planets";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			List<Planet> planets = new ArrayList<>();
			while(rs.next()){
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));
				planets.add(planet);
			}
			return planets;
		}catch(SQLException e){
				System.out.println(e.getMessage());
				return new ArrayList<>();
			}
	}

	public Planet getPlanetByName(String owner, String planetName) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from planets where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1,planetName);
			ResultSet rs = ps.executeQuery();
            rs.next();
            Planet planet = new Planet();
            planet.setId(rs.getInt(1));
            planet.setName(rs.getString(2));
            planet.setOwnerId(rs.getInt(3));
			return planet;
		}catch(SQLException e){
            System.out.println(e.getMessage());
            return new Planet();
        }
	}

	public Planet getPlanetById(String username, int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,planetId);
			ResultSet rs = ps.executeQuery();
            rs.next();
            Planet planet = new Planet();
            planet.setId(rs.getInt(1));
            planet.setName(rs.getString(2));
            planet.setOwnerId(rs.getInt(3));
			return planet;
		}catch(SQLException e){
            System.out.println(e.getMessage());
            return new Planet();
        }

	}

	public Planet createPlanet(String username, Planet p) {
		try(Connection connection = ConnectionUtil.createConnection()){

            //1. craft the query
            String sql = "insert into planets values(default,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //2. provider relevatn information
            ps.setString(1, p.getName());
            ps.setInt(2, p.getOwnerId());
            //3. execute query
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            //4. handle the result
			Planet planet = new Planet();
            rs.next();
			int newId = rs.getInt("id");
            planet.setId(newId);
            planet.setName(p.getName());
            planet.setOwnerId(p.getOwnerId());
			return planet;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return new Planet();
        }
	}

	public void deletePlanetById(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "delete from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		PlanetDao planetDao = new PlanetDao();
		// try{
		// 	System.out.println(planetDao.getAllPlanets());
		// }catch(SQLException e){
		// 	System.out.println(e.getMessage());
		// }

		// planetDao.deletePlanetById(6);
		// System.out.println(planetDao.getPlanetById("java", 1));
		// System.out.println(planetDao.getPlanetByName("java", "Earth"));
		// Planet username = new Planet();
		// username.setName("Earth1");
		// System.out.println(planetDao.createPlanet("username",username));
	}
}
