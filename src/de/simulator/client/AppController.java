package de.simulator.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import de.simulator.client.presenter.Presenter;
import de.simulator.client.presenter.SimulatorPresenter;
import de.simulator.client.view.SimulatorView;

public class AppController implements Presenter, ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private final SimulatorServiceAsync rpcService;
	private HasWidgets container;

	public AppController( SimulatorServiceAsync rpcService, HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		// TODO 
		// Hier alle globalen Events abhandeln, welche eine Umschaltung der View zur Folge haben
	}

	@Override
	public void go( HasWidgets container) {
		this.container = container;

		if ( "".equals(History.getToken())) {
			History.newItem( "list");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	public void onValueChange( ValueChangeEvent<String> event) {
		String token = event.getValue();

		if ( token != null) {
			Presenter presenter = null;		

			if ( token.equals( "list")) {
				presenter = new SimulatorPresenter( rpcService, eventBus, new SimulatorView());
			}

			if ( presenter != null) {
				presenter.go( container);
			} 
		} // if close
	} // onValueChange() close
}
