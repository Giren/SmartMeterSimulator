package de.simulator.client.view;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;
import com.kiouri.sliderbar.client.solution.simplevertical.SliderBarSimpleVertical;

import de.simulator.client.presenter.*;
import de.simulator.client.widgets.KDEHorizontalLeftBW;

public class DeviceDialogView extends Composite implements DeviceDialogPresenter.Display{
	//Panels
	private VerticalPanel dialogContents = new VerticalPanel();
	private VerticalPanel deviceInfoPanelRight = new VerticalPanel();
	private VerticalPanel deviceInfoPanelLeft = new VerticalPanel();
	private HorizontalPanel infoPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	//Buttons
	private Button okButton = new Button("OK");
	private Button cancelButton = new Button("Cancel");
	//Chart
	private Chart preView = new Chart().setType(Series.Type.SPLINE)
			.setChartTitleText("Lastgang").setMarginRight(10);
	//TextBoxen
	private final TextBox categoryTextBox = new TextBox();
	private final TextBox manufacturerTextBox = new TextBox();
	private final TextBox deviceNameTextBox = new TextBox();
	private final TextBox deviceDescriptionTextBox = new TextBox();
	//SlideBar
	private SliderBarSimpleHorizontal sliderBarSimpleHorizontel;
	
	Label test1 = new Label("test1");
	
	public DeviceDialogView() {

		sliderBarSimpleHorizontel = new SliderBarSimpleHorizontal(100, "700px", true);
		sliderBarSimpleHorizontel.setMinMarkStep(1);
		sliderBarSimpleHorizontel.setNotSelectedInFocus();
		
		categoryTextBox.setSize("200px", "25px");
		manufacturerTextBox.setSize("200px", "25px");
		deviceNameTextBox.setSize("200px", "25px");;
		deviceDescriptionTextBox.setSize("200px", "25px");
		
		deviceInfoPanelLeft.add(categoryTextBox);
		deviceInfoPanelLeft.add(manufacturerTextBox);
		deviceInfoPanelRight.add(deviceNameTextBox);
		deviceInfoPanelRight.add(deviceDescriptionTextBox);
		infoPanel.add(deviceInfoPanelLeft);
		infoPanel.add(deviceInfoPanelRight);
		infoPanel.setCellWidth(deviceInfoPanelLeft, "400px");
		infoPanel.setCellWidth(deviceInfoPanelRight, "400px");
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		dialogContents.setStyleName("dialogContents");
		dialogContents.add(infoPanel);
		dialogContents.add(sliderBarSimpleHorizontel);
		dialogContents.add(preView);
		dialogContents.add(buttonPanel);
		
		initWidget(dialogContents);
	}

	@Override
	public HasValue<String> getManufacturer() {
		return manufacturerTextBox;
	}

	@Override
	public HasValue<String> getDeviceName() {
		return deviceNameTextBox;
	}

	@Override
	public HasValue<String> getCategory() {
		return categoryTextBox;
	}

	@Override
	public HasValue<String> getDeviceDescription() {
		return deviceDescriptionTextBox;
	}

	@Override
	public HasClickHandlers getOkButton() {
		return okButton;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}

	@Override
	public Chart getLoadProfile() {
		return preView;
	}

	@Override
	public SliderBarSimpleHorizontal getSlider() {
		return sliderBarSimpleHorizontel;
	}
}
