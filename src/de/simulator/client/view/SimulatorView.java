package de.simulator.client.view;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.HasDropHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropColumn;
import gwtquery.plugins.droppable.client.gwt.DragAndDropDataGrid;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.simulator.client.SimulatorServiceAsync;
import de.simulator.client.presenter.SimulatorPresenter;
import de.simulator.client.widgets.LoadProfileChartWidget;
import de.simulator.shared.Device;

public class SimulatorView extends Composite implements
		SimulatorPresenter.Display {

	DragAndDropDataGrid<Device> DeviceDataGrid;

	@UiField(provided = true)
	DroppableWidget<CellList<String>> deviceCellList;

	private SimulatorServiceAsync rpcService;
	// Panels
	private VerticalPanel menu = new VerticalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel browserPanel = new HorizontalPanel();
	private HorizontalPanel configPanel = new HorizontalPanel();
	private HorizontalPanel LoadProfilePanel = new HorizontalPanel();
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

	ListDataProvider<String> addedDeviceList;

	private SingleSelectionModel<Device> selectionModelDnD;

	public SimulatorView(SimulatorServiceAsync rpc) {
		this.rpcService = rpc;

		channels.addSeries(series);
		channels.setStyleName("channels");
		
		preViewDevice.addSeries(preViewSeries);
		preViewDevice.setStyleName("preViewDevice");

		createDeviceTableDND();
		configPanel.add(DeviceDataGrid); // erste Spalte DeviceTabelle
		configPanel.setCellWidth(DeviceDataGrid, "50%");
		configPanel.add(preViewDevice); // zweite Spalte VorschauGraph
		configPanel.setCellWidth(preViewDevice, "50%");

		configPanel.addStyleName("configPanel");

		menu.add(runButton);
		menu.add(reloadButton);
		menu.add(pushButton);
		menu.addStyleName("menu");

		mainPanel.add(configPanel);
		// mainPanel.add(channels);
		mainPanel.addStyleName("mainpanel");

		LoadProfilePanel.addStyleName("configPanel");
		createDroppableList();
		// 1. Spalte CellList anzeigen
		LoadProfilePanel.add(deviceCellList);
		LoadProfilePanel.setCellWidth(deviceCellList, "20%");
		// 2. Spalte FinalLoadProfile anzeigen
		// LoadProfilePanel.add( new LoadProfileView());
		
			

		//LoadProfilePanel.add(channels);
		//LoadProfilePanel.setCellWidth(channels, "80%");
		
		LoadProfileChartWidget mychart = new LoadProfileChartWidget( "title");	
		mychart.addSeries( series );
		channels = mychart.getLoadProfileChart();
		LoadProfilePanel.add( mychart);
		LoadProfilePanel.setCellWidth( mychart, "80%");
		
		mainPanel.add(LoadProfilePanel);

		browserPanel.add(menu);
		browserPanel.add(mainPanel);
		browserPanel.addStyleName("browserpanel");
		browserPanel.setCellWidth(mainPanel, "100%");

		initWidget(browserPanel);

		testLabel.addDragStartHandler(new DragStartHandler() {

			@Override
			public void onDragStart(DragStartEvent event) { // required
				event.setData("text", "Hello World");
			}
		});

		deviceCellList.addDomHandler(new DropHandler() {
			public void onDrop(DropEvent event) {
				series.addPoint(7, 100);
			}
		}, DropEvent.getType());

		deviceCellList.addDropHandler(new DropEventHandler() {
			@Override
			public void onDrop(
					gwtquery.plugins.droppable.client.events.DropEvent event) {
				Device dev = event.getDraggableData();
				// series.addPoint(7, 100);
				// first remove the contact to the table
			}
		});

		deviceCellList.addDomHandler(new DragOverHandler() {
			public void onDragOver(DragOverEvent event) {
			}
		}, DragOverEvent.getType());

	}

	@Override
	public HasClickHandlers reloadDatabase() {
		return reloadButton;
	}

	public HasDropHandler addDevice() {
		return deviceCellList;
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

	public ListDataProvider<String> getCellList() {
		return this.addedDeviceList;
	}

	public DragAndDropDataGrid<Device> getDeviceDataGrid() {
		return this.DeviceDataGrid;
	}

	public SingleSelectionModel<Device> getSingleSelectionModel() {
		return this.selectionModelDnD;
	}

	private void createDeviceTableDND() {
		DeviceDataGrid = new DragAndDropDataGrid<Device>();

		// Add a selection model so we can select cells.
		final MultiSelectionModel<Device> selectionModel = new MultiSelectionModel<Device>();
		DeviceDataGrid.setSelectionModel(selectionModel);

		// Initialize the columns.
		initTableColumns(selectionModel);

		// Attach a column sort handler to the ListDataProvider to sort the
		// list.
		// ListHandler<Device> sortHandler = new
		// ListHandler<Device>(getDevices());
		// DeviceDataGrid.addColumnSortHandler(sortHandler);

		DeviceDataGrid.setSize("100%", "300px");

		// Add a selection model to handle user selection
		selectionModelDnD = new SingleSelectionModel<Device>();
		DeviceDataGrid.setSelectionModel(selectionModelDnD);

		rpcService.getDeviceList(new AsyncCallback<ArrayList<Device>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Cannot get DeviceList");
			}

			@Override
			public void onSuccess(ArrayList<Device> result) {
				DeviceDataGrid.setRowData(0, result);
				// DeviceDataGrid.setVisible( true);
				// DeviceDataGrid.setSize("100%", "300px");
				//
				// // Add a selection model to handle user selection
				// selectionModelDnD = new SingleSelectionModel<Device>();
				// DeviceDataGrid.setSelectionModel( selectionModelDnD);

				// fill the helper when the drag operation start
				DeviceDataGrid.addDragStartHandler(new DragStartEventHandler() {
					@Override
					public void onDragStart(
							gwtquery.plugins.draggable.client.events.DragStartEvent event) {
						Device device = event.getDraggableData();
						Element helper = event.getHelper();
					}
				});
			}
		});

	}

	static interface Templates extends SafeHtmlTemplates {
		Templates INSTANCE = GWT.create(Templates.class);

		@Template("<div id='dragHelper' style='border:1px solid black; background-color:#ffffff; color:black; width:150px;'></div>")
		SafeHtml outerHelper();
	}

	private void initDragOperation(DragAndDropColumn<?, ?> column) {

		// retrieve draggableOptions on the column
		DraggableOptions draggableOptions = column.getDraggableOptions();
		// use template to construct the helper. The content of the div will be
		// set
		// after
		draggableOptions.setHelper($(Templates.INSTANCE.outerHelper()
				.asString()));
		// opacity of the helper
		draggableOptions.setOpacity((float) 0.8);
		// cursor to use during the drag operation
		draggableOptions.setCursor(Cursor.MOVE);
		// set the revert option
		draggableOptions.setRevert(RevertOption.ON_INVALID_DROP);
		// prevents dragging when user click on the category drop-down list
		draggableOptions.setCancel("select");
		// attach the helper to the document because datagrid is scrollable
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
		// Create a ConcactCell
		// DeviceCell deviceCell = new DeviceCell(Resource.INSTANCE.contact());
		// Create a cell to render each value.
		TextCell deviceCell = new TextCell();

		// Create a CellList that uses the deviceCell
		// CellList<Device> cellList = new CellList<Device>(deviceCell);
		CellList<String> cellList = new CellList<String>(deviceCell);
		cellList.setWidth("100%");
		cellList.setStyleName("browserpanel");

		// cellList.addStyleName(Resource.INSTANCE.css().exportCellList());

		// cellList.setPageSize(30);
		// cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		// temporary ListDataProvider to keep list of contacts to delete
		addedDeviceList = new ListDataProvider<String>();

		addedDeviceList.addDataDisplay(cellList);
		// cellList.setRowData(0, DAYS);
		// make the cell list droppable.
		deviceCellList = new DroppableWidget<CellList<String>>(cellList);
		deviceCellList.setWidth("100%");

		// setup the drop operation
		// deviceCellList.setDroppableHoverClass(Resource.INSTANCE.css().droppableHover());
		// deviceCellList.setActiveClass(Resource.INSTANCE.css().droppableActive());

		// deviceCellList.addDropHandler(new DropEventHandler() {
		//
		// @Override
		// public void onDrop(
		// gwtquery.plugins.droppable.client.events.DropEvent event) {
		// Device deviceToAdd = event.getDraggableData();
		// // first remove the contact to the table
		//
		// addedDeviceList.getList().add(deviceToAdd.getName());
		// channels.addSeries(deviceToAdd.loadProfile, true, true);
		//
		// }
		// });
	}

	private static class DeviceCell extends AbstractCell<Device> {

		/**
		 * The html of the image used for contacts.
		 * 
		 */
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

			// Add the contact image.
			sb.appendHtmlConstant("<tr><td rowspan='3'>");
			sb.appendHtmlConstant(imageHtml);
			sb.appendHtmlConstant("</td>");

			// Add the name and address.
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped(value.getName());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getManufacturer());
			sb.appendHtmlConstant("</td></tr></table>");

		}
	}
}
