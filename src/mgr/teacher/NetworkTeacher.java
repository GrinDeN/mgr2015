package mgr.teacher;

public interface NetworkTeacher {
    public double getErrorOfNetwork(double[] weights) throws Exception;
    public void setWeightsToNetwork(double[] weights);
    public void printNetworkWeights();
}
