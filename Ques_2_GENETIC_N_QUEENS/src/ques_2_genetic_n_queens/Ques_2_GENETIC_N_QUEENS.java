/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ques_2_genetic_n_queens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author basit
 */
public class Ques_2_GENETIC_N_QUEENS {

    /**
     * @param args the command line arguments
     */
    
    static int number_of_queens = 8;  //default value of number_of_queens
    static int threshold=2;          //The minimum fitness Score on which the program will terminate. (Only deal with even numbers)
    static boolean break_loop=false; //use for the check either threshold value of fitness score is reached or not.
    static int population_size=4;    // the Size of Population(number of chromosomes in one generation).
    static int no_of_generations=0; //Keeps track of how many generations are generated.
    static int higest_fitness_score=-1; //keeps track of Highest Fitness score across all generations.
    static int lowest_fitness_score=999999999; //keeps track of Lowest Fitness score across all generations.
    
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
    }
    
    //Simple printing of an array
    public static void printArray(int[][] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.println(array[i][j]);
            }
            System.out.println("");
        }
    }
    
    //Processing the Input Either Invalid or Valid. Some Validations checks performed in the method.
    
    public static void processInput(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of Queens: ");
    	number_of_queens = input.nextInt();
        if(number_of_queens<=3){
            if (number_of_queens<0) {
                System.out.println("\n\nNo Of Queens Should be positive number.\n\nTry again with a positive Number.");
            }
            else{
                if (number_of_queens<=3) {
                    System.out.println("\n\nQueens can't be placed without conflicts therefore Solution doesn't Exists.\n\nTry again with different input.");
                }
            }
            System.out.println("\n\nProgram Ended Gracefully...\n\n");
            System.exit(0);
            
        }
        System.out.print("Enter the Population Size: ");
    	population_size = input.nextInt();
        if (population_size<3) {
            if (population_size<0) {
                System.out.println("\n\nPopulation size be positive number.\n\nTry again with a positive Number.");
                
            }
            else{
                if(population_size<3){
                    System.out.println("\n\nPopulation size should be greater then 2.\n\nTry again with a Number greater then 2.");
                }
            }
            System.out.println("\n\nProgram Ended Gracefully...\n\n");
            System.exit(0);
        }
        System.out.print("Enter the Minimum Threshold value on which the program will terminate: ");
    	threshold = input.nextInt();
        if (threshold<0) {
            System.out.println("\n\nThreshold value Should be positive number.\n\nTry again with a positive Number.");
            System.out.println("\n\nProgram Ended Gracefully...\n\n");
            System.exit(0);
        }
        
    }
    
    //Method to find Fitness Score of each board in One Generation
    
    public static int [] findFitnessScore(int[][] array){
        int[] fitnessScores =new int[population_size];  //the Array which stores the fitness values of each chromosomes in one Generation
        int[][] board=new int[number_of_queens][number_of_queens]; // The n x n matric which will represent a board. 
        int k=0;
        
        //System.out.println("\n\n-----------NEW GENERATION-------------\n\n");
        no_of_generations++; //Keeping track of how many generations are generated so far.
        for (int i = 0; i < population_size; i++) { //For loop for each board in one generation
            //placing queens on board
            k=0;
            initialize2DArray(board,0);
            for (int j = 0; j < board.length; j++) {
                board[k][array[i][j]-1]=2;
                k++;
                
            }
            //finding fitness of that board on which the queens has been just placed.
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[x].length; y++) {
                    if (board[x][y]==2) {
                        fitnessScores[i]+=calculateFitness(board,x,y); // cumultative fitness scores of all the queens placed on one board according to chromosomes.
                    }
                }
            }
            if(fitnessScores[i] > higest_fitness_score){
                higest_fitness_score=fitnessScores[i];
                System.out.println("\nThe new Highest Fitness Score across all generations is  : " +higest_fitness_score+"\n");

            }
            if(fitnessScores[i] < lowest_fitness_score){
                lowest_fitness_score=fitnessScores[i];
                System.out.println("\nThe new Lowest Fitness Score across all generations is  : " +lowest_fitness_score+"\n");

            }
            //System.out.println(" \n Fitness Scoreo is : "+fitnessScores[i]+"\n"); //Printing out the Fitness Score.
            if (fitnessScores[i]<=threshold) { //Checking if we have reached our threshold value or less then print the board and break.
                System.out.println("\n\n------------------------------------FINAL ANSWER------------------------------------\n\nFitness score is : "+fitnessScores[i]+" . Which is less then or equal to our Threshold Value.\n");
                System.out.println("\nNOTE: The Fitness Score : " +fitnessScores[i]+" means that there are : "+(fitnessScores[i]/2)+" Conflicting Pairs in total on the given board.\n");
                System.out.println("\n\nSUMMARY: The Highest Fitness Score across all generations is  : " +higest_fitness_score+"\nWhereas Lowest fitness score across all generations is : "+lowest_fitness_score+"\n\n");
                printBoard(board); // Printing the Resulting Board.
                break_loop=true; //allow the program to terminate.
                break;
            }
            //print2DArray(board); // Printing the Resulting Array.
            //System.out.println("\nFitness score for board no "+(i+1)+" is "+fitnessScores[i]+"\n");
            
        }
        return fitnessScores;
    }
    
    //Method to find out fitness score of just one queen according to its constraints.
    
    public static int calculateFitness(int[][] board,int row,int col){
        int fitness_score=0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                if (Math.abs(i-row) == Math.abs(j-col) || i-row==0 || j-col==0){ //If it is any diagonal or same row or same column cell on which queen was placed 
                    if(board[i][j]==2 ){
                        if(row!=i || col!=j){
                            fitness_score++; // if the constraints applies then increment the fitness score of that queen.
                        }
                        
                    }

                }
                
            }
        }
        
        return fitness_score;
    }
    
    
    //Method wich implement the logic behind how the best chromosomes are selected and the one with worst(maximum) fitness score has been left out.
    
    
    public static int [][] selectionCriteria(int[][] array,int[] fitnessScores){
        
        int max = fitnessScores[0],max_index=0; 
        
        // Traverse array fitnessScores from second and 
        // compare every fitnessScores with current max   
        for (int i = 1; i < fitnessScores.length; i++) {
            if (fitnessScores[i] > max) {
                max = fitnessScores[i];
                max_index=i;
            }     
        }
        
        
        //the max_index contains the index of that chromosome who has highest fitness value.
        
        for (int i = 0; i < array[max_index].length; i++) { // just make that chromose a NULL.
            array[max_index][i]=0;
        }
        
        //swap the values of that chromosome with the last one in order to easily apply crossover.
        if(max_index!=(population_size-1)){
            for (int i = 0; i < array[max_index].length; i++) {
                int temp=0;
                //swapping elements
                temp=array[max_index][i];
                array[max_index][i]=array[population_size-1][i];
                array[population_size-1][i]=temp;
            }
        }
        //return new generation having one blank chromosome.
        return array;
    }
    
    //Method which apply crossover to the given chromosomes and pass array to mutate the chromosomes.
    
    public static int[][] applyCrossover(int[][] array){
        int[] temp1=new int[number_of_queens],temp2=new int[number_of_queens]; // two blanks arrays are created in otder to hold the crossover results. 
        int k=0;
        //example of chromose 
        //Suppose we have no_of_queens=8 and we have population size =4 
        // and we have 1st chromosome [1,2,3,4,5,6,7,8]
        //and second [8,7,6,5,4,3,2,1]
        
        // so crossover answer will be a new chromosomes valued [1,7,3,5,5,3,7,1] and [8,2,6,4,4,6,2,8]
        //it just swap the even/odd values with each other and form two new chromosomes 
        // and then in continuity these are cross overred means next the second chromosome is crossovered in same fashion with third one and so on.
        //therfore it only handle the even number of queens.
        for (int i = 0; i < (population_size-2); i++) {
            if (number_of_queens%2==0) {
                //FOR EVEN NUMBER OF QUEENS
                k=0;
                for (int j = 0; j < number_of_queens; j+=2) {
                    temp1[k++]=array[i][j];
                    temp1[k++]=array[i+1][j+1];
                }
                k=0;
                for (int j = 0; j < number_of_queens; j+=2) {
                    temp2[k++]=array[i+1][j];
                    temp2[k++]=array[i][j+1];
                }
                System.arraycopy(temp1, 0, array[i], 0, number_of_queens);
                System.arraycopy(temp2, 0, array[i+1], 0, number_of_queens);
            }
            else{
                //FOR ODD NUMBER OF QUEENS
                k=0;
                for (int j = 0; j < number_of_queens-1; j+=2) {
                    temp1[k++]=array[i][j];
                    temp1[k++]=array[i+1][j+1];
                }
                k=0;
                for (int j = 0; j < (number_of_queens-1); j+=2) {
                    temp2[k++]=array[i+1][j];
                    temp2[k++]=array[i][j+1];
                }
                System.arraycopy(temp1, 0, array[i], 0, number_of_queens-1);
                System.arraycopy(temp2, 0, array[i+1], 0, number_of_queens-1);
                temp1[number_of_queens-1]=ThreadLocalRandom.current().nextInt(1, number_of_queens + 1);
                temp2[number_of_queens-1]=ThreadLocalRandom.current().nextInt(1, number_of_queens + 1);
            }
            
        }
        
        //crossover performed.
        
        //the last chromosome is left to filled with a random values once again.
        ArrayList<Integer> list = new ArrayList<>();
        for (int j=1; j<(number_of_queens+1); j++) {
            list.add(j);
        }
        Collections.shuffle(list);
        for (int j=0; j<number_of_queens; j++) {
            array[population_size-1][j] = list.get(j);
        }
        //return the new generation (without mutation).
        return array;
    }
    
    //Method which just mutate most probably the repeating bits in the chromosomes in order to minimize the fitness score of that particular chromosome.
    
    public static int[][] mutate(int[][] array){
        int random_integer=0;
        //searching for duplicates and replacing them with a new random value.
        for (int i = 0; i < population_size; i++) {
            for (int j = 0; j < (number_of_queens-1); j++) {
                if(array[i][j]==array[i][j+1]){
                    random_integer = ThreadLocalRandom.current().nextInt(1, number_of_queens + 1);
                    array[i][j+1]=random_integer;
                }
                
            }
        }
        ////return the new generation (with mutation).
        return array;
    }
    
    
    //Method to initialize the population firstly with random values.
    public static int[][] initializePopulation(){
        int i,j;
        ArrayList<Integer> list = new ArrayList<>();
        int[][] array= new int[population_size][number_of_queens]; // our main array which hold chromosomes. in each of the method. In evey other methos this array is not created again n again it is just referenced over there.
        for (i = 0; i < population_size; i++) {
            for (j=1; j<(number_of_queens+1); j++) {
                list.add(j);
            }
            //just shuffling the list and use the list values to initialize a population with chromosomes.
            Collections.shuffle(list);
            for (j=0; j<number_of_queens; j++) {
                array[i][j] = list.get(j);
            }
            list.clear();
        }
        return array;
    }
    
    
    //The basi function which implements the methodology of genetic algorithms and calls out each step of genetic algorithm sequentially.
    public static void NQueenGeneticAlgorithm(){
        int[][] array=initializePopulation(); //initializing the popuation.
        int[] fitnessScores; //array to hold out values of fitness Scores.
        
        //This do while loop will terminate once we have reached out threshodl value.
        do {
            //printArray(array); // Printing the Resulting Array.
            fitnessScores=findFitnessScore(array); //Calculating fitness scores for each generations.
            if (break_loop) {
                break;
            }
           //Select the best chromosomes and remove one from it.
           
            array=selectionCriteria(array,fitnessScores);
            //printArray(array); // Printing the Resulting Array.
            //Apply Crossover
            array=applyCrossover(array);
            //printArray(array); // Printing the Resulting Array.
            //mutate to get our new generation.
            array=mutate(array);
            //repeat the process.
        } while (true);
    }
    
    
    public static void main(String[] args) {
        //Program Started
        processInput(); //Processing Input
        System.out.println("\n\nPlease Wait while..\n\nWhile programs reached the given threshold value.\n\n");
        final long startTime = System.nanoTime(); 
        NQueenGeneticAlgorithm();  //Calling out main method of genetics.
        final long final_time = System.nanoTime() - startTime;
        //printArray(array); // Printing the Resulting Array.
        System.out.println("\n\nResults are provided in "+no_of_generations+" generations.\n\n");
        System.out.println("\n\nTime Taken By my N-Queen Program by Genetic Algorithm : "+((float)final_time/(float)1000000000)+ " seconds."+"\n\nProgram Ended Gracefully...\n\n"); 
        //Program Ended
    }

    
    
    
    
    
    
}
