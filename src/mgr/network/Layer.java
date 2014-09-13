package mgr.network;

import mgr.math.utils.ActivateFunc;

import java.util.Arrays;

public abstract class Layer{

    public final double BIAS = 1.0;
    public final ActivateFunc ACTIVATE_FUNCTION = ActivateFunc.SIGMOID_BI;

    protected int numOfNeurons;
    protected Neuron[] neurons;
    protected String name;
    protected double[] inputToLayer;

    public abstract void initialize();

    public abstract void updateWeights();

    public int getNumOfNeurons(){
        return this.numOfNeurons;
    }

    public String toString(){
        return this.name;
    }

    public void setInput(final double[] inputLayer){
        this.inputToLayer = Arrays.copyOf(inputLayer, inputLayer.length);
    }

    public Neuron getNeuronAtIndex(int index){
        return this.neurons[index];
    }
}
