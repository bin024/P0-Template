package com.revature.service;

import java.util.List;

import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(){
		this.dao = new PlanetDao();
	}

	public List<Planet> getAllPlanets() {
		// TODO Auto-generated method stub
		return this.dao.getAllPlanets();
	}

	public Planet getPlanetByName(String owner, String planetName) {
		// TODO Auto-generated method stub
		return this.dao.getPlanetByName(owner, planetName);
	}

	public Planet getPlanetById(String username, int planetId) {
		// TODO Auto-generated method stub
		return this.dao.getPlanetById(username, planetId);
	}

	public Planet createPlanet(String username, Planet p) {
		// TODO Auto-generated method stub
		return this.dao.createPlanet(username, p);
	}

	public void deletePlanetById(int planetId) {
		// TODO Auto-generated method stub
		this.dao.deletePlanetById(planetId);
	}

	public static void main(String[] args) {
		PlanetService planetService = new PlanetService();
		System.out.println(planetService.getPlanetById("username", 1));
		// Planet username = new Planet();
		// username.setName("Earth1");
		// planetService.deletePlanetById(20);
		// System.out.println(planetService.getAllPlanets());
	}
}
