import java.io.*;
import java.util.*;

public class mergeSortPaper{
    // need to check if the last few digits are TZ1 and if so then add __ digits for the organization
    //need to check if it is paper_1 paper_2 or P1 P2
    //note two "_" between paper1 and HL
    //for those that atre shortened, HL or SL is in front of P1 P2 and that this only doesn't apply to some of Bio
    public static void merge(List<paper> arr, List<paper> leftArray, List<paper> rightArray, int left, int right){
        int i , j , k;
        i = j = k = 0;
        while(i < left && j < right){
            paper leftArr = leftArray.get(i);
            paper rightArr = rightArray.get(j);
            if(leftArr.isLater(rightArr)) arr.set(k++, leftArray.get(i++));
            else arr.set(k++, rightArray.get(j++));
        }
        
        while(i < left){
            arr.set(k++, leftArray.get(i++));
        }
        
        while(j < right){
            arr.set(k++, rightArray.get(j++));
        }
    }
    
    public static void mergeSort(List<paper> arr, int n){
        //System.out.println("mergeSort has been run");
        if (n >= 2){
            int mid = n / 2;
            List<paper> leftArray = new ArrayList<>(Collections.nCopies(mid, null));
            List<paper> rightArray = new ArrayList<>(Collections.nCopies(n- mid, null));
            for(int i = 0; i < mid; i++) leftArray.set(i, arr.get(i));
            for(int i = mid; i < n; i++) rightArray.set(i - mid, arr.get(i));
            mergeSort(leftArray, mid);
            mergeSort(rightArray, n - mid);
            
            merge(arr, leftArray, rightArray, mid, n - mid);
        } else {
            return;
        }
    }
    
    public static ArrayList<String> startStr(ArrayList<String> arr){
        ArrayList<paper> arr1 = new ArrayList<>();
        for(int x = 0; x < arr.size(); x++){
            arr1.add(new paper(arr.get(x)));
        }
        mergeSort(arr1, arr1.size());
        
		//Getting rid of renaming it
        // for(int x = 0; x < arr1.size(); x++){
        //     arr.set(x, arr1.get(x).rename("Computer_Science"));
        // }
        
        return arr;
    }

	public static ArrayList<paper> startPap(ArrayList<paper> arr){
		mergeSort(arr, arr.size());
		return arr;
	}
    
    public static ArrayList<String> findPaper(String myStr, String myFileNAME){
        ArrayList<String> papers = new ArrayList<>();
        try{
            File Folder = new File(myFileNAME);
            Scanner myReader = new Scanner(Folder);
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();
                papers.add(data);
            }
            myReader.close();
        } catch(IOException e){
            System.out.println("An error occurred.");
            System.out.println(e);
        }
        return papers;
    }


    public static void main(String[] args){
		mergeSortPaper ob = new mergeSortPaper();
        
        directoryNavigator dirNav = new directoryNavigator();
        dirNav.setCurFolder("Computer Science");

        ArrayList<String> papers = dirNav.getTestNames();

        System.out.println("\n\n" +papers);

        ArrayList<paper> paperPapers = new ArrayList<paper>();
        for(String x: papers){
            if(x.equals(".DS_Store")) continue;
            paperPapers.add(new paper(x));
        }
        
        paperPapers = ob.startPap(paperPapers);

        for(paper x: paperPapers){
            System.out.println(x.getName());
        }
    }
}