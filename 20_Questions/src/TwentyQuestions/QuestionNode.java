package TwentyQuestions;


/*
 *  Jonathan Lai
 *  Ms. Maloney
 *  Data Structures
 *  Project 6
 *  Question node stores a text value and a left/right connection where left is yes and right is no.
 *  The text of each file is preceded by either a "Q:" indicating a question/branch
 *  or an "A:" indicating an answer/leaf
 *
 */
public class QuestionNode {

    //text defining node ("Q:" = question/branch | "A:" = answer/leaf)
    private String text;

    //"yes" = left
    private QuestionNode yes;

    //"no" = right
    private QuestionNode no;

    //QuestionNode constructor used to make branch nodes (questions)
    public QuestionNode(String text, QuestionNode yes, QuestionNode no) {

        this.text = text;
        this.yes = yes;
        this.no = no;

    }

    //QuestionNode constructor used to make leaf nodes (answers)
    public QuestionNode(String text) {

        this(text, null, null);

    }

    //Returns true/false depending on if a node is a leaf node
    public boolean isLeaf() {

        return this.yes == null && this.no == null;

    }

    public String getText() {

        return text;
    }

    public QuestionNode getYes() {

        return yes;
    }

    public QuestionNode getNo() {

        return no;
    }

    public void setYes(QuestionNode yes) {

        this.yes = yes;

    }

    public void setNo(QuestionNode no) {

        this.no = no;

    }

}
