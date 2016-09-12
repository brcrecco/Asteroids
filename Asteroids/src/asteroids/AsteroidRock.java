package asteroids;

import Models.Direction;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * A moving asteroid object
 *
 * @author Brandon Crecco
 */
public class AsteroidRock implements Enemy {

    Position position;
    Direction direction;
    int radius;
    final double delta = 2;
    final int minRadius = 10;
    final int maxRadius = 100;

    public AsteroidRock(Position p, Direction d, int r) {
        position = p;
        direction = d;
        radius = r;
    }

    public AsteroidRock(Position p, Direction d) {
        position = p;
        direction = d;
        radius = randInt(maxRadius, minRadius);
    }

    public void move(Field field) {
        moveX(field);
        moveY(field);
    }

    private void moveX(Field field) {
        int displacedX = position.x + ((int) (delta * Math.cos(Math.toRadians(direction.getAngle()))));
        displacedX = scrubMovement(displacedX, field.width);
        position.x = displacedX;
    }

    private void moveY(Field field) {
        int displacedY = position.y + ((int) (delta * Math.sin(Math.toRadians(direction.getAngle()))));
        displacedY = scrubMovement(displacedY, field.height);
        position.y = displacedY;
    }

    private int scrubMovement(int position, int boundary) {
        return (position + boundary) % boundary;
    }

    public WorldImage makeImage() {
        return new DiskImage(position, radius, new Color(222, 184, 135));
    }

    public List<AsteroidRock> explode() {
        List<AsteroidRock> asteroids = new ArrayList<AsteroidRock>();
        for (int i = 0; i < randInt(4, 2); i++) {
            AsteroidRock asteroid = new AsteroidRock(
                    this.position,
                    new Direction(randInt(360, 0)),
                    randInt(radius, minRadius));
            asteroids.add(asteroid);
        }
        return asteroids;
    }

    private int randInt(int max, int min) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void update(Field field) {
        move(field);
    }

    public Position getPosition() {
        return position;
    }
}
