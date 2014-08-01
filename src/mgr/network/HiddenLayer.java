package mgr.network;

public class HiddenLayer extends Layer{

    private double[] outputArray;

    public HiddenLayer(int neurons){
        this.numOfNeurons = neurons;
        this.neurons = new Neuron[this.numOfNeurons + 1]; // numeracja od 1
        this.outputArray = new double[this.numOfNeurons + 1]; // bias
        this.name = "Warstwa ukryta";
    }


    @Override
    public void initialize(){
        for (int i = 1; i < this.neurons.length; i++){
            this.neurons[i].setInput(inputToLayer);
            for (int j = 0; j < this.neurons[i].getConnectionsSize(); j++){
                this.neurons[i].addConnection(new Dendrite(this.neurons[i]));
            }
        }
    }

    public void calculateOutputArray(){
        this.outputArray[0] = BIAS;
        for (int i = 1; i < this.neurons.length; i++){
            setOutputElement(i, this.neurons[i].getFinalOutput(ACTIVATE_FUNCTION));
        }
    }

    private void setOutputElement(int index, double value){
        this.outputArray[index] = value;
    }

    public double[] getOutputArray(){
        return this.outputArray;
    }

    @Override
    public void updateWeights(){
        // TODO Auto-generated method stub

    }
}
