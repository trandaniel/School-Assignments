import java.util.Scanner ;
/**
 * Write a description of class PokerTester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PokerTester
{
  public static void main(String [] args)
  {
      Scanner scanner = new Scanner(System.in) ;
      //Testing if a deck is created and shuffled
      PokerDeck deck = new PokerDeck() ;
      deck.getDeck() ;
      System.out.println("Expected: List of Cards in order.") ;
      deck.shuffle() ;
      deck.getDeck() ;
      System.out.println("Expected: List of Cards shuffled.") ;
      //Check if card picked dispayed removes value from actual deck
      System.out.println("Cards picked: " + deck.pickCard() + " " + deck.pickCard() + " " + deck.pickCard()) ;
      System.out.println("Cards in Deck: " + deck.getCards()) ;
      System.out.println("Expected: Three cards printed out with remaining cards in Deck") ;
      deck.getDeck() ;
      System.out.println("Expected: Current cards remaining in deck.") ;
      deck = new PokerDeck() ;
      deck.getDeck() ;
      System.out.println("Expected: New list of cards in order") ;
      PokerHands hand = new PokerHands() ;
      hand.drawHand(deck) ;
      System.out.println(hand.showHand()) ;
      System.out.println("Expected: SA SK SQ SJ S10") ;
      deck.getDeck() ;
      System.out.println("Expected: Remaining cards = 47") ;
      System.out.println("From left to right (1-5) pick a card to be removed and replaced with the top card from deck") ;
      hand.removeCard(deck, scanner.nextInt()) ;
      System.out.println(hand.showHand()) ;
      System.out.println("Expected: Card removed and replaced with S9") ;
  }
    
}
