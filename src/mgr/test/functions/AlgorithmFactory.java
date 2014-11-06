package mgr.test.functions;

/**
 * Created by Lukasz on 2014-11-06.
 */
public class AlgorithmFactory {

    public static double getResultOfAlgorithm(AlgsEnum alg, double x, double y){
        double result = 0;
        switch (alg) {
            case ACKLEY:
                result = AckleyFunc.function(x, y);
                break;
            case BEALES:
                result = BealesFunc.function(x, y);
                break;
            case EASOM:
                result = EasomFunc.function(x, y);
                break;
            case GOLDSTEINPRICE:
                result = GoldsteinPriceFunc.function(x, y);
                break;
            case MATYAS:
                result = MatyasFunc.function(x, y);
                break;
        }
        return result;
    }
}
