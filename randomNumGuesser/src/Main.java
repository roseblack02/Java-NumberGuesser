import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String userInput, keepPlaying = "";
        int bet, gold, playerChoice, tokens = 100;
        boolean goldFound = false;
        int [] randomNums = {0,0,0};
        Scanner input = new Scanner(System.in);

        //show rules
        System.out.println("Welcome!\n" +
                "There will be 3 pieces of gold randomly placed on a 6x6 grid.\n" +
                "You must place a bet and then you will be given 3 chances to find the gold.\n" +
                "The position on the grid will have an 'O' if gold has been found or an 'X' if nothing was found.\n" +
                "Your bet will be multiplied by the amount of gold found.");

        //main loop
        while(!keepPlaying.equalsIgnoreCase("no")){
            //reset values for new round
            bet = 0;
            gold = 0;
            int[] usedSpots = {0, 0, 0};
            String [] grid = {"#","#","#","#","#","#",
                    "#","#","#","#","#","#",
                    "#","#","#","#","#","#",
                    "#","#","#","#","#","#",
                    "#","#","#","#","#","#",
                    "#","#","#","#","#","#",};

            //ask player for bet
            while(bet<1 || bet>tokens){
                System.out.println("\nCurrent balance is " + tokens);
                System.out.println("Please enter a valid bet above 0: ");

                userInput = input.next();

                //convert to int
                try{
                    bet = Integer.valueOf(userInput);
                }catch(Exception e){
                    System.out.println("\nERROR!");
                }

                if(bet<1 || bet>tokens){
                    System.out.println("\nERROR!");
                }
            }
            //take bet from player
            tokens -= bet;

            //get numbers
            randomNums = generateNums(randomNums);

            //get users choice 3 times
            for(int i=0; i<3; i++){
                //reset player choice
                playerChoice = -1;

                while(playerChoice<0 || playerChoice>35){
                    System.out.println("\nPlease enter a valid spot on the grid(0-35): ");
                    userInput = input.next();

                    //convert input to int
                    try{
                        playerChoice = Integer.valueOf(userInput);
                    }catch (Exception e){
                        System.out.println("\nERROR!");
                    }

                    if(playerChoice<0 || playerChoice>35){
                        System.out.println("\nERROR!");
                    }
                }

                //check if spot is correct
                goldFound = checkSpot(randomNums, usedSpots, playerChoice);

                //if true returned then set spot from player choice to used
                //set spot to O and add gold
                if(goldFound){
                    gold++;
                    usedSpots[i] = playerChoice;
                    grid[playerChoice] = "O";
                }else if(!goldFound){
                    // set spot to X if nothing found
                    grid[playerChoice] = "X";
                }

                //print grid
                printGrid(grid);
            }

            //show results and give player bet multiplied by num of gold found
            System.out.println("\nGold found: " + gold);
            System.out.println("\nPayout: " + (bet*gold));

            tokens+=(bet*gold);

            //ask player to play again unless they have no tokens
            if(tokens<1){
                System.out.println("\nNo more tokens! You lose!");
                keepPlaying = "no";
            }else{
                System.out.println("\nType 'no' to quit or anything to keep playing: ");
                keepPlaying = input.next();
            }
        }

    }

    //get 3 random numbers
    public static int [] generateNums(int [] randomNums){
        int num;

        //loop 3 times
        for(int i=0; i<3; i++){
            //int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
            num = (int)Math.floor(Math.random() * (35 - 0 + 0) + 0);

            //add to list
            randomNums[i] = num;
        }

        return randomNums;
    }

    // print out the grid
    public static void printGrid(String [] grid){
        // for loops to write out the grid
        // write 6 items from the list into 6 separate rows
        System.out.print("-------------------------\n");
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                int spot = j + (i * 6);
                System.out.print("| " + grid[spot] + " ");
            }
            System.out.print("|\n");
            System.out.print("-------------------------\n");
        }
    }

    //check if player has chosen the correct spot
    public static boolean checkSpot(int [] randomNums, int [] usedSpots, int choice){
        boolean result = false;

        //check if choice is in the list of random nums and not in used spots list
        for(int i=0; i<3; i++){
            if(randomNums[i] == choice && choice != usedSpots[i]){
                result = true;

                System.out.println("\nGold found!");
            }
        }

        return result;
    }

}