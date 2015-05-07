package de.simulator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Simulator implements EntryPoint {
    Label title = new Label("Simulator");

	public void onModuleLoad() {
		RootPanel.get().add(title);
	}

}
