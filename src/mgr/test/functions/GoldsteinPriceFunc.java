package mgr.test.functions;

public class GoldsteinPriceFunc extends TestFunction{

//    public static double function(double x, double y){
//        return (1+Math.pow(x+y+1, 2)*(19-14*x+3*Math.pow(x, 2)-14*y+6*x*y+3*Math.pow(y, 2)))*
//                (30+Math.pow(2*x-3*y, 2)*(18-32*x+12*Math.pow(x, 2)+48*y-36*x*y+27*Math.pow(y, 2)));
//    }
//
//    public static void main(String[] args){
//        System.out.println(GoldsteinPriceFunc.function(0, -1));
//    }

    private final static double LOWER_BOUNDARY = -2.0;
    private final static double UPPER_BOUNDARY = 2.0;
    private final static double MINIMUM = 3.0;
    private final static double STOP_CRITERIA = 0.001;

    @Override
    public double getResult(double[] positions){
        double x = positions[0];
        double y = positions[1];
        return (1+Math.pow(x+y+1, 2)*(19-14*x+3*Math.pow(x, 2)-14*y+6*x*y+3*Math.pow(y, 2)))*
                (30+Math.pow(2*x-3*y, 2)*(18-32*x+12*Math.pow(x, 2)+48*y-36*x*y+27*Math.pow(y, 2)));
    }

    @Override
    public double getUpperBoundary(){
        return UPPER_BOUNDARY;
    }

    @Override
    public double getLowerBoundary(){
        return LOWER_BOUNDARY;
    }

    @Override
    public boolean isSolutionEnoughNearMinimum(double solution){
        return Math.abs(solution-MINIMUM) <= STOP_CRITERIA;
    }
}
