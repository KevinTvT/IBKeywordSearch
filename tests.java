import javax.swing.*;
import java.awt.event.*;

public class tests {
    static JFrame alertWindow;
    static JLabel alert;
    static boolean exit;
    public static void main(String[] args) {
        alertWindow = new JFrame("STUFF");
        String alertStr = "Loading";
        alert = new JLabel(alertStr);
        alertWindow.add(alert);
        alertWindow.setBounds(100, 100, 100, 25);
        alertWindow.setVisible(true);

        SwingWorker swWkr = new SwingWorker() {
            @Override protected String doInBackground() throws Exception{
                exit = false;
                for(int x = 0; x < 25; x++){
                    try{ Thread.sleep(300);}
                    catch(InterruptedException e){}
                    System.out.println("doing stuff in background " + x);
                }
                exit = true;
                alertWindow.dispose();
                return "Finished Execution";
            }
        };
        swWkr.execute();

        for(int x = 0; !exit; x++){
            try{
                Thread.sleep(300);
                for(int i = 0; i < x%4; i++) alertStr = alertStr + ".";
                alert.setText(alertStr);
                // JLabel stuff = (JLabel) alertWindow.getComponent(0);
                // System.out.println(stuff.getText());
                System.out.println(alert.getText());
                alertStr = "Loading";
                alertWindow.repaint();
                alertWindow.revalidate();
            } catch(InterruptedException err) { System.out.println(err); }
        }
    }  

}
