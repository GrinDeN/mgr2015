package mgr.network;

import static mgr.config.Config.INPUT_SIZE;

public class HiddenLayer extends Layer{

    private double[] outputArray;

    public HiddenLayer(int neurons){
        this.numOfNeurons = neurons;
        this.neurons = new Neuron[this.numOfNeurons + 1]; // counting from 1
        this.outputArray = new double[this.numOfNeurons + 1]; //place for the BIAS
        this.name = "Warstwa ukryta";
    }


    @Override
    public void initialize(){
        for (int i = 1; i < this.getNumOfNeurons()+1; i++){
            this.neurons[i] = new Neuron();
            for (int j = 0; j < INPUT_SIZE; j++){
                this.neurons[i].addConnection(new Dendrite(this.neurons[i]));   //with BIAS connection
            }
        }
    }

    public double[] getCalculateOutputArray(){
        this.outputArray[0] = BIAS;     //setting the BIAS to further layer
        for (int i = 1; i < this.getNumOfNeurons()+1; i++){
            this.neurons[i].setInput(inputToLayer);
            this.setOutputValue(i, this.neurons[i].getHiddenNeuronResult(ACTIVATE_FUNCTION));
        }
        return this.outputArray;
    }

    private void setOutputValue(int index, double value){
        this.outputArray[index] = value;
    }

    @Override
    public void updateWeights(){
        // TODO Auto-generated method stub

    }
}
