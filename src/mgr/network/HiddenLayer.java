package mgr.network;

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

    public double[] getCalculateOutputArray(int iter){
        this.outputArray[BIAS_POSITION] = BIAS_VALUE;
        for (int i = 1; i <= this.getNumOfNeurons(); i++){
            this.neurons[i].setCurrentInput(inputToLayer);
            this.setOutputValue(i, this.neurons[i].getHiddenNeuronResult(iter, ACTIVATE_FUNCTION));
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
    public void updateWeights(){
        // TODO Auto-generated method stub

    }
}
