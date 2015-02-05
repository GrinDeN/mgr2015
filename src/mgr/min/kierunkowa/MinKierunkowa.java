package mgr.min.kierunkowa;

import mgr.config.Config;
import mgr.teacher.RecursiveNetworkTeacher;

import java.util.ArrayList;
import java.util.Arrays;

public class MinKierunkowa {

    private static final double START_ZERO = 0.0;
    private static final double EPS = 1E-5;

    private double firstParam;
    private double secParam;

    private RecursiveNetworkTeacher recursiveTeacher;

    private double[] startingWeights;
    private double[] candidateWeights;
    private double[] sumOfStartingAndCandidates;

    private ArrayList<Double> currentPWk;

    public MinKierunkowa(RecursiveNetworkTeacher teacher){
        this.recursiveTeacher = teacher;
        this.firstParam = START_ZERO;
        this.secParam = EPS;
        this.startingWeights = new double[Config.NUM_OF_WEIGHTS];
        this.candidateWeights = new double[Config.NUM_OF_WEIGHTS];
        this.sumOfStartingAndCandidates = new double[Config.NUM_OF_WEIGHTS];
        this.currentPWk = new ArrayList<Double>();
    }

    public void setCurrentPWk(ArrayList<Double> pwk){
        this.currentPWk.clear();
        this.currentPWk.addAll(pwk);
    }

    public double getParamOfMinKierunkowa(double[] currentStartingWeights) throws Exception{
        setStartingWeights(currentStartingWeights);
        resetParamsToBeginningValues();
        double ff1 = goForwardMinKierunkowa(this.firstParam);
        double ff2 = goForwardMinKierunkowa(this.secParam);
        // gdy od razu wspolczynnik bledu rosnie to przerwij
        // Reset kierunku czyli: p(k)=-gW(k) (kierunek = -gradient)
        // i znowu wchodzimy w te funkcje liczac juz dla tego nowego kierunku
        if (ff2 > ff1){
//            this.resetKierunek = true;
            System.out.println("Resetuje kierunek");
            return -2000;
        }
        // jesli blad maleje to poszerzaj
        while (ff1 > ff2) {
            firstParam = secParam;
            secParam = 2 * secParam;
            ff1 = goForwardMinKierunkowa(this.firstParam);
            ff2 = goForwardMinKierunkowa(this.secParam);
        }
        double newStep = ZlotyPodzial.getMinimum(this, firstParam, secParam);
        return newStep;
    }

    private void setStartingWeights(double[] weights){
        this.startingWeights = Arrays.copyOf(weights, weights.length);
    }

    private void resetParamsToBeginningValues(){
        this.firstParam = START_ZERO;
        this.secParam = EPS;
    }

    //TODO obliczenia bledu
    public double goForwardMinKierunkowa(double step) throws Exception{
        double currentError;
        makeCandidateWeights(step);
        makeSumOfStartingAndCandidatesWeights();
        currentError = recursiveTeacher.getErrorOfNetwork(sumOfStartingAndCandidates, false);
//        double[] input;
//        for (int t = Config.S; t <= Config.P; t++){
//            if (t == Config.S){
////                input = builder.build(t, demandValues.get(t));
//            } else {
//                input = builder.build(t, network.getCurrentOutput());
//            }
////            network.setNetworkInput(input);
//            network.calculateOutput();
//        }
        return currentError;
    }

    private void makeCandidateWeights(double step){
        for (int index = 0; index < candidateWeights.length; index++){
            candidateWeights[index] = step*currentPWk.get(index);
        }
    }

    private void makeSumOfStartingAndCandidatesWeights(){
        for (int index = 0; index < candidateWeights.length; index++){
            sumOfStartingAndCandidates[index] = startingWeights[index]+candidateWeights[index];
        }
    }

//    public void updateWeightsInInnerNetwork(double[] weights){
//        this.network.updateWeightsWithoutPrevious(weights);
//    }

}
