package mgr.input.builder;

import mgr.config.Config;

import java.util.ArrayList;

import static mgr.config.Config.*;

public class RecursiveInputBuilder{

    private int currentIter;
    private ArrayList<ArrayList<Double>> dataList;
    private ArrayList<ParamPair> inputParamList;
    private ArrayList<Double> networkOutputs;
    private ArrayList<Double> startingOutputs;
    private double[] input;
    private int currentIndex;
    private double currentNetOutput;

    public RecursiveInputBuilder(ArrayList<ArrayList<Double>> dataList, ArrayList<ParamPair> params, ArrayList<Double> currentOutputs){
        this.currentIter = 0;
        this.currentIndex = 0;

        this.dataList = new ArrayList<ArrayList<Double>>();
        this.dataList.addAll(dataList);

        this.inputParamList = new ArrayList<ParamPair>();
        this.inputParamList.addAll(params);
        this.input = new double[INPUT_SIZE];

        this.startingOutputs = new ArrayList<Double>();
        this.startingOutputs.addAll(currentOutputs);

        this.networkOutputs = new ArrayList<Double>();
        this.networkOutputs.addAll(startingOutputs);
    }


    public double[] build(int iter, double netOutputValue){
        this.currentIndex = 0;
        setCurrentIter(iter);
        addValueToNetOutputsIfNotFirstIter(netOutputValue);
        buildInput();
        return getInput();
    }

    private void setCurrentIter(int iter){
        this.currentIter = iter;
    }

    private void addValueToNetOutputsIfNotFirstIter(double value){
        if (currentIter != Config.S){
            addValueToNetworkOutputs(value);
        }
    }

    private void addValueToNetworkOutputs(double value){
        this.networkOutputs.add(value);
    }

    private void buildInput(){
        addBIASToInput();
        addValuesToInputFromDataFile();
        addValuesToInputFromNetworkOutputs();
    }

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
            input[currentIndex] = networkOutputs.get(currentIter-delay);
            currentIndex++;
        }
    }

    private ParamPair getNetworkOutputs(){
        return inputParamList.get(inputParamList.size()-1);
    }

    public void resetNetOutputs(){
        this.networkOutputs.clear();
        this.networkOutputs.addAll(startingOutputs);
    }

    public void addStartingOutputsToNetOutputs(){
        this.networkOutputs.clear();
        this.networkOutputs.addAll(startingOutputs);
    }

//    public ArrayList<Double> getInput(){
//        return this.input;
//    }
    private double[] getInput(){
        return this.input;
    }

}
