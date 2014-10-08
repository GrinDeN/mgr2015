package mgr.network;

import mgr.math.utils.ActivateFunc;
import mgr.math.utils.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static mgr.config.Config.DEBUG;

public class Neuron{

    private double inputWeigthSum;
    private double output;
    private String name;
    private double[] currentInput;

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
        if (DEBUG) {
            for (Dendrite den : this.connections)
                System.out.println("Waga dendrytu: " + den.getWeight());
        }
        if (currentInput.length != connsArray.length){
            throw new ArrayStoreException(
                    "Niewłaściwy rozmiar tablic - currentInput: " + currentInput.length
                            + " Dendrite: " + connsArray.length);
        }
        this.connections.toArray(connsArray);
        for (int i = 0; i < currentInput.length; i++) {
            sumResult += currentInput[i] * connsArray[i].getWeight();
        }
        this.inputWeigthSum = sumResult;
    }

    private void setOutput(double result){
        this.output = result;
    }

    private double getOutput(){
        return this.output;
    }

    protected double getInputWeigthSum(){
        return this.inputWeigthSum;
    }

    public void addConnection(Dendrite conn){
        this.connections.add(conn);
    }

//    public int getConnectionsSize(){
//        return this.connections.size();
//    }
//
//    public void removeAllConnetions(){
//        this.connections.removeAll(connections);
//    }
//
//    public ArrayList<Dendrite> getAllConnections(){
//        return this.connections;
//    }

    public double getConnWeigthAtIndex(int index){
        return this.connections.get(index).getWeight();
    }

    public double getHiddenNeuronResult(ActivateFunc mathActiveFunc){
        this.calculateInputWeigthSum();
        double funcResult = MathUtils.getMainResult(this.getInputWeigthSum(), mathActiveFunc);
        if (DEBUG){
            System.out.println("Suma iloczynu wag i wejscia dla neuronu " + toString() + " : " + this.getInputWeigthSum());
            System.out.println("Wynik funkcji_aktywacji(suma): " + funcResult);
        }
        this.setOutput(funcResult);
        return this.getOutput();
    }

    public double getOutputNeuronResult(){
        double neuronSum = 0;
        double elemOfSum;
        for (int weigth = 0; weigth < this.currentInput.length; weigth++){
            elemOfSum = this.getConnWeigthAtIndex(weigth)*this.currentInput[weigth];
            if (DEBUG){
//                System.out.println("Iloczyn wagi i wejścia w warstwie wyjściowej dla wagi " + weigth + " = " + elemOfSum);
                System.out.println("Wejscie neuronu na pozycji " +weigth+ ": " +this.currentInput[weigth]);
                System.out.println("Waga polaczenia na pozycji " +weigth+ ": " +this.getConnWeigthAtIndex(weigth));
            }
            neuronSum += elemOfSum;
        }
        return neuronSum;
    }

    public void setCurrentInput(double[] currentInput){
        this.currentInput = Arrays.copyOf(currentInput, currentInput.length);
    }

}
