package TwentyQuestions;

import java.util.*;
import java.io.*;

/*
 *  Jonathan Lai
 *  Ms. Maloney
 *  Data Structures
 *  Project 6
 *  QuestionTree is a class used within QuestionMain. The class handles the reading and writing of a QuestionTree
 *  an object consisting of interlinked QuestionNodes each of which representing either a question (branch node) or answer (leaf node).
 *  These QuestionNodes are ordered in such a way that through a series of yes/no questions the computer is able to try
 *  to guess the user's input. Although the computer may initially be incorrect the more instances of games that are played
 *  and stored within an external text file the better the computer gets at the user's object. This is achieved
 *  by using the QuestionNodes to store yes/no paths that help the computer narrow down possible solutions.
 *
 */
public class QuestionTree {

    private QuestionNode overallRoot; //QuestionTree root node
    private Scanner console; //Used to take in user keyboard inputs

    //Constructs a QuestionTree object with one leaf node with text "computer."
    public QuestionTree() {

        this.overallRoot = new QuestionNode("computer");
        console = new Scanner(System.in);

    }

    //Pre: File is legal and in standard format.
    //Post: QuestionTree content will be replaced by inputted tree.
    public void read(Scanner input) {

        overallRoot = compile(input);

    }

    //Paired with read method. Used to read file + compile node objects into QuestionTree in preorder.
    private QuestionNode compile(Scanner input) {

        String type = input.nextLine();
        String text = input.nextLine();

        if (type.equals("Q:")) {

            return new QuestionNode(text, compile(input), compile(input)); //if it is a question compile a branch node w/ question and a yes/no option

        } else {

            return new QuestionNode(text); //create a leaf node (answer) w/ reply

        }

    }

    //Pre: PrintStream can write to a file
    //Post: "question.txt" will contain existing QuestionTree content (binary tree)
    public void write(PrintStream output) {

        write(overallRoot, output);

    }

    //Paired with write method. Used to write QuestionTree content to external file (question.txt) in preorder fashion.
    private void write(QuestionNode node, PrintStream output) {

        if (node != null) {

            if (!node.isLeaf()) { //if not a leaf node then it is a question

                output.println("Q:");

            } else {

                output.println("A:"); // else it is an answer (leaf node)

            }

            output.println(node.getText());
            write(node.getYes(), output);
            write(node.getNo(), output);

        }

    }

    //Post: Expands existing QuestionTree using user question and answer to distinguish one object from another
    public void askQuestions() {

        overallRoot = askQuestions(overallRoot);

    }


    //Paired with askQuestions method. Used to add new objects and the question that distinguishes them from objects that already exist in the question tree
    private QuestionNode askQuestions(QuestionNode currentNode) {

        if (currentNode.isLeaf()) { //if at a leaf node then attempt to guess the object

            if (yesTo("Would your object happen to be " + currentNode.getText())) {

                System.out.print("Great, I got it right!");

            } else { //if wrong proceed to add object + question to distinguish newly added object

                System.out.print("What is the name of your object? ");
                String failedAnswer = console.nextLine().toLowerCase();
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String failedAnswerQuestion = console.nextLine();

                boolean answer = yesTo("And what is the answer for your object? "); //takes answer and creates a new node branch depending on answer to user question

                if (answer) {

                    currentNode = new QuestionNode(failedAnswerQuestion, new QuestionNode(failedAnswer), currentNode);

                } else {

                    currentNode = new QuestionNode(failedAnswerQuestion, currentNode, new QuestionNode(failedAnswer));

                }

            }

        } else { // if node is not a leaf node then continue to traverse question tree depending on answer to question


            if (yesTo(currentNode.getText())) {

                currentNode.setYes(askQuestions(currentNode.getYes()));

            } else {

                currentNode.setNo(askQuestions(currentNode.getNo()));

            }

        }

        return currentNode;

    }

    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {

        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();

        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }

        return response.equals("y");
    }
}


