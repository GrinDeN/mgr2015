package mgr.network;

import java.util.Random;

public class Dendrite{

    private Neuron inputNeuron;
    private Neuron outputNeuron;
    private double weight;

    //dendrite between input and hidden neuron
    public Dendrite(Neuron outputN){
        this.outputNeuron = outputN;
        setRandomWeigth();
    }

    public Dendrite(Neuron inputN, Neuron outputN){
        this(outputN);
        this.inputNeuron = inputN;
    }

    public void setRandomWeigth(){
        Random rand = new Random();
        setWeight(rand.nextDouble());
    }

    public void setWeight(double w){
        this.weight = w;
    }

    public void updateWeight(double newWeight){
        this.weight += newWeight;
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
