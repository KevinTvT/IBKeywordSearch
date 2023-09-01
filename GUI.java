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
    private static String searchTerm;
    private static String subjectName;

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
                subjectName = (String) temp.getSelectedItem();
                dirNav.setCurFolder(subjectName);
            }
        });
        return list;
    }

    public static paper getMrkScheme(ArrayList<String> tests, paper test){
        for(String t: tests){
            if(t.toLowerCase().contains("marks")){
                paper markScheme = new paper(t);
                if(test.otherIsMrkScheme(markScheme))
                    return markScheme;
            }
        }
        throw new NullPointerException("ERROR: Could not find the Markscheme of this paper" + test.getName());
    }

    public static void createPapersWindow(ArrayList<paper> papers){
        // System.out.println("HELLO \n\n\n\n\n\n\n\n");
        ArrayList<paper>[] sepPapers = new ArrayList[6];

        // for(paper x: papers){
        //     System.out.println(x.getName());
        // }

        mergeSortPaper sorter = new mergeSortPaper();

        //initializing separated papers and organized papers
        for(int x = 0; x < sepPapers.length; x++){
            sepPapers[x] = new ArrayList<>();
        }


        for(paper x: papers){
            if(x.isMrkScheme()) {
                sepPapers[x.getPaper()+2].add(x);
            }
            else sepPapers[x.getPaper()-1].add(x);
        }

        // System.out.println(sepPapers);
        // for(paper x: sepPapers[0]){
        //     System.out.println(x.getName());
        // }

        for(int x = 0; x < sepPapers.length; x++){
            sepPapers[x] = sorter.startPap(sepPapers[x]);
        }

        for(int y = 0; y < sepPapers.length; y++){    
            System.out.println("Paper: " + (y+1));
            for(paper x: sepPapers[y]){
                System.out.println(x.getName());
            }
            System.out.println();
        }

        //Creating three windows for the three papers
        for(int x = 0; x < sepPapers.length/2; x++){
            if(sepPapers[x].size() == 0) continue;
            int xIndex = 800 + 300*x;
            makePaperWindow("Paper " + String.valueOf(x+1), sepPapers[x], sepPapers[x+3], xIndex, 400);
        }
        // }
        // makePaperWindow("Paper 2", sepPapers[1], sepPapers[4], 1100, 400);
        // makePaperWindow("Paper 3", sepPapers[2], sepPapers[5], 1400, 400);
        
        
    }

    public static JFrame makePaperWindow(String paperNumber, ArrayList<paper> papers, ArrayList<paper> mrkSchemes, int x, int y){
        JFrame paperWindow = new JFrame("Search for \"" + searchTerm + "\" in " + subjectName);
        paperWindow.setLayout(new FlowLayout());
        paperWindow.add(new JLabel(paperNumber + ": "));
        // JPanel paper = makePaperPanel(papers);
        // JScrollPane paperScroll = new JScrollPane(paper);
        // paperWindow.add(paperScroll);
        paperWindow.add(makePaperPanel(papers));
        paperWindow.add(new JLabel("MarkSchemes: "));
        paperWindow.add(makePaperPanel(mrkSchemes));
        // JPanel mrkScheme = makePaperPanel(mrkSchemes);
        // JScrollPane mrkSchemeScroll = new JScrollPane(mrkScheme);
        // paperWindow.add(mrkSchemeScroll);

        paperWindow.setBounds(x, y, 300, 400);
        paperWindow.dispatchEvent(new WindowEvent(paperWindow, WindowEvent.WINDOW_CLOSING));
        paperWindow.setResizable(false);
        paperWindow.setVisible(true);


        return paperWindow;
    }

    public static JScrollPane makePaperPanel(ArrayList<paper> papers){
        JPanel paperPanel = new JPanel();
        paperPanel.setLayout(new GridLayout(papers.size(), 1));
        
        for(paper x: papers){
            JLabel paperLink = new JLabel(x.getName());
            paperLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            paperLink.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    if (e.getClickCount() > 0){
                        File test = new File(dirNav.getCurPATH() + "/" + x.getName());
                        try{ Desktop.getDesktop().open(test); } 
                        catch (IOException err) { System.out.println(err); }
                    }
                }
            });
            paperPanel.add(paperLink);
        }
        JScrollPane scrollPanel = new JScrollPane(paperPanel);
        return scrollPanel;
        // return paperPanel;
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
                    searchTerm = searchBox.getText().toLowerCase();
                    ArrayList<String> tests = dirNav.getTestNames();
                    HashMap<paper, ArrayList<String>> questions = new HashMap<paper, ArrayList<String>>();

                    
                    for(int i = 0; i < tests.size()-1; i++){
                        // System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
                        
                        // System.out.println(tests);
                        File test = new File(dirNav.getCurPATH() + "/" + tests.get(i));
                        String text = "";
                        
                        // Skips if its a case study or the hidden folder with metadata for folder
                        // System.out.println(test.getName() + ", "+ (test.getName().toLowerCase().indexOf("marks") != -1));
                        if(test.getName().contains("case") || test.getName().contains(".DS_Store") || test.getName().toLowerCase().contains("marks")) continue;
                        
                        // System.out.println(test.getName());

                        //Getting the text from the file
                        try{
                            PDDocument document =  Loader.loadPDF(test);
                            PDFTextStripper pdfStripper = new PDFTextStripper();
                            text = pdfStripper.getText(document);
                        } catch (IOException ioException) { System.out.println(ioException); }
                        
                        // System.out.println(text.indexOf("  "));

                        //Removes extra whitespace
                        while(text.contains("  ")) { text = text.substring(0, text.indexOf("  ")) + text.substring(text.indexOf("  ") + 1); }

                        // System.out.println(text);

                        questionFinder qFind = new questionFinder();
                        paper testPaper = new paper(test.getName());
                        // System.out.println(test.getName());
                    
                        ArrayList<String> testQuestion = qFind.findQuestion(text, searchTerm);
                        if(testQuestion.size() != 0){
                            // System.out.println(testQuestion);
                            questions.put(testPaper, testQuestion);
                            try{
                                paper mrkScheme = getMrkScheme(tests, testPaper);
                                questions.put(mrkScheme, testQuestion);
                            }catch(NullPointerException err){
                                System.out.println(err);
                            }
                        }
                        // System.out.println("HELLO!!! \n\n\n\n\n");
                    }
                    if(questions.size() == 0) throw new NullPointerException("ERROR: Keyword was not found in any test");
                    try{ createPapersWindow(new ArrayList<paper>(questions.keySet())); }
                    catch(NullPointerException err){ System.out.println(err); }

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
