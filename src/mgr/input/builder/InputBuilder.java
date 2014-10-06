package mgr.input.builder;

import java.util.ArrayList;

import static mgr.config.Config.BIAS_VALUE;

public class InputBuilder {

    private int currentIter;
    private ArrayList<ArrayList<Double>> dataList;
    private ArrayList<ParamPair> inputParamList;
    private ArrayList<Double> networkOutputs;
    private ArrayList<Double> input;

    public InputBuilder(ArrayList<ArrayList<Double>> dataList, ArrayList<ParamPair> params){
        this.currentIter = 0;
        this.dataList = dataList;
        this.inputParamList = params;
        this.input = new ArrayList<Double>();
        this.networkOutputs = null;
    }

    public void build(int iter, ArrayList<Double> networkOutputs){
        setCurrentIter(iter);
        setNetworkOutputs(networkOutputs);
        addBIASToInput();
        addValuesToInputFromDataFile();
        addValuesToInputFromNetworkOutputs();
    }

    private void setCurrentIter(int iter){
        this.currentIter = iter;
    }

    private void setNetworkOutputs(ArrayList<Double> outputValues){
        this.networkOutputs = null;
        this.networkOutputs = outputValues;
    }

    private void addBIASToInput(){
        input.add(BIAS_VALUE);
    }

    private void addValuesToInputFromDataFile(){
        int numOfPairsWithoutDemand = getNumOfPairsWithoutDemand();
        for (int pairCounter = 0; pairCounter < numOfPairsWithoutDemand; pairCounter++){
            ParamPair pair = inputParamList.get(pairCounter);
            int firstParam = pair.getFirstValue();
            int secParam = pair.getSecondValue();
            ArrayList<Double> currentColumn = dataList.get(pairCounter);
            for (int delay = firstParam; delay <= secParam; delay++){
                input.add(currentColumn.get(currentIter-delay));
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
            input.add(networkOutputs.get(currentIter-delay));
        }
    }

    private ParamPair getNetworkOutputs(){
        return inputParamList.get(inputParamList.size()-1);
    }

    public ArrayList<Double> getInput(){
        return this.input;
    }
}
