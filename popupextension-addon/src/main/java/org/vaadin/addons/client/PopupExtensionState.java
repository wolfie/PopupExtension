package org.vaadin.addons.client;

import com.vaadin.shared.communication.SharedState;

public class PopupExtensionState extends SharedState {
    private static final long serialVersionUID = 989923054470446192L;

    public boolean open = false;

    public int anchor = 0;
    public int direction = 0;

    public String id;

    public boolean closeOnOutsideMouseClick;

    public int xOffset;
    public int yOffset;

    public String popupStyleName;
}
