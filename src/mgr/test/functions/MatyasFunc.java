package mgr.test.functions;

/**
 * Created by Lukasz on 2014-10-27.
 */
public class MatyasFunc {

    public static double function(double x, double y){
        return 0.26*(Math.pow(x, 2)+Math.pow(y, 2))-0.48*x*y;
    }

    public static void main(String[] args){
        System.out.println(MatyasFunc.function(0, 0));
    }
}
