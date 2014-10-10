package mgr.gradient;

import mgr.math.utils.MathUtils;
import mgr.network.Dendrite;
import mgr.network.Network;
import mgr.teacher.NetworkTeacher;

import static mgr.config.Config.*;

public class HiddenGradient {

    private Network network;
    private NetworkTeacher netTeacher;
    private Gradient gradient;

    public HiddenGradient(Network net, NetworkTeacher networkTeacher, Gradient grad){
        this.network = net;
        this.netTeacher = networkTeacher;
        this.gradient = grad;
    }

    public double compute_dZdW1(int t, int n, int i, int j){
        double result = 0;
        double[] x = network.getHiddLayerInput();
        Dendrite[][] w1 = network.getHiddLayer().getLayerDendrites();
        if (n == i){
            result = x[j];
        }
        for (int t0 = 1; t0 <= N2; t0++){
            result += w1[n][N1+t0].getWeight()*gradient.getElementdYdW1AtIndex(t-t0, i, j);
        }
        return result;
    }

    //tu pytanie czy nie powinienem brac tego dZdW1 juz obliczonego, po chuj to liczyc w nieskonocznosc?
    public double compute_dVdW1(int n, int i, int j){
        double hiddLayerNeuronZt = network.getHiddLayerNeuronZt(n);
//        double result = MathUtils.getDerivResult(hiddLayerNeuronZt, ACTIVATE_FUNCTION)*compute_dZdW1(t, n, i, j);
        double result = MathUtils.getDerivResult(hiddLayerNeuronZt, ACTIVATE_FUNCTION)*gradient.getCurrent_dZdW1(i, j, n);
        return result;
    }

    // tu tez
    public double compute_dYdW1(int i, int j){
        Dendrite[] w2 = network.getOutLayer().getLayerDendrites();
        double result = 0.0;
        for (int n = 1; n <= HIDD_NEURONS; n++){
//            result += w2[n].getWeight()*compute_dVdW1(t, n, i, j);
            result += w2[n].getWeight()*gradient.getCurrent_dVdW1(i, j, n);
        }
        return result;
    }

    // i tu
    public double compute_dEdW1(int t, int i, int j){
//        double result = (netTeacher.getNetOutput(t) - netTeacher.getDemandOutput(t))*compute_dYdW1(t, i, j);
        double result = (netTeacher.getNetOutput(t) - netTeacher.getDemandOutput(t))*gradient.getElementdYdW1AtIndex(t, i, j);
        return result;
    }
}
