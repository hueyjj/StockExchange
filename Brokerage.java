import java.lang.reflect.*;
import java.util.*;


/**
 * Represents a brokerage.
 */
public class Brokerage implements Login
{
    private Map<String, Trader> traders;

    private Set<Trader> loggedTraders;

    private StockExchange exchange;


    // TODO complete class

    public Brokerage( StockExchange exchange )
    {
        this.exchange = exchange;
        traders = new TreeMap<String, Trader>();
        loggedTraders = new TreeSet<Trader>();
    }


    public int addUser( String name, String password )
    {

        if ( name.length() <= 4 || name.length() >= 10 )
        {
            return -1;
        }
        else if ( password.length() <= 2 || password.length() >= 10 )
        {
            return -2;
        }
        else if ( traders.keySet().contains( name ) )
        {
            return -3;
        }
        else
        {
            traders.put( name, new Trader( this, name, password ) );
            return 0;
        }

    }


    public void getQuote( String symbol, Trader trader )
    {

        // String msg = exchange.getQuote( symbol );
        // if ( msg != null )
        // {
        //
        // trader.receiveMessage( msg );
        // }
        trader.receiveMessage( exchange.getQuote( symbol ) );

    }


    public int login( String name, String password )
    {

        if ( !traders.containsKey( name ) )
        {
            return -1;
        }
        else if ( traders.containsKey( name )
            && !traders.get( name ).getPassword().equals( password ) )
        {
            return -2;
        }
        else if ( loggedTraders.contains( traders.get( name ) ) )
        {
            return -3;
        }
        else if ( !loggedTraders.contains( traders.get( name ) )
            && traders.containsKey( name ) )
        {
            if ( !traders.get( name ).hasMessages() )
            {
                traders.get( name ).receiveMessage( "Welcome to SafeTrade!" );
            }
            traders.get( name ).openWindow();
            loggedTraders.add( traders.get( name ) );
            return 0;
        }
        return -999; // end method
    }


    public void logout( Trader trader )
    {
        if ( loggedTraders.contains( trader ) )
        {
            loggedTraders.remove( trader );
        }
    }


    public void placeOrder( TradeOrder order )
    {
        try
        {
            exchange.placeOrder( order );
        }
        catch ( Exception e )
        {
        }

    }


    //
    // The following are for test purposes only
    //
    protected Map<String, Trader> getTraders()
    {
        return traders;
    }


    protected Set<Trader> getLoggedTraders()
    {
        return loggedTraders;
    }


    protected StockExchange getExchange()
    {
        return exchange;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Brokerage.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for ( Field field : fields )
        {
            try
            {
                str += separator + field.getType().getName() + " "
                    + field.getName() + ":" + field.get( this );
            }
            catch ( IllegalAccessException ex )
            {
                System.out.println( ex );
            }

            separator = ", ";
        }

        return str + "]";
    }
}
