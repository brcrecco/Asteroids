package asteroids;

import javalib.worldimages.*;
import java.awt.Color;
import Models.Direction;
/**
 *
 * @author Brandon Crecco
 */
public class Bullet {
    private Position position;
    private Direction direction;
    
    private final int radius = 5;
    private final int delta = 4;
    
    public Bullet (Position p, Direction d) {
        position = p;
        direction = d;
    }
    
    public WorldImage makeImage() {
        return new DiskImage(position, radius, Color.WHITE);
    }
    
    public void move(Field f) {
        position.x = position.x + ((int) (delta * Math.cos(Math.toRadians(direction.getAngle()))));
        position.y = position.y - ((int) (delta * Math.sin(Math.toRadians(direction.getAngle()))));
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public Position getPosition() {
        return position;
    }
}
