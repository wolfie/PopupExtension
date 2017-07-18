package org.vaadin.addons.popupextension.demo.test1;

import org.vaadin.addons.popupextension.PopupExtension;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class TestComponent extends CustomComponent {
	public TestComponent() {
		final CssLayout layout = new CssLayout();
		setCompositionRoot(layout);
		final Button button = new Button("foo");
		layout.addComponent(button);

		final PopupExtension.PopupExtensionManualBundle bundle = PopupExtension
				.extendWithManualBundle(button);

		final PopupExtension popupExtension = bundle.getPopupExtension();
		popupExtension.closeOnOutsideMouseClick(true);
		popupExtension.setContent(new Label("manual placement works!"));
		button.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				if (popupExtension.isOpen()) {
					popupExtension.close();
				} else {
					popupExtension.open();
				}
			}
		});
		layout.addComponent(bundle.getDataTransferComponent());
	}
}
