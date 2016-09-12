package asteroids;

import Models.Direction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javalib.worldimages.Position;

/**
 *
 * @author Brandon Crecco
 */
public class Asteroids {

    private final static int width = 700;
    private final static int height = 700;
    private final static int lives = 3;

    /**
     * Launches a window for a playable game of asteroids
     *
     * @param args none
     */
    public static void main(String[] args) {
        List<Enemy> enemies = new ArrayList<Enemy>();
        for (int i = 0; i < randInt(5, 4); i++) {
            AsteroidRock asteroid = new AsteroidRock(
                    new Position(randInt(width, 0), randInt(height, 0)), 
                    new Direction(randInt(359, 0)));
            enemies.add(asteroid);
        }

        List<Player> players = new ArrayList<Player>();
        Player ship = new Ship(new Position(width / 2, height / 2), new Direction(0), lives);
        ship.setState(State.PLAYING);
        players.add(ship);

        Field gameField = new Field(width, height, enemies, players);
        gameField.bigBang(gameField.width, gameField.height, .0001);
    }

    private static int randInt(int max, int min) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
