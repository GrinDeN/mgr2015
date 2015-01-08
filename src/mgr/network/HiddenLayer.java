package mgr.network;

import mgr.config.Config;

import static mgr.config.Config.*;

public class HiddenLayer extends Layer{

    private double[] outputArray;
    private Dendrite[][] layerDendrites;


    public HiddenLayer(int neurons){
        this.numOfNeurons = neurons;
        this.neurons = new Neuron[this.numOfNeurons + 1]; // counting from 1
        this.outputArray = new double[this.numOfNeurons + 1]; //place for the BIAS
        this.layerDendrites = new Dendrite[this.numOfNeurons+1][INPUT_SIZE];
        this.name = "Warstwa ukryta";
    }

    @Override
    public void initialize(){
        for (int i = 1; i <= this.getNumOfNeurons(); i++){
            this.neurons[i] = new Neuron();
            for (int j = 0; j < INPUT_SIZE; j++){
                Dendrite layerDendrite = new Dendrite(this.neurons[i]);
                this.neurons[i].addConnection(layerDendrite);   //with BIAS connection
                layerDendrites[i][j] = layerDendrite;
            }
        }
    }

    public double[] getCalculateOutputArray(){
        this.outputArray[BIAS_POSITION] = BIAS_VALUE;
        for (int i = 1; i <= this.getNumOfNeurons(); i++){
            this.neurons[i].setCurrentInput(inputToLayer);
            this.setOutputValue(i, this.neurons[i].getHiddenNeuronResult(ACTIVATE_FUNCTION));
        }
        return this.outputArray;
    }

    public Dendrite[][] getLayerDendrites(){
        return this.layerDendrites;
    }

    private void setOutputValue(int index, double value){
        this.outputArray[index] = value;
    }

    @Override
    public void updateWeights(double[] newWeights){
        int weightCounter = 0;
        for (int i = 1; i <= this.getNumOfNeurons(); i++){
            for (int j = 0; j < INPUT_SIZE; j++){
                layerDendrites[i][j].updateWeight(newWeights[weightCounter]);
                weightCounter++;
            }
        }
    }

    @Override
    public void setWeights(double[] weights){
        int weightCounter = 0;
        for (int i = 1; i <= this.getNumOfNeurons(); i++){
            for (int j = 0; j < INPUT_SIZE; j++){
                layerDendrites[i][j].setWeight(weights[weightCounter]);
                weightCounter++;
            }
        }
    }

    @Override
    public double[] getWeights(){
        int weightCounter = 0;
        double[] layerWeights = new double[Config.INPUT_SIZE*Config.HIDD_NEURONS];
        for (int i = 1; i <= this.getNumOfNeurons(); i++){
            for (int j = 0; j < INPUT_SIZE; j++){
                layerWeights[weightCounter] = layerDendrites[i][j].getWeight();
                weightCounter++;
            }
        }
        return layerWeights;
    }

}
