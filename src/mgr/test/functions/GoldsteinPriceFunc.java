package mgr.test.functions;

/**
 * Created by Lukasz on 2014-10-27.
 */
public class GoldsteinPriceFunc{

    public static double function(double x, double y){
        return (1+Math.pow(x+y+1, 2)*(19-14*x+3*Math.pow(x, 2)-14*y+6*x*y+3*Math.pow(y, 2)))*
                (30+Math.pow(2*x-3*y, 2)*(18-32*x+12*Math.pow(x, 2)+48*y-36*x*y+27*Math.pow(y, 2)));
    }

    public static void main(String[] args){
        System.out.println(GoldsteinPriceFunc.function(0, -1));
    }
}
