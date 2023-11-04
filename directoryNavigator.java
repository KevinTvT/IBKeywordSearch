import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class directoryNavigator {

    private static String home;
    private static File resFolder;
    private static File curFolder;
    private static String PATH;
    private static File readMe;
    private static String readMeStr;

    public directoryNavigator() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        try{
            Path temp = Files.createTempFile("readMe-", ".md");
            Files.copy(classLoader.getResourceAsStream("README.md"), temp, StandardCopyOption.REPLACE_EXISTING);
            readMe = temp.toFile();
            
        } catch(Exception e){ System.out.println(e); }
        home = System.getProperty("user.home");
        try { readMeStr = Files.readString(readMe.toPath()); }
        catch (IOException e) { System.out.println(e); }
        PATH = home + "/" + readMeStr.substring(readMeStr.indexOf(": ") + 2, readMeStr.indexOf("~"));
        resFolder = new File(PATH);
        if (resFolder.list() == null) {
            System.out.println("EXCEPTION THROWN");
            throw new NullPointerException("FOLDER NOT FOUND");
        }
    }

    public directoryNavigator(boolean folderError) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        try{
            Path temp = Files.createTempFile("readMe-", ".md");
            Files.copy(classLoader.getResourceAsStream("README.md"), temp, StandardCopyOption.REPLACE_EXISTING);
            readMe = temp.toFile();

        } catch(Exception e) {System.out.println("WHOOPSIES: " + e); }
        home = System.getProperty("user.home");
        try { readMeStr = Files.readString(readMe.toPath()); }
        catch (IOException e) { System.out.println(e); }
        PATH = folderError ? home : home + "/" + readMeStr.substring(readMeStr.indexOf(": ") + 2, readMeStr.indexOf("~"));
        resFolder = new File(PATH);
    }

    public void main(String[] args) {
        getSubjectNames();
        curFolder = new File(home + "/Downloads/IBKeywordSearchResources/Biology");
        getTestNames();
    }

    public ArrayList<String> getSubjectNames() {
        System.out.println("PATH: " + resFolder.getPath());
        ArrayList<String> subjectNames = new ArrayList<String>();
        for (File x : resFolder.listFiles()) {
            if (!x.isHidden())
                subjectNames.add(x.getName());
        }
        return subjectNames;
    }

    public String getFolderNames() {
        System.out.println("FOLDER PATH: " + resFolder.getPath());
        BST tree = new BST();
        for (File x : resFolder.listFiles()) {
            // System.out.println(x.getName());
            // System.out.println(x.isDirectory());
            // System.out.println(x.isHidden());
            // System.out.println("%s: isDirectory -> %s; isHidden ->
            // %s".format(x.getName(), x.isDirectory(), x.isHidden()));
            if (!x.isDirectory() || x.isHidden())
                continue;
            tree.insert(x.getName());
        }
        return tree.inorder();
    }

    public ArrayList<String> getTestNames() {
        ArrayList<String> testNames = new ArrayList<String>();
        if (curFolder != null) {
            for (File x : curFolder.listFiles()) {
                if (x.getName().substring(0, 1).equals(".")) continue;
                testNames.add(x.getName());
            }
        } else System.out.println("CURRENT FOLDER IS NOT SET");
        return testNames;
    }

    // GETTERS AND SETTERS
    public File getCurFolder() {
        return curFolder;
    }

    public void setCurFolder(String folderName) {
        curFolder = new File(PATH + "/" + folderName);
    }

    public File getResFolder() {
        return resFolder;
    }

    public void setResFolder(String newResFolder) {
        String[] withoutHome = newResFolder.split("/"); // Index 2 of the array is where the /Users/username/ stuff
                                                        // ends.
        String folderString = "";
        for (int x = 3; x < withoutHome.length; x++) {
            folderString = folderString + "/" + withoutHome[x];
        }
        try {
            if (withoutHome.length > 3)
                Files.write(readMe.toPath(), (readMeStr.substring(0, readMeStr.indexOf(": ") + 2) + folderString
                        + readMeStr.substring(readMeStr.indexOf("~"))).getBytes());
            else
                Files.write(readMe.toPath(), (readMeStr.substring(0, readMeStr.indexOf(": ") + 2)
                        + readMeStr.substring(readMeStr.indexOf("~") - 1)).getBytes());
        } catch (IOException e) {
            System.out.println(e);
        }
        resFolder = new File(newResFolder);
        curFolder = null;
        System.out.println("CHANGED TO " + newResFolder);
    }

    public String getCurPATH() {
        return curFolder.getAbsolutePath();
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String newPATH) {
        PATH = newPATH;
    }

    public String getHome() {
        return home;
    }
}
