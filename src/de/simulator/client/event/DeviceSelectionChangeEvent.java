package de.simulator.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.simulator.shared.Device;

public class DeviceSelectionChangeEvent extends
		GwtEvent<DeviceSelectionChangeEventHandler> {

	public static Type<DeviceSelectionChangeEventHandler> TYPE = new Type<DeviceSelectionChangeEventHandler>();
	private final Device device;

	public DeviceSelectionChangeEvent(Device deviceSelection) {
		this.device = deviceSelection;
	}

	public Device getDevice() {
		return this.device;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DeviceSelectionChangeEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(DeviceSelectionChangeEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onSelectionChange(this);
	}

}
