package mgr.test.functions;

/**
 * Created by Lukasz on 2014-10-27.
 */
public class BealesFunc {

    public static double function(double x, double y){
        return Math.pow(1.5-x+x*y, 2)+Math.pow(2.25-x+x*Math.pow(y, 2), 2)+Math.pow(2.625-x+x*Math.pow(y, 3), 2);
    }

    public static void main(String[] args){
        System.out.println(BealesFunc.function(3, 0.5));
    }
}
