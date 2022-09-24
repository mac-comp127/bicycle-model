package bicycleviz;

import bicyclemodel.Bicycle;

import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Path;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class BicycleSprite extends GraphicsGroup {
    // Underlying bicycle model
    private final Bicycle bicycle;

    // Bicycle’s relationship to world
    private static final double
        BASE_SPEED = 10,       // Conversion from bike speed to px/s
        STEERING_FACTOR = 20;  // How much handlebars affect direction

    private Point bicyclePosition;
    private double bicycleHeading;

    // Visualization
    private static final double
        FRAME_WIDTH = 5,
        FRAME_LENGTH = 60,
        WHEEL_WIDTH = FRAME_WIDTH * 1.5,
        WHEEL_LENGTH = FRAME_LENGTH * 2 / 3,
        HANDLEBAR_WIDTH = FRAME_LENGTH,
        HANDLEBAR_BEND = HANDLEBAR_WIDTH / 6,
        SEAT_POSITION = FRAME_LENGTH * 1 / 3,
        SEAT_LENGTH = FRAME_LENGTH / 5,
        SEAT_WIDTH = SEAT_LENGTH * 2 / 3;
    private static final Point
        WRAP_MARGIN = new Point(FRAME_LENGTH, FRAME_LENGTH);

    private final GraphicsGroup frameSprite;
    private final GraphicsObject frontSectionSprite;
    private Point viewSize;

    public BicycleSprite(int width, int height, Color color) {
        viewSize = new Point(width, height);

        bicycle = new Bicycle();
        bicycle.setDirection(Math.random() * 90 - 45);
        bicycleHeading = Math.random() * 360;
        bicyclePosition = new Point(
            (Math.random() * 0.5 + 0.25) * width,
            (Math.random() * 0.5 + 0.25) * height);

        frontSectionSprite = makefrontSectionSprite(color);
        frameSprite = makeBicycleSprite(color);
        add(frontSectionSprite);
        add(frameSprite);
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void update(double dt) {
        dt = Math.max(0.1, dt);  // prevent big time jumps

        // Terrible simulation of effect of handlebars on bicycle direction
        // (Please never show this to a physicist. If you are a physicist, stop reading immediately.)
        bicycleHeading +=
            Math.toDegrees(
                Math.atan2(
                    Math.sin(Math.toRadians(bicycle.getDirection()))
                        * bicycle.getSpeed()
                        * STEERING_FACTOR
                        * dt,
                    FRAME_LENGTH));
        bicycleHeading %= 360;  // prevents float precision loss

        bicyclePosition = bicyclePosition
            .add(
                Point.atAngle(Math.toRadians(bicycleHeading))
                    .scale(bicycle.getSpeed() * dt * BASE_SPEED))
            .wrapAround(
                Point.ONE_ONE.subtract(WRAP_MARGIN),
                viewSize.add(WRAP_MARGIN));

        frontSectionSprite.setRotation(bicycle.getDirection());
        setPosition(bicyclePosition);
        setRotation(bicycleHeading);
    }

    // –––––– Bicycle graphics ––––––

    private static GraphicsGroup makeBicycleSprite(Color color) {
        GraphicsGroup sprite = new GraphicsGroup();

        // Add an invisible rect to keep the group bounds constant, so that kilt-graphics doesn't
        // reposition it awkwardly when the handlebars move
        Rectangle invisibleBounds = new Rectangle(
            -FRAME_LENGTH * 2, -FRAME_LENGTH * 2,
            FRAME_LENGTH * 4, FRAME_LENGTH * 4);
        invisibleBounds.setStroked(false);
        sprite.add(invisibleBounds);

        Rectangle frame = new Rectangle(0, -FRAME_WIDTH / 2, FRAME_LENGTH, FRAME_WIDTH);
        frame.setFillColor(color);
        frame.setStroked(false);
        sprite.add(frame);

        sprite.add(makeWheel(new Point(0, 0)));

        Path seat = new Path(
            new Point(SEAT_POSITION - SEAT_LENGTH, -SEAT_WIDTH),
            new Point(SEAT_POSITION, 0),
            new Point(SEAT_POSITION - SEAT_LENGTH, SEAT_WIDTH)
        );
        seat.setFillColor(color.darker());
        seat.setStroked(true);
        seat.setStrokeColor(seat.getFillColor());
        seat.setStrokeWidth(SEAT_LENGTH / 2);
        sprite.add(seat);

        return sprite;
    }

    private static GraphicsObject makefrontSectionSprite(Color color) {
        GraphicsGroup sprite = new GraphicsGroup();

        sprite.add(makeWheel(new Point(-WHEEL_LENGTH * 0.1, 0)));

        Path handlebars = new Path(
            List.of(
                new Point(-HANDLEBAR_BEND, -HANDLEBAR_WIDTH / 2),
                new Point(0, -HANDLEBAR_WIDTH / 4),
                new Point(0, HANDLEBAR_WIDTH / 4),
                new Point(-HANDLEBAR_BEND, HANDLEBAR_WIDTH / 2)
            ),
            false
        );
        handlebars.setStrokeWidth(FRAME_WIDTH / 2);
        handlebars.setStrokeColor(color);
        sprite.add(handlebars);

        sprite.add(makeHandlebarGrip(color, 1));
        sprite.add(makeHandlebarGrip(color, -1));

        sprite.setPosition(FRAME_LENGTH, 0);
        return sprite;
    }

    private static Line makeHandlebarGrip(Color color, double flip) {
        Line grip = new Line(
            -HANDLEBAR_BEND,     flip * -HANDLEBAR_WIDTH / 2,
            -HANDLEBAR_BEND / 2, flip * -HANDLEBAR_WIDTH * 3 / 8);
        grip.setStrokeWidth(FRAME_WIDTH);
        grip.setStrokeColor(color.darker());
        return grip;
    }

    private static GraphicsObject makeWheel(Point center) {
        Rectangle wheel = new Rectangle(0, 0, WHEEL_LENGTH, WHEEL_WIDTH);
        wheel.setStroked(false);
        wheel.setFillColor(Color.DARK_GRAY);
        wheel.setCenter(center);
        return wheel;
    }
}
