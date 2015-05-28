package de.simulator.client.view;

import org.moxieapps.gwt.highcharts.client.Series;

public class Device {
	
	// LoadProfile hinzufügen
	
	// Variablen Deklarationen
	private final String category;
	private final String manufacturer;
	private final String name;
	private final String description;
	
	//private ArrayList<LoadProfile> loadProfile;
	Series loadProfile;
	
	// LoadProfile hinzufügen
	
	// Konstruktor
	public Device( String category, String manufacturer, String name, String description, Series loadProfile) {
		this.category = category;
		this.manufacturer = manufacturer;
		this.name = name;
		this.description = description;
		this.loadProfile = loadProfile;		
	} // Konstruktor Device() zu

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
	

}