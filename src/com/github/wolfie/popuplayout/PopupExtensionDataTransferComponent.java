package com.github.wolfie.popuplayout;

import com.github.wolfie.popuplayout.client.PopupExtensionDataTransferComponentState;
import com.vaadin.ui.AbstractSingleComponentContainer;

/**
 * This component exists only to send Components over the wire from the server
 * to the client. This is a workaround for Vaadin's lack of support of sending
 * arbitrary components over the wire - they need to be attached to the
 * component hierarchy to be sent.
 */
public class PopupExtensionDataTransferComponent extends
		AbstractSingleComponentContainer {
	private static final long serialVersionUID = 4709424341178526679L;

	/**
	 * @deprecated Don't use this class. This is a hack around Vaadin.
	 */
	@Deprecated
	public PopupExtensionDataTransferComponent() {
	}

	public PopupExtensionDataTransferComponent(final String popupId) {
		getState().popupId = popupId;
	}

	@Override
	protected PopupExtensionDataTransferComponentState getState() {
		return (PopupExtensionDataTransferComponentState) super.getState();
	}
}
