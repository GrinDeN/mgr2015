package mgr.network;

import java.util.Arrays;

public abstract class Layer{

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

    protected double getNeuronZt(int iter, int neuron){
        return this.neurons[neuron].getInputWeigthSum(iter);
    }
}
