package de.simulator.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ReloadDatabaseEventHandler extends EventHandler {
	public void onReloadDatabase(ReloadDatabaseEvent event);
}
