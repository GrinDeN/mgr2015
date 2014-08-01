package mgr.network;

/**
 * Created by Lukasz on 2014-08-01.
 */
public class OutputLayer extends Layer {

    private HiddenLayer hiddLayer;
    private double outputOfLayer;

    public OutputLayer(int neurons, HiddenLayer hiddLayer){
        this.numOfNeurons = neurons;
        this.hiddLayer = hiddLayer;
        this.neurons = new Neuron[this.numOfNeurons + 1]; // numeracja od 1
        this.name = "Warstwa wyjściowa";
    }

    @Override
    public void initialize(){
        for (int i = 1; i < this.neurons.length; i++) {
            this.neurons[i].setInput(inputToLayer);
            for (int j = 1; j < hiddLayer.getNumOfNeurons(); j++) {
                for (int k = 0; k < this.neurons[i].getConnectionsSize(); k++) {
                    this.neurons[i].addConnection(new Dendrite(hiddLayer.getNeuronAtIndex(k), this.neurons[i]));
                }
            }
        }
    }

    @Override
    public void updateWeights(){

    }

    public double calculateOutput(){
        this.outputOfLayer = 0;
        for (int i = 0; i < this.inputToLayer.length; i++){
            for (int j = 0; j < this.neurons[i].getConnectionsSize(); j++) {
                this.outputOfLayer += this.inputToLayer[i]*this.neurons[i].getConnWeigthAtIndex(j);
            }
        }
        return this.outputOfLayer;
    }
}
