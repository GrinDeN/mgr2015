package mgr.network;

import mgr.math.utils.ActivateFunc;

public abstract class Layer{

    public final double BIAS = 1.0;
    public final ActivateFunc ACTIVATE_FUNCTION = ActivateFunc.SIGMOID_BI;

    protected int numOfNeurons;
    protected Neuron[] neurons;
    protected String name;
    protected double[] inputToLayer;

    public abstract void initialize();

    protected double outputLayer;

    public abstract void updateWeights();

    public int getNumOfNeurons(){
        return this.numOfNeurons;
    }

    public String toString(){
        return this.name;
    }

    public void setInput(double[] inputLayer){
        this.inputToLayer = inputLayer.clone();
    }

    public Neuron getNeuronAtIndex(int index){
        return this.neurons[index];
    }
}
