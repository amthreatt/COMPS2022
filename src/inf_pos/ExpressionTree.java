package inf_pos;

import java.sql.SQLOutput;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import  java.util.LinkedList;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeString.length;


public class ExpressionTree {

    public static String inverseOp(String op){
        Hashtable<String, String> my_dict = new Hashtable<String, String>();
        my_dict.put("+", "-");
        my_dict.put("-", "+");
        my_dict.put("*", "/");
        my_dict.put("/", "*");

        return my_dict.get(op);

    }



    public static boolean isOperator(char ch){
        if(ch=='+' || ch=='-'|| ch=='*' || ch=='/' || ch=='^' || ch == '=' ){ //no parenthesis included on the operators because parenthesis is accounted for already on postfix notation
            return true;
        }
        return false;
    }

    public static String[] toList(String eqn) {
        String tempstr = "";
        int l = 0;
        int parenth = 0;
        for (int k = 0; k < eqn.length(); k++){
            if(isOperator(eqn.charAt(k))){
                l++;
            }
            if(eqn.charAt(k) == '(' || eqn.charAt(k) == ')'){
                parenth++;
            }
        }
        String[] eqnToList = new String[l*2 + 1 + parenth];

        int j =0;
        for (int i = 0; i < eqn.length(); i++) {
            //while(!eqnToList[eqnToList.length-1].contains(eqn.charAt(eqn.length()-1)))
            if (!isOperator(eqn.charAt(i)) && eqn.charAt(i) != '(' && eqn.charAt(i) != ')' ) {
                tempstr += (String.valueOf(eqn.charAt(i)));
                // System.out.println("do we get here: " + tempstr);
            }
            eqnToList[j] = tempstr;
            int j1 = 0;
            int j2 = 0;
            if (isOperator(eqn.charAt(i))){
                eqnToList[j + 1] = String.valueOf(eqn.charAt(i));
                tempstr = "";
                 j1 = j + 2;
                if(eqn.charAt(i+1) == '('){
                    eqnToList[j + 1] = String.valueOf(eqn.charAt(i));
                    eqnToList[j+2] = String.valueOf(eqn.charAt(i+1));
                    tempstr = "";
                     j2 = j+3;
                    //break;
                }

                 if(eqn.charAt(i-1) == ')'){
                    eqnToList[j + 2] = String.valueOf(eqn.charAt(i));
                    eqnToList[j+1] = String.valueOf(eqn.charAt(i-1));
                    tempstr = "";
                    j2 = j+3;
                }

                if(j1 < j2){
                    j = j2;
                }
                else{
                    j = j1;
                }
            }
            if(eqn.charAt(eqn.length()-1) == ')'){
                eqnToList[eqnToList.length-1] = ")";
            }
        }return eqnToList;
    }

    //expression tree returns a node statck lowk
    public static Node expressionTree(String[] postfix){
        //instantiate empty stack for nodes
        Stack<Node> st = new Stack<Node>();
        Node t1,t2,temp;
        String tempstr = "";

        for(int i=0;i<postfix.length;i++){


          if(!isOperator(postfix[i].charAt(0))){  // todo go in here and make a method to find the distance or the numeber of elements between every two operations so that whole segment can be stored as a node not the singular element next to it
                temp = new Node(postfix[i]);
                st.push(temp);
            }


            else{
                temp = new Node(postfix[i]);

                t1 = st.pop();
                t2 = st.pop();

                temp.left = t2;
                temp.right = t1;

                st.push(temp);
            }

        }
        temp = st.pop();

        return temp;
    }

//inorder recursive function to work for any given node
    public static String inorder(Node root, String eqn){

        if(root==null)
            return eqn;

        eqn = inorder(root.left,eqn);
        //System.out.print(root.data);
        eqn = eqn + root.data;
        //System.out.println(eqn);
       eqn =  inorder(root.right,eqn);
      //  System.out.println(eqn);
        return eqn;
    }

    public static String inorder(Node root){
        return inorder(root,"");
    }

    //todo not in use delete
    public static Node highestOperator(Node root){
    return root;
    }

  //print binary tree to see operations occuring
    public static void printBinaryTree(Node root)
    {
        LinkedList<Node> treeLevel = new LinkedList<Node>();
        treeLevel.add(root);
        LinkedList<Node> temp = new LinkedList<Node>();
        int counter = 0;
        int height = heightOfTree(root) - 1;
        // System.out.println(height);
        double numberOfElements
                = (Math.pow(2, (height + 1)) - 1);
        // System.out.println(numberOfElements);
        while (counter <= height) {
            Node removed = treeLevel.removeFirst();
            if (temp.isEmpty()) {
                printSpace(numberOfElements
                                / Math.pow(2, counter + 1),
                        removed);
            }
            else {
                printSpace(numberOfElements
                                / Math.pow(2, counter),
                        removed);
            }
            if (removed == null) {
                temp.add(null);
                temp.add(null);
            }
            else {
                temp.add(removed.left);
                temp.add(removed.right);
            }

            if (treeLevel.isEmpty()) {
                System.out.println("");
                System.out.println("");
                treeLevel = temp;
                temp = new LinkedList<>();
                counter++;
            }
        }
    }

