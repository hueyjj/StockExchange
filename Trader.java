import java.lang.reflect.*;
import java.util.*;


/**
 * Represents a stock trader.
 */
public class Trader implements Comparable<Trader>
{
    private Brokerage brokerage;

    private String screenName, password;

    private TraderWindow myWindow;

    public Queue<String> mailbox;


    // TODO complete class
    public Trader(
        Brokerage broker,
        java.lang.String name,
        java.lang.String pswd )
    {
        brokerage = broker;
        screenName = name;
        password = pswd;
        mailbox = new LinkedList<String>();
        myWindow = null;

    }


    public int compareTo( Trader other )
    {

        int compareInt = screenName.compareToIgnoreCase( other.screenName );
        return compareInt;
    }


    public boolean equals( Trader other )
    {
        if ( other instanceof Trader )
        {
            return screenName.equalsIgnoreCase( other.screenName );
        }
        else
        {
            throw new ClassCastException();
        }

    }


    public String getName()
    {
        return screenName;
    }


    public String getPassword()
    {
        return password;
    }


    public void getQuote( String symbol )
    {
        // System.out.println( "I WORK" );
        // System.out.println( this.mailbox() );
        // System.out.println( this.mailbox() );

        brokerage.getQuote( symbol, this );

    }


    public void receiveMessage( String msg )
    {

        mailbox.add( msg );
        if ( myWindow != null )
        {

            while ( !mailbox.isEmpty() )
            {
                myWindow.showMessage( mailbox.remove() );
            }

        }
    }


    public boolean hasMessages()
    {

        return mailbox.size() != 0;
    }


    public void openWindow()
    {
        myWindow = new TraderWindow( this );
        while ( !mailbox.isEmpty() )
        {
            myWindow.showMessage( mailbox.remove() );
        }
    }


    public void placeOrder( TradeOrder order )
    {

        try
        {
            brokerage.placeOrder( order );
        }
        catch ( Exception e )
        {
        }
    }


    public void quit()
    {
        brokerage.logout( this );
        myWindow = null;

    }


    //
    // The following are for test purposes only
    //
    protected Queue<String> mailbox()
    {
        return mailbox;
    }


    // added a protected method..
    protected Brokerage brokerage()
    {
        return brokerage;
    }


    // added a protected method..
    protected TraderWindow window()
    {
        return myWindow;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Trader.
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
                if ( field.getType().getName().equals( "Brokerage" ) )
                    str += separator + field.getType().getName() + " "
                        + field.getName();
                else
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
