import java.awt.*;
public class Line {
    private Point startPoint;
    private Point endPoint;
    private Color color;
    private boolean doted;


    public Line(Point startPoint, Point endPoint, Color color, boolean doted) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.color = color;
        this.doted=doted;

    }

    public boolean isDoted() {
        return doted;
    }

    public Point getStartPoint() {
        return startPoint;
    }
    public Point getEndPoint() {
        return endPoint;
    }
    public Color getColor() {
        return color;
    }
        public boolean inside(Point eraserPoint, Color backgroundColor) {
            // Calculate distance from point to line using line equation
            double distance = Math.abs(((endPoint.getY() - startPoint.getY()) * eraserPoint.getX()
                    - (endPoint.getX() - startPoint.getX()) * eraserPoint.getY() + endPoint.getX() * startPoint.getY() - endPoint.getY() * startPoint.getX())
                    / (Math.sqrt(Math.pow(endPoint.getY() - startPoint.getY(), 2) + Math.pow(endPoint.getX() - startPoint.getX(), 2))));

            // If distance is within a threshold, set the color to background color (erase)
            if (distance <= 1) { // Set a suitable threshold value
                color = new Color(0,0,0, 0);
                return true;
            }
            return false;
    }

}
