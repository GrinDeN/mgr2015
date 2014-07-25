package mgr.network;

import java.util.ArrayList;

import mgr.math.utils.ActivateFunc;
import mgr.math.utils.MathUtils;

public class Neuron{

    private double inputWeigthSum;
    private double output;
    private String name;
    private final int inputSize;
    private double[] input;

    private ArrayList<Dendrite> connections;

    private static int neuronCount = 0;

    public Neuron(int inputSize){
        this.inputSize = inputSize + 1; // miejsce na "1"
        this.input = new double[this.inputSize];
        this.connections = new ArrayList<Dendrite>();
        Neuron.neuronCount++;
        this.setName("Neuron " + neuronCount);
    }

    public String toString(){
        return name;
    }

    private void setName(String name){
        this.name = name;
    }

    private void setInputWeigthSum(){
        this.inputWeigthSum = 0;
        double sumResult = 0;
        Dendrite[] connsArray = new Dendrite[connections.size()];
        if (input.length != connsArray.length){
            throw new ArrayStoreException(
                    "Niewłaściwy rozmiar tablic - input: " + input.length
                            + " Dendrite: " + connsArray.length);
        }
        connections.toArray(connsArray);
        for (int i = 0; i < input.length; i++){
            sumResult += input[i] * connsArray[i].getWeight();
        }
        this.inputWeigthSum = sumResult;
    }

    private void setOutput(double result){
        this.output = result;
    }

    private double getOutput(){
        return this.output;
    }

    private double getInputWeigthSum(){
        return this.inputWeigthSum;
    }

    public void addConnection(Dendrite conn){
        this.connections.add(conn);
    }

    public void removeAllConnetions(){
        this.connections.removeAll(connections);
    }

    public ArrayList<Dendrite> getAllConnections(){
        return this.connections;
    }

    public double getFinalOutput(ActivateFunc activEnum){
        setInputWeigthSum();
        double funcResult = MathUtils.getMainResult(getInputWeigthSum(), activEnum);
        setOutput(funcResult);
        return getOutput();
    }

}
