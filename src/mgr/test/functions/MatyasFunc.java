package mgr.test.functions;

public class MatyasFunc extends TestFunction{

//    public static double function(double x, double y){
//        return 0.26*(Math.pow(x, 2)+Math.pow(y, 2))-0.48*x*y;
//    }
//
//    public static void main(String[] args){
//        System.out.println(MatyasFunc.function(0, 0));
//    }

    private final static double LOWER_BOUNDARY = -10.0;
    private final static double UPPER_BOUNDARY = 10.0;
    private final static double MINIMUM = 0.0;
    private final static double STOP_CRITERIA = 0.001;

    @Override
    public double getResult(double[] positions){
        double x = positions[0];
        double y = positions[1];
        return 0.26*(Math.pow(x, 2)+Math.pow(y, 2))-0.48*x*y;
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
