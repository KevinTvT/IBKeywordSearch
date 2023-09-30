import javax.swing.*;
import javax.swing.text.html.*;
import java.awt.FlowLayout;
import java.awt.event.*;

public class tests {
    public static void main(String[] args) {
        JFrame stuff = new JFrame("Hello");
        JLabel what = new JLabel("<html><img src=\"/img/mainGUI.png\" alt=\"estoy dumb\"></html>");
        // JPanel htmlPanel = new HtmlPanel("<html><body><img src=\"img/mainGUI.png\"></body></html>");

        stuff.add(what);
        // stuff.add(htmlPanel);
        stuff.setVisible(true);
    }  

}
