package de.simulator.client.presenter;

import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;

import de.simulator.client.event.AddDeviceEvent;
import de.simulator.client.event.BarValueChangedHandler;
import de.simulator.shared.Device;
import de.simulator.shared.SimulatorDevice;

public class DeviceDialogPresenter {
	public interface Display {
		HasValue<String> getManufacturer();

		HasValue<String> getDeviceName();

		HasValue<String> getCategory();

		HasValue<String> getDeviceDescription();

		HasClickHandlers getOkButton();

		HasClickHandlers getCancelButton();

		Chart getLoadProfile();

		SliderBarSimpleHorizontal getSlider();

		Widget asWidget();
	}

	private final HandlerManager eventBus;
	private final Display display;
	private DialogBox dialogBox;
	private int sliderValue;
	private Device device;

	public DeviceDialogPresenter(HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	public void bind() {
		this.display.getOkButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//setNewDeviceLoadProfile();
				eventBus.fireEvent(new AddDeviceEvent(DeviceDialogPresenter.this.device));
				DeviceDialogPresenter.this.dialogBox.hide();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				DeviceDialogPresenter.this.dialogBox.hide();
			}
		});

		eventBus.addHandler(BarValueChangedEvent.TYPE,
				new BarValueChangedHandler() {
					@Override
					public void onBarValueChanged(BarValueChangedEvent event) {
						DeviceDialogPresenter.this.sliderValue = event
								.getValue();
						updateSeries(event.getValue());
					}
				});

		display.getSlider().addBarValueChangedHandler(
				new com.kiouri.sliderbar.client.event.BarValueChangedHandler() {
					@Override
					public void onBarValueChanged(BarValueChangedEvent event) {
						eventBus.fireEvent(new BarValueChangedEvent(event
								.getValue()));
					}
				});
	}

	private void updateSeries(int sliderValue){
		this.display.getLoadProfile().removeAllSeries();
		this.display.getLoadProfile().addSeries(arrayListToSeries(DeviceDialogPresenter.this.device.getLoadProfile(),sliderValue));
	}
	
	private Series arrayListToSeries(ArrayList<Integer> arrayList, int size) {
		Series newSeries = new Chart().createSeries().setName(
				"Leistungsaufnahme");

		for (int i = 0; i < size; i++) {
			newSeries.addPoint(arrayList.get(i));
		}

		return newSeries;
	}
	
	private void setNewDeviceLoadProfile(){
		int len = this.device.getLoadProfile().size();
		for(int i = this.sliderValue+1; i < len; i++){
			this.device.getLoadProfile().remove(i);
		}
	}

	public void go(Device device) {
		this.device = device;
		this.display.getCategory().setValue(device.getCategory());
		this.display.getDeviceDescription().setValue(device.getDescription());
		this.display.getDeviceName().setValue(device.getName());
		this.display.getManufacturer().setValue(device.getManufacturer());
		this.display.getLoadProfile().addSeries(
				arrayListToSeries(device.getLoadProfile(),device.getLoadProfile().size()));

		int size = device.getLoadProfile().size();
		this.display.getSlider().setMaxValue(size);
		this.display.getSlider().drawMarks("white",size);
		this.display.getSlider().setValue(size);
		
		dialogBox = new DialogBox();
		dialogBox.setWidget(display.asWidget());
		dialogBox.setAnimationEnabled(true);
		dialogBox.setGlassEnabled(true);
		dialogBox.setText("Geräteinformation");
		dialogBox.center();
		dialogBox.show();
	}

}
