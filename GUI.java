import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class GUI extends JFrame {
    //the current subject folder
    private static String home = System.getProperty("user.home");
    private static File resourceFolder = new File(home+"/Downloads/IBKeywordSearchResources");
    private static File currentFolder;

    public GUI(){
        //Set up the window
        

        //c.add(classDropDown);
        
    }

    

    public static void main(String[] args){
        GUI window = new GUI();
        
        window.setBounds(400, 400, 400, 200);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
    }
}
