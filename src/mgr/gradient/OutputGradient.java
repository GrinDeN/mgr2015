package mgr.gradient;

import mgr.math.utils.MathUtils;
import mgr.network.Dendrite;
import mgr.network.Network;
import mgr.teacher.RecursiveNetworkTeacher;

import static mgr.config.Config.*;

public class OutputGradient {

    private Network network;
    private RecursiveNetworkTeacher netTeacher;
    private Gradient gradient;

    public OutputGradient(Network net, RecursiveNetworkTeacher networkTeacher, Gradient grad){
        this.network = net;
        this.netTeacher = networkTeacher;
        this.gradient = grad;
    }

    public double compute_dZdW2(int t, int n, int i){
        Dendrite[][] w1 = network.getHiddLayer().getLayerDendrites();
        double result = 0.0;
        for (int t0 = 1; t0 <= N2; t0++){
            if (t-t0 <= S){
                result += w1[n][N1+t0].getWeight()*0;
            } else {
                result += w1[n][N1+t0].getWeight()*gradient.getElementdYdW2AtIndex(t-t0, i);
            }
        }
        return result;
    }

    //tu pytanie czy nie powinienem brac tego dZdW2 juz obliczonego, po chuj to liczyc w nieskonocznosc?
    public double compute_dVdW2(int n, int i){
        double hiddLayerNeuronZt = network.getHiddLayerNeuronZt(n);
//        double result = MathUtils.getDerivResult(hiddLayerNeuronZt, ACTIVATE_FUNCTION)*compute_dZdW2(t, n, i);
        double result = MathUtils.getDerivResult(hiddLayerNeuronZt, ACTIVATE_FUNCTION)*gradient.getCurrent_dZdW2(n, i);
        return result;
    }

    // to samo tu
    public double compute_dYdW2(int i){
        Dendrite[] w2 = network.getOutLayer().getLayerDendrites();
        double result = 0.0;
        double[] v = network.getOutputLayerInput();
        for (int n = 1; n <= HIDD_NEURONS; n++) {
//            result += w2[n].getWeight()*compute_dVdW2(t, n, i);
            result += w2[n].getWeight()*gradient.getCurrent_dVdW2(n, i);
        }
        result += v[i];
        return result;
    }

    //i tu?
    public double compute_dEdW2(int t, int i){
//        double result = (netTeacher.getNetOutput(t) - netTeacher.getDemandOutput(t))*compute_dYdW2(t, i);
        double result = (netTeacher.getNetworkOutputFromErrorCounter(t) - netTeacher.getDemandValueFromErrorCounter(t))*gradient.getElementdYdW2AtIndex(t, i);
        return result;
    }
}
