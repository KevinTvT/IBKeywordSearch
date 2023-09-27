import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AlertWindow{
    private static JFrame window;
    private static JLabel alert;
    private static boolean exit;
    private static String alertStr;

    public AlertWindow(JFrame mainFrame, String str, int width, int height){
        window = new JFrame("");
        window.setLayout(new FlowLayout(FlowLayout.CENTER));
        alert = new JLabel(str);
        alertStr = str;
        window.add(alert);

        Dimension size = mainFrame.getSize();
        
        window.setBounds((int)size.getHeight(), (int)size.getWidth(), width, height);
        window.setLocationRelativeTo(mainFrame);
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        window.setResizable(false);
        window.setVisible(true);
    }

    public void start(){
        SwingWorker swingWorker = new SwingWorker() {
            @Override protected String doInBackground() throws Exception {
                run();
                return "Finished Execution";
            }

            @Override protected void done() {
                System.out.println("HELLO");
                exit();
            }
            
        };
        swingWorker.execute();
    }

    public void run(){
        for(int x = 0; !exit; x++){
            try{
                Thread.sleep(300);
                updateAlert(x%4);
            } catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }

    public void exit() {
        System.out.println("HELLO?");
        exit = true;
        window.dispose();
    }

    public static void updateAlert(int counter){
        String str = alertStr;
        for(int x = 0; x < counter; x ++){
            str = str + ".";
        }
        alert.setText(str);
        System.out.println(window.getComponent(0).toString());
        window.repaint();
        window.revalidate();
        System.out.println(str + ": x=" + counter);
    }

    public static void main(String[] args){
        AlertWindow alertWindow = new AlertWindow(new JFrame(), "Loading", 100, 50);
        alertWindow.start();
        try{ Thread.sleep(5000); }
        catch(InterruptedException e ) { System.out.println(e); }
        // alertWindow.exit();
    }
}
