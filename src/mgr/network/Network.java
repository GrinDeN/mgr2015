package mgr.network;

import java.util.ArrayList;
import java.util.Arrays;

import static mgr.config.Config.*;

public class Network {

    private ArrayList<Layer> layers;
    private HiddenLayer hiddLayer;
    private OutputLayer outLayer;
    private double[] networkInput;
    private double currentOutput;
    private double[] weights;
    private double[] previousWeights;

    public Network(){
        this.networkInput = new double[INPUT_SIZE];
        this.layers = new ArrayList<Layer>(NUM_OF_LAYERS);
        this.hiddLayer = new HiddenLayer(HIDD_NEURONS);
        this.outLayer = new OutputLayer(OUT_NEURONS, this.hiddLayer);
        this.weights = new double[NUM_OF_WEIGHTS];
        this.previousWeights = new double[NUM_OF_WEIGHTS];
        this.addLayersToList();
        this.initialize();
    }

    private void addLayersToList(){
        this.layers.add(this.hiddLayer);
        this.layers.add(this.outLayer);
    }

    private void initialize(){
        for (Layer eachLayer : layers){
            eachLayer.initialize();
        }
    }

    public void updateWeights(double[] newWeights){
        getWeights();
        setPreviousWeights();
        for (Layer eachLayer : layers){// kolejnosc ma znaczenie?
            eachLayer.updateWeights(newWeights);
        }
    }

    public void updateWeightsWithoutPrevious(double[] newWeights){
        for (Layer eachLayer : layers){// kolejnosc ma znaczenie?
            eachLayer.updateWeights(newWeights);
        }
    }

    public void setWeights(double[] weights){
        getWeights();
        setPreviousWeights();
        for (Layer eachLayer : layers){
            eachLayer.setWeights(weights);
        }
    }

    public void setPreviousWeights(){
        System.arraycopy(this.weights, 0, this.previousWeights, 0, this.weights.length);
    }

    public double[] getWeights(){
        int weightCounter = 0;
        for (Layer eachLayer : layers){
            double[] layerWeights = eachLayer.getWeights();
            for (int i = 0; i < layerWeights.length; i++) {
                this.weights[weightCounter] = layerWeights[i];
                weightCounter++;
            }
        }
        return this.weights;
    }

    public double[] getPreviousWeights(){
        return this.previousWeights;
    }

    public void setNetworkInput(double[] input){
        this.networkInput = Arrays.copyOf(input, input.length);
    }

    public void calculateOutput(){
        this.hiddLayer.setInput(networkInput);
        this.outLayer.setInput(this.hiddLayer.getCalculateOutputArray());
        this.currentOutput = this.outLayer.getCalculateOutput();
//        return this.currentOutput;
    }

    public double getCurrentOutput(){
        return this.currentOutput;
    }

    public HiddenLayer getHiddLayer(){
        return hiddLayer;
    }

    public OutputLayer getOutLayer(){
        return outLayer;
    }

    public double getHiddLayerNeuronZt(int neuron){
        return hiddLayer.getNeuronZt(neuron);
    }

    public double[] getOutputLayerInput(){
        return outLayer.getInputToLayer();
    }

    public double[] getHiddLayerInput(){
        return hiddLayer.getInputToLayer();
    }
}
