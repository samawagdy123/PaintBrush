
import javax.swing.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        JFrame frame=new JFrame();
        MyPicture p=new MyPicture();
        frame.setTitle("Moving");
        frame.setSize(1700,800);
        frame.setContentPane(p);
        frame.setVisible(true);
    }
}