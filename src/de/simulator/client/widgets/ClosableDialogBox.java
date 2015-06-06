package de.simulator.client.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class ClosableDialogBox extends DialogBox {
	
	Image closeButton;
	Image closeButtonActive;
	Image closeButtonInactive;
	
	HorizontalPanel captionPanel;
	
	Label title;
	String dialogTitle;
	String titleInformation;
	
	public ClosableDialogBox( String dialogTitle, String titleInformation) {
		this.dialogTitle = dialogTitle;
		this.titleInformation = titleInformation;

		this.closeButton = new Image( "images/closeButtonActive.png");;
		
		this.captionPanel = new HorizontalPanel();
		
		if ( this.titleInformation.isEmpty()) {
			this.title = new Label( this.dialogTitle);
		} else {
			this.title = new Label( this.dialogTitle + " - " + this.titleInformation);
		}
			 

		this.captionPanel.setStyleName( "myCaption");
		this.captionPanel.setWidth( "100%");
		this.captionPanel.setHeight( "34px");
		 
		this.captionPanel.add( this.title);
		this.captionPanel.setCellHorizontalAlignment( this.title, HasHorizontalAlignment.ALIGN_LEFT);
		this.captionPanel.add( closeButton);
		this.captionPanel.setCellHorizontalAlignment( closeButton, HasHorizontalAlignment.ALIGN_RIGHT);

		Element td = getCellElement(0, 1);  // Get the cell element that holds the caption
		td.setInnerHTML( "");
		td.appendChild( captionPanel.getElement());
		
		Event.sinkEvents( this.closeButton.getElement(), Event.ONCLICK);
        Event.setEventListener( this.closeButton.getElement(), new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                 if(Event.ONCLICK == event.getTypeInt()) {
                     hide();
                 } 
            }
        });
        
        setHTML( captionPanel.getElement().getInnerHTML());        
	}
	
	public void setTitleInformation( String titleInformation) {	
		this.titleInformation = titleInformation;
		if ( this.titleInformation.isEmpty()) {
			this.title.setText( this.dialogTitle);
		} else {
			this.title.setText( this.dialogTitle + " - " + this.titleInformation);
		}
	}
	
	public void setCloseButtonVisible( Boolean var) {
		if( var) {
			this.closeButton.setVisible( true);
		} else {
			this.closeButton.setVisible( false);
		}
	}
	
}