    public static void printSpace(double n, Node removed)
    {
        for (; n > 0; n--) {
            System.out.print("\t");
        }
        if (removed == null) {
            System.out.print(" ");
        }
        else {
            System.out.print(removed.data);
        }
    }

    public static int heightOfTree(Node root)
    {
        if (root == null) {
            return 0;
        }
        return 1
                + Math.max(heightOfTree(root.left),
                heightOfTree(root.right));
    }


//precendence to use when making infixpostfix funciton
        static int Prec(String ch)
        {
            switch (ch) {
                case "+":
                case "-":

                    return 1;

                case "*":
                case "/":

                    return 2;

                case "^":

                    return 3;
            }
            return -1;
        }

    public static<T> T[] subArray(T[] array, int beg, int end) {
        return Arrays.copyOfRange(array, beg, end + 1);
    }

    //this will take in a string an put it into postifx notation
    //postfix notation is used to constrcut the tree
    public static String[] infixToPostfix(String[] exp)
        {
            int num_parenthesis = 0;
            for(int i =0; i < exp.length; i++){
                if(exp[i].contains( ")") || exp[i].contains( "(") ){
                    num_parenthesis++;
                }
                else{
                    num_parenthesis = 0;
                }
            }
            // initializing empty String for result
            String[] result = new String[exp.length - num_parenthesis -1];
            int index = 0;

            // initializing empty stack
            Deque<String> stack = new ArrayDeque<String>();

            for (int i = 0; i < exp.length; ++i) {
                String c = exp[i];
                //System.out.println(c);

                // If the scanned character is an
                // operand, add it to output.
                if ( !isOperator(c.charAt(0)) && !c.contains("(")&& !c.contains(")")) {
                    // result[i] = c;
                    result[index] = c;
                    index++;
                }

                    // If the scanned character is an '(',
                    // push it to the stack.
                else if (c.contains("(")){
                    stack.push(c);}

                    //  If the scanned character is an ')',
                    // pop and output from the stack
                    // until an '(' is encountered.
                else if (c.contains( ")") ){
                    while (!stack.isEmpty() && !stack.peek().contains("(")) {
                       // result += stack.peek();
                        result[index] = stack.peek();
                        index++;
                        stack.pop();
                    }

                    stack.pop();
                }
                else // an operator is encountered
                {
                    while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())) {
                        result[index]=stack.peek();
                        index++;
                        stack.pop();
                    }
                    System.out.println(c);
                    stack.push(c);
                }
            }

            String[] inv = new String[1];
            // pop all the operators from the stack


            while (!stack.isEmpty()) {
                if (stack.peek().contains("("))
                   // inv[1] = "invalid string";
                    return inv;
                //for(int l =0; l < result.length; l++){

                   // if(result[l] == null){

                        result[index] = stack.peek();
                        index++;
                      //  System.out.println(stack.pop());
                        stack.pop();
                  //  }

                }
                //stack.pop();
            int endOfResult = 0;
            for(int i = 0; i < result.length; i++){
                if(result[i] != null){
                    endOfResult++;
                }
            }
            result = subArray(result, 0, endOfResult-1);
            return result;
            }



//todo as of now this method is not in use
        public static Character side(String eqn, char var){
        //find which side of the equation the var is on
            if(eqn.indexOf(var) < eqn.indexOf('=')){
                return 'L';
            }
            else if(eqn.indexOf(var) > eqn.indexOf('=')){
                return 'R';
            }
            else {
                return 'M'; //return M for missing variable
            }
        }
//todo as of now this fucniton is not being use delete at end
    public static void insert(Node root,Character op){

        Node x = root;
        Node y = null;

        while(x != null){
           y = x;
        }

    }


    //this method is to complete one step of solving the equation given the variable is one the left side
    public static Node OneLeftMove(Node root, char var){
        Node Tree = root;
        Node LTree = root.left;
        Node RTree = root.right;
        Character getVar = 'L';

        if(getVar.equals('L')){
            if(inorder(LTree.right).indexOf(var) == -1 && inorder(LTree).indexOf(var) != -1){
                //inverse operation because this is the root we need to remove now
                String dat = inverseOp(LTree.data);

                Node temp;
                temp = new Node(dat);
                temp.left = RTree;
                temp.right = LTree.right;

                System.out.println("right tree");
                printBinaryTree(RTree);
                System.out.println("left tree");
                printBinaryTree(LTree);

                Tree.right = temp;
                Tree.left = LTree.left;
            }

        }
       return Tree;
    }
//this method is to complete one step of solving the equation given the variable is one the right side of the equation
    public static Node OneRightMove(Node root, Character var){
        Node Tree = root;
        Node LTree = root.left;
        Node RTree = root.right;
        Character getVar = 'R';

        if(getVar.equals('R')){
            if(inorder(RTree.left).indexOf(var) == -1 && inorder(RTree).indexOf(var) != -1){
                System.out.println("this is the if condition statment if there is no x in there: "+inorder(RTree.left));
                //inverse operation because this is the root we need to remove now
                String dat = inverseOp(RTree.data);
                Node temp = null;
                temp = new Node(dat);
                temp.left = LTree;
                temp.right = RTree.left;

                System.out.println("right tree");
                printBinaryTree(RTree);
                System.out.println("left tree");
                printBinaryTree(LTree);

                Tree.left = temp;
                Tree.right = RTree.right;
            }

        }
        return Tree;
    }

