package de.simulator.client.view;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.user.client.ui.Composite;

public class LoadProfileView extends Composite {
	
	private Chart channels;
	//SimplePanel loadProfileView;
	
	public LoadProfileView () {
		
		//loadProfileView = new SimplePanel();
		
		channels = new Chart().setType( Series.Type.SPLINE).setChartTitleText( "Lastgang").setMarginRight( 10);
		Series series = channels.createSeries().setName("Leistungsaufnahme").setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
		
		initWidget( channels);
	}

	public void addSeries( Series loadProfile) {
		channels.addSeries( loadProfile, true, true);
	}
}
