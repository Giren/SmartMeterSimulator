package de.simulator.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.simulator.shared.Device;

public class AddDeviceEvent extends GwtEvent<AddDeviceEventHandler> {
	public static Type<AddDeviceEventHandler> TYPE = new Type<AddDeviceEventHandler>();
	private final Device device;
	
	public AddDeviceEvent( Device deviceToAdd){
		this.device = deviceToAdd;
	}
	
	public Device getDevice(){
		return this.device;
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