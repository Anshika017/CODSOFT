import java.util.*;

public class NumberGuessingGame 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        int attempts = 5;
        int score = 0;
        String playAgain = "yes";
        
        System.out.println("Welcome to the Number Guessing Game!");

        while (playAgain.equalsIgnoreCase("yes")) 
	{
            int num = 1+(int)(100*Math.random());
            int attemptsLeft = attempts;
            boolean hasWon = false;
            
            System.out.println("\nA number is chosen between 1 to 100");
            System.out.println("Guess the number between "+attempts +"attempts");

            while (attemptsLeft > 0) 
	    {
                System.out.print("Enter your guess: ");
                int guess = sc.nextInt();

                if (guess == num) 
		{
                    System.out.println("Congratulations! Your guess is correct.");
                    hasWon = true;
                    score += attemptsLeft;
                    break;
                } 
		else if (guess < num) 
		{
                    System.out.println("Your guess is too low. Try again!");
                } 
		else 
		{
                    System.out.println("Your guess is too high. Try Again!");
                }
                attemptsLeft--;
                System.out.println("Attempts left: " + attemptsLeft);
            }

            if (!hasWon) 
	    {
                System.out.println("Sorry, you've used all your attempts. The number was " + num );
            }

            System.out.println("Your current score: " + score);
            System.out.print("Do you want to play another round? (yes/no): ");
            playAgain = sc.next();
        }

        System.out.println("Thank you for playing! Your final score is: " + score);
        sc.close();
    }
}
