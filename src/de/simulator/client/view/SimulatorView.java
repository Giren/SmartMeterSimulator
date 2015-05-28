package de.simulator.client.view;

import static com.google.gwt.query.client.GQuery.$;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropColumn;
import gwtquery.plugins.droppable.client.gwt.DragAndDropDataGrid;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
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
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sun.corba.se.pept.transport.ContactInfo;

import de.simulator.client.presenter.SimulatorPresenter;

public class SimulatorView extends Composite implements
		SimulatorPresenter.Display {
	
	
	DragAndDropDataGrid<Device> DeviceDataGrid;
	  /**
	   * The droppable "device" cell list.
	   */
	  @UiField(provided = true)
	  DroppableWidget<CellList<String>> deviceCellList;
	  
	  
	
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
	
	
	private ArrayList<Device> getDevices() {
		ArrayList<Device> deviceList = new ArrayList<Device>();
		
		deviceList.add(new Device("cat1", "WMF", "Mixer 400W", "Info1",	new Chart().createSeries().setPoints( new Number[] { 100, 123, 125, 110, 115, 150, 128 })));
		deviceList.add(new Device("cat2", "Grundig", "Kühlschrank", "Info2",
				new Chart().createSeries().setPoints(
						new Number[] { 90, 150, 115, 135, 120, 130,
								170 })));
		deviceList.add( new Device("cat3", "WMF", "Küchenmaschine", "Info3",
				new Chart().createSeries().setPoints(
						new Number[] { 95, 100, 135, 123, 140, 128,
								110 })) );
		deviceList.add(new Device("cat1", "WMF", "Mixer 400W", "Info1",
				new Chart().createSeries().setPoints(
						new Number[] { 100, 123, 125, 110, 115,
								150, 128 })) );
		deviceList.add(new Device("cat2", "Grundig", "Kühlschrank", "Info2",
				new Chart().createSeries().setPoints(
						new Number[] { 90, 150, 115, 135, 120, 130,
								170 })) );
		deviceList.add(new Device("cat3", "WMF", "Küchenmaschine", "Info3",
				new Chart().createSeries().setPoints(
						new Number[] { 95, 100, 135, 123, 140, 128,
								110 })) );
		deviceList.add(new Device("cat1", "WMF", "Mixer 400W", "Info1",
				new Chart().createSeries().setPoints(
						new Number[] { 100, 123, 125, 110, 115,
								150, 128 })) );
		deviceList.add(new Device("cat2", "Grundig", "Kühlschrank", "Info2",
				new Chart().createSeries().setPoints(
						new Number[] { 90, 150, 115, 135, 120, 130,
								170 })) );
		deviceList.add(new Device("cat3", "WMF", "Küchenmaschine", "Info3",
				new Chart().createSeries().setPoints(
						new Number[] { 95, 100, 135, 123, 140, 128,
								110 })) );
		deviceList.add(new Device("cat1", "WMF", "Mixer 400W", "Info1",
				new Chart().createSeries().setPoints(
						new Number[] { 100, 123, 125, 110, 115,
								150, 128 })) );
		deviceList.add(new Device("cat2", "Grundig", "Kühlschrank", "Info2",
				new Chart().createSeries().setPoints(
						new Number[] { 90, 150, 115, 135, 120, 130,
								170 })) );
		deviceList.add(new Device("cat3", "WMF", "Küchenmaschine", "Info3",
				new Chart().createSeries().setPoints(
						new Number[] { 95, 100, 135, 123, 140, 128,
								110 })) );
		deviceList.add(new Device("cat1", "WMF", "Mixer 400W", "Info1",
				new Chart().createSeries().setPoints(
						new Number[] { 100, 123, 125, 110, 115,
								150, 128 })) );
		deviceList.add(new Device("cat2", "Grundig", "Kühlschrank", "Info2",
				new Chart().createSeries().setPoints(
						new Number[] { 90, 150, 115, 135, 120, 130,
								170 })) );
		deviceList.add(new Device("cat3", "WMF", "Küchenmaschine", "Info3",
				new Chart().createSeries().setPoints(
						new Number[] { 95, 100, 135, 123, 140, 128,
								110 })) );		
		return deviceList;
	}
	
	
	// Testdaten
		private static final List<Device> DEVICES = Arrays
				.asList(new Device("cat1", "WMF", "Mixer 400W", "Info1",
						new Chart().createSeries().setPoints(
								new Number[] { 100, 123, 125, 110, 115, 150, 128 })),
						new Device("cat2", "Grundig", "Kühlschrank", "Info2",
								new Chart().createSeries().setPoints(
										new Number[] { 90, 150, 115, 135, 120, 130,
												170 })),
						new Device("cat3", "WMF", "Küchenmaschine", "Info3",
								new Chart().createSeries().setPoints(
										new Number[] { 95, 100, 135, 123, 140, 128,
												110 })),
						new Device("cat1", "WMF", "Mixer 400W", "Info1",
								new Chart().createSeries().setPoints(
										new Number[] { 100, 123, 125, 110, 115,
												150, 128 })),
						new Device("cat2", "Grundig", "Kühlschrank", "Info2",
								new Chart().createSeries().setPoints(
										new Number[] { 90, 150, 115, 135, 120, 130,
												170 })),
						new Device("cat3", "WMF", "Küchenmaschine", "Info3",
								new Chart().createSeries().setPoints(
										new Number[] { 95, 100, 135, 123, 140, 128,
												110 })),
						new Device("cat1", "WMF", "Mixer 400W", "Info1",
								new Chart().createSeries().setPoints(
										new Number[] { 100, 123, 125, 110, 115,
												150, 128 })),
						new Device("cat2", "Grundig", "Kühlschrank", "Info2",
								new Chart().createSeries().setPoints(
										new Number[] { 90, 150, 115, 135, 120, 130,
												170 })),
						new Device("cat3", "WMF", "Küchenmaschine", "Info3",
								new Chart().createSeries().setPoints(
										new Number[] { 95, 100, 135, 123, 140, 128,
												110 })),
						new Device("cat1", "WMF", "Mixer 400W", "Info1",
								new Chart().createSeries().setPoints(
										new Number[] { 100, 123, 125, 110, 115,
												150, 128 })),
						new Device("cat2", "Grundig", "Kühlschrank", "Info2",
								new Chart().createSeries().setPoints(
										new Number[] { 90, 150, 115, 135, 120, 130,
												170 })),
						new Device("cat3", "WMF", "Küchenmaschine", "Info3",
								new Chart().createSeries().setPoints(
										new Number[] { 95, 100, 135, 123, 140, 128,
												110 })),
						new Device("cat1", "WMF", "Mixer 400W", "Info1",
								new Chart().createSeries().setPoints(
										new Number[] { 100, 123, 125, 110, 115,
												150, 128 })),
						new Device("cat2", "Grundig", "Kühlschrank", "Info2",
								new Chart().createSeries().setPoints(
										new Number[] { 90, 150, 115, 135, 120, 130,
												170 })),
						new Device("cat3", "WMF", "Küchenmaschine", "Info3",
								new Chart().createSeries().setPoints(
										new Number[] { 95, 100, 135, 123, 140, 128,
												110 })));
	

	public SimulatorView() {
		
		channels.addSeries(series);
		channels.setStyleName("channels");

		preViewDevice.addSeries(preViewSeries);
		preViewDevice.setStyleName("preViewDevice");
		
		createDeviceTableDND();
		configPanel.add( DeviceDataGrid); // erste Spalte DeviceTabelle
		configPanel.setCellWidth(DeviceDataGrid, "50%");
		configPanel.add(preViewDevice); // zweite Spalte VorschauGraph
		configPanel.setCellWidth(preViewDevice, "50%");

		// configPanel.add(preViewDevice);
		//configPanel.setCellWidth(preViewDevice, "100%");

		configPanel.addStyleName("configPanel");
		
		menu.add(runButton);
		menu.add(reloadButton);
		menu.add(pushButton);
		menu.addStyleName("menu");

		mainPanel.add(configPanel);
		mainPanel.add(channels);
		mainPanel.addStyleName("mainpanel");
		
		LoadProfilePanel.addStyleName("configPanel");
		createDroppableList();
		// 1. Spalte CellList anzeigen
		LoadProfilePanel.add( deviceCellList);
		LoadProfilePanel.setCellWidth( deviceCellList , "20%");
		// 2. Spalte FinalLoadProfile anzeigen
		LoadProfilePanel.add( channels);
		LoadProfilePanel.setCellWidth( channels , "80%");
		mainPanel.add( LoadProfilePanel);
				
				
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
		      public void onDrop(gwtquery.plugins.droppable.client.events.DropEvent event) {
		        Device dev = event.getDraggableData();
		        series.addPoint(7, 100);
		        // first remove the contact to the table
			}
		});

		deviceCellList.addDomHandler(new DragOverHandler() {
			public void onDragOver(DragOverEvent event) {
			}
		}, DragOverEvent.getType());
		
		
	}

	public Widget asWidget() {
		return this;
	}
	
	
	private void createDeviceTableDND() {
		DeviceDataGrid = new DragAndDropDataGrid<Device>();

		// Pager
		
		// 
		// Add a selection model so we can select cells.
	    final MultiSelectionModel<Device> selectionModel = new MultiSelectionModel<Device>();
	    DeviceDataGrid.setSelectionModel( selectionModel);
	    
	    // Attach a column sort handler to the ListDataProvider to sort the list.
	    ListHandler<Device> sortHandler = new ListHandler<Device>( getDevices());
	    DeviceDataGrid.addColumnSortHandler(sortHandler);
	    
	    // Initialize the columns.
	    initTableColumns(selectionModel);
	    
	    DeviceDataGrid.setRowData( 0, DEVICES);
	    //DeviceDataGrid.setVisible( true);
	    DeviceDataGrid.setSize("100%", "300px");
	    
	    
	 // Add a selection model to handle user selection
		final SingleSelectionModel<Device> selectionModelDnD = new SingleSelectionModel<Device>();
		DeviceDataGrid.setSelectionModel(selectionModelDnD);
		selectionModelDnD
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						Device selected = selectionModelDnD.getSelectedObject();
						if (selected != null) {
							// Window.alert( "You selected: " +
							// selected.getName());
							preViewDevice.removeAllSeries();
							// preViewDevice.setTitle( new
							// ChartTitle().setText("Title"), new
							// ChartSubtitle().setText("SubTitle"));
							preViewDevice.setTitle(
									new ChartTitle().setText("Vorschau "
											+ selected.getManufacturer() + ", "
											+ selected.getName()), null);
							preViewDevice.addSeries(selected.loadProfile, true,
									true);
						}
					}
				});
		
		
	    // fill the helper when the drag operation start
		DeviceDataGrid.addDragStartHandler( new DragStartEventHandler() {
			@Override
	      public void onDragStart( gwtquery.plugins.draggable.client.events.DragStartEvent  event) {
	        Device device = event.getDraggableData();
	        Element helper = event.getHelper();
	      }
	    });	    
	}
	
	
	/**
	   * Template for the helper
	   * 
	   * @author Julien Dramaix (julien.dramaix@gmail.com)
	   * 
	   */
	  static interface Templates extends SafeHtmlTemplates {
	    Templates INSTANCE = GWT.create(Templates.class);

	    @Template("<div id='dragHelper' style='border:1px solid black; background-color:#ffffff; color:black; width:150px;'></div>")
	    SafeHtml outerHelper();
	  }
	
	 /**
	   * Init draggable operation for column
	   * 
	   * @param draggableOptions
	   */
	  private void initDragOperation(DragAndDropColumn<?, ?> column) {

	    // retrieve draggableOptions on the column
	    DraggableOptions draggableOptions = column.getDraggableOptions();
	    // use template to construct the helper. The content of the div will be set
	    // after
	    draggableOptions.setHelper($(Templates.INSTANCE.outerHelper().asString()));
	    // opacity of the helper
	    draggableOptions.setOpacity((float) 0.8);
	    // cursor to use during the drag operation
	    draggableOptions.setCursor(Cursor.MOVE);
	    // set the revert option
	    draggableOptions.setRevert(RevertOption.ON_INVALID_DROP);
	    // prevents dragging when user click on the category drop-down list
	    draggableOptions.setCancel("select");
	    //attach the helper to the document because datagrid is scrollable
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

	
	 private static final List<String> DAYS = Arrays.asList("Sunday", "Monday",
		      "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
	  /**
	   * Create a droppable CellList
	   */
	  private void createDroppableList() {
	    // Create a ConcactCell
		//DeviceCell deviceCell = new DeviceCell(Resource.INSTANCE.contact());
		// Create a cell to render each value.
	    TextCell deviceCell = new TextCell();

		// Create a CellList that uses the deviceCell
	    //CellList<Device> cellList = new CellList<Device>(deviceCell);
	    CellList<String> cellList = new CellList<String>(deviceCell);
	    cellList.setWidth( "100%");
	    cellList.setStyleName( "browserpanel");
	    
	    //cellList.addStyleName(Resource.INSTANCE.css().exportCellList());

	    //cellList.setPageSize(30);
	    //cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
	    // temporary ListDataProvider to keep list of contacts to delete
	    final ListDataProvider<String> addedDeviceList = new ListDataProvider<String>();
	    
	    addedDeviceList.addDataDisplay(cellList);
	    //cellList.setRowData(0, DAYS);
	    // make the cell list droppable.
	    deviceCellList = new DroppableWidget<CellList<String>>(cellList);
	    deviceCellList.setWidth( "100%");

	    // setup the drop operation
	    //deviceCellList.setDroppableHoverClass(Resource.INSTANCE.css().droppableHover());
	    //deviceCellList.setActiveClass(Resource.INSTANCE.css().droppableActive());
	    deviceCellList.addDropHandler(new DropEventHandler() {

	    	@Override
	      public void onDrop(gwtquery.plugins.droppable.client.events.DropEvent event) {
	        Device deviceToAdd = event.getDraggableData();
	        // first remove the contact to the table

	        addedDeviceList.getList().add( deviceToAdd.getName());
	        channels.addSeries( deviceToAdd.loadProfile);

	      }
	    });
	  }
	  
	  
	  /**
	   * The Cell used to render a {@link ContactInfo}.
	   * 
	   * Code coming from the GWT showcase
	   * 
	   */
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
	    public void render(Context context,
	        Device value, SafeHtmlBuilder sb) {
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
