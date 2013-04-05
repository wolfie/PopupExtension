package com.github.wolfie.popupextension;

import java.util.List;
import java.util.UUID;

import com.github.wolfie.popupextension.client.PopupExtensionServerRpc;
import com.github.wolfie.popupextension.client.PopupExtensionState;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

public class PopupExtension extends AbstractExtension {

	public interface PopupVisibilityListener {
		void visibilityChanged(boolean isOpened);
	}

	private static final long serialVersionUID = -5944700694390672500L;
	private static final Alignment DEFAULLT_ANCHOR = Alignment.MIDDLE_CENTER;
	private static final Alignment DEFAULT_DIRECTION = Alignment.MIDDLE_CENTER;

	private Alignment anchor;
	private Alignment direction;
	private Component content;
	private PopupExtensionDataTransferComponent dataTransferComponent;
	private List<PopupVisibilityListener> listeners;

	private PopupExtension() {
		setAnchor(DEFAULLT_ANCHOR);
		setDirection(DEFAULT_DIRECTION);
		getState().id = UUID.randomUUID().toString();

		registerRpc(new PopupExtensionServerRpc() {
			private static final long serialVersionUID = 741086893127824221L;

			@Override
			public void setOpen(final boolean open) {
				if (open != getState().open) {
					getState().open = open;
					fireVisibilityListeners();
				}
			}
		});
	}

	public static PopupExtension extend(final Component c) {
		final PopupExtension popup = new PopupExtension();
		popup.extend((AbstractClientConnector) c);

		final Component content = UI.getCurrent().getContent();
		if (!(content instanceof ComponentContainer)) {
			throw new UnsupportedOperationException(
					"UI.getCurrent().getContent() doesn't "
							+ "return a ComponentContainer (Currently: "
							+ content.getClass().getSimpleName()
							+ ") PopupExtension requires a ComponentContainer "
							+ "as the UI's content to work properly.");
		} else {
			final ComponentContainer ccContent = (ComponentContainer) content;
			popup.dataTransferComponent = new PopupExtensionDataTransferComponent(
					popup.getState().id);
			ccContent.addComponent(popup.dataTransferComponent);

			if (ccContent instanceof AbstractOrderedLayout) {
				final AbstractOrderedLayout aol = (AbstractOrderedLayout) ccContent;
				aol.setExpandRatio(popup.dataTransferComponent, 0.0f);
			}
		}

		return popup;
	}

	@Override
	public void detach() {
		AbstractSingleComponentContainer.removeFromParent(dataTransferComponent);
		super.detach();
	}

	public void open() {
		getState().open = true;
	}

	public void close() {
		getState().open = false;
	}

	public void closeOnOutsideMouseClick(final boolean enable) {
		getState().closeOnOutsideMouseClick = enable;
	}

	public boolean closeOnOutsideMouseClick() {
		return getState().closeOnOutsideMouseClick;
	}

	/**
	 * @throws IllegalArgumentException
	 *             if <code>content</code> is <code>null</code>.
	 */
	public void setContent(final Component content)
			throws IllegalArgumentException {
		if (content == null) {
			throw new IllegalArgumentException(
					"Null not a valid argument. Use removeContent() instead");
		}

		if (this.content != content) {
			this.content = content;
			dataTransferComponent.setContent(content);
		}
	}

	/**
	 * @throws IllegalArgumentException
	 *             if <code>anchor</code> is <code>null</code>.
	 * 
	 */
	public void setAnchor(final Alignment anchor)
			throws IllegalArgumentException {
		if (anchor == null) {
			throw new IllegalArgumentException("null not a valid anchor");
		}

		if (this.anchor != anchor) {
			this.anchor = anchor;
			getState().anchor = anchor.getBitMask();
		}
	}

	public Alignment getAnchor() {
		return anchor;
	}

	/**
	 * @throws IllegalArgumentException
	 *             if <code>direction</code> is <code>null</code>.
	 */
	public void setDirection(final Alignment direction) {
		if (direction == null) {
			throw new IllegalArgumentException("null not a valid direction");
		}

		if (this.direction != direction) {
			this.direction = direction;
			getState().direction = direction.getBitMask();
		}
	}

	public Alignment getDirection() {
		return direction;
	}

	public Component getContent() {
		return content;
	}

	public boolean isOpen() {
		return getState().open;
	}

	public void setOffset(final int x, final int y) {
		getState().xOffset = x;
		getState().yOffset = y;
	}

	@Override
	protected PopupExtensionState getState() {
		return (PopupExtensionState) super.getState();
	}

	/**
	 * @param listener
	 *          a non-<code>null</code> listener.
	 * @throws IllegalArgumentException
	 *           if <code>listener</code> is <code>null</code>.
	 */
	public void addPopupVisibilityListener(
			final PopupVisibilityListener listener) {
		if (listener != null) {
			listeners.add(listener);
		} else {
			throw new IllegalArgumentException("Listener can't be null");
		}
	}

	/**
	 * @return <code>true</code> iff the given <code>listener</code> was found and
	 *         removed from this.
	 */
	public boolean removePopupVisibilityListener(
			final PopupVisibilityListener listener) {
		return listeners.remove(listener);
	}

	private void fireVisibilityListeners() {
		for (final PopupVisibilityListener listener : listeners) {
			listener.visibilityChanged(getState().open);
		}
	}
}
