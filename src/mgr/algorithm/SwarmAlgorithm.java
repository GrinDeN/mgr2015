package mgr.algorithm;

public interface SwarmAlgorithm {
    public int getMinimum() throws Exception;
    public String getName();
    public double[] getBestPositions();
}
