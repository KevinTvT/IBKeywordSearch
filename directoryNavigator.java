import java.io.*;
import java.util.*;

public class directoryNavigator {
    private static String home = System.getProperty("user.home");
    private static File resFolder = new File(home+"/Downloads/IBKeywordSearchResources");
    private static File curFolder;

    public static void main(String[] args){
        getSubjectNames();
        curFolder = new File(home+"/Downloads/IBKeywordSearchResources/Biology");
        getTestNames();
    }
    public static ArrayList<String> getSubjectNames(){
        ArrayList<String> subjectNames = new ArrayList<String>();
        for(File x: resFolder.listFiles()){
            subjectNames.add(x.getName());
        } 
        return subjectNames;
    }
    public static ArrayList<String> getTestNames(){
        ArrayList<String> testNames = new ArrayList<String>();
;        for(File x: curFolder.listFiles()){
            testNames.add(x.getName());
        }
        return testNames;
    }

    public static File setCurFolder(File folderName){
        curFolder = folderName;
        return curFolder;
    }

}
