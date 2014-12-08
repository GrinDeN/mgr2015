package mgr.test.functions;

public abstract class TestFunction {
    public abstract double getResult(double[] positions);
    public abstract double getUpperBoundary();
    public abstract double getLowerBoundary();
    public abstract boolean isSolutionEnoughNearMinimum(double solution);
}
