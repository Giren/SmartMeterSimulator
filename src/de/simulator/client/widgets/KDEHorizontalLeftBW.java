package de.simulator.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.kiouri.sliderbar.client.view.SliderBarHorizontal;

public class KDEHorizontalLeftBW extends SliderBarHorizontal {

	ImagesKDEHorizontalLeftBW images = GWT
			.create(ImagesKDEHorizontalLeftBW.class);

	public KDEHorizontalLeftBW(int maxValue, String width) {
		setLessWidget(new Image(images.less()));
		//setMoreWidget(new Image(images.more()));
		setScaleWidget(new Image(images.scale().getUrl()), 16);
		setMoreWidget(new Image(images.more()));
		setDragWidget(new Image(images.drag()));
		this.setWidth(width);
		this.setMaxValue(maxValue);
	}

	interface ImagesKDEHorizontalLeftBW extends ClientBundle {

		@Source("Drag.png")
		ImageResource drag();

		@Source("Less.png")
		ImageResource less();

		@Source("More.png")
		ImageResource more();

		@Source("Scale.png")
		DataResource scale();
	}

}
