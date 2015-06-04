package de.simulator.shared;

import java.io.Serializable;
import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

public class SimulatorDevice implements Serializable {
	
	ArrayList<Device> deviceArrayList;
	
	ArrayList<Double> simulatorLoadProfile; 
	
	public SimulatorDevice () {
		
		this.deviceArrayList = new ArrayList<Device>();
		this.simulatorLoadProfile = new ArrayList<Double>();
		
	}
	
	public void addDevice( Device device) {
		this.deviceArrayList.add( device);
		// TODO
		// Calculate new LoadProfile
		// Methode Implementieren
		this.calcSimulatorLoadProfile();
	}
	
	public Series getSimulatorLoadProfileAsSeries() {
		// TODO
		// LoadProfile als Series aufbereiten
		Series simulatorLoadProfileSeries = null;
		
		
		return simulatorLoadProfileSeries;		
	}
	
	public ArrayList<Double> getSimulatorLoadProfileAsArrayList() {
		return this.simulatorLoadProfile;
	}
 
	
	
	private void calcSimulatorLoadProfile() {
		// TODO
		// Über alle Devices Iterieren und 
		// SimulatorLoadProfile berechnen >>> Taktung 1 Sekunde <<<
		// IMPLEMENTIEREN
	}
}
