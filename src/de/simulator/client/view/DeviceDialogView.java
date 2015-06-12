package de.simulator.client.view;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;

import de.simulator.client.presenter.DeviceDialogPresenter;

public class DeviceDialogView extends Composite implements DeviceDialogPresenter.Display {
	
	//Panels
	private VerticalPanel dialogContents = new VerticalPanel();
	
	private HorizontalPanel controlPanel = new HorizontalPanel(); // Panel für DeviceInfos und OkCanel Button
	
	private VerticalPanel deviceInformationPanel = new VerticalPanel();
	private HorizontalPanel deviceNamePanel = new HorizontalPanel();
	private HorizontalPanel deviceCategoryPanel = new HorizontalPanel();
	private HorizontalPanel deviceManufacturerPanel = new HorizontalPanel();
	private HorizontalPanel deviceDescriptionPanel = new HorizontalPanel();
	
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	
//	private VerticalPanel deviceInfoPanelRight = new VerticalPanel();
//	private VerticalPanel deviceInfoPanelLeft = new VerticalPanel();
//	private HorizontalPanel infoPanel = new HorizontalPanel();
	
	//Buttons
	private PushButton okButton;
	private PushButton cancelButton;
	
	//Chart
	private Chart preView = new Chart().setType(Series.Type.SPLINE)
			.setChartTitleText("Lastgang").setMarginRight(10);
	//TextBoxen
	private final TextBox deviceCategoryTextBox = new TextBox();
	private final TextBox deviceManufacturerTextBox = new TextBox();
	private final TextBox deviceNameTextBox = new TextBox();
	private final TextBox deviceDescriptionTextBox = new TextBox();
	// Labels
	private final Label deviceNameLabel = new Label( "Device Name:");
	private final Label deviceCategoryLabel = new Label( "Device Category:");
	private final Label deviceManufacturerLabel = new Label( "Device Manufacturer:");
	private final Label deviceDescriptionLabel = new Label( "Device Description:");
	//SlideBar
	private SliderBarSimpleHorizontal sliderBarSimpleHorizontel;
	
	
	public DeviceDialogView() {
		
		this.sliderBarSimpleHorizontel = new SliderBarSimpleHorizontal(100, "100%", true);
		this.sliderBarSimpleHorizontel.setMinMarkStep( 1);
		this.sliderBarSimpleHorizontel.setNotSelectedInFocus();
		
		this.dialogContents.setSize( "800px", "400px");
		this.dialogContents.setWidth( "800px");
		this.dialogContents.setHeight( "400px");
		this.dialogContents.setStyleName( "simulationPopUp");
		
		this.dialogContents.setSpacing( 8);
		this.deviceInformationPanel.setSpacing( 4);
		this.buttonPanel.setSpacing( 4);
		
		this.deviceNameTextBox.setTitle( "Device Name");
		this.deviceNameTextBox.setWidth( "90%");
		this.deviceCategoryTextBox.setTitle( "Device Category");
		this.deviceCategoryTextBox.setWidth( "90%");
		this.deviceManufacturerTextBox.setTitle( "Device Manufacturer");
		this.deviceManufacturerTextBox.setWidth( "90%");
		this.deviceDescriptionTextBox.setTitle( "Device Description");
		this.deviceDescriptionTextBox.setWidth( "90%");
		
		this.deviceCategoryLabel.setStyleName( "deviceDialogFont");
		this.deviceNameLabel.setStyleName( "deviceDialogFont");
		this.deviceManufacturerLabel.setStyleName( "deviceDialogFont");
		this.deviceDescriptionLabel.setStyleName( "deviceDialogFont");
		
		this.deviceNameTextBox.setTitle( "Device Name");
		this.deviceManufacturerTextBox.setTitle( "Device Manufacturer");
		this.deviceDescriptionTextBox.setTitle( "Device Description");
		
		// DeviceInformationPanel bauen
		this.deviceNamePanel.add( this.deviceNameLabel);
		this.deviceNamePanel.setCellWidth( this.deviceNameLabel, "160px");
		this.deviceNamePanel.add( this.deviceNameTextBox);
		this.deviceNamePanel.setWidth( "100%");
		this.deviceNamePanel.setCellHorizontalAlignment( this.deviceNameLabel, HasHorizontalAlignment.ALIGN_LEFT);
		this.deviceNamePanel.setCellHorizontalAlignment( this.deviceNameTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		
		this.deviceCategoryPanel.add( this.deviceCategoryLabel);
		this.deviceCategoryPanel.setCellWidth( this.deviceCategoryLabel, "160px");
		this.deviceCategoryPanel.add( this.deviceCategoryTextBox);
		this.deviceCategoryPanel.setWidth( "100%");
		this.deviceCategoryPanel.setCellHorizontalAlignment( this.deviceCategoryLabel, HasHorizontalAlignment.ALIGN_LEFT);
		this.deviceCategoryPanel.setCellHorizontalAlignment( this.deviceCategoryTextBox, HasHorizontalAlignment.ALIGN_RIGHT);		
		
		this.deviceManufacturerPanel.add( this.deviceManufacturerLabel);
		this.deviceManufacturerPanel.setCellWidth( this.deviceManufacturerLabel, "160px");
		this.deviceManufacturerPanel.add( this.deviceManufacturerTextBox);
		this.deviceManufacturerPanel.setWidth( "100%");
		this.deviceManufacturerPanel.setCellHorizontalAlignment( this.deviceManufacturerLabel, HasHorizontalAlignment.ALIGN_LEFT);
		this.deviceManufacturerPanel.setCellHorizontalAlignment( this.deviceManufacturerTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		
		this.deviceDescriptionPanel.add( this.deviceDescriptionLabel);
		this.deviceDescriptionPanel.setCellWidth( this.deviceDescriptionLabel, "160px");
		this.deviceDescriptionPanel.add( this.deviceDescriptionTextBox);
		this.deviceDescriptionPanel.setWidth( "100%");
		this.deviceDescriptionPanel.setCellHorizontalAlignment( this.deviceDescriptionLabel, HasHorizontalAlignment.ALIGN_LEFT);
		this.deviceDescriptionPanel.setCellHorizontalAlignment( this.deviceDescriptionTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		
		this.deviceInformationPanel.add( this.deviceNamePanel);
		this.deviceInformationPanel.add( this.deviceCategoryPanel);
		this.deviceInformationPanel.add( this.deviceManufacturerPanel);
		this.deviceInformationPanel.add( this.deviceDescriptionPanel);
		this.deviceInformationPanel.setTitle( "Device Informations");
		
		this.okButton = new PushButton( new Image( "images/okButton.png"));
		this.okButton.setTitle( "OK");
		this.cancelButton = new PushButton( new Image( "images/cancelButton.png"));
		this.cancelButton.setTitle( "Cancel");
		
		this.buttonPanel.add( this.okButton);
		this.buttonPanel.add( this.cancelButton);
		
		this.deviceInformationPanel.setStyleName( "simulationPopUp");
		this.buttonPanel.setStyleName( "simulationPopUp");
		this.dialogContents.setStyleName( "simulationPopUp");
		
		this.deviceInformationPanel.setWidth( "400px");
		this.deviceInformationPanel.setHeight( "122px");
		this.deviceInformationPanel.setSize( "400px", "122px");
		
		this.controlPanel.add( this.deviceInformationPanel);
		this.controlPanel.add( this.buttonPanel);
		this.controlPanel.setWidth( "100%");
		this.controlPanel.setCellHorizontalAlignment( this.buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		this.preView.getXAxis().setAxisTitleText( "time [s]", false);
		this.preView.getYAxis().setAxisTitleText( "W", false);
		this.preView.setOption( "legend/enabled", false);
		this.preView.getXAxis().setAllowDecimals( true);
		this.preView.setWidth( "780px");
		this.preView.setHeight( "250px");
		
		this.dialogContents.add( this.controlPanel);
		this.dialogContents.add( this.sliderBarSimpleHorizontel);
		this.dialogContents.add( this.preView);
		
		
		
		initWidget( this.dialogContents);
	}

	@Override
	public HasValue<String> getManufacturer() {
		return this.deviceManufacturerTextBox;
	}

	@Override
	public HasValue<String> getDeviceName() {
		return this.deviceNameTextBox;
	}

	@Override
	public HasValue<String> getCategory() {
		return this.deviceCategoryTextBox;
	}

	@Override
	public HasValue<String> getDeviceDescription() {
		return this.deviceDescriptionTextBox;
	}

	@Override
	public HasClickHandlers getOkButton() {
		return this.okButton;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		return this.cancelButton;
	}

	@Override
	public Chart getLoadProfile() {
		return this.preView;
	}

	@Override
	public SliderBarSimpleHorizontal getSlider() {
		return this.sliderBarSimpleHorizontel;
	}
}
