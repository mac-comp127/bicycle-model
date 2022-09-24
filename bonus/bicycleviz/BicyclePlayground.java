package bicycleviz;

import bicyclemodel.Bicycle;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

public class BicyclePlayground {
    public static void main(String[] args) {
        new BicyclePlayground(1000, 800);
    }

    private int bikeCount;
    private double nextControlsY;

    public BicyclePlayground(int width, int height) {
        CanvasWindow canvas = new CanvasWindow("Bicycle", width, height);

        Button addBikeButton = new Button("Add Bicycle");
        addBikeButton.onClick(() -> createBicycle(canvas));
        addBikeButton.setPosition(CONTROLS_SPACING, CONTROLS_SPACING);
        nextControlsY = CONTROLS_HEIGHT + CONTROLS_SPACING;
        canvas.add(addBikeButton);

        createBicycle(canvas);
    }

    private void createBicycle(CanvasWindow canvas) {
        bikeCount++;
        Color color = Color.getHSBColor(bikeCount * 137.5f / 360, 1, 1);
        BicycleSprite bikeViz = new BicycleSprite(canvas.getWidth(), canvas.getHeight(), color);
        canvas.add(bikeViz);
        canvas.animate(bikeViz::update);

        ControlPanel controls = new ControlPanel(bikeViz.getBicycle(), color);
        controls.setPosition(0, nextControlsY);
        nextControlsY += controls.getHeight();
        canvas.add(controls);
        canvas.animate(controls::update);
    }

    private static final double
        CONTROLS_HEIGHT = 32,
        CONTROLS_SPACING = 8;

    private class ControlPanel extends GraphicsGroup {
        private final Bicycle bicycle;

        private final GraphicsGroup controls = new GraphicsGroup();
        private final GraphicsText speedometer;
        private final TextField directionField;

        private double displayedDirection = Double.NaN;

        ControlPanel(Bicycle bicycle, Color color) {
            this.bicycle = bicycle;
            
            addControl(new GraphicsText("Speed:"));
            speedometer = new GraphicsText("????");  // placeholder text to get a good size
            speedometer.setFontStyle(FontStyle.BOLD);
            addControl(speedometer);

            addControl(new GraphicsText("Dir:"));
            directionField = new TextField();
            directionField.setText("???");
            addControl(directionField)
                .onChange(text ->updateDirection(text));

            addControl(new Button("Pedal Harder"))
                .onClick(() -> bicycle.pedalHarder());

            addControl(new Button("Brake"))
                .onClick(() -> bicycle.brake());

            Rectangle controlsBG = new Rectangle(0, 0, nextControlX(), CONTROLS_HEIGHT);
            controlsBG.setStroked(false);
            controlsBG.setFillColor(new Color(color.getRGB() & 0xFFFFFF | 0x55000000, true));

            add(controlsBG);
            add(controls);
        }

        private void updateDirection(String text) {
            try {
                displayedDirection = Double.parseDouble(text);
                bicycle.setDirection(displayedDirection);
                directionField.setBackground(Color.WHITE);
            } catch(NumberFormatException nfe) {
                // let the user keep typing
                directionField.setBackground(new Color(255, 140, 128));
            }
        }

        private <Control extends GraphicsObject> Control addControl(Control control) {
            control.setCenter(0, CONTROLS_HEIGHT / 2);
            control.setPosition(nextControlX(), control.getY());
            controls.add(control);
            return control;
        }

        private double nextControlX() {
            return controls.getBoundsInParent().getMaxX() + CONTROLS_SPACING;
        }

        public void update() {
            speedometer.setText(
                String.format("%1.1f", bicycle.getSpeed()));

            // Avoid changing the displayed text if the bicycle is still going the way we think
            // it’s going. This prevents the text from changing underfoot while the user is trying
            // to edit it.
            if (displayedDirection != bicycle.getDirection()) {
                directionField.setText(
                    String.format("%1.0f", bicycle.getDirection()));
            }
        }
    }
}
