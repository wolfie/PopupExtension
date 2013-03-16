package com.github.wolfie.popuplayout;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class PopuplayoutDemoUI extends UI {

	@Override
	protected void init(final VaadinRequest request) {
		final AbsoluteLayout layout = new AbsoluteLayout();
		setContent(layout);
		layout.setSizeFull();

		final Button button = new Button("Click Me");
		final PopupExtension popupExtension = PopupExtension.extend(button);
		button.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				popupExtension.open();
			}
		});
		layout.addComponent(button, "top:50%;left:50%");

		popupExtension.setContent(new Image("", new ExternalResource(
				"http://data.whicdn.com/images/12472262/"
						+ "tumblr_loxja5GvVA1qf7ikto1_400_thumb.jpg")));
		popupExtension.setAnchor(Alignment.BOTTOM_RIGHT);
		popupExtension.setDirection(Alignment.BOTTOM_RIGHT);

		layout.addComponent(new ControlPanel(popupExtension),
				"top:10px;left:10px");
	}

}