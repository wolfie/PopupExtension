package org.vaadin.addons.popupextension.client;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.addons.popupextension.PopupExtensionDataTransferComponent;

import com.google.gwt.user.client.Timer;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.shared.ui.Connect;

@Connect(PopupExtensionDataTransferComponent.class)
public class PopupExtensionDataTransferComponentConnector extends
        AbstractSingleComponentContainerConnector {
    private static final long serialVersionUID = -7639852945015403740L;

    static final Map<String, PopupExtensionDataTransferComponentConnector> INSTANCES = new HashMap<String, PopupExtensionDataTransferComponentConnector>();

    @Override
    public void onConnectorHierarchyChange(
            final ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {

		/*
         * this ensures that we'll fetch the popup even if the states are
		 * updated in a "wrong" order.
		 */
        new Timer() {
            int i = 0;

            @Override
            public void run() {
                final PopupExtensionWidget popup = PopupExtensionWidget.INSTANCES
                        .get(getState().popupId);

                if (popup != null) {
                    if (getChildComponents().isEmpty()) {
                        popup.clear();
                    } else {
                        popup.setWidget(
                                getChildComponents().get(0).getWidget());
                    }
                    cancel();
                    return;
                }

                i++;
                if (i == 10) {
                    VConsole.error("Can't find PopupExtension with id "
                            + getState().popupId);
                    cancel();
                }
            }
        }.scheduleRepeating(50);
    }

    @Override
    public void onStateChanged(final StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        if (stateChangeEvent.hasPropertyChanged("popupId")) {
            INSTANCES.put(getState().popupId, this);
        }
    }

    @Override
    public void onUnregister() {
        INSTANCES.remove(getState().popupId);
        super.onUnregister();
    }

    @Override
    public PopupExtensionDataTransferComponentWidget getWidget() {
        return (PopupExtensionDataTransferComponentWidget) super.getWidget();
    }

    @Override
    public PopupExtensionDataTransferComponentState getState() {
        return (PopupExtensionDataTransferComponentState) super.getState();
    }

    @Override
    public void updateCaption(final ComponentConnector connector) {
    }

    void reinject(final PopupExtensionWidget popup) {
        if (getChildComponents().isEmpty()) {
            popup.clear();
        } else {
            popup.setWidget(getChildComponents().get(0).getWidget());
        }
    }

}
