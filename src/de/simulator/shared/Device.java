package de.simulator.shared;

import java.io.Serializable;
import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Series;

public class Device implements Serializable {

	// Variablen Deklarationen
	private String id;
	private String category;
	private String manufacturer;
	private String name;
	private String description;
	private ArrayList<Integer> loadProfile;

	
	public Device(){}
	
	// Konstruktor
	public Device(String id, String category, String manufacturer, String name, String description, ArrayList<Integer> loadProfile) {
		this.id = id;
		this.category = category;
		this.manufacturer = manufacturer;
		this.name = name;
		this.description = description;
		this.loadProfile = loadProfile;		
	} // Konstruktor Device() zu

	public ArrayList<Integer> getLoadProfile() {
		return loadProfile;
	}

	public void setLoadProfile(ArrayList<Integer> loadProfile) {
		this.loadProfile = loadProfile;
	}

	public String getID(){
		return id;
	}
	
	public String getCategory() {
		return category;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}	

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCategory(String category) {
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

