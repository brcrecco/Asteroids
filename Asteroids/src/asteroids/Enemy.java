package asteroids;

import javalib.worldimages.*;
/**
 *
 * @author Brandon Crecco
 */
public interface Enemy {
    public void update(Field f);
    public WorldImage makeImage();
    public Position getPosition();
}
