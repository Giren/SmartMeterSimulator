package de.simulator.client.presenter;

import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.HasDropHandler;

import java.util.List;

import javax.xml.bind.annotation.DomHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDropHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.simulator.client.SimulatorServiceAsync;
import de.simulator.client.event.AddDeviceEvent;
import de.simulator.client.event.ReloadDatabaseEvent;

public class SimulatorPresenter implements Presenter {
	private final SimulatorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public interface Display {
		HasDropHandler addDevice();
		HasClickHandlers reloadDatabase();
		Widget asWidget();
	}

	public SimulatorPresenter(SimulatorServiceAsync rpcService,
			HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind() {

		display.reloadDatabase().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ReloadDatabaseEvent());
			}
		});

		display.addDevice().addDropHandler(new DropEventHandler() {
			
			@Override
			public void onDrop(DropEvent event) {
				int id = 4711;
				eventBus.fireEvent(new AddDeviceEvent(id));
			}
		});
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
	}
}
