package com.github.wolfie.popuplayout.client;

import com.vaadin.shared.communication.ServerRpc;

public interface PopupExtensionServerRpc extends ServerRpc {
	void setOpen(boolean open);
}
