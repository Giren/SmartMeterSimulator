package de.simulator.client.presenter;

import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.HasDropHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropDataGrid;

import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
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
import de.simulator.shared.Device;

public class SimulatorPresenter implements Presenter {
	private final SimulatorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public interface Display {
		HasDropHandler addDevice();

		HasClickHandlers reloadDatabase();

		Chart getLoadProfilePreViewChart();

		Chart getLoadProfileViewChart();

		ListDataProvider<String> getCellList();

		DragAndDropDataGrid<Device> getDeviceDataGrid();

		SingleSelectionModel<Device> getSingleSelectionModel();

		Widget asWidget();
	}

	public SimulatorPresenter(SimulatorServiceAsync rpcService,
			HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
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
						// Device selected = event.getDevice();
						// display.getDeviceDataGrid().getSelectionModel().;
						// Window.alert("alertmessage " +
						// event.getDevice().getName());
						Device selected = event.getDevice();
						if (selected != null) {
							// Window.alert( "You selected: " +
							// selected.getName());
							display.getLoadProfilePreViewChart()
									.removeAllSeries();
							// preViewDevice.setTitle( new
							// ChartTitle().setText("Title"), new
							// ChartSubtitle().setText("SubTitle"));
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
				doAddNewDeviceToChart(event.getDevice());
			}
		});

		display.addDevice().addDropHandler(new DropEventHandler() {

			@Override
			public void onDrop(DropEvent event) {
				Device selected = event.getDraggableData();
				eventBus.fireEvent(new AddDeviceEvent(selected));
			}
		});
	}

	private void reloadDatabase() {
		Window.alert("reloadDatabase");
	}

	private void doAddNewDeviceToChart(Device device) {
		// Window.alert( "AddNewDeviceToChart der ID:"+id);
		display.getCellList().getList().add(device.getName());
		display.getLoadProfileViewChart().addSeries(
				arrayListToSeries(device.getLoadProfile()), true, true);
	}

	private Series arrayListToSeries(ArrayList<Integer> arrayList) {
		Series newSeries = new Chart().createSeries().setName(
				"Leistungsaufnahme");

		for (int i = 0; i < arrayList.size(); i++) {
			newSeries.addPoint(arrayList.get(i));
		}

		return newSeries;
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
	}
}
