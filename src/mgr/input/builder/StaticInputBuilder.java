package mgr.input.builder;

import java.util.ArrayList;

import static mgr.config.Config.BIAS_POSITION;
import static mgr.config.Config.BIAS_VALUE;
import static mgr.config.Config.INPUT_SIZE;

public class StaticInputBuilder{

    private ArrayList<ArrayList<Double>> dataList;
    private int currentIter;
    private int currentIndex;
    private double[] input;

    public StaticInputBuilder(ArrayList<ArrayList<Double>> dataList){
        this.dataList = new ArrayList<ArrayList<Double>>();
        this.dataList.addAll(dataList);
        this.input = new double[INPUT_SIZE];
        this.currentIter = 0;
    }

    public double[] build(int iter){
        this.currentIndex = 0;
        setCurrentIter(iter);
        buildInput();
        return getInput();
    }

    private void setCurrentIter(int iteration){
        this.currentIter = iteration;
    }

    private void buildInput(){
        addBIASToInput();
        addValuesToInputFromDataFile();
    }

    private void addBIASToInput(){
        input[BIAS_POSITION] = BIAS_VALUE;
        currentIndex++;
    }

    private void addValuesToInputFromDataFile(){
        int numOfColsWithData = getNumberOfColsWithData();
        for (int i = 0; i < numOfColsWithData; i++){
            input[currentIndex] = dataList.get(numOfColsWithData).get(currentIter);
            currentIndex++;
        }
    }

    private int getNumberOfColsWithData(){
        return dataList.size()-1;
    }

    private double[] getInput(){
        return this.input;
    }
}
