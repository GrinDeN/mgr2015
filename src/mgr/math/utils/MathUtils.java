package mgr.math.utils;

public class MathUtils{
    public final static double BETA = 1;
    public final static double E = Math.E;

    public static double getMainResult(double arg, ActivateFunc activate){
        double result = 0;
        switch (activate) {
        case SIGMOID_UNI:
            result = uniSigmoid(arg);
            break;
        case SIGMOID_BI:
            result = biSigmoid(arg);
            break;
        }
        return result;
    }
    
    public static double getDerivResult(double arg, ActivateFunc activate){
        double result = 0;
        switch (activate) {
        case SIGMOID_UNI:
            result = uniSigmoidDeriv(arg);
            break;
        case SIGMOID_BI:
            result = biSigmoidDeriv(arg);
            break;
        }
        return result;
    }

    private static double uniSigmoid(double arg){
        return 1 / (1 + power(E, -BETA * arg));
    }

    private static double biSigmoid(double arg){
        return Math.tanh(arg);
    }
    
    private static double uniSigmoidDeriv(double arg){
        double mainResult = uniSigmoid(arg);
        return mainResult*(1-mainResult);
    }
    
    private static double biSigmoidDeriv(double arg){
        double mainResult = biSigmoid(arg);
        return 1-(power(mainResult, 2));
    }

    private static double power(double first, double sec){
        return Math.pow(first, sec);
    }
}
