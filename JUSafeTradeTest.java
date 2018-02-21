import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;


/**
 * SafeTrade tests: TradeOrder PriceComparator Trader Brokerage StockExchange
 * Stock
 *
 * @author TODO Name of principal author
 * @author TODO Name of group member
 * @author TODO Name of group member
 * @version TODO date
 * @author Assignment: JM Chapter 19 - SafeTrade
 * 
 * @author Sources: TODO sources
 *
 */
public class JUSafeTradeTest
{
    // --Test TradeOrder
    /**
     * TradeOrder tests: TradeOrderConstructor - constructs TradeOrder and then
     * compare toString TradeOrderGetTrader - compares value returned to
     * constructed value TradeOrderGetSymbol - compares value returned to
     * constructed value TradeOrderIsBuy - compares value returned to
     * constructed value TradeOrderIsSell - compares value returned to
     * constructed value TradeOrderIsMarket - compares value returned to
     * constructed value TradeOrderIsLimit - compares value returned to
     * constructed value TradeOrderGetShares - compares value returned to
     * constructed value TradeOrderGetPrice - compares value returned to
     * constructed value TradeOrderSubtractShares - subtracts known value &
     * compares result returned by getShares to expected value
     */
    private String symbol = "GGGL";

    private boolean buyOrder = true;

    private boolean marketOrder = true;

    private int numShares = 123;

    private int numToSubtract = 24;

    private double price = 123.45;


    @Test
    public void tradeOrderConstructor()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        String toStr = to.toString();

