package mgr.teacher;

public interface NetworkTeacher {
    public double getErrorOfNetwork(double[] weights, boolean czyPrintowac) throws Exception;
    public void setWeightsToNetwork(double[] weights);
    public void printNetworkWeights();
}
