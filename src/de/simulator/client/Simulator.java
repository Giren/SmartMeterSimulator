package de.simulator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class Simulator implements EntryPoint {
	
	public void onModuleLoad() {
		SimulatorServiceAsync rpcService = GWT.create( SimulatorService.class);
		HandlerManager eventBus = new HandlerManager( null);
		AppController appViewer = new AppController( rpcService, eventBus);
		appViewer.go( RootPanel.get());
	}
}
