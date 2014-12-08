package mgr.test.functions;

public class AckleyFunc extends TestFunction{

    private final static double LOWER_BOUNDARY = -5.0;
    private final static double UPPER_BOUNDARY = 5.0;
    private final static double MINIMUM = 0.0;
//    private final static double UPPER_STOP_BOUNDARY = MINIMUM+0.1;
//    private final static double LOWER_STOP_BOUNDARY = MINIMUM-0.1;
    private final static double STOP_CRITERIA = 0.001;

//    public static double function(double x, double y){
//        return -20*Math.exp(-0.2*Math.sqrt(0.5*(Math.pow(x, 2)+Math.pow(y, 2))))-
//                Math.exp(0.5*(Math.cos(2*Math.PI*x)+Math.cos(2*Math.PI*y)))+20+Math.E;
//    }

//    public static void main(String[] args){
//        System.out.println(AckleyFunc.function(0, 0));
//    }

    @Override
    public double getResult(double[] positions){
        double x = positions[0];
        double y = positions[1];
        return -20*Math.exp(-0.2*Math.sqrt(0.5*(Math.pow(x, 2)+Math.pow(y, 2))))-
                Math.exp(0.5*(Math.cos(2*Math.PI*x)+Math.cos(2*Math.PI*y)))+20+Math.E;
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
//        return solution <= UPPER_STOP_BOUNDARY && solution >= LOWER_STOP_BOUNDARY;
        return Math.abs(solution-MINIMUM) <= STOP_CRITERIA;
    }
}
