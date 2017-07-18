package org.vaadin.addons.demo;

import org.vaadin.addons.demo.test1.PopupExtensionTest1UI;
import org.vaadin.addons.demo.test2.PopupExtensionTest2UI;
import org.vaadin.addons.demo.test8.PopupExtensionTest8UI;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class PopupExtensionUIProvider extends UIProvider {
	private static final Class<? extends UI> DEFAULT_VIEW = PopupextensionDemoUI.class;
	private static final long serialVersionUID = 4081637558863864608L;

	@Override
	public Class<? extends UI> getUIClass(final UIClassSelectionEvent event) {
		final String path = event.getRequest().getPathInfo();

		if (path == null) {
			return DEFAULT_VIEW;
		}

		if (path.startsWith("/test1")) {
			return PopupExtensionTest1UI.class;
		} else if (path.startsWith("/test2")) {
			return PopupExtensionTest2UI.class;
		} else if (path.startsWith("/test8")) {
			return PopupExtensionTest8UI.class;
		} else {
			return DEFAULT_VIEW;
		}
	}

}
