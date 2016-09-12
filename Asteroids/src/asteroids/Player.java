package asteroids;

import javalib.worldimages.*;
import java.util.List;
/**
 *
 * @author Brandon Crecco
 */
public interface Player {

    public void move(String key);
    public void rotateClockwise();
    public void rotateCounterClockwise();
    public void shoot();
    public WorldImage makeImage();
    public void setState(State state);
    public void update(Field f, String key);
    public State getState();
    public List<Bullet> getBullets();
}
