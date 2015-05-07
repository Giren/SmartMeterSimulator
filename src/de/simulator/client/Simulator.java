package de.simulator.client;

import org.moxieapps.gwt.highcharts.client.*;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.CellBrowser.Builder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

public class Simulator implements EntryPoint {
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
	private Chart channels = new Chart().setType(Series.Type.SPLINE).setChartTitleText("Lastgang").setMarginRight(10);
	private Chart preViewDevice = new Chart().setType(Series.Type.SPLINE).setChartTitleText("Vorschau").setMarginRight(10);
	// Labels
	private Label test4 = new Label("menu");

	// cellbrowser
	private static class CustomTreeModel implements TreeViewModel {

		/**
		 * Get the {@link NodeInfo} that provides the children of the specified
		 * value.
		 */
		public <T> NodeInfo<?> getNodeInfo(T value) {
			/*
			 * Create some data in a data provider. Use the parent value as a
			 * prefix for the next level.
			 */
			ListDataProvider<String> dataProvider = new ListDataProvider<String>();
			for (int i = 0; i < 2; i++) {
				dataProvider.getList().add(value + "." + String.valueOf(i));
			}

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<String>(dataProvider, new TextCell());
		}

		/**
		 * Check if the specified value represents a leaf node. Leaf nodes
		 * cannot be opened.
		 */
		public boolean isLeaf(Object value) {
			// The maximum length of a value is ten characters.
			return value.toString().length() > 10;
		}
	}

	
	public void onModuleLoad() {
		Series series = channels.createSeries().setName("Leistungsaufnahme").
				setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
		channels.addSeries(series);
		channels.setStyleName("channels");
		
		Series preViewSeries = preViewDevice.createSeries().setName("Leistungsaufnahme des Geraets").
				setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
		preViewDevice.addSeries(preViewSeries);
		preViewDevice.setStyleName("preViewDevice");
			
	    TreeViewModel model = new CustomTreeModel();
	    Builder<String> cellTreeBuilder = new Builder<String>(model,"item1");
	    CellBrowser deviceTree = cellTreeBuilder.build();
	    deviceTree.setSize("625px","300px");

		configPanel.add(deviceTree);
		configPanel.add(preViewDevice);
		configPanel.addStyleName("configPanel");
		configPanel.setCellWidth(deviceTree, "200px");
		configPanel.setCellWidth(preViewDevice, "100%");

		menu.add(test4);
		menu.addStyleName("menu");

		mainPanel.add(configPanel);
		mainPanel.add(channels);
		mainPanel.addStyleName("mainpanel");

		browserPanel.add(menu);
		browserPanel.add(mainPanel);
		browserPanel.addStyleName("browserpanel");
		browserPanel.setCellWidth(menu, "300px");
		browserPanel.setCellWidth(mainPanel, "100%");

		RootPanel.get("entry").add(browserPanel);
	}
}
