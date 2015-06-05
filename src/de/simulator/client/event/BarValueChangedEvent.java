package de.simulator.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

import de.simulator.shared.Device;

public class BarValueChangedEvent extends GwtEvent<BarValueChangedHandler>{
	public static Type<BarValueChangedHandler> TYPE = new Type<BarValueChangedHandler>();
	private final int value;
	
	public BarValueChangedEvent(int currentSliderValue){
		this.value = currentSliderValue;
	}
	
	public int getDevice(){
		return this.value;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BarValueChangedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BarValueChangedHandler handler) {
		//handler.onBarValueChanged(this);
	}
}
