package mgr.test.functions;

public class TestFuncFactory {

//    public static TestFunction getResultOfAlgorithm(AlgsEnum alg){
    public static TestFunction getTestFunction(AlgsEnum alg){
        switch (alg){
            case ACKLEY:
//                result = AckleyFunc.function(x, y);
//                break;
                return new AckleyFunc();
            case BEALES:
//                result = BealesFunc.function(x, y);
//                break;
                return new BealesFunc();
            case EASOM:
//                result = EasomFunc.function(x, y);
//                break;
                return new EasomFunc();
            case GOLDSTEINPRICE:
//                result = GoldsteinPriceFunc.function(x, y);
//                break;
                return new GoldsteinPriceFunc();
            case MATYAS:
//                result = MatyasFunc.function(x, y);
//                break;
                return new MatyasFunc();
        }
        return null;
//        return result;
    }
}
