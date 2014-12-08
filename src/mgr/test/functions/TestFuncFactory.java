package mgr.test.functions;

public class TestFuncFactory {

    public static TestFunction getTestFunction(TestFuncEnum alg){
        switch (alg){
            case ACKLEY:
                return new AckleyFunc();
            case BEALES:
                return new BealesFunc();
            case EASOM:
                return new EasomFunc();
            case GOLDSTEINPRICE:
                return new GoldsteinPriceFunc();
            case MATYAS:
                return new MatyasFunc();
        }
        return null;
    }
}
