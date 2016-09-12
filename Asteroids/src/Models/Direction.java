package Models;

/**
 *
 * @author Brandon Crecco
 */
public class Direction {
    private int angle;
    
    public Direction(int angle) {
        this.angle = (angle + 360) % 360;
    }
    
    public int getAngle() {
        return angle;
    }
    
    public void setAngle(int angle) {
        this.angle = (angle + 360) % 360;
    }
}
