import java.awt.*;

public class Rectangel {
    private Point startPoint;
    private int width;
    private int height;
    private Color color;
    private boolean doted;
    private boolean filled;


    public Rectangel(Point startPoint, Point endPoint, Color color,boolean doted,boolean filled) {

        this.startPoint = startPoint;
        this.width = endPoint.getX() - startPoint.getX();
        this.height = endPoint.getY() - startPoint.getY();
        this.color = color;
        this.doted=doted;
        this.filled=filled;
    }

    public boolean isFilled() {
        return filled;
    }

    public boolean isDoted() {
        return doted;
    }

    public Color getColor() {
        return color;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    /*public boolean inside(Point eraserPoint, Color backgroundColor) {
        int x = Math.min(startPoint.getX(), startPoint.getX() + width);
        int y = Math.min(startPoint.getY(), startPoint.getY() + height);
        int w = Math.abs(width);
        int h = Math.abs(height);

        // Check if the point is within the rectangle, if yes, set the color to background color
        if (eraserPoint.getX() >= x && eraserPoint.getX() <= x + w &&
                eraserPoint.getY() >= y && eraserPoint.getY() <= y + h) {
            color = new Color(0,0,0, 0);
            return true;
        }
        return false;
    }
*/

}
