package asteroids;

import java.util.List;
import java.util.ArrayList;
import javalib.worldimages.*;
import javalib.funworld.World;

/**
 * Class for the game field
 *
 * @author Brandon Crecco
 */
public class Field extends World {

    int width;
    int height;
    List<Enemy> enemies;
    List<Player> players;

    public Field(int width, int height, List<Enemy> enemies, List<Player> players) {
        this.width = width;
        this.height = height;
        this.enemies = enemies;
        this.players = players;
    }

    public WorldImage makeImage() {
        WorldImage field = new RectangleImage(new Position(0, 0), 1500, 1500, java.awt.Color.BLACK);
        for (Enemy enemy : enemies) {
            field = field.overlayImages(field, enemy.makeImage());
        }
        for (Player player : players) {
            field = field.overlayImages(field, player.makeImage());
        }
        return field;
    }
    
    public World onTick() {
        for(Enemy enemy : enemies) {
            enemy.update(this);
        }
        for(Player player : players) {
            player.update(this, "");
        }
        checkCollisions();
        return this;
    }
    
    private void checkCollisions(){
        List<Enemy> hitEnemies = new ArrayList<Enemy>();
        for(Enemy enemy : enemies) {
            for(Player player : players) {
                if(player.getState() == State.PLAYING) {
                    for(Bullet bullet : player.getBullets()) {
                        if(bullet.getPosition() == enemy.getPosition()){
                            hitEnemies.add(enemy);
                        }
                    }
                }
            }
        }
        for(Enemy enemy : hitEnemies) {
            enemies.remove(enemy);
        }
    }
    
    public World onKeyEvent(String key) {
        for(Player player : players) {
            player.update(this, key);
        }
        return this;
    }
}
