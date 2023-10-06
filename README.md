# IB Keyword Search

## Project Description
This Project was created to help teachers in the IB program find questions from past IB tests so that they can create tests more efficiently. 

## Setup
Download the papers and insert them into a folder named whatever the subject name is and put it inside a "IBKeywordSearchResources" inside your Downloads folder. Or alternatively you can place the folder anywhere and change the location the folder access to that location.

Right Now the folder it is accessing to get the files are: /Downloads/IBKeywordSearchResources~

Download the project folder as a .zip file or download the code files and either run the .zip file by double clicking on it or running the main method of the "GUI.java" file. 

![Screenshot of change folders tab](/img/changeFolder.png)<br>
After running the file it will ask for access to the Downloads folder, if you don't give the program access you will need to add the tests to a different folder and then change the location that the program is trying to access the tests from.

## Instructions
![Screenshot of main tab](/img/mainGUI.png)<br>
After running the program a tab should pop up with a dropdown a searchbox and instructions button.

Step 1: Select desired class
Step 2: Enter search term
Step 3: Click Search

After clicking "Search" a Loading Tab will pop up please wait until the Searching tab dissapears.

![Screenshot of new tabs](/img/tests.png)<br>
If the keyword was found new tabs will pop up with a sorted list of papers organized by whether they are paper 1, 2, or 3. 
Each paper name is a link, to open a pdf click on the paper name and the pdf will open in your default editor. Because of this, it may take some time to open depending on your default pdf editor. The questions that contain the keyword in them will show up in the tab that used to say "Question Numbers will appear here".

There is an instructions button that will pop up a tab where users can look at the instructions while using the program.

Before searching a second time REMEMBER to delete the tabs containing the test questions from paper 1 to paper 3 because those are not ultimately deleted.

## Warning/Errors
### If you can't find the keyword in the question
Each question usually has multiple parts and can span multiple pages so if you can't find the keyword keep scrolling down until you see the next question.

If that doesn't work...

Once in a while for some specific papers the program cannot output the right questions that contain the keyword. If this happens type Cmd+F on Mac and type in the keyword to search for the keyword using the default editor.

### Keyword was not found
![Screenshot of "Keyword was not found" alert window](/img/alertWindow.png)<br>
If the tab "Keyword was not found" pops up for a search try entering a different search term IB may word their questions differently.

If that doesn't work...

IB tends to be biased towards some topics so there is a chance that IB hasn't had a question on that topic yet. Or some topics like Physics don't usually contain the keyword in the question and IB leaves the students to figure out what topic or skill to use to solve the problem. 