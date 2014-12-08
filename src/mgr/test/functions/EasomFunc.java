package mgr.test.functions;

public class EasomFunc extends TestFunction{

    private final static double LOWER_BOUNDARY = -100.0;
    private final static double UPPER_BOUNDARY = 100.0;
    private final static double MINIMUM = -1.0;
    private final static double STOP_CRITERIA = 0.001;

    @Override
    public double getResult(double[] positions){
        double x = positions[0];
        double y = positions[1];
        return -Math.cos(x)*Math.cos(y)*Math.exp(-(Math.pow(x-Math.PI, 2)+Math.pow(y-Math.PI, 2)));
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
//        return -Math.cos(x)*Math.cos(y)*Math.exp(-(Math.pow(x-Math.PI, 2)+Math.pow(y-Math.PI, 2)));
//    }
//
//    public static void main(String[] args){
//        System.out.println(EasomFunc.function(Math.PI, Math.PI));
//    }
}
