package mgr.test.functions;

/**
 * Created by Lukasz on 2014-10-27.
 */
public class EasomFunc {

    public static double function(double x, double y){
        return -Math.cos(x)*Math.cos(y)*Math.exp(-(Math.pow(x-Math.PI, 2)+Math.pow(y-Math.PI, 2)));
    }

    public static void main(String[] args){
        System.out.println(EasomFunc.function(Math.PI, Math.PI));
    }
}
