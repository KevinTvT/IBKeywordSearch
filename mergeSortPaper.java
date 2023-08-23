import java.io.*;
import java.util.*;

public class mergeSortPaper{
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
    
    public static ArrayList<String> start(ArrayList<String> arr){
        ArrayList<paper> arr1 = new ArrayList<>();
        for(int x = 0; x < arr.size(); x++){
            arr1.add(new paper(arr.get(x)));
        }
        mergeSort(arr1, arr1.size());
        
        for(int x = 0; x < arr1.size(); x++){
            arr.set(x, arr1.get(x).rename("Computer_Science"));
        }
        
        return arr;
    }

    public static void main(String[] args){
        directoryNavigator dirNav = new directoryNavigator();
        dirNav.setCurFolder("Computer Science");

        ArrayList<String> papers = dirNav.getTestNames();

        System.out.println(papers);
        System.out.println(start(papers));
    }
}