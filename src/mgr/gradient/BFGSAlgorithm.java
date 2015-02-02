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
        this.mainOuterTeacher.setGradient(gradient);

        this.innerNetwork = new Network();
        this.innerTeacher = new RecursiveNetworkTeacher(innerNetwork, mainOuterTeacher.getFilenameOfData(), mainOuterTeacher.getParams());
        this.minKierunkowa = new MinKierunkowa(innerTeacher);
    }

    public void getMinimumError() throws Exception{
        double step;
        double[] updates;
        double[][] vk;
        for (int t = 0; t <= Config.BFGS_ITERATIONS; t++) {
            mainOuterTeacher.getErrorOfNetwork(null);
            mainOuterTeacher.printActualErrorNetwork(t);
            gradient.setCurrent_gW();
            bfgs.getVkMatrix(t);
            vk = bfgs.getVk();
//            System.out.println(MatrixUtils.matrixToString(Vk));
//            System.out.println("NO CO DO KURWY Z TA MACIERZA:");
//            System.out.println(vk[1][1]);
//            for (int i = 0; i < vk.length; i++) {
//                for (int j = 0; j < vk[i].length; j++) {
//                    System.out.print(vk[i][j] + " ");
//                }
//                System.out.println();
//            }
            gradient.setNewPwk_BFGS(vk);
//            System.out.println(gradient.getpWk());
            minKierunkowa.setCurrentPWk(gradient.getpWk());
            step = minKierunkowa.getParamOfMinKierunkowa(mainOuterTeacher.getNetworkWeights());
            if (step == -2000){ //blad
                gradient.setPwkAsMinusGradient();
                minKierunkowa.setCurrentPWk(gradient.getpWk());
                step = minKierunkowa.getParamOfMinKierunkowa(mainOuterTeacher.getNetworkWeights());
            }
            updates = setNewValuesForWeightsUpdate(step, gradient.getpWk());
            mainOuterTeacher.updateWeightsInNetwork(updates);
//            mainOuterTeacher.getErrorOfNetwork(null);
//            mainOuterTeacher.printActualErrorNetwork(t);
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
