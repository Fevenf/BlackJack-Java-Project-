import java.util.Scanner;

public class Game {

    public static void main(String[] args) throws Exception {

        Deck mainDeck = new Deck();
        mainDeck.CreatDeck();
        mainDeck.shuffle();

        Deck player = new Deck();
        Deck dealerDeck = new Deck();
        double playMoney = 100.00;

        Scanner scanner = new Scanner(System.in);
        while (playMoney > 0) {
            System.out.println("Welcome to Blackjack!");
            System.out.println("You have $" + playMoney + ", How much would you like to bet?");
            double playerBet = scanner.nextDouble();
            if (playerBet > playMoney) {
                System.out.println("You don't have enough money, you Can't bet");
                break;
            }
            boolean endRound = false;
            player.draw(mainDeck);
            player.draw(mainDeck);

            dealerDeck.draw(mainDeck);
            dealerDeck.draw(mainDeck);

            while (true) {
                System.out.println("your hand");
                System.out.println(player.toString());
                System.out.println("Your hand valued at: " + player.cardValue());
                System.out.println("Dealer hand value: " + dealerDeck.getCard(0).toString() + " and [hidden]");
                System.out.println("Would you like to (1)Hit or (2)Stand? ");
                int userResponse = scanner.nextInt();

                if (userResponse == 1) {
                    player.draw(mainDeck);
                    System.out.println("You draw a: " + player.getCard(player.deckSize() - 1).toString());
                    if (player.cardValue() > 21) {
                        System.out.println("You lose, Currently valued at: " + player.cardValue());
                        playMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }
                if (userResponse == 2) {
                    break;
                }
            }

            System.out.println("Dealer cards: " + dealerDeck.toString());
            if (dealerDeck.cardValue() > player.cardValue() && endRound == false) {
                System.out.println("Dealer wins!");
                playMoney -= playerBet;
                endRound = true;
            }
            while (dealerDeck.cardValue() < 17 && endRound == false) {
                dealerDeck.draw(mainDeck);
                System.out.println("Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());
            }
            System.out.println("Dealer's Hand is valued at: " + dealerDeck.cardValue());
            if ((dealerDeck.cardValue() > 21) && endRound == false) {
                System.out.println("Dealer loses! You win!");
                playMoney += playerBet;
                endRound = true;
            }
            if (player.cardValue() == dealerDeck.cardValue() && endRound == false) {
                System.out.println("Push");
                endRound = true;
            }
            if (player.cardValue() > dealerDeck.cardValue() && endRound == false) {
                System.out.println("You win the hand!");
                playMoney += playerBet;
                endRound = true;
            } else if (endRound == false) {
                System.out.println("You lose the hand");
                playMoney -= playerBet;
                endRound = true;
            }

            player.moveAllToDeck(mainDeck);
            dealerDeck.moveAllToDeck(mainDeck);
            System.out.println("End of Hand.");
        }
        System.out.println("Game Over!!!");
    }
}