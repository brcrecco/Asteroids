package asteroids;

import Models.Direction;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Brandon Crecco
 */
public class Ship implements Player {

    private Position position;
    private Direction direction;
    private int lives;
    private State state;
    private List<Bullet> bullets;

    private final int deltaX = 2;
    private final int deltaY = 2;
    private final int deltaAngle = 2;
    private final int shipWidth = 20;
    private final int shipHeight = 20;
    private final Color color = Color.WHITE;

    public Ship(Position p, Direction d, int l) {
        position = p;
        direction = d;
        lives = l;
        state = state.WAITING;
        bullets = new ArrayList<Bullet>();
    }

    public void move(String key) {
        switch (key) {
            case "up":
                position.y -= deltaY;
                break;
            case "down":
                position.y += deltaY;
                break;
            case "left":
                position.x -= deltaX;
                break;
            case "right":
                position.x += deltaX;
                break;
            default:
                break;
        }
    }

    public void rotateClockwise() {
        int newAngle = direction.getAngle() - deltaAngle;
        scrubAngle(newAngle);
        direction.setAngle(newAngle);
    }

    public void rotateCounterClockwise() {
        int newAngle = (direction.getAngle() + deltaAngle);
        newAngle = scrubAngle(newAngle);
        direction.setAngle(newAngle);
    }

    private int scrubAngle(int angle) {
        return (angle + 360) % 360;
    }

    public void shoot() {
        if (bullets.size() <= 7) {
            bullets.add(new Bullet(
                    new Position(position.x, position.y),
                    new Direction(scrubAngle(direction.getAngle()))));
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public void update(Field f, String key) {
        handleInput(key);
        moveBullets(f);
    }

    private void handleInput(String key) {
        switch (key) {
            case "up":
            case "down":
            case "left":
            case "right":
                move(key);
                break;
            case "s":
                rotateCounterClockwise();
                System.out.println(direction.getAngle());
                break;
            case "f":
                rotateClockwise();
                break;
            case " ":
                shoot();
                break;
            default:
                break;
        }
    }

    private void moveBullets(Field f) {
        List<Bullet> toRemove = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            bullet.move(f);
            if (bullet.getPosition().x < 0
                    || bullet.getPosition().x > f.width
                    || bullet.getPosition().y < 0
                    || bullet.getPosition().y > f.height) {
                toRemove.add(bullet);
            }
        }
        for (Bullet bullet : toRemove) {
            bullets.remove(bullet);
        }
    }

    public WorldImage makeImage() {
        WorldImage ship;
        switch (state) {
            case PLAYING:
                ship = drawImage();
                break;
            case WAITING:
                ship = new DiskImage(position, 0, color);
                ;
                break;
            default:
                ship = new DiskImage(position, 0, color);
                ;
                break;
        }
        return ship;
    }

    private WorldImage drawImage() {
        WorldImage image = new DiskImage(position, 0, color);

        WorldImage shipImage = drawShip();
        image = image.overlayImages(image, shipImage);

        WorldImage bulletImage = drawBullets();
        image = image.overlayImages(image, bulletImage);

        return image;
    }

    private WorldImage drawShip() {
        Position p1 = new Position(
                (int) (position.x - ((Math.cos(Math.toRadians(scrubAngle(90 + direction.getAngle()))) * shipWidth))),
                (int) (position.y - ((Math.sin(Math.toRadians(scrubAngle(90 + direction.getAngle() + 90))) * shipWidth))));
        Position p2 = new Position(
                (int) (position.x - ((Math.cos(Math.toRadians(scrubAngle(90 - direction.getAngle()))) * shipWidth))),
                (int) (position.y - ((Math.sin(Math.toRadians(scrubAngle(90 - direction.getAngle()))) * shipWidth))));
        Position p3 = new Position(
                (int) (position.x + ((Math.cos(Math.toRadians(direction.getAngle())) * shipHeight))),
                (int) (position.y - ((Math.sin(Math.toRadians(direction.getAngle())) * shipHeight))));

        return new TriangleImage(p1, p2, p3, color);
    }

    private WorldImage drawBullets() {
        WorldImage bulletImage = new DiskImage(position, 0, color);
        for (Bullet bullet : bullets) {
            bulletImage = bulletImage.overlayImages(bulletImage, bullet.makeImage());
        }
        return bulletImage;
    }
    
    public State getState(){
        return state;
    }
    
    public List<Bullet> getBullets() {
        return bullets;
    }
}
