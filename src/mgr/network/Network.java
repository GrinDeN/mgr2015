package mgr.network;

import java.util.ArrayList;
import java.util.Arrays;

import static mgr.config.Config.*;

/**
 * Created by Lukasz on 2014-08-07.
 */
public class Network {

    private ArrayList<Layer> layers;
    private HiddenLayer hiddLayer;
    private OutputLayer outLayer;
    private double[] networkInput;

    public Network(final double[] input){
        this.networkInput = Arrays.copyOf(input, input.length);
        this.layers = new ArrayList<Layer>(NUM_OF_LAYERS);
        this.hiddLayer = new HiddenLayer(HIDD_NEURONS);
        this.outLayer = new OutputLayer(OUT_NEURONS, this.hiddLayer);
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

    public void setNetworkInput(double[] input){
        this.networkInput = Arrays.copyOf(input, input.length);
    }

    public double calculateOutput(int iter){
        this.hiddLayer.setInput(networkInput);
        this.outLayer.setInput(this.hiddLayer.getCalculateOutputArray(iter));
        return this.outLayer.getCalculateOutput();
    }

    public HiddenLayer getHiddLayer(){
        return hiddLayer;
    }

    public OutputLayer getOutLayer(){
        return outLayer;
    }

    public double getHiddLayerNeuronZt(int iter, int neuron){
        return hiddLayer.getNeuronZt(iter, neuron);
    }

    public double[] getOutputLayerInputAtIndex(int iter){
        return this.outLayer.getInputToLayer(iter);
    }

}
