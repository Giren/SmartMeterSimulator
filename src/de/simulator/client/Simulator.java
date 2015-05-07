package de.simulator.client;

import org.moxieapps.gwt.highcharts.client.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Simulator implements EntryPoint {
	//Panels
    private VerticalPanel menu = new VerticalPanel();
    private VerticalPanel mainPanel = new VerticalPanel();
    private HorizontalPanel browserPanel = new HorizontalPanel();
    private HorizontalPanel configPanel = new HorizontalPanel();
    //Buttons
    private Button reloadButton = new Button("reload");
    private Button runButton = new Button("run");
    private Button pushButton = new Button("push");
    //Labels
    private Label title = new Label("Simulator");
    private Label channels = new Label("Kanäle");
    private Label test2 = new Label("Renderergebnis");
    private Label test3 = new Label("Bibliothek");
    private Label test4 = new Label("menu");
    
	public void onModuleLoad() {
		Chart chart = new Chart()
		   .setType(Series.Type.SPLINE)
		   .setChartTitleText("Lawn Tunnels")
		   .setMarginRight(10);
		
		Series series = chart.createSeries()
				   .setName("Moles per Yard")
				   .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
				chart.addSeries(series);
		
		configPanel.add(test2);
		configPanel.add(test3);
		configPanel.addStyleName("configPanel");
		
		menu.add(test4);
		menu.addStyleName("menu");
		
		mainPanel.add(configPanel);
		mainPanel.add(chart);
		mainPanel.addStyleName("mainpanel");
		
		browserPanel.add(menu);
		browserPanel.add(mainPanel);
		browserPanel.addStyleName("browserpanel");
		browserPanel.setCellWidth(menu, "300");
		browserPanel.setCellWidth(mainPanel, "100%");
		
		RootPanel.get("entry").add(title);
		RootPanel.get("entry").add(browserPanel);
	}
}
