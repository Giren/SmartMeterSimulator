package de.simulator.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

import de.simulator.shared.Device;

public class BarValueChangedEvent extends GwtEvent<BarValueChangedHandler>{
	public static Type<DeviceSelectionChangeEventHandler> TYPE = new Type<DeviceSelectionChangeEventHandler>();
	private final int value;
	
	public BarValueChangedEvent(int currentSliderValue){
		this.value = currentSliderValue;
	}
	
	public int getDevice(){
		return this.value;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BarValueChangedHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void dispatch(BarValueChangedHandler handler) {
		// TODO Auto-generated method stub
		
	}
}
