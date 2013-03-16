package com.github.wolfie.popupextension;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Slider;

public class ControlPanel extends CustomComponent {
	private static final long serialVersionUID = 3753446696128340948L;

	private final PopupExtension popupExtension;
	private final FormLayout layout = new FormLayout();

	private final OptionGroup anchorsV;
	private final OptionGroup anchorsH;
	private final OptionGroup directionH;
	private final OptionGroup directionV;
	private final Slider xOffset;
	private final Slider yOffset;

	@SuppressWarnings("serial")
	public ControlPanel(final PopupExtension popupExtension) {
		this.popupExtension = popupExtension;
		setCompositionRoot(layout);
		setWidth("350px");

		anchorsV = new OptionGroup("Anchor, Vertical");
		anchorsV.setImmediate(true);
		addItem(anchorsV, Bits.ALIGNMENT_TOP, "Top");
		addItem(anchorsV, Bits.ALIGNMENT_VERTICAL_CENTER, "Middle");
		addItem(anchorsV, Bits.ALIGNMENT_BOTTOM, "Bottom");
		anchorsV.setValue(Bits.ALIGNMENT_BOTTOM);
		anchorsV.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				updateAnchor();
			}
		});
		layout.addComponent(anchorsV);

		anchorsH = new OptionGroup("Anchor, Horizontal");
		anchorsH.setImmediate(true);
		addItem(anchorsH, Bits.ALIGNMENT_LEFT, "Left");
		addItem(anchorsH, Bits.ALIGNMENT_HORIZONTAL_CENTER, "Center");
		addItem(anchorsH, Bits.ALIGNMENT_RIGHT, "Right");
		anchorsH.setValue(Bits.ALIGNMENT_RIGHT);
		anchorsH.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				updateAnchor();
			}
		});
		layout.addComponent(anchorsH);

		directionV = new OptionGroup("Direction, Vertical");
		directionV.setImmediate(true);
		addItem(directionV, Bits.ALIGNMENT_TOP, "Top");
		addItem(directionV, Bits.ALIGNMENT_VERTICAL_CENTER, "Middle");
		addItem(directionV, Bits.ALIGNMENT_BOTTOM, "Bottom");
		directionV.setValue(Bits.ALIGNMENT_BOTTOM);
		directionV.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				updateDirection();
			}
		});
		layout.addComponent(directionV);

		directionH = new OptionGroup("Direction, Horizontal");
		directionH.setImmediate(true);
		addItem(directionH, Bits.ALIGNMENT_LEFT, "Left");
		addItem(directionH, Bits.ALIGNMENT_HORIZONTAL_CENTER, "Center");
		addItem(directionH, Bits.ALIGNMENT_RIGHT, "Right");
		directionH.setValue(Bits.ALIGNMENT_RIGHT);
		directionH.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				updateDirection();
			}
		});
		layout.addComponent(directionH);

		final CheckBox closeonclick = new CheckBox(
				"Close on Outside Mouse Click");
		closeonclick.setValue(popupExtension.closeOnOutsideMouseClick());
		closeonclick.setImmediate(true);
		closeonclick.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				popupExtension.closeOnOutsideMouseClick(closeonclick.getValue());
			}
		});
		layout.addComponent(closeonclick);

		xOffset = new Slider("X Offset");
		xOffset.setImmediate(true);
		xOffset.setMin(-25d);
		xOffset.setMax(25d);
		xOffset.setValue(0d);
		xOffset.setWidth("100px");
		xOffset.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				updateOffset();
			}
		});
		layout.addComponent(xOffset);

		yOffset = new Slider("Y Offset");
		yOffset.setImmediate(true);
		yOffset.setMin(-25d);
		yOffset.setMax(25d);
		yOffset.setValue(0d);
		yOffset.setWidth("100px");
		yOffset.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				updateOffset();
			}

		});
		layout.addComponent(yOffset);
	}

	private void updateOffset() {
		popupExtension.setOffset(xOffset.getValue().intValue(), yOffset
				.getValue().intValue());
	}

	private void updateAnchor() {
		popupExtension.setAnchor(new Alignment((Integer) anchorsV.getValue()
				+ (Integer) anchorsH.getValue()));
	}

	private void updateDirection() {
		popupExtension.setDirection(new Alignment((Integer) directionV
				.getValue() + (Integer) directionH.getValue()));
	}

	private void addItem(final OptionGroup og, final int bit,
			final String string) {
		og.addItem(bit);
		og.setItemCaption(bit, string);
	}
}
