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
    private static HashMap<paper, ArrayList<String>> questions;

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
        questions = new HashMap<paper, ArrayList<String>>();
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

        //TESTING TO SEE IF TEH THING SWORKS

        for(int y = 0; y < sepPapers.length; y++){    
            System.out.println("Paper: " + (y+1));
            for(paper x: sepPapers[y]){
                System.out.println(x.getName());
            }
            System.out.println();
        }

        //Creating three windows for the three papers and also testQuestionOutputWindow
        JFrame questionNumber = new JFrame("Question Number");
        JLabel numTxtField = new JLabel("Question Numbers will appear here");
        questionNumber.add(numTxtField);
        questionNumber.setBounds(800, 800, 200, 100);
        questionNumber.dispatchEvent(new WindowEvent(questionNumber, WindowEvent.WINDOW_CLOSING));
        questionNumber.setVisible(true);

        for(int x = 0; x < sepPapers.length/2; x++){
            if(sepPapers[x].size() == 0) continue;
            int xIndex = 800 + 300*x;
            makePaperWindow("Paper " + String.valueOf(x+1), sepPapers[x], sepPapers[x+3], xIndex, 400, numTxtField);
        }

        // }
        // makePaperWindow("Paper 2", sepPapers[1], sepPapers[4], 1100, 400);
        // makePaperWindow("Paper 3", sepPapers[2], sepPapers[5], 1400, 400);
        
        
    }

    public static GridBagConstraints createConstraints(int gridy, int anchor, int ipady){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = gridy;
        c.anchor = anchor;
        c.ipadx = 400;
        c.ipady = ipady;
        return c;
    }

    public static void makePaperWindow(String paperNumber, ArrayList<paper> papers, ArrayList<paper> mrkSchemes, int x, int y, JLabel numTxtField){
        JFrame paperWindow = new JFrame("Search for \"" + searchTerm + "\" in " + subjectName);
        // paperWindow.setLayout(new FlowLayout());
        paperWindow.setLayout(new GridBagLayout());
        GridBagConstraints cTopAnchor = createConstraints(0, GridBagConstraints.PAGE_START, 0);
        paperWindow.add(new JLabel(paperNumber + ": "), cTopAnchor);
        // JPanel paper = makePaperPanel(papers);
        // JScrollPane paperScroll = new JScrollPane(paper);
        // paperWindow.add(paperScroll);
        JScrollPane paperScroll = makePaperPanel(papers, numTxtField);
        GridBagConstraints cNoAnchor1 = createConstraints(1, GridBagConstraints.CENTER, (int) paperScroll.getSize().getHeight());
        paperWindow.add(paperScroll, cNoAnchor1);

        GridBagConstraints cMidAnchor = createConstraints(2, GridBagConstraints.CENTER, 0);
        paperWindow.add(new JLabel("MarkSchemes: "), cMidAnchor);

        JScrollPane mrkSchemeScroll = makePaperPanel(mrkSchemes, numTxtField);
        GridBagConstraints cNoAnchor2 = createConstraints(3, GridBagConstraints.CENTER, (int) mrkSchemeScroll.getSize().getHeight());
        paperWindow.add(mrkSchemeScroll, cNoAnchor2);
        // JPanel mrkScheme = makePaperPanel(mrkSchemes);
        // JScrollPane mrkSchemeScroll = new JScrollPane(mrkScheme);
        // paperWindow.add(mrkSchemeScroll);

        paperWindow.setBounds(x, y, 300, 400);
        paperWindow.dispatchEvent(new WindowEvent(paperWindow, WindowEvent.WINDOW_CLOSING));
        // paperWindow.setResizable(false);
        paperWindow.setVisible(true);
    }

    public static JScrollPane makePaperPanel(ArrayList<paper> papers, JLabel numTxtField){
        JPanel paperPanel = new JPanel();
        paperPanel.setLayout(new GridLayout(papers.size(), 1));
        
        for(paper x: papers){
            JLabel paperLink = new JLabel(x.getName());


            paperLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            paperLink.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    if (e.getClickCount() > 0){
                        File test = new File(dirNav.getCurPATH() + "/" + x.getName());
                        numTxtField.setText(String.join(", ", questions.get(x)));
                        // System.out.println(questions.get(x));
                        
                        try{ Desktop.getDesktop().open(test); } 
                        catch (IOException err) { System.out.println(err); }
                    }
                }
            });

            // System.out.println(paperLink.getSize().getHeight());
            paperPanel.add(paperLink);
        }
        // if(papers.size() > 19) paperPanel.setSize(400, 190);
        // else paperPanel.setSize(400, papers.size() * 10);
        JScrollPane scrollPanel = new JScrollPane(paperPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        if(papers.size() > 9) scrollPanel.setSize(400, 150);
        else scrollPanel.setSize(400, papers.size() * 17);
        // System.out.println(papers.size() + "*17 = " + scrollPanel.getSize().getHeight());
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
