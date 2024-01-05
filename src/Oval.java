import java.awt.*;

class Oval {
    private Point startPoint;
    private int width;
    private int height;
    private Color color;
    private boolean doted;
    private boolean filled;

    public Oval(Point startPoint, Point endPoint, Color color,boolean doted,boolean filled) {

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
        int x = startPoint.getX();
        int y = startPoint.getY();

        int xRadius = width / 2;
        int yRadius = height / 2;

        int xCenter = x + xRadius;
        int yCenter = y + yRadius;

        // Calculate distance from point to center of oval
        double distance = Math.pow((eraserPoint.getX() - xCenter) / xRadius, 2) +
                Math.pow((eraserPoint.getY() - yCenter) / yRadius, 2);

        // If distance is less than or equal to 1, the point is inside the oval
        if (distance <= 0.1) {
            color = new Color(0,0,0, 0);
            return true;
        }
        return false;
    }*/
}
