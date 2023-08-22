import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;

import org.apache.pdfbox.Loader;
// Remeber when exporting as jar file include the imported librar
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class GUI extends JFrame {
    private static directoryNavigator dirNav;

    public GUI(){
        //Set up the window
        super("IB Keyword Search :)");
        
        //setting up directory navigator
        //throws a NUll POINTER EXCEPTION when folder not found
        try{ dirNav = new directoryNavigator(); }
        catch (NullPointerException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public static JComboBox createComboBox (String[] objects){
        JComboBox list = new JComboBox(objects);
        list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JComboBox temp = (JComboBox) e.getSource();
                String subjectName = (String) temp.getSelectedItem();
                dirNav.setCurFolder(subjectName);
            }
        });
        return list;
    }

    public static Container createSearchRow(){
        Container searchRow = new Container();
        searchRow.setLayout(new GridLayout(1, 2));

        JTextField searchBox = new JTextField("");
        JButton searchButton = new JButton("Search");
        //CREATING SEARCH BUTTON
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String searchTerm = searchBox.getText();
                ArrayList<String> tests = dirNav.getTestNames();
                HashMap<String, ArrayList<String>> questions = new HashMap<String, ArrayList<String>>();
                
                for(int i = 0; i < tests.size(); i++){
                    // System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
                    
                    // System.out.println(tests);
                    File test = new File(dirNav.getSubjectPATH() + "/" + tests.get(i));
                    String text = "";
                    
                    // System.out.println(test.getName());

                    //Getting the text from the file
                    try{
                        PDDocument document =  Loader.loadPDF(test);
                        PDFTextStripper pdfStripper = new PDFTextStripper();
                        text = pdfStripper.getText(document);
                    } catch (IOException ioException) { System.out.println(ioException); }
                    
                    // System.out.println(text.indexOf("  "));

                    //Removes extra whitespace
                    while(text.indexOf("  ") != -1) { text = text.substring(0, text.indexOf("  ")) + text.substring(text.indexOf("  ") + 1); }

                    // System.out.println(text);

                    questionFinder qFind = new questionFinder();
                    questions.put(test.getName(), qFind.findQuestion(text, searchTerm));
                }

                System.out.println("HELLO!!! \n\n\n\n\n");
            }
        });
        
        searchRow.add(searchBox);
        searchRow.add(searchButton);

        // System.out.println("HELLO!!!!! \n\n\n\n\n\n\n");
        // System.out.println(questions);
        // System.out.println(questions.get("2016Nov_Computer_science_paper_3__HL_Spanish.pdf"));
        
        return searchRow;
    }

    public static void main(String[] args){
        GUI window = new GUI();
        window.setLayout(new GridLayout(4, 1));

        window.setBounds(400, 400, 400, 500);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setResizable(false);
    
        //ADDING SUBJECT DROP DOWN STUFF
        ArrayList<String> subjectsList = dirNav.getSubjectNames();
        String[] subjects = subjectsList.toArray(new String[subjectsList.size()]);
        JComboBox subjectDropDown = createComboBox(subjects);
        subjectDropDown.setSelectedIndex(0);
        window.add(subjectDropDown);
        
        //ADDING SEARCH BOX STUFF
        Container searchRow = createSearchRow();
        window.add(searchRow);

        window.setVisible(true);
    }
}
