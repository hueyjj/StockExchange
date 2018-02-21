import java.lang.reflect.Field;


/**
 * A price comparator for trade orders.
 */
public class PriceComparator implements java.util.Comparator<TradeOrder>
{

    // TODO complete class
    private boolean ascendingFlag;


    public PriceComparator()
    {
        ascendingFlag = true;
    }


    public PriceComparator( boolean asc )
    {
        ascendingFlag = asc;

    }


    public int compare( TradeOrder order1, TradeOrder order2 )
    {
        if ( order1.isMarket() && order2.isMarket() )
        {
            return 0;
        }
        else if ( order1.isMarket() && order2.isLimit() )
        {
            return -1;
        }
        else if ( order1.isLimit() && order2.isMarket() )
        {
            return 1;
        }
        else if ( ascendingFlag == true )
        {
            return (int)( Math.round( 100 * order1.getPrice() ) - Math.round( 100 * order2.getPrice() ) );
        }
        else
        {
            return (int)( Math.round( 100 * order2.getPrice() ) - Math.round( 100 * order1.getPrice() ) );

        }

    }


    // is this even supposed to be here?
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
