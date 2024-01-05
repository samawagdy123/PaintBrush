import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

public class MyPicture extends JPanel {

    int drawingMode = -1;// -1 for none, 0 for line, 1 for oval, 2 for rectangle,4 for eraser, 5 for fill oval, 6 for fill rect, 7 for free line
    private boolean drawDotted=false,fill=false;
    private boolean draw;
    int x,y,width,height;
    // Add a stack to keep track of shapes drawn
    private Stack<Object> shapeStack = new Stack<>();
    private ArrayList<Line> lines= new ArrayList<>();
    private ArrayList<Oval> ovals= new ArrayList<>();
    private ArrayList<Rectangel> rects= new ArrayList<>();
    private ArrayList<Line> ovals_eraser= new ArrayList<>();
    private Point startPoint,endPoint;
    JButton oval,rect,line,color,clear,eraser,free_draw,undo;
    JCheckBox dottedCheckbox,fillCheckbox;
    Color col=Color.white;

    public MyPicture() {

        this.setBackground(Color.black);
        this.setFocusable(true);

        oval=new JButton("Oval");
        rect=new JButton("Rect");
        line=new JButton("Line");
        color=new JButton("Color");
        clear=new JButton("Clear");
        eraser=new JButton("Eraser");
        free_draw=new JButton("Free line");
        undo=new JButton("Undo");
        dottedCheckbox = new JCheckBox("Dotted");
        fillCheckbox = new JCheckBox("Filled");

        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                col = JColorChooser.showDialog(null,"my color",Color.white);
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    lines.clear();
                    ovals.clear();
                    rects.clear();
                    ovals_eraser.clear();
                    repaint();
            }
        });

        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingMode = 0; // Set drawing mode to line
            }
        });

        oval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingMode = 1; // Set drawing mode to oval
            }
        });

        rect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingMode = 2; // Set drawing mode to rectangel
            }
        });

        eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingMode = 4; // Set drawing mode to eraser
            }
        });

        free_draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingMode = 7; // Set drawing mode to free lines
            }
        });

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!shapeStack.isEmpty()) {
                    shapeStack.pop();
                    updateShapeLists(); // Update the shape lists based on the stack content
                    repaint();
                }
            }
        });

        dottedCheckbox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                drawDotted = dottedCheckbox.isSelected();
                repaint();
            }
        });
        fillCheckbox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                fill = fillCheckbox.isSelected();
                repaint();
            }
        });

        this.addMouseListener(new ShapeMouseListener());
        this.addMouseMotionListener(new ShapeMouseListener());
        this.add(line);
        this.add(oval);
        this.add(rect);
        this.add(free_draw);
        this.add(color);
        this.add(clear);
        this.add(eraser);
        this.add(undo);
        this.add(dottedCheckbox);
        this.add(fillCheckbox);
    }
    private void updateShapeLists() {
        lines.clear();
        ovals.clear();
        rects.clear();
        ovals_eraser.clear();

        // Add the shapes from the stack back to the respective lists
        for (Object shape : shapeStack) {
            if (shape instanceof Line) {
                lines.add((Line) shape);
            } else if (shape instanceof Oval) {
                ovals.add((Oval) shape);
            } else if (shape instanceof Rectangel) {
                rects.add((Rectangel) shape);
            }

        }repaint();
    }

    public void paint(Graphics g){
        super.paint(g);

        //temporary shape that follows the mouse movement while the user is dragging to create a new shape
        if (draw && startPoint != null && endPoint != null) {
             g.setColor(col);
             x= Math.min(startPoint.getX(), endPoint.getX());
             y = Math.min(startPoint.getY(), endPoint.getY());
             width = Math.abs(startPoint.getX()- endPoint.getX());
             height = Math.abs(startPoint.getY() - endPoint.getY());
             if(drawingMode==0)
                 g.drawLine(startPoint.getX(),startPoint.getY(), endPoint.getX(),endPoint.getY());
             else if(drawingMode==2)
                g.drawRect(x, y, width, height);
             else if (drawingMode==1)
                g.drawOval(x, y, width, height);
        }

//Draw real shapes and save it on screen
        for (Line lin : lines) {
            g.setColor(lin.getColor());
            if(lin.isDoted()){
                    // Create a dashed line effect
                    float[] dashPattern = {5, 5}; // 5 pixels drawn, 5 pixels skipped
                    BasicStroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, dashPattern, 0);
                    ((Graphics2D) g).setStroke(dashed);
                } else {
                    // If not dotted, set the stroke to a solid line
                    ((Graphics2D) g).setStroke(new BasicStroke());
                }
            g.drawLine(lin.getStartPoint().getX(), lin.getStartPoint().getY(), lin.getEndPoint().getX(), lin.getEndPoint().getY());
        }

        for (Oval ov : ovals) {
            g.setColor(ov.getColor());
            if(ov.isDoted()){
                float[] dashPattern = {5, 5}; // 5 pixels drawn, 5 pixels skipped
                BasicStroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, dashPattern, 0);// Create a dashed line effect
                ((Graphics2D) g).setStroke(dashed);
            } else {
                ((Graphics2D) g).setStroke(new BasicStroke()); // If not dotted, set the stroke to a solid line
            }
            if(ov.isFilled())
                g.fillOval(ov.getStartPoint().getX(),ov.getStartPoint().getY(),ov.getWidth(), ov.getHeight());
            else
                g.drawOval(ov.getStartPoint().getX(),ov.getStartPoint().getY(),ov.getWidth(), ov.getHeight());
        }

        for (Rectangel re : rects) {
            g.setColor(re.getColor());
            if(re.isDoted()){
                float[] dashPattern = {5, 5}; // 5 pixels drawn, 5 pixels skipped
                BasicStroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, dashPattern, 0);// Create a dashed line effect
                ((Graphics2D) g).setStroke(dashed);
            } else {
                ((Graphics2D) g).setStroke(new BasicStroke()); // If not dotted, set the stroke to a solid line
            }
            if(re.isFilled())
                g.fillRect(re.getStartPoint().getX(),re.getStartPoint().getY(),re.getWidth(), re.getHeight());
            else
                g.drawRect(re.getStartPoint().getX(),re.getStartPoint().getY(),re.getWidth(), re.getHeight());

        }

        for (Line re_eraser : ovals_eraser) {
            g.setColor(getBackground());
            ((Graphics2D)g).setStroke(new BasicStroke(3));
            g.drawLine(re_eraser.getStartPoint().getX(),re_eraser.getStartPoint().getY(),re_eraser.getEndPoint().getX(),re_eraser.getStartPoint().getY());
        }

    }

    // Add a method to handle the undo action
    class ShapeMouseListener implements MouseListener,MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {}
        
      @Override
        public void mousePressed(MouseEvent e) {
            startPoint = new Point(e.getX(), e.getY());
            draw=true;
        }

        @Override
        public void mouseReleased(MouseEvent e) { //to save shapes in arraylist after draw in drag
            endPoint = new Point(e.getX(), e.getY());
            draw=false;
            if(drawingMode==0){
                lines.add(new Line(startPoint, endPoint, col,drawDotted));
                shapeStack.push(lines.get(lines.size() - 1));
               repaint();
            }else if (drawingMode==1) {
                ovals.add(new Oval(startPoint, endPoint, col,drawDotted,fill));
                shapeStack.push(ovals.get(ovals.size() - 1));
                repaint();
            }else if (drawingMode==2) {
                rects.add(new Rectangel(startPoint, endPoint, col,drawDotted,fill));
                shapeStack.push(rects.get(rects.size() - 1));
                repaint();
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) { // to draw the shapes
            if (drawingMode == 7) {
                endPoint = new Point(e.getX(), e.getY());
                lines.add(new Line(startPoint, endPoint, col,drawDotted));
                startPoint = endPoint;
                repaint();
            }
            else if(drawingMode==4){
                endPoint = new Point(e.getX(), e.getY());
                ovals_eraser.add(new Line(startPoint, endPoint, getBackground(),drawDotted));
                startPoint = endPoint;
                repaint();
            }
            else if(drawingMode==2||drawingMode==1||drawingMode==0 ){ //rect & oval & fill Rect & fill oval
                if(draw){
                    endPoint=new Point(e.getX(), e.getY());
                    repaint();
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}