        assertTrue( "<< Invalid TradeOrder Constructor >>",
            toStr.contains( "TradeOrder[Trader trader:null" )
                && toStr.contains( "java.lang.String symbol:" + symbol )
                && toStr.contains( "boolean buyOrder:" + buyOrder )
                && toStr.contains( "boolean marketOrder:" + marketOrder )
                && toStr.contains( "int numShares:" + numShares )
                && toStr.contains( "double price:" + price ) );
    }


    @Test
    public void tradeOrderToString()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertNotNull( to.toString() );
    }


    @Test
    public void tradeOrderGetTrader()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertNull( "<< TradeOrder: " + to.getTrader() + " should be null >>",
            to.getTrader() );
    }


    @Test
    public void tradeOrderGetSymbol()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertEquals( "<< TradeOrder: " + to.getTrader() + " should be "
            + symbol + " >>", symbol, to.getSymbol() );
    }


    @Test
    public void tradeOrderIsBuy()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );

        assertTrue( "<< TradeOrder: " + to.isBuy() + " should be " + buyOrder
            + " >>", to.isBuy() );
    }


    @Test
    public void tradeOrderIsSell()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertFalse( "<< TradeOrder: " + to.isSell() + " should be "
            + !buyOrder + " >>", to.isSell() );
    }


    @Test
    public void tradeOrderIsMarket()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertTrue( "<< TradeOrder: " + to.isMarket() + " should be "
            + marketOrder + " >>", to.isMarket() );
    }


    @Test
    public void tradeOrderIsLimit()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );

        assertFalse( "<< TradeOrder: " + to.isLimit() + " should be "
            + !marketOrder + ">>", to.isLimit() );
    }


    @Test
    public void tradeOrderGetShares()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertTrue( "<< TradeOrder: " + to.getShares() + " should be "
            + numShares + ">>", numShares == to.getShares()
            || ( numShares - numToSubtract ) == to.getShares() );
    }


    @Test
    public void tradeOrderGetPrice()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        assertEquals( "<< TradeOrder: " + to.getPrice() + " should be " + price
            + ">>", price, to.getPrice(), 0.0 );
    }


    @Test
    public void tradeOrderSubtractShares()
    {
        TradeOrder to = new TradeOrder( null,
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        to.subtractShares( numToSubtract );
        assertEquals( "<< TradeOrder: subtractShares(" + numToSubtract
            + ") should be " + ( numShares - numToSubtract ) + ">>", numShares
            - numToSubtract, to.getShares() );
    }


    // --Test TraderWindow Stub
    @Test
    public void traderWindowConstructor()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
    }


    @Test
    public void traderWindowShowMessage()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
        tw.showMessage( null );
    }

    // --Test PriceComparator

    // TODO your tests here
    TradeOrder market = new TradeOrder( null, symbol, true, true, 7, 4 );

    TradeOrder otherMarket = new TradeOrder( null, symbol, true, true, 3, 5 );

    TradeOrder limit = new TradeOrder( null, symbol, true, false, 7, 3 );

    TradeOrder otherLimit = new TradeOrder( null, symbol, true, false, 10, 6 );


    @Test
    public void priceComparatorConstructor()
    {
        PriceComparator to = new PriceComparator();
        PriceComparator flse = new PriceComparator( false );
        PriceComparator tru = new PriceComparator( true );

        String toStr = to.toString();
        String flseStr = flse.toString();
        String truStr = tru.toString();
        assertTrue( "<< Invalid PriceComparator Constructor >>",
            flseStr.contains( "boolean ascendingFlag:" + false )
                && truStr.contains( "boolean ascendingFlag:" + true )
                && toStr.contains( "boolean ascendingFlag:" ) );
        assertFalse( "<< Invalid PriceComparator Constructor >>",
            flseStr.contains( "boolean ascendingFlag:" + true )
                && truStr.contains( "boolean ascendingFlag:" + false )
                && toStr.contains( "boolean ascendingFlag:" ) );
    }


    @Test
    public void priceComparatorCompare()
    {
        PriceComparator flse = new PriceComparator( false );
        PriceComparator tru = new PriceComparator( true );
        PriceComparator to = new PriceComparator();
        assertEquals( "<<market and otherMarket should be 0>>",
            0,
            to.compare( market, otherMarket ) );
        assertEquals( "<<market and limit should be -1>>",
            -1,
            to.compare( market, limit ) );
        assertEquals( "<<limit and market should be 1>>",
            1,
            to.compare( limit, market ) );
        assertEquals( "<<limit and otherLimit and ascendingFlag:false should be "
            + 300 + ">>",
            300,
            flse.compare( limit, otherLimit ) );
        assertEquals( "<<limit and otherLimit and asecndingFlag:true should be "
            + -300 + ">>",
            -300,
            tru.compare( limit, otherLimit ) );

    }


    @Test
    public void priceComparatorToString()
    {
        PriceComparator to = new PriceComparator();
        assertNotNull( to.toString() );
    }


    // --Test Trader

    // TODO your tests here

    @Test
    public void traderConstructor()
    {
        String name = "Jasper";
        String pass = "Jeng";
        Brokerage broker = null;
        Trader to = new Trader( broker, name, pass );

        String toStr = to.toString();

        assertTrue( "<< Invalid Trader Constructor >>",
            toStr.contains( "String screenName:" + name )
                && toStr.contains( "String password:" + pass ) );
        assertNotNull( "<< Invalid mailbox Constructor >>",
            toStr.contains( "Queue<String> mailbox:" + to.mailbox() ) );
        assertNull( "<< Invalid brokerage Constructor >>", to.brokerage() );
    }


    @Test
    public void traderCompareTo()
    {
        Trader trader = new Trader( null, "Jasper", "Jeng" );
        Trader sameTrader = new Trader( null, "Jasper", "man" );
        Trader biggerTrader = new Trader( null, "Kasper", "Jeng" );
        Trader lowerTrader = new Trader( null, "Iasper", "Jeng" );

        assertEquals( "should be -1", -1, trader.compareTo( biggerTrader ) );
        assertEquals( "should be 0", 0, trader.compareTo( sameTrader ) );
        assertEquals( "should be 1", 1, trader.compareTo( lowerTrader ) );

    }


    @Test(expected = ClassCastException.class)
    public void traderEquals()
    {
        Trader trader = new Trader( null, "Jasper", "Jeng" );
        Trader sameTrader = trader;
        Trader trader2 = new Trader( null, "Jasper", "Jeng" );
        Trader notSame = new Trader( null, "Super", "Manly" );

        assertTrue( "should be true", trader.equals( sameTrader ) );
        assertTrue( "should be true", trader.equals( trader2 ) );
        assertFalse( "should be false", trader.equals( notSame ) );

        Object throwTrader = "Jasper Jeng";

        trader.equals( (Trader)throwTrader );

    }


    @Test
    public void traderGetName()
    {
        Trader trader = new Trader( null, "Jasper", "Jeng" );

        assertEquals( "should equal Jasper", "Jasper", trader.getName() );
        assertNotSame( "should not be same", trader.getName(), "Gordon" );
    }


    @Test
    public void traderGetPassword()
    {
        Trader trader = new Trader( null, "Jasper", "Jeng" );
        assertEquals( "should equal Jeng", "Jeng", trader.getPassword() );
        assertNotSame( "should no be same", trader.getPassword(), "Jenga" );
    }


    @Test
    public void traderGetQuote()
    {
        StockExchange ex = new StockExchange();
        Brokerage broker = new Brokerage( ex );
        Trader trader = new Trader( broker, "Jasper", "Jeng" );
        ex.listStock( "JJ", "JasperJeng", 99 );

        trader.getQuote( "JJ" );
        assertTrue( "Should be true", trader.hasMessages() );
        // System.out.println( trader.mailbox() );
    }


    @Test
    public void traderReceiveMessage()
    {
        Trader trader = new Trader( null, "Jasper", "Jeng" );
        trader.receiveMessage( "I am super manly and you are too!!" );
        assertTrue( "Should be true", !trader.mailbox().isEmpty() );
        // System.out.println( trader.mailbox() );
    }


    @Test
    public void traderHasMessages()
    {
        Trader trader = new Trader( null, "Jasper", "Jeng" );
        assertFalse( "Should be false", trader.hasMessages() );
        trader.receiveMessage( "I am super manly and you are too!!" );
        assertTrue( "Should be true", trader.hasMessages() );

    }


    @Test
    public void traderOpenWindow()
    {
        Trader trader = new Trader( null, "Jasper", "Jeng" );
        trader.receiveMessage( "I am super manly and you are too!!" );
        assertTrue( "Should be true", trader.hasMessages() );
        // System.out.println( trader.mailbox() + " ----" );
        trader.openWindow();
        // System.out.println( trader.mailbox() + " ......" );
        assertFalse( "Should be false", trader.hasMessages() );

        assertNotNull( "Should be equal to something", trader.window() );
        // System.out.println( trader.window() );
    }


    @Test
    public void traderPlaceOrder()
    {
        StockExchange ex = new StockExchange();
        Brokerage broker = new Brokerage( ex );
        Trader trader = new Trader( broker, "Jasper", "Jeng" );
        TradeOrder order = new TradeOrder( trader, "JJ", true, true, 10, 99 );
        ex.listStock( "JJ", "JasperJeng", 99 );
        assertTrue( "Should have no messages", trader.mailbox().isEmpty() );
        trader.placeOrder( order );
        assertTrue( "Should have message (true)", !trader.mailbox().isEmpty() );

    }


    @Test
    public void traderQuit()
    {
        StockExchange ex = new StockExchange();
        Brokerage broker = new Brokerage( ex );
        Trader trader = new Trader( broker, "Jasper", "Jeng" );

        broker.addUser( "Jasper", "Jeng" );
        broker.login( "Jasper", "Jeng" );

        assertTrue( "Should be logged in", !broker.getLoggedTraders().isEmpty() );
        // System.out.println( broker.getLoggedTraders() + " hihihihi" );
        broker.logout( trader );
        // System.out.println( broker.getLoggedTraders() + "zzzzzzzzzzzz" );
        assertTrue( "Should be logged out", broker.getLoggedTraders().isEmpty() );
    }


    @Test
    public void traderToString()
    {
        Trader to = new Trader( null, "Jasper", "Jeng" );
        assertNotNull( to.toString() );
    }


    // --Test Brokerage

    // TODO your tests here

    @Test
    public void brokerageConstructor()
    {

        Brokerage to = new Brokerage( null );

        String toStr = to.toString();
        assertTrue( "<< Invalid Brokerage Constructor >>",
            toStr.contains( "StockExchange exchange:" + to.getExchange() ) );
        assertNotNull( "<< Invalid Brokerage Constructor >>",
            toStr.contains( "Map<String,Trader> traders:" + to.getTraders() )
                && toStr.contains( "Set<Trader> loggedTraders:"
                    + to.getLoggedTraders() ) );
    }


    @Test
    public void brokerageAddUser()
    {
        Brokerage to = new Brokerage( null );
        to.addUser( "Jasper", "Jeng" );

        assertEquals( "addUser(J, Leester) should be -1",
            -1,
            to.addUser( "J", "Leester" ) );
        assertEquals( "addUser(Jasper, asdfghjkl;asdf) should be -2",
            -2,
            to.addUser( "Jasper", "asdfghjkl;asdf" ) );
        assertEquals( "addUser(Jasper, Jeng) should be -3",
            -3,
            to.addUser( "Jasper", "Jeng" ) );
        assertEquals( "addUser(Edward, ranger) should be 0",
            0,
            to.addUser( "Edward", "ranger" ) );
    }


    @Test
    public void brokerageGetQuote()
    {
        StockExchange ex = new StockExchange();
        ex.listStock( "JJ", "Jasper", 999 );

        Brokerage br = new Brokerage( ex );
        Trader trader = new Trader( br, "Jasper", "Jeng" );
        TradeOrder order = new TradeOrder( trader, "JJ", true, true, 1, 99 );
        ex.placeOrder( order );

        br.getQuote( "JJ", trader );

        assertTrue( "should return true ", trader.hasMessages() );

        StockExchange ex1 = new StockExchange();
        ex1.listStock( "JJ", "Jasper", 999 );

        Brokerage br1 = new Brokerage( ex1 );
        Trader trader1 = new Trader( br1, "Jasper1", "Jeng1" );

        br1.getQuote( "JJ", trader1 );
        // System.out.println( trader1.mailbox() );
        assertFalse( "should return false ", !trader1.hasMessages() );

    }


    @Test
    public void brokerageLogin()
    {
        StockExchange exchange = new StockExchange();
        Brokerage broker = new Brokerage( exchange );
        // Trader trader = new Trader( broker, "Jasper", "Jeng" );
        // Trader noobTrader = new Trader( broker, "Magic", "Ponies" );

        broker.addUser( "Jasper", "Jeng" );
        broker.addUser( "Magic", "Ponies" );
        assertEquals( "should be 0", 0, broker.login( "Jasper", "Jeng" ) );
        // there's no way (i think) to test if the message went through b/c it
        // gets
        // deleted when the openWindow() is called in the login method of
        // Brokerage

        assertEquals( "should be -1", -1, broker.login( "Hello", "Kitty" ) );
        assertEquals( "should be -2", -2, broker.login( "Magic", "brownies" ) );
        assertEquals( "should be -3", -3, broker.login( "Jasper", "Jeng" ) );

    }


    @Test
    public void brokerageLogout()
    {
        StockExchange exchange = new StockExchange();
        Brokerage broker = new Brokerage( exchange );

        broker.addUser( "Jasper", "Jeng" );
        broker.login( "Jasper", "Jeng" );
        assertNotNull( "traders should have Jasper",
            broker.getTraders().get( "Jasper" ) );
        assertTrue( "should be logged in", !broker.getLoggedTraders().isEmpty() );

        broker.logout( broker.getTraders().get( "Jasper" ) );
        assertTrue( "loggedTraders should be empty", broker.getLoggedTraders()
            .isEmpty() );

    }


    @Test
    public void brokeragePlaceOrder()
    {
        StockExchange exchange = new StockExchange();
        Brokerage broker = new Brokerage( exchange );
        Trader trader = new Trader( broker, "Jasper", "Jeng" );
        TradeOrder order = new TradeOrder( trader, "Jasper", true, true, 1, 99 );
        exchange.listStock( "Jasper", "Jeng", 99 );

        assertTrue( "should have no messages", trader.mailbox().isEmpty() );
        broker.placeOrder( order );

        assertTrue( "should have message", !trader.mailbox().isEmpty() );

        // System.out.println( trader.mailbox().remove() );
        // test that trader got the order

    }


    @Test
    public void brokerageToString()
    {
        Brokerage broker = new Brokerage( null );
        assertNotNull( broker.toString() );
    }


    // --Test StockExchange

    // TODO your tests here

    @Test
    public void stockExchangeConstructor()
    {
        StockExchange to = new StockExchange();
        String toStr = to.toString();

        assertNotNull( "<< Invalid StockExchange Constructor >>",
            toStr.contains( "Map<String, Stock> listedStocks:"
                + to.getListedStocks() ) );
    }


    @Test
    public void stockExchangeToString()
    {
        StockExchange to = new StockExchange();
        assertNotNull( to.toString() );
    }


    @Test
    public void stockExchangeGetQuote()
    {
        StockExchange to = new StockExchange();
        assertEquals( "<< StockExchange: " + to.getQuote( symbol )
            + " should be error message >>",
            symbol + " not found.",
            to.getQuote( symbol ) );

        to.listStock( "Jasper", "Jeng", 99 );

        Stock dummyStock = new Stock( "Jasper", "Jeng", 99 );
        assertEquals( "should be\r\n" + dummyStock.getQuote(),
            dummyStock.getQuote(),
            to.getQuote( "Jasper" ) );
    }


    @Test
    public void stockExchangePlaceOrder()
    {
        // might have to test if sellOrders and buyOrders in Stock class
        // contains the order
        StockExchange to = new StockExchange();
        Brokerage broker = new Brokerage( to );
        Trader trader = new Trader( broker, "Jasper", "Jeng" );
        TradeOrder buyOrder = new TradeOrder( trader,
            "JJ",
            true,
            false,
            100,
            99 );

        to.listStock( "JJ", "Jasper", 99 );
        assertTrue( "should have no messages", trader.mailbox().isEmpty() );
        to.placeOrder( buyOrder );

        assertNotNull( "buyOrders should not be null", to.getListedStocks()
            .get( "JJ" )
            .getBuyOrders() );
        assertTrue( "should have buy message", !trader.mailbox().isEmpty() );

        Trader trader1 = new Trader( broker, "Jasper1", "Jeng1" );
        TradeOrder sellOrder = new TradeOrder( trader1,
            "JJ",
            false,
            true,
            1,
            99 );
        TradeOrder asdf = new TradeOrder( trader1, "JJ", false, false, 2, 100 );
        to.placeOrder( sellOrder );
        to.placeOrder( asdf );
        assertNotNull( "sellOrders should not be null", to.getListedStocks()
            .get( "JJ" )
            .getSellOrders() );
        assertTrue( "should have sell message", !trader1.mailbox().isEmpty() );

        // System.out.println( trader.mailbox() );
        // System.out.println( to.getListedStocks().get( "JJ" ).getQuote() );
        // System.out.println(trader1.mailbox());
    }


    // --Test Stock

    // TODO your tests here
    @Test
    public void stockConstructor()
    {
        String symbol = "JJ";
        String name = "JasperJeng";
        double price = 99;
        Stock stock = new Stock( symbol, name, price );

        String toStr = stock.toString();

        assertTrue( "<< Invalid Stock Constructor >>",
            toStr.contains( "String stockSymbol:" + symbol )
                && toStr.contains( "String companyName:" + name )
                && toStr.contains( "double loPrice:" + price )
                && toStr.contains( "double hiPrice:" + price )
                && toStr.contains( "double lastPrice:" + price )
                && toStr.contains( "int volume:" + stock.getVolume() ) );
        assertNotNull( "buyOrders should be initialized", stock.getBuyOrders() );
        assertNotNull( "sellOrders should be initialized",
            stock.getSellOrders() );

    }


    @Test
    public void stockGetQuote()
    {
        StockExchange ex = new StockExchange();
        Brokerage broker = new Brokerage( ex );
        Trader trader = new Trader( broker, "Jasper", "Jeng" );
        ex.listStock( "JJ", "JasperJeng", 99 );
        TradeOrder buyOrder = new TradeOrder( trader, "JJ", true, true, 1, 99 );
        TradeOrder sellOrder = new TradeOrder( trader, "JJ", false, true, 2, 99 );

        Stock stock = ex.getListedStocks().get( "JJ" );

        assertNotNull( "Should be a quote", stock.getQuote() ); // test 3
        // System.out.println( stock.getQuote() );

        stock.placeOrder( buyOrder );
        stock.placeOrder( sellOrder );

        assertNotNull( "Should be a quote", stock.getQuote() );// 4

    }


    @Test
    public void stockPlaceOrder()
    {
        StockExchange ex = new StockExchange();
        Brokerage broker = new Brokerage( ex );
        Trader trader = new Trader( broker, "Jasper", "Jeng" );

        ex.listStock( "JJ", "JasperJeng", 99 );
        Stock stock = ex.getListedStocks().get( "JJ" );

        TradeOrder buyOrder = new TradeOrder( trader, "JJ", true, true, 1, 99 );
        stock.placeOrder( buyOrder );

        assertTrue( "Should receive buy message", trader.hasMessages() );
        // System.out.println( trader.mailbox() );

        TradeOrder sellOrder = new TradeOrder( trader, "JJ", false, true, 1, 99 );
        stock.placeOrder( sellOrder );

        trader.mailbox().remove(); // remove buy order

        assertTrue( "Should receive sell message", trader.hasMessages() );
        // System.out.println( trader.mailbox() );
    }


    @Test
    public void stockExecuteOrders()
    {
        StockExchange ex = new StockExchange();
        Brokerage broker = new Brokerage( ex );
        Trader hot = new Trader( broker, "Jasper", "Jeng" );
        Trader cold = new Trader( broker, "Super", "Manly" );

        ex.listStock( "JJ", "JasperJeng", 99 );
        Stock stock = ex.getListedStocks().get( "JJ" );

        TradeOrder sellLOrder = new TradeOrder( hot, "JJ", false, false, 1, 99 );// sell
        // limit
        TradeOrder buyLOrder = new TradeOrder( cold, "JJ", true, false, 1, 99 );// buy
        // limit

        stock.placeOrder( sellLOrder );
        stock.placeOrder( buyLOrder );

        stock.executeOrders();

        assertTrue( "hot should have message", hot.hasMessages() );
        assertTrue( "cold should have message", cold.hasMessages() );

        assertTrue( "there should be no more buy orders left",
            stock.getBuyOrders().isEmpty() );
        assertTrue( "there should be no more sell orders left",
            stock.getSellOrders().isEmpty() );

        /*
         * clears the messages (inefficient but ehhhh)
         */
        hot.openWindow();
        hot.quit();
        cold.openWindow();
        cold.quit();

        TradeOrder sellLOrder2 = new TradeOrder( hot, "JJ", false, false, 1, 99 );// sell
        // limit
        TradeOrder buyMOrder = new TradeOrder( cold, "JJ", true, true, 1, 99 );// buy
        // market

        stock.placeOrder( sellLOrder2 );
        stock.placeOrder( buyMOrder );
        stock.executeOrders();

        assertTrue( "hot should have message", hot.hasMessages() );
        assertTrue( "cold should have message", cold.hasMessages() );

        assertTrue( "there should be no more buy orders left",
            stock.getBuyOrders().isEmpty() );
        assertTrue( "there should be no more sell orders left",
            stock.getSellOrders().isEmpty() );

        hot.openWindow();
        hot.quit();
        cold.openWindow();
        cold.quit();

        TradeOrder sellMOrder = new TradeOrder( hot, "JJ", false, true, 1, 99 );
        TradeOrder buyLOrder2 = new TradeOrder( cold, "JJ", true, false, 1, 99 );

        stock.placeOrder( sellMOrder );
        stock.placeOrder( buyLOrder2 );
        stock.executeOrders();

        assertTrue( "hot should have message", hot.hasMessages() );
        assertTrue( "cold should have message", cold.hasMessages() );

        assertTrue( "there should be no more buy orders left",
            stock.getBuyOrders().isEmpty() );
        assertTrue( "there should be no more sell orders left",
            stock.getSellOrders().isEmpty() );

        hot.openWindow();
        hot.quit();
        cold.openWindow();
        cold.quit();

        TradeOrder sellMOrder1 = new TradeOrder( cold,
            "JJ",
            false,
            true,
            1,
            100 );
        TradeOrder buyMOrder1 = new TradeOrder( hot, "JJ", true, true, 1, 120 );

        stock.placeOrder( sellMOrder1 );
        stock.placeOrder( buyMOrder1 );
        stock.executeOrders();

        assertTrue( "hot should have message", hot.hasMessages() );
        assertTrue( "cold should have message", cold.hasMessages() );

        assertTrue( "there should be no more buy orders left",
            stock.getBuyOrders().isEmpty() );
        assertTrue( "there should be no more sell orders left",
            stock.getSellOrders().isEmpty() );
        hot.openWindow();
        hot.quit();
        cold.openWindow();
        cold.quit();

        // System.out.println( "cold mail box " + cold.mailbox() );
        // System.out.println( "hot mail box " + hot.mailbox() );

        // test if it repeats

        TradeOrder buyALot = new TradeOrder( hot, "JJ", true, true, 5, 99 );
        TradeOrder sell = new TradeOrder( cold, "JJ", false, false, 1, 99 );
        TradeOrder sell2 = new TradeOrder( cold, "JJ", false, false, 1, 99 );
        TradeOrder sell3 = new TradeOrder( cold, "JJ", false, false, 1, 99 );
        TradeOrder sell4 = new TradeOrder( cold, "JJ", false, false, 1, 99 );
        TradeOrder sell5 = new TradeOrder( cold, "JJ", false, false, 1, 99 );

        stock.placeOrder( buyALot );
        stock.placeOrder( sell );
        stock.placeOrder( sell2 );
        stock.placeOrder( sell3 );
        stock.placeOrder( sell4 );
        stock.placeOrder( sell5 );

        stock.executeOrders();
        assertTrue( "hot should have message", hot.hasMessages() );
        assertTrue( "cold should have message", cold.hasMessages() );

        assertTrue( "there should be no more buy orders left",
            stock.getBuyOrders().isEmpty() );
        assertTrue( "there should be no more sell orders left",
            stock.getSellOrders().isEmpty() );
        // System.out.println( "cold mail box " + cold.mailbox() );
        // System.out.println( "hot mail box " + hot.mailbox() );

    }


    @Test
    public void stockToString()
    {
        Stock stock = new Stock( "JJ", "JasperJeng", 99 );
        assertNotNull( stock.toString() );
    }


    // Remove block comment below to run JUnit test in console

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUSafeTradeTest.class );
    }


    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "JUSafeTradeTest" );
    }

}
