package org.vaadin.addons.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.PopupExtension;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class PopupextensionDemoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PopupextensionDemoUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private final AbsoluteLayout layout = new AbsoluteLayout();

	@Override
	protected void init(final VaadinRequest request) {
		final CssLayout cssLayout = new CssLayout(layout);
		setContent(cssLayout);
		cssLayout.setSizeFull();
		layout.setSizeFull();

		resetUi();
	}

	private void resetUi() {
		layout.removeAllComponents();
		final Button button = new Button("Click Me");
		final PopupExtension popupExtension = PopupExtension.extend(button);
		button.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				popupExtension.open();
			}
		});
		layout.addComponent(button, "top:50%;left:50%");

		popupExtension.setContent(getTestLayout());
		popupExtension.setAnchor(Alignment.BOTTOM_RIGHT);
		popupExtension.setDirection(Alignment.BOTTOM_RIGHT);
		popupExtension.setPopupStyleName("demopopup");
		popupExtension.addPopupVisibilityListener(new PopupExtension.PopupVisibilityListener() {
			@Override
			public void visibilityChanged(final boolean isOpened) {
				Notification.show("Popup was " + (isOpened ? "opened" : "closed"),
						Notification.Type.TRAY_NOTIFICATION);
			}
		});

		layout.addComponent(new ControlPanel(popupExtension), "top:10px;left:10px");

		layout.addComponent(new Button("reset view", new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				resetUi();
			}
		}), "top:0;right:0");
	}

	private Component getTestLayout() {
		final CssLayout layout = new CssLayout();
		layout.setWidth("300px");
		layout.setHeight("300px");

		layout.addComponent(new Label("<h1>Test Layout</h1>", ContentMode.HTML));
		layout.addComponent(new Button("I do nothing"));
		final Embedded embedded = new Embedded("", new ExternalResource(
				"http://i1.kym-cdn.com/photos/"
						+ "images/original/000/011/296/success_baby.jpg"));
		embedded.setWidth("250px");
		layout.addComponent(embedded);
		return layout;
	}

}