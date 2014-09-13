package mgr.network;

/**
 * Created by Lukasz on 2014-08-07.
 */
public class Test {

    public static void main(String[] args){
        final double[] input = new double[]{1.0, 1.5, 1.2, 2.4};
        Network net = new Network(input);
        System.out.println("Wynik działania sieci: " + net.calculateOutput());
    }
}
