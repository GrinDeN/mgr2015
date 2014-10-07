package mgr.gradient;

import mgr.math.utils.MathUtils;
import mgr.network.Dendrite;
import mgr.network.Network;

import static mgr.config.Config.*;

public class OutputGradient {

    private Network network;
    private NetworkTeacher netTeacher;
    private Gradient gradient;

    public OutputGradient(Network net, NetworkTeacher networkTeacher, Gradient grad){
        this.network = net;
        this.netTeacher = networkTeacher;
        this.gradient = grad;
    }

    public double compute_dZdW2(int t, int n, int i){
        Dendrite[][] w1 = network.getHiddLayer().getLayerDendrites();
        double result = 0.0;
        for (int t0 = 1; t0 <= N2; t0++){
            result += w1[n][N1+t0].getWeight()*compute_dYdW2(t-t0, i);
        }
        return result;
    }

    public double compute_dVdW2(int t, int n, int i){
        double result = MathUtils.getDerivResult(network.getHiddLayerNeuronZt(t, n), ACTIVATE_FUNCTION)*compute_dZdW2(t, n, i);
        return result;
    }

    public double compute_dYdW2(int t, int i){
        Dendrite[] w2 = network.getOutLayer().getLayerDendrites();
        double result = 0.0;
        double[] v = network.getOutputLayerInputAtIndex(t);
        for (int n = 1; n <= HIDD_NEURONS; n++) {
            result += w2[n].getWeight()*compute_dVdW2(t, n, i);
        }
        result += v[i];
        return result;
    }

    public double compute_dE_dW2(int t, int i){
        double result = (netTeacher.getNetOutput(t) - netTeacher.getDemandOutput(t))*compute_dYdW2(t, i);
        return result;
    }
}
