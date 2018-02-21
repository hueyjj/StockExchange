import java.util.*;
import java.lang.reflect.*;
import java.text.DecimalFormat;


/**
 * Represents a stock in the SafeTrade project
 */
public class Stock
{
    public static DecimalFormat money = new DecimalFormat( "0.00" );

    private String stockSymbol;

    private String companyName;

    private double loPrice, hiPrice, lastPrice;

    private int volume;

    private PriorityQueue<TradeOrder> buyOrders, sellOrders;


    // TODO complete class

    public Stock( String symbol, String name, double price )
    {
        stockSymbol = symbol;
        companyName = name;
        loPrice = hiPrice = lastPrice = price;
        volume = 0;
        // sellOrders = new PriorityQueue<TradeOrder>();
        // buyOrders = new PriorityQueue<TradeOrder>();
        buyOrders = new PriorityQueue<TradeOrder>( 150,
            new PriceComparator( false ) );
        sellOrders = new PriorityQueue<TradeOrder>( 150,
            new PriceComparator( true ) );
        // PriceComparator sell = new PriceComparator( true );
        // PriceComparator buy = new PriceComparator( false );
    }


    // public Stock( String symbol, double price )
    // {
    // this( symbol, symbol, price );
    // }
    //
    //
    // public Stock()
    // {
    // this( null, null, 0 );
    // }

    public String getQuote()
    {// I'M SORRY FOR THE HARD CODING T_T
     // System.out.println("I'm Working");
        TradeOrder selling = null;
        TradeOrder buying = null;
        if ( !sellOrders.isEmpty() )
        {
            selling = sellOrders.peek();
        }
        if ( !buyOrders.isEmpty() )
        {
            buying = buyOrders.peek();
        }
        if ( selling == null && buying != null ) // 1 (for j Test)
        {
            return companyName + " (" + stockSymbol + ")\r\n" + "Price: "
                + money.format( lastPrice ) + " hi: " + money.format( hiPrice )
                + " lo: " + money.format( loPrice ) + " vol: " + volume
                + "\r\n" + "Ask: none " + " Bid: "
                + money.format( buying.getPrice() ) + " size: "
                + buying.getShares();
        }
        if ( selling != null && buying == null )// 2 (for j Test)
        {
            return companyName + " (" + stockSymbol + ")\r\n" + "Price: "
                + money.format( lastPrice ) + " hi: " + money.format( hiPrice )
                + " lo: " + money.format( loPrice ) + " vol: " + volume
                + "\r\n" + "Ask: " + money.format( selling.getPrice() )
                + " size: " + selling.getShares() + " Bid: none";
        }
        if ( selling == null && buying == null )// 3 (for j Test)
        {
            return companyName + " (" + stockSymbol + ")\r\n" + "Price: "
                + money.format( lastPrice ) + " hi: " + money.format( hiPrice )
                + " lo: " + money.format( loPrice ) + " vol: " + volume
                + "\r\n" + "Ask: none " + "Bid: none";
        }
        if ( selling != null && buying != null )// 4 (for j Test)
        {
            return companyName + " (" + stockSymbol + ")\r\n" + "Price: "
                + money.format( lastPrice ) + " hi: " + money.format( hiPrice )
                + " lo: " + money.format( loPrice ) + " vol: " + volume
                + "\r\n" + "Ask: " + money.format( selling.getPrice() )
                + " size: " + money.format( selling.getShares() ) + " Bid: "
                + money.format( buying.getPrice() ) + " size: "
                + buying.getShares();
        }
        return "";

    }


    public void placeOrder( TradeOrder order )
    {
        String msg = "New order: ";

        if ( order.isBuy() )
        {
            buyOrders.add( order );
            msg += "Buy ";

        }
        else
        {
            sellOrders.add( order );
            msg += "Sell ";
        }
        msg += order.getSymbol();
        msg += " (" + companyName + ")\r\n";
        msg += order.getShares() + " shares at ";

        if ( order.isMarket() )
        {
            msg += "market";
        }
        else
        {
            msg += "$" + money.format( order.getPrice() );
        }

        order.getTrader().receiveMessage( msg );
        // System.out.println( order.getTrader().mailbox() );

        executeOrders();

    }

    int counter = 0;


    protected void executeOrders()
    {

        TradeOrder sell = sellOrders.peek();
        TradeOrder buy = buyOrders.peek();
        if ( sell == null || buy == null )
        {
            return;
        }
        if ( sell.isLimit() && buy.isLimit()
            && buy.getPrice() >= sell.getPrice() )
        {
            execute( sell, buy, sell.getPrice() );
        }
        if ( sell.isLimit() && buy.isMarket() )
        {
            execute( sell, buy, sell.getPrice() );
        }
        if ( sell.isMarket() && buy.isLimit() )
        {
            execute( sell, buy, buy.getPrice() );
        }
        if ( sell.isMarket() && buy.isMarket() )
        {
            execute( sell, buy, lastPrice );
        }

        // repeat if any movement in either queues
        if ( sellOrders.isEmpty() || buyOrders.isEmpty() )
        {
            return;
        }
        else if ( counter == 1 )
        {
            counter = 0;
            executeOrders();
        }

    }


    private void execute(
        TradeOrder sellOrder,
        TradeOrder buyOrder,
        double price )
    {
        int tradeShares = Math.min( sellOrder.getShares(), buyOrder.getShares() );

        TradeOrder sell = sellOrders.remove();
        TradeOrder buy = buyOrders.remove();

        sell.subtractShares( (int)tradeShares );
        buy.subtractShares( (int)tradeShares );

        double sellShare = sell.getShares();
        double buyShare = buy.getShares();

        if ( buyShare != 0 )
        {
            buyOrders.add( buy );
        }

        if ( sellShare != 0 )
        {
            sellOrders.add( sell );
        }

        // update

        hiPrice = Math.max( hiPrice, price );

        loPrice = Math.min( loPrice, price );

        volume += (int)tradeShares;
        // update lastprice?
        // lastPrice = price;

        String boughtRcpt = "You bought: " + tradeShares + " "
            + buyOrder.getSymbol() + " at " + money.format( price ) + " amt "
            + money.format( ( tradeShares * price ) );
        String soldRcpt = "You sold: " + tradeShares + " "
            + sellOrder.getSymbol() + " at " + money.format( price ) + " amt "
            + money.format( ( tradeShares * price ) );

        buyOrder.getTrader().receiveMessage( boughtRcpt );
        sellOrder.getTrader().receiveMessage( soldRcpt );
        counter = 1;
    }


    //
    // The following are for test purposes only
    //

    protected String getStockSymbol()
    {
        return stockSymbol;
    }


    protected String getCompanyName()
    {
        return companyName;
    }


    protected double getLoPrice()
    {
        return loPrice;
    }


    protected double getHiPrice()
    {
        return hiPrice;
    }


    protected double getLastPrice()
    {
        return lastPrice;
    }


    protected int getVolume()
    {
        return volume;
    }


    protected PriorityQueue<TradeOrder> getBuyOrders()
    {
        return buyOrders;
    }


    protected PriorityQueue<TradeOrder> getSellOrders()
    {
        return sellOrders;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Stock.
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
