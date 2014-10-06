package mgr.input.builder;

import java.util.ArrayList;
import static mgr.config.Config.BIAS_POSITION;
import static mgr.config.Config.BIAS_VALUE;

/**
 * Created by Lukasz on 2014-10-05.
 */
public class InputBuilder {

    private int currentIter;
    private ArrayList<ArrayList<Double>> dataList;
    private ArrayList<ParamPair> inputParamList;
    private ArrayList<Double> networkOutputs;
    private double[] input;

    public  InputBuilder(int iter, ArrayList<ArrayList<Double>> dataList, ArrayList<ParamPair> params){
        this.currentIter = iter;
        this.dataList = dataList;
        this.inputParamList = params;
        int inputLength = calcInputLength();
        this.input = new double[inputLength];
        this.networkOutputs = null;
    }

    private int calcInputLength(){
        int inputLength = inputParamList.size();
        inputLength = inputLength + 1; //BIAS
        return inputLength;
    }

    public void build(){
        input[BIAS_POSITION] = BIAS_VALUE;
        int inputPositionCounter = BIAS_POSITION+1;
        int pairCounter = 0;
//        for (ParamPair pair : inputParamList){
//            int firstParam = pair.getFirstValue();
//            int secParam = pair.getSecondValue();
//            for (int i = firstParam; i <= secParam; i++){
//                input[inputPositionCounter]
//                inputPositionCounter++;
//            }
//            pairCounter++;
//        }
    }

    public double[] getInput(){
        return this.input;
    }

    public void setNetworkOutputs(ArrayList<Double> outputValues){
        this.networkOutputs = null;
        this.networkOutputs = outputValues;
    }
}
