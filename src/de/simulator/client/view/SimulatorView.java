package de.simulator.client.view;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.droppable.client.events.HasDropHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropColumn;
import gwtquery.plugins.droppable.client.gwt.DragAndDropDataGrid;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.simulator.client.presenter.SimulatorPresenter;
import de.simulator.shared.Device;
import de.simulator.shared.SimulatorDevice;

public class SimulatorView extends Composite implements SimulatorPresenter.Display {	
	// Panels
	private VerticalPanel menu = new VerticalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel browserPanel = new HorizontalPanel();
	private HorizontalPanel configPanel = new HorizontalPanel();
	private HorizontalPanel LoadProfilePanel = new HorizontalPanel();
	
	// Buttons
	private Button reloadButton = new Button( "reload");
	private Button runButton = new Button( "run");
	private Button pushButton = new Button( "push");
	private Button resetButton = new Button ( "Reset");
	
	// Charts
	private Chart channels = new Chart().setType( Series.Type.SPLINE)
			.setChartTitleText( "Lastgang").setMarginRight( 10);
	private Chart preViewDevice = new Chart().setType( Series.Type.SPLINE)
			.setChartTitleText( "Vorschau").setMarginRight( 10);

	// Grid
	private DragAndDropDataGrid<Device> DeviceDataGrid;
	
	// CellList
	@UiField(provided = true)
	private DroppableWidget<CellList<Device>> deviceCellList;
	
	//Variables
	private SingleSelectionModel<Device> selectionModelDnD;
	private ListDataProvider<Device> addedDeviceList;
	
	// Divers
	SimulatorDevice simulatorDevice = new SimulatorDevice();
	
	public SimulatorView() {
		channels.setStyleName( "channels");
		preViewDevice.setStyleName( "preViewDevice");
		
		this.channels.getXAxis().setAxisTitleText( "time [s]", true);
		this.channels.getYAxis().setAxisTitleText( "W", true);
		
		this.preViewDevice.getXAxis().setAxisTitleText( "time [s]", true);
		this.preViewDevice.getYAxis().setAxisTitleText( "W", true);

		createDeviceTableDND();
		configPanel.add( DeviceDataGrid); // erste Spalte DeviceTabelle
		configPanel.setCellWidth( DeviceDataGrid, "50%");
		configPanel.add( preViewDevice); // zweite Spalte VorschauGraph
		configPanel.setCellWidth( preViewDevice, "50%");

		configPanel.addStyleName("configPanel");
		
		this.runButton.setSize( "50px", "50px");
		this.reloadButton.setSize( "50px", "50px");
		this.pushButton.setSize( "50px", "50px");
		this.resetButton.setSize( "50px", "50px");

		menu.add( runButton);
		menu.add( reloadButton);
		//menu.add( pushButton);
		menu.add( resetButton);
		menu.addStyleName( "menu");

		mainPanel.add( configPanel);
		mainPanel.addStyleName( "mainpanel");

		LoadProfilePanel.addStyleName( "configPanel");
		createDroppableList();
		LoadProfilePanel.add( deviceCellList);
		LoadProfilePanel.setCellWidth( deviceCellList, "15%");

		LoadProfilePanel.add( this.channels);
		LoadProfilePanel.setCellWidth( channels, "85%");
		
		mainPanel.add( LoadProfilePanel);

		browserPanel.add( menu);
		browserPanel.add( mainPanel);
		browserPanel.addStyleName( "browserpanel");
		browserPanel.setCellWidth( mainPanel, "100%");

		initWidget( browserPanel);
	}

	@Override
	public HasClickHandlers reloadDatabase() {
		return reloadButton;
	}
	
	public HasClickHandlers getRunButton() {
		return this.runButton;
	}

	public HasDropHandler addDevice() {
		return deviceCellList;
	}
	
	public Button getResetButton() {
		return this.resetButton;
	}

	public Widget asWidget() {
		return this;
	}

	public Chart getLoadProfilePreViewChart() {
		return this.preViewDevice;
	}

	public Chart getLoadProfileViewChart() {
		return this.channels;
	}

	public ListDataProvider<Device> getCellList() {
		return this.addedDeviceList;
	}

	public DragAndDropDataGrid<Device> getDeviceDataGrid() {
		return this.DeviceDataGrid;
	}

	public SingleSelectionModel<Device> getSingleSelectionModel() {
		return this.selectionModelDnD;
	}
	
