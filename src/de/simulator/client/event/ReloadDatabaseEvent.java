package de.simulator.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class ReloadDatabaseEvent extends GwtEvent<ReloadDatabaseEventHandler>{
	public static Type<ReloadDatabaseEventHandler> TYPE = new Type<ReloadDatabaseEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ReloadDatabaseEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReloadDatabaseEventHandler handler) {
		handler.onReloadDatabase(this);
	}
}
