package de.simulator.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DeviceSelectionChangeEventHandler extends EventHandler {
	void onSelectionChange(DeviceSelectionChangeEvent event);
}
