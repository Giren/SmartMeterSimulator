package de.simulator.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.simulator.client.SimulatorServiceAsync;

public class SimulatorPresenter implements Presenter {
	private final SimulatorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public interface Display {
		Widget asWidget();
	}

	public SimulatorPresenter(SimulatorServiceAsync rpcService,
			HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind() {
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
	}
}
