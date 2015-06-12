package de.simulator.client.view;



import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.simulator.client.presenter.RunDialogPresenter;

public class RunDialogView extends Composite implements RunDialogPresenter.Display {
	
	//Panels
	private VerticalPanel simulationContent;
	private HorizontalPanel controlPanel;
	private HorizontalPanel buttonPanel;
	private HorizontalPanel powerPanel;
	private HorizontalPanel showPanel;
	
	// Toggle Buttons
	PushButton playButton;
	PushButton stopButton;
	PushButton resetButton;
	
	// Label
	private Label actualPowerLabel = new Label("power");
	private Label unitLabel = new Label( "W");
	
	// LoadProfileChart
	private Chart simulationLoadProfile = new Chart().setType(Series.Type.SPLINE)
				.setChartTitleText("Lastgang").setMarginRight(10);
	private Series loadProfileSeries = simulationLoadProfile.createSeries();
	
	public RunDialogView() {
		simulationContent = new VerticalPanel();
		this.simulationContent.setStyleName( "simulationPopUp");
		this.simulationContent.setSize( "800px", "400px");
		this.simulationContent.setWidth( "800px");
		this.simulationContent.setHeight( "400px");
		this.simulationContent.setSpacing( 8);
		
		// Panel Init
		this.controlPanel = new HorizontalPanel();

		this.buttonPanel = new HorizontalPanel();
		this.buttonPanel.setSpacing( 4);
		this.powerPanel = new HorizontalPanel();
		this.powerPanel.setSpacing( 4);
		this.showPanel = new HorizontalPanel();
		
		// Simulation ToggleButtons
		this.playButton = new PushButton( new Image( "images/playButton.png"));
		this.stopButton = new PushButton( new Image( "images/stopButton.png"));
		this.resetButton = new PushButton( new Image( "images/resetButton.png"));
		
		this.playButton.setTitle( "Start");
		this.stopButton.setTitle( "Stop/Pause");
		this.resetButton.setTitle( "Reset");
		this.powerPanel.setTitle( "aktueller Verbrauch [kWh]");
		
		this.actualPowerLabel.setStyleName( "powerLabel");	
		this.unitLabel.setStyleName( "unitLabel");
		
		this.buttonPanel.add( playButton);
		this.buttonPanel.add( stopButton);
		this.buttonPanel.add( resetButton);
		
		this.buttonPanel.setStyleName( "simulationPopUp");
		this.buttonPanel.setWidth( "100%");
		this.buttonPanel.setHeight( "125px");
		
		this.controlPanel.add( this.buttonPanel);
		this.controlPanel.setCellWidth( this.buttonPanel, "320px");
		
		this.powerPanel.add( actualPowerLabel);
		this.powerPanel.setCellVerticalAlignment( actualPowerLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		this.powerPanel.setCellHorizontalAlignment( actualPowerLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		this.powerPanel.add( unitLabel);	
		this.powerPanel.setCellVerticalAlignment( unitLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		this.powerPanel.setCellHorizontalAlignment( unitLabel, HasHorizontalAlignment.ALIGN_CENTER);

		this.powerPanel.setStyleName( "simulationPopUp");
		this.powerPanel.setCellWidth( unitLabel, "50px");
		this.powerPanel.setWidth( "350px");
		this.powerPanel.setHeight( "125px");
		
		this.controlPanel.add( this.powerPanel);
		this.controlPanel.setCellHorizontalAlignment( this.powerPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		this.controlPanel.setCellWidth( this.powerPanel, "100%");

		this.simulationLoadProfile.addSeries( this.loadProfileSeries);
		this.simulationLoadProfile.getXAxis().setAxisTitleText( "time [s]", false);
		this.simulationLoadProfile.getYAxis().setAxisTitleText( "W", false);
		this.simulationLoadProfile.setOption( "legend/enabled", false);
		this.simulationLoadProfile.getXAxis().setAllowDecimals( true);
		
		this.showPanel.add( this.simulationLoadProfile);
		this.simulationLoadProfile.setWidth( "780px");
		this.simulationLoadProfile.setHeight( "250px");

		this.simulationContent.add( this.controlPanel);
		this.simulationContent.add( this.showPanel);
		
		initWidget( this.simulationContent);
	}
	
	// ---------------------------------------
	// INTERFACE Methoden
	// ---------------------------------------
	
	public PushButton getPlayButton() {
		return this.playButton;
	}
	
	public PushButton getStopButton() {
		return this.stopButton;
	}
	
	public PushButton getResetButton() {
		return this.resetButton;
	}
	
	public Chart getSimulatorLoadProfileChart() {
		return this.simulationLoadProfile;
	}
	
	public Series getSimulatorLoadProfileSeries() {
		return this.loadProfileSeries;
	}
	
	public void resetSeries() {
		this.simulationLoadProfile.removeAllSeries();
		this.loadProfileSeries = this.simulationLoadProfile.createSeries();
		this.simulationLoadProfile.addSeries( this.loadProfileSeries);
	}
	
	public Label getPowerLabel() {
		return this.actualPowerLabel;
	}
	
	public void setPowerLabel( double actualPowerValue) {
		String newPowerVal = NumberFormat.getFormat("000000.00").format( actualPowerValue);
		this.actualPowerLabel.setText( newPowerVal);
	}

}
