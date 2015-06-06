package de.simulator.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface MyResources extends ClientBundle {
	
	 public static final MyResources INSTANCE =  GWT.create(MyResources.class);
	 
	 @Source("deviceImage.png")
	 ImageResource deviceImage();
	 
}
