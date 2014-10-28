package mgr.test.functions;

/**
 * Created by Lukasz on 2014-10-24.
 */
public class AckleyFunc{

    public static double function(double x, double y){
        return -20*Math.exp(-0.2*Math.sqrt(0.5*(Math.pow(x, 2)+Math.pow(y, 2))))-
                Math.exp(0.5*(Math.cos(2*Math.PI*x)+Math.cos(2*Math.PI*y)))+20+Math.E;
    }

    public static void main(String[] args){
        System.out.println(AckleyFunc.function(0, 0));
    }
}
