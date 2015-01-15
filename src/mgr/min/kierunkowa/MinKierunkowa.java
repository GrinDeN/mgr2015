package mgr.min.kierunkowa;

import mgr.config.Config;
import mgr.input.builder.RecursiveInputBuilder;
import mgr.network.Network;

import java.util.ArrayList;
import java.util.Arrays;

public class MinKierunkowa {

    private static final double EPS = 1E-5;

    private Network network;
    private RecursiveInputBuilder inputBuilder;
    private double firstParam;
    private double secParam;

    private double[] startingWeights;

    private ArrayList<Double> currentPWk;

    public MinKierunkowa(RecursiveInputBuilder inputBuilder){
        this.network = new Network();
        this.inputBuilder = inputBuilder;
        this.firstParam = 0.0;
        this.secParam = EPS;

        this.startingWeights = new double[Config.NUM_OF_WEIGHTS];

        this.currentPWk = new ArrayList<Double>();
    }

    public void setCurrentPWk(ArrayList<Double> pwk){
        this.currentPWk.clear();
        this.currentPWk.addAll(pwk);
    }

    public double goForwardMinKierunkowa(double step){
        double currentError = 0.0;
        setStartingWeightsAsCurrent();
        double[] candidateWeights = makeCandidateWeights(step);
        updateWeightsInInnerNetwork(candidateWeights);
        for (int t = Config.S; t <= Config.P; t++){
            double[] input = inputBuilder.build(t, network.getCurrentOutput());
            network.setNetworkInput(input);
            network.calculateOutput();
        }

        return currentError;
    }

    private double[] makeCandidateWeights(double step){
        double[] weightsValues = new double[Config.NUM_OF_WEIGHTS];
        for (int index = 0; index < weightsValues.length; index++){
            weightsValues[index] = step*currentPWk.get(index);
        }
        return weightsValues;
    }

    public double getParamOfMinKierunkowa(){
        return 0.0;
    }

    private void setStartingWeightsAsCurrent(){
//        setWeightsToInnerNetwork(this.startingWeights);
        this.network.setWeights(this.startingWeights);
    }

//    private void setWeightsToInnerNetwork(double[] weights){
//        this.network.setWeights(weights);
//    }

    public void updateWeightsInInnerNetwork(double[] weights){
        this.network.updateWeights(weights);
    }

    private void setStartingWeights(double[] weights){
        this.startingWeights = Arrays.copyOf(weights, weights.length);
    }

}
