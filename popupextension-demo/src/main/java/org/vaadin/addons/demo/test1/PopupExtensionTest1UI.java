package org.vaadin.addons.demo.test1;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class PopupExtensionTest1UI extends UI {
	@Override
	protected void init(final VaadinRequest request) {
		final Panel layout = new Panel();
		setContent(layout);
		layout.setContent(new TestComponent());
	}
}
