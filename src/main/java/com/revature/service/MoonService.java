package com.revature.service;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import com.revature.models.Moon;
import com.revature.repository.MoonDao;
import com.revature.utilities.ConnectionUtil;
public class MoonService {

	private MoonDao dao;

	public MoonService(){
		this.dao = new MoonDao();
	}

	public List<Moon> getAllMoons() {
		// TODO Auto-generated method stub
		return this.dao.getAllMoons();
	}

	public Moon getMoonByName(String username, String moonName) {
		return this.dao.getMoonByName(username, moonName);
	}

	public Moon getMoonById(String username, int moonId) {
		// TODO Auto-generated method stub
		return this.dao.getMoonById(username, moonId);
	}

	public Moon createMoon(String username, Moon m) {
		// TODO Auto-generated method stub
		return this.dao.createMoon(username, m);
	}

	public void deleteMoonById(int moonId) {
		// TODO Auto-generated method stub
		this.dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		// TODO Auto-generated method stub
		return this.dao.getMoonsFromPlanet(planetId);
	}

	public static void main(String[] args) {
		MoonService moonService = new MoonService();

		System.out.println(moonService.getMoonsFromPlanet(10));
	}
}