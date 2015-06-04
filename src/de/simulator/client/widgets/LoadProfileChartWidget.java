package de.simulator.client.widgets;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.user.client.ui.Composite;

public class LoadProfileChartWidget extends Composite {
	
	private Chart loadProfileChart;
	
	public LoadProfileChartWidget( String chartTitle) {
		
		loadProfileChart = new Chart().setType( Series.Type.SPLINE).setChartTitleText( chartTitle).setMarginRight( 10);
		
		
		initWidget( this.loadProfileChart);
	}
	
	public Chart getLoadProfileChart() {
		return this.loadProfileChart;
	}
	
	public void setStyleName( String styleName) {
		this.loadProfileChart.setStyleName( styleName);
	}

	public void addPoint() {
		//this.loadProfileChart
		return;
	}
	
	public void addSeries( Series series) {
		this.loadProfileChart.addSeries( series);
	}
	
	
}
