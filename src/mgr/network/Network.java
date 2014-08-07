package mgr.network;

import java.util.ArrayList;

import static mgr.config.Config.*;

/**
 * Created by Lukasz on 2014-08-07.
 */
public class Network {

    private ArrayList<Layer> layers;
    private HiddenLayer hiddLayer;
    private OutputLayer outLayer;

    public Network(){
        this.layers = new ArrayList<Layer>(NUM_OF_LAYERS);
        this.hiddLayer = new HiddenLayer(HIDD_NEURONS);
        this.outLayer = new OutputLayer(OUT_NEURONS, this.hiddLayer);
        this.addLayersToList();
        this.initialize();
    }

    private void addLayersToList(){
        this.layers.add(this.hiddLayer);
        this.layers.add(this.outLayer);
    }

    private void initialize(){
        for (Layer eachLayer : layers){
            eachLayer.initialize();
        }
    }

    public double calculateOutput(final double[] argValues){
        this.hiddLayer.setInput(argValues);
        this.outLayer.setInput(this.hiddLayer.calculateOutputArray());
        return this.outLayer.calculateOutput();
    }

}
