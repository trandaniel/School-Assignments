import java.util.Random ;
/**
 * Write a description of class PokerDeck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PokerDeck
{
    //constants for max number of cards and array for the master list of cards
    private final int MAX_CARDS = 52 ;
    private final String[] CARD_LIST = new String[] {"D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "DJ", "DQ", "DK", "DA",
                                                     "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "CJ", "CQ", "CK", "CA",
                                                     "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "HJ", "HQ", "HK", "HA",
                                                     "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10", "SJ", "SQ", "SK", "SA"} ;
    String[] currentDeck ;
    String cardPicked ;
    int currentCards ;
    

    /**
     * Constructor for objects of class PokerDeck
     */
    public PokerDeck()
    {
        // initialise instance variables
        currentCards = MAX_CARDS ;
        currentDeck = new String[52] ;
        for(int i = 0 ; i < CARD_LIST.length ; i++)
        {
            currentDeck[i] = CARD_LIST[i] ;
        }     
    }
    
    
    //method to shuffle the cards
    public void shuffle()
    {
        //create a random object to shuffle the deck
        Random randomInt = new Random() ;
        String tempCard ;
        //create a random range for how many times the cards are shuffled
        int shuffleRange = randomInt.nextInt(200) + 50 ;
        //create varibles to hold the indices of the cards to be swapped
        int randShuffleA ;
        int randShuffleB ;
        
        //create loop to swap random cards shuffleRange times
        for(int i = 0 ; i < shuffleRange ; i++)
        {
            //check if the two indices are the same, if not swap cards
            do
            {
                randShuffleA = randomInt.nextInt(52) ;
                randShuffleB = randomInt.nextInt(52) ;
            }while(randShuffleA == randShuffleB) ;
                        
            tempCard = currentDeck[randShuffleA] ;
            currentDeck[randShuffleA] = currentDeck[randShuffleB] ;
            currentDeck[randShuffleB] = tempCard ;
        }
    }
    
    //method to print the current deck
    public void getDeck()
    {
        for(int i = 0 ; i < currentCards ; i++)
        {
            System.out.print(currentDeck[i] + ", ") ;           
        }
        System.out.println(currentCards) ;
    }
    
    //return the current cards in deck
    public int getCards()
    {
        return currentCards ;
    }

    //picking a card
    public String pickCard()
    {
        cardPicked = "" ;
        cardPicked = cardPicked + currentDeck[currentCards - 1] ;
        currentDeck[currentCards - 1] = null ;
        currentCards = currentCards - 1 ;
        return cardPicked ;
    }
}
