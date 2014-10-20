package mgr.input.builder;

import mgr.config.Config;

import java.util.ArrayList;

import static mgr.config.Config.*;

public class InputBuilder {

    private int currentIter;
    private ArrayList<ArrayList<Double>> dataList;
    private ArrayList<ParamPair> inputParamList;
    private ArrayList<Double> networkOutputs;
//    private ArrayList<Double> input;
    private double[] input;
    private int currentIndex;

    public InputBuilder(ArrayList<ArrayList<Double>> dataList, ArrayList<ParamPair> params, ArrayList<Double> currentOutputs){
        this.currentIter = 0;
        this.currentIndex = 0;
        this.dataList = new ArrayList<ArrayList<Double>>();
        this.dataList.addAll(dataList);
        this.inputParamList = new ArrayList<ParamPair>();
        this.inputParamList.addAll(params);
//        this.input = new ArrayList<Double>();
        this.input = new double[INPUT_SIZE];
        this.networkOutputs = new ArrayList<Double>();
        this.networkOutputs.addAll(currentOutputs);
    }

    public void build(int iter, double netOutputValue){
        this.currentIndex = 0;
        setCurrentIter(iter);
//        setNetworkOutputs(networkOutputs);
        addValueToNetOutputsIfNotFirstIter(netOutputValue);
        addBIASToInput();
        addValuesToInputFromDataFile();
        addValuesToInputFromNetworkOutputs();
    }

    private void setCurrentIter(int iter){
        this.currentIter = iter;
    }

//    private void setNetworkOutputs(ArrayList<Double> outputValues){
//        this.networkOutputs = null;
//        this.networkOutputs = outputValues;
//    }

    private void addValueToNetOutputsIfNotFirstIter(double value){
        if (currentIter != Config.S){
            addValueToNetworkOutputs(value);
        }
    }

    private void addValueToNetworkOutputs(double value){
        this.networkOutputs.add(value);
    }

//    private void addBIASToInput(){
//        input.add(BIAS_VALUE);
//    }
    private void addBIASToInput(){
        input[BIAS_POSITION] = BIAS_VALUE;
        currentIndex++;
    }

    private void addValuesToInputFromDataFile(){
        int numOfPairsWithoutDemand = getNumOfPairsWithoutDemand();
        for (int pairCounter = 0; pairCounter < numOfPairsWithoutDemand; pairCounter++){
            ParamPair pair = inputParamList.get(pairCounter);
            int firstParam = pair.getFirstValue();
            int secParam = pair.getSecondValue();
            ArrayList<Double> currentColumn = dataList.get(pairCounter);
            for (int delay = firstParam; delay <= secParam; delay++){
//                input.add(currentColumn.get(currentIter-delay));
                input[currentIndex] = currentColumn.get(currentIter-delay);
                currentIndex++;
            }
        }
    }

    private int getNumOfPairsWithoutDemand(){
        return inputParamList.size()-1;
    }

    private void addValuesToInputFromNetworkOutputs(){
        ParamPair networkOutputsPairOfParams = getNetworkOutputs();
        int firstParam = networkOutputsPairOfParams.getFirstValue();
        int secParam = networkOutputsPairOfParams.getSecondValue();
        for (int delay = firstParam; delay <= secParam; delay++){
//            input.add(networkOutputs.get(currentIter-delay));
            input[currentIndex] = networkOutputs.get(currentIter-delay);
            currentIndex++;
        }
    }

    private ParamPair getNetworkOutputs(){
        return inputParamList.get(inputParamList.size()-1);
    }

//    public ArrayList<Double> getInput(){
//        return this.input;
//    }
    public double[] getInput(){
        return this.input;
    }
}
