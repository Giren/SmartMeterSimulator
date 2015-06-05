package de.simulator.client.presenter;

import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.HasDropHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropDataGrid;

import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.simulator.client.SimulatorServiceAsync;
import de.simulator.client.event.AddDeviceEvent;
import de.simulator.client.event.AddDeviceEventHandler;
import de.simulator.client.event.DeviceSelectionChangeEvent;
import de.simulator.client.event.DeviceSelectionChangeEventHandler;
import de.simulator.client.event.ReloadDatabaseEvent;
import de.simulator.client.event.ReloadDatabaseEventHandler;
import de.simulator.client.view.DeviceDialogView;
import de.simulator.client.view.MyResources;
import de.simulator.client.view.SimulatorView;
import de.simulator.shared.Device;
import de.simulator.shared.SimulatorDevice;

public class SimulatorPresenter implements Presenter {
	private final SimulatorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	private int deviceID;

	public interface Display {
		HasDropHandler addDevice();

		HasClickHandlers reloadDatabase();

		Chart getLoadProfilePreViewChart();

		Chart getLoadProfileViewChart();

		ListDataProvider<Device> getCellList();

		DragAndDropDataGrid<Device> getDeviceDataGrid();

		SingleSelectionModel<Device> getSingleSelectionModel();

		SimulatorDevice getSimulatorDevice();
		
		Widget asWidget();
	}

	public SimulatorPresenter(SimulatorServiceAsync rpcService,
			HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		deviceID = 0;
	}

	public void bind() {
		// Hier alle Events abhandeln, die SimulatorView() betreffen

		// Event Reload Button
		eventBus.addHandler(ReloadDatabaseEvent.TYPE,
				new ReloadDatabaseEventHandler() {
					public void onReloadDatabase(ReloadDatabaseEvent event) {
						reloadDatabase();
					}
				});

		display.reloadDatabase().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ReloadDatabaseEvent());
			}
		});

		// Ein Device auswählen
		eventBus.addHandler(DeviceSelectionChangeEvent.TYPE,
				new DeviceSelectionChangeEventHandler() {

					@Override
					public void onSelectionChange(
							DeviceSelectionChangeEvent event) {
						Device selected = event.getDevice();
						if (selected != null) {
							display.getLoadProfilePreViewChart()
									.removeAllSeries();
							display.getLoadProfilePreViewChart().setTitle(
									new ChartTitle().setText("Vorschau "
											+ selected.getManufacturer() + ", "
											+ selected.getName()), null);
							display.getLoadProfilePreViewChart()
									.addSeries(
											arrayListToSeries(selected
													.getLoadProfile()), true,
											true);
						}
					}
				});
		display.getDeviceDataGrid().getSelectionModel()
				.addSelectionChangeHandler(new Handler() {

					@Override
					public void onSelectionChange(
							com.google.gwt.view.client.SelectionChangeEvent event) {
						// Window.alert( "fire onselectionchange");
						eventBus.fireEvent(new DeviceSelectionChangeEvent(
								display.getSingleSelectionModel()
										.getSelectedObject()));
					}

				});
		
		// Event neues Device hinzufügen
		eventBus.addHandler(AddDeviceEvent.TYPE, new AddDeviceEventHandler() {
			public void onAddDevice(AddDeviceEvent event) {
				//doAddNewDeviceToChart(event.getDevice());
				display.getCellList().getList().add( event.getDevice());
				display.getLoadProfileViewChart().addSeries(
						arrayListToSeries( event.getDevice().getLoadProfile()), true, true);
				//showDeviceWindow(event.getDevice());
			}
		});

		display.addDevice().addDropHandler(new DropEventHandler() {
			@Override
			public void onDrop(DropEvent event) {
				Device selected = event.getDraggableData();
				doAddNewDeviceToChart( selected);
				//eventBus.fireEvent(new AddDeviceEvent(selected));
			}
		});
	}


	private void reloadDatabase() {
		Window.alert("reloadDatabase");
	}

	private void doAddNewDeviceToChart(Device device) {
		//display.getCellList().getList().add(device.getName());
		//display.getCellList().getList().add( device);
		//display.getLoadProfileViewChart().addSeries(
		//		arrayListToSeries(device.getLoadProfile()), true, true);
		showDeviceWindow(device);
	}
	
	private void showDeviceWindow(Device device) {
		DeviceDialogPresenter dialogPresenter = new DeviceDialogPresenter(eventBus, new DeviceDialogView());
		Device confDevice = new Device(
				String.valueOf(deviceID++),
				device.getCategory(),
				device.getManufacturer(),
				device.getName(),
				device.getDescription(),
				device.getLoadProfile());
		dialogPresenter.go(confDevice);
	}

	private Series arrayListToSeries(ArrayList<Integer> arrayList) {
		Series newSeries = new Chart().createSeries().setName(
				"Leistungsaufnahme");

		for (int i = 0; i < arrayList.size(); i++) {
			newSeries.addPoint(arrayList.get(i));
		}

		return newSeries;
	}

	private void initDeviceDataGrid() {
		rpcService.getDeviceList(new AsyncCallback<ArrayList<Device>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Cannot get DeviceList");
			}

			@Override
			public void onSuccess(ArrayList<Device> result) {
				display.getDeviceDataGrid().setRowData(0, result);
				display.getDeviceDataGrid().addDragStartHandler(
						new DragStartEventHandler() {
							@Override
							public void onDragStart(
									gwtquery.plugins.draggable.client.events.DragStartEvent event) {
								Device newDevice = event.getDraggableData();
								Element helper = event.getHelper();
								
								SafeHtmlBuilder sb = new SafeHtmlBuilder();
						        // reuse the contact cell to render the inner html of the drag helper.
						        new SimulatorView.DeviceCell( MyResources.INSTANCE.deviceImage()).render(null, newDevice, sb);
						        helper.setInnerHTML(sb.toSafeHtml().asString());
							}
						});
			}
		});
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		initDeviceDataGrid();
	}
}