// this is to output out tree in post order notation
    //this is useful because sometimes when inorder tranverising the output neglect the position of parnthesis this omits that issue
    //when finally solving the "other" side of the equation use this because it will be correct infix probably will not
    public static String Postorder(Node node, String eqn)
    {
        if (node == null)
            return eqn;

        // first recur on left subtree
        eqn = Postorder(node.left,eqn);
        // then recur on right subtree
        eqn = Postorder(node.right,eqn);
        eqn = eqn + (node.data);
        // now deal with the node
        return eqn;
    }

    public static String Postorder(Node root){
        return Postorder(root, "");
    }

    public static void Solver(){
//conditions for input
        System.out.println("Welcome to the Automated Math Tutor :)");
        System.out.println("Goals of this program: \n\tProvide students with helpful step by step guidance for solving" +
                " for a singular variable in an equation \n\tOffer user engagement for further practice \n\tHave fun learning Math!\n\n");

        System.out.println("Okay lets get started, here are some general guidelines for using this program: ");
        System.out.println("\tHere's whats going to happen, you will enter any *linear algebra equation with coefficients between 0-9 with any of the following operations: ");// for now until i fix exponent option
        System.out.println("\t\t * : multiplication");
        System.out.println("\t\t / : division");
        System.out.println("\t\t + : addition");
        System.out.println("\t\t - : subtraction");
        System.out.println("\tEquations with parentheses () are also welcomed to be inputted");
        System.out.println("\tNext the program will ask you to identity what variable you would like to solve for, this can be any letter even a number if that's what you're looking for");
        System.out.println("\nAmazing that's about all the instructions you'll need have fun!\n");


        Scanner scan1 = new Scanner(System.in);
        System.out.println("Enter the equation you'd like to solve: ");
        String eqn = scan1.nextLine();  // Read user input
        eqn = eqn.replaceAll("\\s","");
        while(!eqn.contains("=")){
            System.out.println("Hmmmm I think we are missing the = sign, lets try again");
            Scanner scan4 = new Scanner(System.in);
            System.out.println("Enter the equation you'd like to solve: ");
            eqn = scan4.nextLine();  // Read user input
            eqn = eqn.replaceAll("\\s","");
        }


        Scanner scan2 = new Scanner(System.in);
        System.out.println("Enter the variable you'd like to solve for: ");
        String var1 = scan2.nextLine();  // Read user input
        Character var = var1.charAt(0);
        while(var1.length() != 1 || !eqn.contains(var1)){
            if(var1.length() != 1) {
                System.out.println("\tMake sure you're inputting just one variable\n");
            }
            if(!eqn.contains(var1)){
                System.out.println("\tMake sure the variable is in the equation you inputted\n");
            }
             Scanner scan3 = new Scanner(System.in);
            System.out.println("Enter the variable you'd like to solve for: ");
             var1 = scan3.nextLine();  // Read user input
             var = var1.charAt(0);
        }

        System.out.println("Okay one final look through to make sure everything is put in correctly");
     //   for(int i = 0; i )

        //no two ops right next to eachother
        //always an op before an after parenth
        //confirm variable is alone, if not sugges adding in a multiplication before hand
        //check for exponents (we cant quite do that yet)
    }

    public static void print(String[] str){
        for(int i =0; i < str.length; i++){
            System.out.print("[" + str[i] + "], ");
        }
    }

    public static void Tester(){
      //  String eqn = infixToPostfix("c*b-e=c*(d+x^2)");
        //String[] eqn = infixToPostfix(toList("c*b-e=c*(d+x)"));
        String[] eqn = infixToPostfix(toList("abc*def-(gh+kl)=mn/op - x"));
        print(eqn);
        Character var = 'x';
        Node root = expressionTree(eqn);
        System.out.println("\nbefore");
        printBinaryTree(root);
       // printBinaryTree(root.left);
        Node x = OneRightMove(root, 'x');
        System.out.println("after 1");
        printBinaryTree(x);
        x = OneRightMove(root, 'x');
        System.out.println("after 2");
        printBinaryTree(x);
    }



        public static void main(String[] args) {
           Solver();
           // Tester();
            /*String[] eqn =toList("abc*def-(gh+kl)=mn/op");////toList("c*b-e=c*(d+x)");//
            print(eqn);
            System.out.println("\n");
            eqn = infixToPostfix(eqn);//infixToPostfix(toList("abc*def-(gh+kl)=mn/op"));
            print(eqn);*/
/*/*
            String eqn = "ab-(cde)+x=f";
           String u[] =  toList(eqn);
            for(int i =0; i < u.length; i++) {
                System.out.println(u[i]);
            }*/

    }
}


