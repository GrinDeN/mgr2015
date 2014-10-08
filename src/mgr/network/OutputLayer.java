package mgr.network;

import static mgr.config.Config.BIAS_POSITION;

public class OutputLayer extends Layer {

    private HiddenLayer hiddLayer;
    private double outputOfLayer;
    private Dendrite[] layerDendrites;

    public OutputLayer(int neurons, HiddenLayer hiddLayer){
        this.numOfNeurons = neurons;
        this.hiddLayer = hiddLayer;
        this.neurons = new Neuron[this.numOfNeurons+1]; // numeracja od 1
        this.layerDendrites = new Dendrite[hiddLayer.getNumOfNeurons()+1];
        this.name = "Warstwa wyjściowa";
    }

    @Override
    public void initialize(){
        for (int neur = 1; neur < this.getNumOfNeurons()+1; neur++) {
            this.neurons[neur] = new Neuron();
            for (int k = 0; k <= hiddLayer.getNumOfNeurons(); k++) {
                if (k == BIAS_POSITION){
                    Dendrite BIASdendrite = new Dendrite(this.neurons[neur]);
                    this.neurons[neur].addConnection(BIASdendrite);
                    this.layerDendrites[k] = BIASdendrite;
                } else {
                    Dendrite hiddNeuronDendrite = new Dendrite(hiddLayer.getNeuronAtIndex(k), this.neurons[neur]);
                    this.neurons[neur].addConnection(hiddNeuronDendrite);
                    this.layerDendrites[k] = hiddNeuronDendrite;
                }
            }
        }
    }

    public Dendrite[] getLayerDendrites(){
        return this.layerDendrites;
    }

    @Override
    public void updateWeights(){

    }

    public double getCalculateOutput(){
        this.outputOfLayer = 0;
        for (int neur = 1; neur < this.getNumOfNeurons()+1; neur++) {
            this.neurons[neur].setCurrentInput(inputToLayer);
            this.outputOfLayer += this.neurons[neur].getOutputNeuronResult();
        }
        return this.outputOfLayer;
    }

}
