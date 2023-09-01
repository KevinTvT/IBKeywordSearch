import java.io.*;
import java.util.*;

public class directoryNavigator {

    private static String home;
    private static File resFolder;
    private static File curFolder;
    private static String PATH;

    public directoryNavigator(){
        home = System.getProperty("user.home");
        PATH = home+"/Downloads/IBKeywordSearchResources";
        resFolder = new File(PATH);
        if(resFolder.list() == null) { System.out.println("EXCEPTION THROWN"); throw new NullPointerException("FOLDER NOT FOUND"); }
    }

    public void main(String[] args){
        getSubjectNames();
        curFolder = new File(home+"/Downloads/IBKeywordSearchResources/Biology");
        getTestNames();
    }
    public ArrayList<String> getSubjectNames(){
        ArrayList<String> subjectNames = new ArrayList<String>();
        for(File x: resFolder.listFiles()){
            if(!x.getName().equals(".DS_Store"))
                subjectNames.add(x.getName());
        } 
        return subjectNames;
    }
    public ArrayList<String> getTestNames(){
        ArrayList<String> testNames = new ArrayList<String>();
        if(curFolder != null){
            for(File x: curFolder.listFiles()){
                if(x.getName().equals(".DS_STORE")) continue;
                testNames.add(x.getName());
            }
        } else System.out.println("CURRENT FOLDER IS NOT SET");
        return testNames;
    }

    //GETTERS AND SETTERS
    public File getCurFolder() { return curFolder; }

    public File setCurFolder(String folderName){
        curFolder = new File(PATH + "/" + folderName);
        //System.out.println("Setting Current Folder");
        // System.out.println("current folder is: " + curFolder.getName());
        // System.out.println("current folder path: " + curFolder.getAbsolutePath());
        return curFolder;
    }

    public File getResFolder() { return resFolder; }

    public String getCurPATH() { return curFolder.getAbsolutePath(); }
}
