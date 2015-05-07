package de.simulator.client;

import org.moxieapps.gwt.highcharts.client.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Simulator implements EntryPoint {
    Label title = new Label("Simulator");

	public void onModuleLoad() {				
		Chart chart = new Chart()
		   .setType(Series.Type.SPLINE)
		   .setChartTitleText("Lawn Tunnels")
		   .setMarginRight(10);
		
		Series series = chart.createSeries()
				   .setName("Moles per Yard")
				   .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
				chart.addSeries(series);
		
		RootPanel.get().add(chart);
	}
}
