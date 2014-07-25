package mgr.network;

public class Dendrite{

    private Neuron inputNeuron;
    private Neuron outputNeuron;
    private double weight;

    public void setWeight(double w){
        this.weight = w;
    }

    public double getWeight(){
        return this.weight;
    }

    public void setInputNeuron(Neuron input){
        this.inputNeuron = input;
    }

    public Neuron getInputNeuron(){
        return this.inputNeuron;
    }

    public void setOutputNeuron(Neuron output){
        this.outputNeuron = output;
    }

    public Neuron getOutputNeuron(){
        return this.outputNeuron;
    }
}
