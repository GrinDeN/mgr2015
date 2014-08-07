package mgr.network;

import mgr.math.utils.ActivateFunc;
import mgr.math.utils.MathUtils;

import java.util.ArrayList;

public class Neuron{

    private double inputWeigthSum;
    private double output;
    private String name;
    private double[] input;

    private ArrayList<Dendrite> connections;

    private static int neuronCount = 0;

    public Neuron(){
        Neuron.neuronCount++;
        this.setName("Neuron " + neuronCount);
        this.connections = new ArrayList<Dendrite>();
    }


    public String toString(){
        return name;
    }

    private void setName(String name){
        this.name = name;
    }

    private void calculateInputWeigthSum(){
        this.inputWeigthSum = 0;
        double sumResult = 0;
        Dendrite[] connsArray = new Dendrite[this.connections.size()];
        for (Dendrite den : this.connections) System.out.println("Waga dendrytu: " + den.getWeight());
        if (input.length != connsArray.length){
            throw new ArrayStoreException(
                    "Niewłaściwy rozmiar tablic - input: " + input.length
                            + " Dendrite: " + connsArray.length);
        }
        this.connections.toArray(connsArray);
        for (int i = 0; i < input.length; i++) {
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

    public int getConnectionsSize(){
        return this.connections.size();
    }

    public void removeAllConnetions(){
        this.connections.removeAll(connections);
    }

    public ArrayList<Dendrite> getAllConnections(){
        return this.connections;
    }

    public double getConnWeigthAtIndex(int index){
        return this.connections.get(index).getWeight();
    }

    public double getFinalOutput(ActivateFunc activEnum){
        this.calculateInputWeigthSum();
        double funcResult = MathUtils.getMainResult(this.getInputWeigthSum(), activEnum);
        this.setOutput(funcResult);
        return this.getOutput();
    }

    public void setInput(double[] input){
        this.input = new double[input.length];
        this.input = input.clone();
    }

}
