import javax.swing.*;
import java.util.ArrayList;

public class UserInterface {
    public static void main(String [] args) {

    }
    public static void OpenGUI(ArrayList<Product>ProductDetails){
        UserFrame frame=new UserFrame(ProductDetails);
        frame.setVisible(true);
        frame.setTitle("Westminster Shopping Center");
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}