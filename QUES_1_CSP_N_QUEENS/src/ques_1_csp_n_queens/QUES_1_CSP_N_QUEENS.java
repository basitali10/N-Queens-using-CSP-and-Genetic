/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ques_1_csp_n_queens;

import java.util.Scanner;

/**
 *
 * @author basit
 */
public class QUES_1_CSP_N_QUEENS {

    /**
     * @param args the command line arguments
     */
    
    static int number_of_queens = 8;  //default value of number_of_queens
    static int backtrack_count = 0;  //initial value of backtrack_count
    
    
    //Some Utility Methods
    
    //Initializing 2-Dimensional array giving row,column and value to be filled in the array.
    
    public static void initialize2DArray(int[][] a,int value){
        for(int i = 0; i< a.length; i++){
            for(int j = 0 ;j< a[i].length ; j++){
                a[i][j] = value;
            }
        }
    }
    
    //Printing 2-Dimensional array giving row,column.
    
    public static void printBoard(int[][] array){
        System.out.println("\nN-Queens Array: \n");
        for(int i = 0; i< array.length; i++){
            for(int j = 0 ;j< array[i].length ; j++){
                if(array[i][j]==2){
                    System.out.print(" Q "); 
                }
                else{
                    System.out.print(" " + 1 + " "); 
                }
                
                 
            }
            System.out.println();
        }
        System.out.println("\n\nTotal Backtrack Count : "+backtrack_count+"\n\n");
    }
    
    //Processing the Input Either Invalid or Valid. Some Validation checks performed in the method.
    
    public static void processInput(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of Queens: ");
    	number_of_queens = input.nextInt();
        if(number_of_queens<=3){
            if (number_of_queens<0) {
                System.out.println("\n\nNo Of Queens Should be positive.\n\nTry again with a positive Number.");
            }
            else{
                System.out.println("\n\nQueens can't be placed without conflicts therefore Solution doesn't Exists.\n\nTry again with different input.");
            }
            
            System.out.println("\n\nProgram Ended Gracefully...\n\n");
            System.exit(0);
        }
    }
    
    //Main Method which solves the N-queens Problem and produce the result in 2D array[][]
    
    public static int [][] nQueensProblem(int[][] array){
        boolean has_spot=true;  //just to find out either spot is available or not in current row.
        int[][] queen_no=new int[number_of_queens][number_of_queens]; // array which helps in backtracking . Learn from Program that which grid is blocked by which Queen.
        initialize2DArray(queen_no,0);
        
        for(int i = 0; i< number_of_queens; i++){
            for(int j = 0 ;j< number_of_queens ; j++){
                
                if (array[i][j]==2 && has_spot==false){ //Value (2) Indicates that Queen is placed in that box.
                    backtrack_count++;
                    backtrackConstraints(array,i,j,queen_no); //Find out which queen needs to be removed and then backtrack.
                    if(j==number_of_queens-1){                  //If this was last cell in the row then no more spots available.
                        has_spot=false;
                    }
                    else{
                        has_spot=true;                          //Else more boxs are available in the same row in which queen can be placed
                    }
                }
                
                else{
                    if(array[i][j]==0){                       //0 indicates there is a free space in 2D array with no blocked constraints therefore queen can be placed here.
                        applyConstraints(array,i,j,queen_no);  //Then the constraints which occur due to the placement of queen are applied on array.
                        break;

                    }
                    else{
                        if(j==number_of_queens-1){
                            has_spot=false;                 //If this was last cell in the row then no more spots available.
                        }
                        
                    }
                }
            }
            if(has_spot==false){
                //backtrack
                //System.out.println("no more space to fill the queens. Therefore Backtrack.");
                i=i-2;    //Going to previous Row in order to backtrack.
            }
            
        }
        return array;
    }
    
    //Method which apply backtrack constraints and rollback changes that was previously made. the array q[][] is used for learning of algorithm that which cell is blocked by which queen.
    
    public static void backtrackConstraints(int[][] array , int row, int col,int[][] q){
        
        for (int i = 0; i < number_of_queens; ++i) {
            for (int j = 0; j < number_of_queens; ++j) {
                if (i == row && j == col){   //If it is the same cell on which queen was placed 
                    array[i][j] = 0;        //then mark it as 0 in array[][] as 0 indicated possible place for the queen
                    q[i][j]=0;              //Also change in q[][] array that this cell was previously occupied by this queen constraints
                }
                else{
                    if (Math.abs(i-row) == Math.abs(j-col) || i-row==0 || j-col==0){ //If it is any diagonal or same row or same column cell on which queen was placed 
                        if(q[i][j]==row+1){                                             // and also this change was made by this queen ONLY not any other queen 
                            array[i][j] = 0;                        //Then fill array[][] with 0 as 0 indicated possible place for the queen
                            q[i][j]=0;                           //Also change in q[][] array that this cell was previously occupied by this queen constraints
                        }
                        
                    }
                }
                
            }
        }
    }
    
        //Method which apply constraints make sure that the constraints of queen which is just placed are all applied here and the array q[][] is used for learning of algorithm that which cell is blocked by which queen.

    
    public static void applyConstraints(int[][] array , int row, int col,int[][] q){
        
        for (int i = 0; i < number_of_queens; ++i) {
            for (int j = 0; j < number_of_queens; ++j) {
                if (i == row && j == col){       //If it is the same cell from whcih the function is called  then this means queen should be placed here.
                    array[i][j] = 2;            //as shown by 2
                    q[i][j]=row+1;              //also this change have to be made in q[][] for knowing out that this queen block the specific cell
                }
                else{
                    if (Math.abs(i-row) == Math.abs(j-col) || i-row==0 || j-col==0){   //This condition maps out all horizontal , vertical and diagonal elements
                        if(q[i][j]==0){
                            q[i][j]=row+1;
                        }
                        array[i][j] = 1;                                            //and mark them as 1 as these are bloced by the constraints of this Queen.
                    }
                }
                
                                                     
            }
        }
    }
    
    
    public static void main(String[] args) {
        
        //Program Started
        processInput(); //Processing Input
        int[][] array = new int[number_of_queens][number_of_queens]; //our MAIN ARRAY
        initialize2DArray(array,0); // Initializing it with 0 means Queens can be placed anywhere
        System.out.println("\n\nPlease Wait while..\n\nWhile programs Places the queen in appropriate positions.\n\n");
        final long startTime = System.nanoTime();
        array=nQueensProblem(array);  //Calling method for N Queens Problem
        final long final_time = System.nanoTime() - startTime;
        printBoard(array); // Printing the Resulting Array.
        System.out.println("Time Taken By my N-Queen Program : "+((float)final_time/(float)1000000000)+ " seconds."+"\n\nProgram Ended Gracefully...\n\n"); 
        //Program Ended
    }
    
}
