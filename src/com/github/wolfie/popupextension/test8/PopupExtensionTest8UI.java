package com.github.wolfie.popupextension.test8;

import com.github.wolfie.popupextension.PopupExtension;
import com.github.wolfie.popupextension.PopupExtension.PopupExtensionManualBundle;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PopupExtensionTest8UI extends UI {
	private static final long serialVersionUID = 6058624482286395896L;
	private VerticalLayout layout;

	@Override
	protected void init(final VaadinRequest request) {
		layout = new VerticalLayout();
		setContent(layout);

		final Component layout1 = getLayout1();
		final Component layout2 = getLayout2();

		layout.addComponent(new Button("switch", new Button.ClickListener() {
			private static final long serialVersionUID = 7472396468803460055L;

			@Override
			public void buttonClick(final ClickEvent event) {
				if (layout1.getParent() != null) {
					layout.replaceComponent(layout1, layout2);
				} else {
					layout.replaceComponent(layout2, layout1);
				}
			}
		}));

		layout.addComponent(layout1);
	}

	private Component getLayout1() {
		final Button button = new Button("layotu1");
		final PopupExtensionManualBundle bundle = PopupExtension
				.extendWithManualBundle(button);
		final PopupExtension popup = bundle.getPopupExtension();
		layout.addComponent(bundle.getDataTransferComponent());
		popup.setAnchor(Alignment.MIDDLE_CENTER);
		popup.setDirection(Alignment.BOTTOM_CENTER);
		popup.setContent(new Label("popup"));

		button.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 4779096834916102940L;

			@Override
			public void buttonClick(final ClickEvent event) {
				popup.toggle();
			}
		});
		return button;
	}

	private Component getLayout2() {
		return new Label("Layout2");
	}
}
