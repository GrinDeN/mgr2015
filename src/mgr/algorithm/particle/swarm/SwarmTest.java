package mgr.algorithm.particle.swarm;

import mgr.config.Config;
import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.RecursiveNetworkTeacher;

import java.util.ArrayList;

public class SwarmTest {
    public static void main(String[] args) throws Exception{
        Network net = new Network();
        String filename = "dane_spr_grad.txt";
        ArrayList<ParamPair> params = new ArrayList<ParamPair>();
        params.add(new ParamPair(1, 2));
        params.add(new ParamPair(1));
        RecursiveNetworkTeacher netTeacher = new RecursiveNetworkTeacher(net, filename, params);
        Swarm swarm = new Swarm(netTeacher, 100, 30, Config.NUM_OF_WEIGHTS);
        long start = System.nanoTime();
        swarm.getMinimum();
        long end = System.nanoTime();
        double difference = (end - start)/1e6;
        System.out.println("Czas wykonania wynosi: " + difference + " ms.");
    }
}
