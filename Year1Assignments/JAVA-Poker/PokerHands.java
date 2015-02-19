/**
 * Class to handle current cards in the players hand
 * 
 * 
 * 
 */
public class PokerHands
{
    // 
    final int MAX_CARDS_IN_HAND = 5 ;
    String[] currentHand = new String[5] ;
    String[] discardPile = new String[52] ;
    int cardsRemoved ;
    String handPrint ;
    public PokerHands()
    {
        handPrint = "" ;
        cardsRemoved = 0 ;
        for(int i = 0 ; i < MAX_CARDS_IN_HAND ; i++)
        {
            currentHand[i] = " " ;
        }
    }
    
    //draw five cards for player to start the hand
    public void drawHand(PokerDeck deck)
    {
        //
        for(int i = 0 ; i < MAX_CARDS_IN_HAND ; i++)
        {
            currentHand[i] = deck.pickCard() ;
        }
    }
    
    //print out the current hand
    public String showHand()
    {
        handPrint = "" ;
        for(int i = 0 ; i < currentHand.length ; i++)
        {
            handPrint = handPrint + currentHand[i] + " " ;
        }
        return handPrint ;
    }
    
    //method to remove a card from the hand and replace it with another
    public void removeCard(PokerDeck deck, int index)
    {
        discardPile[cardsRemoved] = currentHand[index - 1] ;
        currentHand[index - 1] = deck.pickCard() ;
        cardsRemoved++ ;
    }
}
