import java.util.*;

public class questionFinder {

    private ArrayList<String> questions = new ArrayList<String>();

    public ArrayList<String> findQuestion(String text, String searchTerm) {

        for (int x = 1; x < 30; x ++) {
            //try catch because not every test has exactly 30 questions
            try {
                //Tests the questions
                String num = String.valueOf(x);
                String num2 = String.valueOf(x+1);

                String currentQuestion = text.substring(text.indexOf(num + ". "), text.indexOf(num2 + ". "));
                
                //Tests if the searchTerm is in the question
                if (currentQuestion.indexOf(searchTerm) != -1) {
                    questions.add(currentQuestion);
                }
            } catch (IndexOutOfBoundsException e) {
                //Tests the last questions
                String num = String.valueOf(x);
                System.out.println("\n\n index of " + num + ". : " + text.indexOf(num + ". \n\n"));
                System.out.println(text.substring(text.indexOf(num + ". "))); 
                if (text.substring(text.indexOf(num + ". ")).indexOf(searchTerm) != -1) {
                   questions.add(text.substring(text.indexOf(num + ". ")));
                }
                break;
            }
        }

        /*for(int i = 0; i < questions.size(); i++) {   
            System.out.println(questions.get(i));
        }*/

        return questions;
    }

    public ArrayList<String> getQuestions() { return questions; }
}
