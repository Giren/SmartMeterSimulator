package de.simulator.client.presenter;

import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;

import de.simulator.client.event.AddDeviceEvent;
import de.simulator.client.widgets.ClosableDialogBox;
import de.simulator.shared.Device;

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
	private ClosableDialogBox dialogBox;
	private Device device;
	private ArrayList<Integer> workLoadProfile;

	public DeviceDialogPresenter(HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	public void bind() {
		this.display.getOkButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				DeviceDialogPresenter.this.device.setLoadProfile( workLoadProfile);
				
				DeviceDialogPresenter.this.device.setName( display.getDeviceName().getValue());
				DeviceDialogPresenter.this.device.setManufacturer( display.getManufacturer().getValue());
				DeviceDialogPresenter.this.device.setDescription( display.getDeviceDescription().getValue());
				DeviceDialogPresenter.this.device.setCategory( display.getCategory().getValue());
				
				DeviceDialogPresenter.this.dialogBox.hide();
				DeviceDialogPresenter.this.dialogBox.clear();
				
				eventBus.fireEvent(new AddDeviceEvent(DeviceDialogPresenter.this.device));
				

			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				DeviceDialogPresenter.this.dialogBox.hide();
				DeviceDialogPresenter.this.dialogBox.clear();
			}
		});

		display.getSlider().addBarValueChangedHandler(
				new com.kiouri.sliderbar.client.event.BarValueChangedHandler() {
					@Override
					public void onBarValueChanged(BarValueChangedEvent event) {
						updateSeries( event.getValue());
					}
				});
	}

	private void updateSeries( int sliderValue){
		this.workLoadProfile = new ArrayList<Integer>();
		
		for( int i = 0; i < sliderValue; i++) {
			this.workLoadProfile.add( DeviceDialogPresenter.this.device.getLoadProfile().get( i));
		}
		this.display.getLoadProfile().removeAllSeries();
		this.display.getLoadProfile().addSeries(arrayListToSeries( this.workLoadProfile, sliderValue), true, false);
	}
	
	private Series arrayListToSeries( ArrayList<Integer> arrayList, int size) {
		Series newSeries = new Chart().createSeries().setName( "Leistungsaufnahme");

		for ( int i = 0; i < size; i++) {
			newSeries.addPoint(arrayList.get( i));
		}

		return newSeries;
	}
	
	public void go( Device device) {
		this.device = new Device( device);
		this.display.getCategory().setValue( this.device.getCategory());
		this.display.getDeviceDescription().setValue( this.device.getDescription());
		this.display.getDeviceName().setValue( this.device.getName());
		this.display.getManufacturer().setValue( this.device.getManufacturer());
		this.display.getLoadProfile().addSeries( arrayListToSeries( this.device.getLoadProfile(), this.device.getLoadProfile().size()));

		int size = this.device.getLoadProfile().size();
		this.display.getSlider().setMaxValue( size);
		this.display.getSlider().drawMarks( "white",size);
		this.display.getSlider().setValue( size);
		
		dialogBox = new ClosableDialogBox( "Geräteinformation", "");
		dialogBox.setWidget( display.asWidget());
		dialogBox.setAnimationEnabled( true);
		dialogBox.setGlassEnabled( true);
		dialogBox.center();
		dialogBox.show();
	}
}
