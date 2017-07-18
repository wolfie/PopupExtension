package org.vaadin.addons.popupextension.demo;

import java.util.Arrays;

import org.vaadin.addons.popupextension.PopupExtension;

import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.Slider;

public class ControlPanel extends CustomComponent {
    private static final long serialVersionUID = 3753446696128340948L;

    private final PopupExtension popupExtension;
    private final FormLayout layout = new FormLayout();

    private final RadioButtonGroup<Integer> anchorsV;
    private final RadioButtonGroup<Integer> anchorsH;
    private final RadioButtonGroup<Integer> directionH;
    private final RadioButtonGroup<Integer> directionV;
    private final Slider xOffset;
    private final Slider yOffset;

    @SuppressWarnings("serial")
    public ControlPanel(final PopupExtension popupExtension) {
        this.popupExtension = popupExtension;
        setCompositionRoot(layout);
        setWidth("350px");

        anchorsV = new RadioButtonGroup<>("Anchor, Vertical");
        anchorsV.setItems(Arrays.asList(Bits.ALIGNMENT_TOP,
                Bits.ALIGNMENT_VERTICAL_CENTER, Bits.ALIGNMENT_BOTTOM));
        anchorsV.setItemCaptionGenerator(this::getCaption);
        anchorsV.setValue(Bits.ALIGNMENT_BOTTOM);
        anchorsV.addValueChangeListener(valueChangeEvent -> updateAnchor());
        layout.addComponent(anchorsV);

        anchorsH = new RadioButtonGroup<>("Anchor, Horizontal");
        anchorsH.setItems(Arrays.asList(Bits.ALIGNMENT_LEFT,
                Bits.ALIGNMENT_HORIZONTAL_CENTER, Bits.ALIGNMENT_RIGHT));
        anchorsH.setItemCaptionGenerator(this::getCaption);
        anchorsH.setValue(Bits.ALIGNMENT_RIGHT);
        anchorsH.addValueChangeListener(valueChangeEvent -> updateAnchor());
        layout.addComponent(anchorsH);

        directionV = new RadioButtonGroup<>("Direction, Vertical");
        directionV.setItems(Arrays.asList(Bits.ALIGNMENT_TOP,
                Bits.ALIGNMENT_VERTICAL_CENTER, Bits.ALIGNMENT_BOTTOM));
        directionV.setItemCaptionGenerator(this::getCaption);
        directionV.setValue(Bits.ALIGNMENT_BOTTOM);
        directionV
                .addValueChangeListener(valueChangeEvent -> updateDirection());
        layout.addComponent(directionV);

        directionH = new RadioButtonGroup<>("Direction, Horizontal");
        directionH.setItems(Arrays.asList(Bits.ALIGNMENT_LEFT,
                Bits.ALIGNMENT_HORIZONTAL_CENTER, Bits.ALIGNMENT_RIGHT));
        directionH.setItemCaptionGenerator(this::getCaption);
        directionH.setValue(Bits.ALIGNMENT_RIGHT);
        directionH
                .addValueChangeListener(valueChangeEvent -> updateDirection());
        layout.addComponent(directionH);

        final CheckBox closeonclick = new CheckBox(
                "Close on Outside Mouse Click");
        closeonclick.setValue(popupExtension.closeOnOutsideMouseClick());
        closeonclick.addValueChangeListener(valueChangeEvent -> popupExtension
                .closeOnOutsideMouseClick(valueChangeEvent.getValue()));
        layout.addComponent(closeonclick);

        xOffset = new Slider("X Offset");
        xOffset.setMin(-25d);
        xOffset.setMax(25d);
        xOffset.setValue(0d);
        xOffset.setWidth("100px");
        xOffset.addValueChangeListener(valueChangeEvent -> updateOffset());
        layout.addComponent(xOffset);

        yOffset = new Slider("Y Offset");
        yOffset.setMin(-25d);
        yOffset.setMax(25d);
        yOffset.setValue(0d);
        yOffset.setWidth("100px");
        yOffset.addValueChangeListener(valueChangeEvent -> updateOffset());
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

    private String getCaption(int alignment) {
        switch (alignment) {
        case Bits.ALIGNMENT_LEFT:
            return "Left";
        case Bits.ALIGNMENT_RIGHT:
            return "Right";
        case Bits.ALIGNMENT_TOP:
            return "Top";
        case Bits.ALIGNMENT_BOTTOM:
            return "Bottom";
        case Bits.ALIGNMENT_HORIZONTAL_CENTER:
            return "Center";
        case Bits.ALIGNMENT_VERTICAL_CENTER:
            return "Middle";
        default:
            return null;
        }
    }
}
