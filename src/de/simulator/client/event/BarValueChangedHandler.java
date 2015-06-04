package de.simulator.client.event;

import com.kiouri.sliderbar.client.event.BarValueChangedEvent;

public interface BarValueChangedHandler extends com.kiouri.sliderbar.client.event.BarValueChangedHandler {
	void onBarValueChanged(BarValueChangedEvent event);
}
