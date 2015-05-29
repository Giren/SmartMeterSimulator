package de.simulator.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class AddDeviceEvent extends GwtEvent<AddDeviceEventHandler> {
	public static Type<AddDeviceEventHandler> TYPE = new Type<AddDeviceEventHandler>();
	private final int id;
	
	public AddDeviceEvent(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AddDeviceEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddDeviceEventHandler handler) {
		handler.onAddDevice(this);
	}
}