package de.simulator.client.presenter;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import de.simulator.client.widgets.ClosableDialogBox;
import de.simulator.shared.SimulatorDevice;

public class RunDialogPresenter {
	
	// Display Interface zur View
	//
	public interface Display {
		PushButton getPlayButton();
		PushButton getStopButton();
		PushButton getResetButton();
		Chart getSimulatorLoadProfileChart();
		Series getSimulatorLoadProfileSeries();
		void resetSeries();
		Label getPowerLabel();
		void setPowerLabel( double actualPowerValue);
		Widget asWidget();
	}
	
	// Variablen Deklaration
	//
	private final HandlerManager eventBus;
	private final Display display;
	private SimulatorDevice simulatorDevice;
	private ClosableDialogBox closableDialogBox;	
	Timer simulationInterval;
	int simulationCounter;
	
	public RunDialogPresenter( HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}
	
	public void bind() {		
		// eventbus handling
		
		display.getPlayButton().addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				display.getPlayButton().setEnabled( false);
				display.getStopButton().setEnabled( true);
				display.getResetButton().setEnabled( false);
				display.getSimulatorLoadProfileChart().getXAxis().setExtremes( 0, simulatorDevice.getSimulatorLoadProfileAsArrayList().size());
				closableDialogBox.setCloseButtonVisible( false);
				closableDialogBox.setTitleInformation( "running 0%");
				
				simulationInterval = new Timer() {
			    	@Override
			        public void run() {
			    		simulationStart();
			        }
			    };
			    simulationInterval.scheduleRepeating( 1000);
			}
		});
		
		display.getStopButton().addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.getPlayButton().setEnabled( true);
				display.getStopButton().setEnabled( false);
				display.getResetButton().setEnabled( true);
				simulationInterval.cancel();	
				closableDialogBox.setTitleInformation( "stopped/paused"); 
				
				closableDialogBox.setCloseButtonVisible( true);
			}
		});
		
		display.getResetButton().addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.getPlayButton().setEnabled( true);
				display.getStopButton().setEnabled( false);
				display.getResetButton().setEnabled( false);	
				display.resetSeries();
				display.setPowerLabel( 0.0);
				simulationCounter = 0;
				simulationInterval.cancel();
				closableDialogBox.setTitleInformation( "reset"); 
				
				closableDialogBox.setCloseButtonVisible( true);
				// TODO
				// clearChart
			}
		});
	}
	
	private void simulationStart() {
		
		int loadProfileSize = simulatorDevice.getSimulatorLoadProfileAsArrayList().size();
		
		double simulationPercent =  ( 100*simulationCounter / ( loadProfileSize-1));
		//Window.alert( String.valueOf( simulationPercent));
		
		if ( simulationCounter < loadProfileSize)
		{
			Double nextValue = simulatorDevice.getSimulatorLoadProfileAsArrayList().get( simulationCounter);
			Series actLoadProfileSeries = display.getSimulatorLoadProfileSeries();
			actLoadProfileSeries.addPoint( nextValue);
			display.setPowerLabel( nextValue);
			
			closableDialogBox.setTitleInformation( "running " + String.valueOf(simulationPercent) + "%"); 
			//Window.alert( simulationCounter + " " + loadProfileSize + " " + nextValue);
			
			simulationCounter++;
			
			// TODO
			// Broadcast to Server
			
		} else {
			simulationInterval.cancel();
			closableDialogBox.setTitleInformation( "finished 100%"); 
			closableDialogBox.setCloseButtonVisible( true);
			display.getPlayButton().setEnabled( false);
			display.getStopButton().setEnabled( false);
			display.getResetButton().setEnabled( true);
		}
		
	}

	public void go( SimulatorDevice simDevice) {
		
		// TODO View initialisieren
		// notwendige Elemente vorladen
		this.simulatorDevice = simDevice;
		this.simulationCounter = 0;
		display.setPowerLabel( 0.0);		
		display.getPlayButton().setEnabled( true);
		display.getStopButton().setEnabled( false);
		display.getResetButton().setEnabled( false);
	    
		closableDialogBox = new ClosableDialogBox( "Simulation", "");
		closableDialogBox.setWidget( display.asWidget());
		closableDialogBox.setAnimationEnabled( true);
		closableDialogBox.setGlassEnabled( true);
		closableDialogBox.center();
		closableDialogBox.show(); // closableDialogBox anzeigen
	}
}
