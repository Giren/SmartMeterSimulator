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
		this.calcSimulatorLoadProfile( device);
	}
	
	public Series getSimulatorLoadProfileAsSeries() {
		// TODO
		// LoadProfile als Series aufbereiten
		Series SimulatorSeries = new Chart().createSeries().setName(
				"Simulator LoadProfile");

		for (int i = 0; i < this.simulatorLoadProfile.size(); i++) {
			SimulatorSeries.addPoint( this.simulatorLoadProfile.get(i));
		}

		return SimulatorSeries;		
	}
	
	public ArrayList<Double> getSimulatorLoadProfileAsArrayList() {
		return this.simulatorLoadProfile;
	}
 
	
	
	private void calcSimulatorLoadProfile( Device device) {
		// TODO
		// Über alle Devices Iterieren und 
		// SimulatorLoadProfile berechnen >>> Taktung 1 Sekunde <<<
		// Zeitstempel der neu hinzuzufügenden Lastgänge müssen noch berücksichtigt werden
		// IMPLEMENTIEREN
		for( int i = 0; i < device.getLoadProfile().size(); i++) {
			if( i >= simulatorLoadProfile.size()) {
				simulatorLoadProfile.add( Double.valueOf( String.valueOf( device.getLoadProfile().get( i))));
			} else {
				double calcVal = simulatorLoadProfile.get( i) + device.getLoadProfile().get( i);
				simulatorLoadProfile.set( i, calcVal);
			}
		}
	}
}
