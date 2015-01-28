package mgr.gradient;

import mgr.config.Config;
import mgr.min.kierunkowa.MinKierunkowa;
import mgr.network.Network;
import mgr.teacher.RecursiveNetworkTeacher;

import java.util.ArrayList;

public class BFGSAlgorithm {

    private BFGS bfgs;
    private Gradient gradient;
    private RecursiveNetworkTeacher mainOuterTeacher;
    private RecursiveNetworkTeacher innerTeacher;
    private MinKierunkowa minKierunkowa;
    private Network innerNetwork;

    public BFGSAlgorithm(RecursiveNetworkTeacher outerTeacher){
        this.mainOuterTeacher = outerTeacher;
        this.gradient = new Gradient(mainOuterTeacher);
        this.bfgs = new BFGS(gradient);

        this.innerNetwork = new Network();
        this.innerTeacher = new RecursiveNetworkTeacher(innerNetwork, mainOuterTeacher.getFilenameOfData(), mainOuterTeacher.getParams());
        this.minKierunkowa = new MinKierunkowa(innerTeacher);
    }

    public void getMinimumError() throws Exception{
        double step;
        double[] updates;
        for (int t = 0; t <= Config.BFGS_ITERATIONS; t++) {
            gradient.computeAllGradients(t);
            gradient.setCurrent_gW();
            double[][] Vk = bfgs.getVkMatrix(t);
            gradient.setNewPwk_BFGS(Vk);
            minKierunkowa.setCurrentPWk(gradient.getpWk());
            step = minKierunkowa.getParamOfMinKierunkowa(mainOuterTeacher.getNetworkWeights());
            updates = setNewValuesForWeightsUpdate(step, gradient.getpWk());
            mainOuterTeacher.updateWeightsInNetwork(updates);
            mainOuterTeacher.getErrorOfNetwork(null);
            mainOuterTeacher.printActualErrorNetwork();
        }
    }

    private double[] setNewValuesForWeightsUpdate(double step, ArrayList<Double> pwk){
        double[] differenceToUpdate = new double[pwk.size()];
        for (int i = 0; i < differenceToUpdate.length; i++) {
            differenceToUpdate[i] = step*pwk.get(i);
        }
        return differenceToUpdate;
    }
}
