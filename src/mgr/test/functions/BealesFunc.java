package mgr.test.functions;

public class BealesFunc extends TestFunction{

    private final static double LOWER_BOUNDARY = -4.5;
    private final static double UPPER_BOUNDARY = 4.5;
    private final static double MINIMUM = 0.0;
    private final static double STOP_CRITERIA = 0.001;

    @Override
    public double getResult(double[] positions){
        double x = positions[0];
        double y = positions[1];
        return Math.pow(1.5-x+x*y, 2)+Math.pow(2.25-x+x*Math.pow(y, 2), 2)+Math.pow(2.625-x+x*Math.pow(y, 3), 2);
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

//    public static double function(double x, double y){
//        return Math.pow(1.5-x+x*y, 2)+Math.pow(2.25-x+x*Math.pow(y, 2), 2)+Math.pow(2.625-x+x*Math.pow(y, 3), 2);
//    }
//
//    public static void main(String[] args){
//        System.out.println(BealesFunc.function(3, 0.5));
//    }
}
