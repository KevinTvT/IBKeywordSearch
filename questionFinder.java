import java.util.*;

public class questionFinder {

    private ArrayList<String> questions = new ArrayList<String>();

    public ArrayList<String> findQuestion(String text, String searchTerm) {

        for (int x = 1; x < 100; x ++) {
            //try catch because not every test has exactly 30 questions
            String num = String.valueOf(x);
            try {
                //Tests the questions
                String num2 = String.valueOf(x+1);

                String currentQuestion = text.substring(text.indexOf(num + ". "), text.indexOf(num2 + ". "));
                
                //Tests if the searchTerm is in the question
                if (currentQuestion.indexOf(searchTerm) != -1) {
                    // Changing from returning the entire question to returning just the question number
                    // questions.add(currentQuestion);
                    questions.add(currentQuestion.substring(0, num.length()));
                }
            } catch (IndexOutOfBoundsException e) {
                //Tests the last questions
                // System.out.println("\n\n index of " + num + ". : " + text.indexOf(num + ". \n\n"));
                // System.out.println(text.substring(text.indexOf(num + ". "))); 
                if (text.substring(text.indexOf(num + ". ")).indexOf(searchTerm) != -1) {
                    // Changing from returning the entire question to returning just the question number
                    // questions.add(text.substring(text.indexOf(num + ". ")));
                    String currentQuestion = text.substring(text.indexOf(num+ ". "));
                    questions.add(currentQuestion.substring(0, num.length()));
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
