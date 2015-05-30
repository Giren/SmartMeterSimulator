package de.simulator.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.view.client.SelectionChangeEvent;

public interface DeviceSelectionChangeEventHandler extends EventHandler {
	void onSelectionChange(DeviceSelectionChangeEvent event);
}
