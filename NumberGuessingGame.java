import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        final int MIN = 1;
        final int MAX = 100;
        final int MAX_ATTEMPTS = 7;

        int roundsPlayed = 0;
        int roundsWon = 0;
        int bestAttempts = Integer.MAX_VALUE;

        System.out.println("==================================");
        System.out.println("🎯 NUMBER GUESSING GAME (Java)");
        System.out.println("Guess a number between " + MIN + " and " + MAX);
        System.out.println("You have maximum " + MAX_ATTEMPTS + " attempts.");
        System.out.println("==================================");

        boolean playAgain = true;

        while (playAgain) {
            roundsPlayed++;
            int target = random.nextInt(MAX - MIN + 1) + MIN;
            int attempts = 0;
            boolean won = false;

            System.out.println("\n🔁 Round " + roundsPlayed + " Started!");
            System.out.println("Guess the number...");

            while (attempts < MAX_ATTEMPTS) {
                System.out.print("Enter your guess (" + (attempts + 1) + "/" + MAX_ATTEMPTS + "): ");

                // Validate input (avoid crash)
                if (!sc.hasNextInt()) {
                    System.out.println("❌ Invalid input! Please enter a valid number.");
                    sc.next(); // clear invalid input
                    continue;
                }

                int guess = sc.nextInt();
                attempts++;

                if (guess == target) {
                    System.out.println("✅ Correct! You guessed it in " + attempts + " attempts.");
                    won = true;
                    roundsWon++;

                    if (attempts < bestAttempts) {
                        bestAttempts = attempts;
                    }
                    break;
                } else if (guess > target) {
                    System.out.println("📉 Too High!");
                } else {
                    System.out.println("📈 Too Low!");
                }
            }

            if (!won) {
                System.out.println("😢 You ran out of attempts!");
                System.out.println("The correct number was: " + target);
            }

            // Score display
            System.out.println("\n📊 SCOREBOARD");
            System.out.println("Rounds Played : " + roundsPlayed);
            System.out.println("Rounds Won    : " + roundsWon);
            if (bestAttempts == Integer.MAX_VALUE) {
                System.out.println("Best Attempts : Not yet");
            } else {
                System.out.println("Best Attempts : " + bestAttempts);
            }

            // Play again option
            System.out.print("\nDo you want to play again? (yes/no): ");
            sc.nextLine(); // consume leftover newline
            String choice = sc.nextLine().trim().toLowerCase();

            if (!choice.equals("yes") && !choice.equals("y")) {
                playAgain = false;
            }
        }

        System.out.println("\n🎉 Thanks for playing! See you again!");
        sc.close();
    }
}