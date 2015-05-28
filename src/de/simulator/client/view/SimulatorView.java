package de.simulator.client.view;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.user.client.ui.*;

import de.simulator.client.presenter.*;

public class SimulatorView extends Composite implements
		SimulatorPresenter.Display {
	// Panels
	private VerticalPanel menu = new VerticalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel browserPanel = new HorizontalPanel();
	private HorizontalPanel configPanel = new HorizontalPanel();
	// Buttons
	private Button reloadButton = new Button("reload");
	private Button runButton = new Button("run");
	private Button pushButton = new Button("push");
	// Charts
	private Chart channels = new Chart().setType(Series.Type.SPLINE)
			.setChartTitleText("Lastgang").setMarginRight(10);
	private Chart preViewDevice = new Chart().setType(Series.Type.SPLINE)
			.setChartTitleText("Vorschau").setMarginRight(10);
	// Label
	private Label testLabel = new Label("test");
	// Punktmenge
	Series series = channels.createSeries().setName("Leistungsaufnahme")
			.setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });

	Series preViewSeries = preViewDevice.createSeries()
			.setName("Leistungsaufnahme des Geraets")
			.setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });

	public SimulatorView() {
		channels.addSeries(series);
		channels.setStyleName("channels");

		preViewDevice.addSeries(preViewSeries);
		preViewDevice.setStyleName("preViewDevice");

		configPanel.add(preViewDevice);
		configPanel.addStyleName("configPanel");
		configPanel.setCellWidth(preViewDevice, "100%");

		menu.add(runButton);
		menu.add(reloadButton);
		menu.add(pushButton);
		menu.addStyleName("menu");

		mainPanel.add(configPanel);
		mainPanel.add(channels);
		mainPanel.addStyleName("mainpanel");

		browserPanel.add(menu);
		browserPanel.add(mainPanel);
		browserPanel.addStyleName("browserpanel");
		browserPanel.setCellWidth(mainPanel, "100%");

		initWidget(browserPanel);
	}

	public Widget asWidget() {
		return this;
	}
}