	public SimulatorDevice getSimulatorDevice() {
		return this.simulatorDevice;
	}

	private void createDeviceTableDND() {
		DeviceDataGrid = new DragAndDropDataGrid<Device>();

		final MultiSelectionModel<Device> selectionModel = new MultiSelectionModel<Device>();
		DeviceDataGrid.setSelectionModel(selectionModel);

		initTableColumns(selectionModel);
		DeviceDataGrid.setSize("100%", "300px");

		selectionModelDnD = new SingleSelectionModel<Device>();
		DeviceDataGrid.setSelectionModel(selectionModelDnD);
	}

	static interface Templates extends SafeHtmlTemplates {
		Templates INSTANCE = GWT.create(Templates.class);
		@Template("<div id='dragHelper' style='border:1px solid black; background-color:#ffffff; color:black; width:150px;'></div>")
		SafeHtml outerHelper();
	}

	private void initDragOperation(DragAndDropColumn<?, ?> column) {
		// retrieve draggableOptions on the column
		DraggableOptions draggableOptions = column.getDraggableOptions();
		draggableOptions.setHelper($(Templates.INSTANCE.outerHelper().asString()));
		draggableOptions.setOpacity((float) 0.8);
		draggableOptions.setCursor(Cursor.MOVE);
		draggableOptions.setRevert(RevertOption.ON_INVALID_DROP);
		draggableOptions.setCancel("select");
		draggableOptions.setAppendTo("body");
	}

	private void initTableColumns(final SelectionModel<Device> selectionModel) {

		// Category
		DragAndDropColumn<Device, String> categoryColumn = new DragAndDropColumn<Device, String>(
				new TextCell()) {
			@Override
			public String getValue(Device object) {
				return object.getCategory();
			}
		};
		categoryColumn.setCellDraggableOnly();
		initDragOperation(categoryColumn);
		DeviceDataGrid.addColumn(categoryColumn, "Kategorie");

		// Hersteller
		DragAndDropColumn<Device, String> manufacturerColumn = new DragAndDropColumn<Device, String>(
				new TextCell()) {
			@Override
			public String getValue(Device object) {
				return object.getManufacturer();
			}
		};
		manufacturerColumn.setCellDraggableOnly();
		initDragOperation(manufacturerColumn);
		DeviceDataGrid.addColumn(manufacturerColumn, "Hersteller");

		// Gerätename
		DragAndDropColumn<Device, String> deviceNameColumn = new DragAndDropColumn<Device, String>(
				new TextCell()) {
			@Override
			public String getValue(Device object) {
				return object.getName();
			}
		};
		deviceNameColumn.setCellDraggableOnly();
		initDragOperation(deviceNameColumn);
		DeviceDataGrid.addColumn(deviceNameColumn, "Gerätename");

		// Beschreibung
		DragAndDropColumn<Device, String> descriptionColumn = new DragAndDropColumn<Device, String>(
				new TextCell()) {
			@Override
			public String getValue(Device object) {
				return object.getDescription();
			}
		};
		descriptionColumn.setCellDraggableOnly();
		initDragOperation(descriptionColumn);
		DeviceDataGrid.addColumn(descriptionColumn, "Beschreibung");
	}


	private void createDroppableList() {
		DeviceCell deviceCell = new DeviceCell( MyResources.INSTANCE.deviceImage());
		CellList<Device> cellList = new CellList<Device>(deviceCell);
		cellList.setStyleName("exportCellList");
		addedDeviceList = new ListDataProvider<Device>();
		addedDeviceList.addDataDisplay(cellList);
		// make the cell list droppable.
		deviceCellList = new DroppableWidget<CellList<Device>>(cellList);
		deviceCellList.setDroppableHoverClass( "droppableHover");
		deviceCellList.setActiveClass( "droppableActive");
	}

	
	public static class DeviceCell extends AbstractCell<Device> {
		private final String imageHtml;

		public DeviceCell(ImageResource image) {
			this.imageHtml = AbstractImagePrototype.create(image).getHTML();
		}

		@Override
		public void render(Context context, Device value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}
			sb.appendHtmlConstant("<table>");
			// Add the device image
			sb.appendHtmlConstant("<tr><td rowspan='3'>");
			sb.appendHtmlConstant(imageHtml);
			sb.appendHtmlConstant("</td>");
			// Add the name and manufacturer of the device
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped(value.getName());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getManufacturer());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}
}
