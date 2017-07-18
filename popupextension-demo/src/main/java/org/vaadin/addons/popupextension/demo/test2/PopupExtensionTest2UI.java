package org.vaadin.addons.popupextension.demo.test2;

import org.vaadin.addons.popupextension.PopupExtension;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/** Test case for {@link https://github.com/wolfie/PopupExtension/issues/2} */
@SuppressWarnings("serial")
public class PopupExtensionTest2UI extends UI {

	@Override
	protected void init(final VaadinRequest request) {
		final VerticalLayout vl = new VerticalLayout();
		setContent(vl);

		final Button replaceButton = new Button("Swap places with label");
		vl.addComponent(replaceButton);

		final Label extendedLabel = new Label("I'm extended");
		extendedLabel.setWidth(null);
		final PopupExtension extension = PopupExtension.extend(extendedLabel);
		extension.setContent(new Label("POP!"));
		extension.closeOnOutsideMouseClick(true);
		vl.addComponent(extendedLabel);

		vl.addComponent(new Button("Open Popup", new Button.ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				extension.open();
			}
		}));

		replaceButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				vl.replaceComponent(replaceButton, extendedLabel);
			}
		});
	}
}
