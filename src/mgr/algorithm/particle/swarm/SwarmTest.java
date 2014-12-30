package mgr.algorithm.particle.swarm;

import mgr.config.Config;
import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.NetworkTeacher;

import java.util.ArrayList;

public class SwarmTest {
    public static void main(String[] args) throws Exception{
        Network net = new Network();
        String filename = "dane_spr_grad.txt";
        ArrayList<ParamPair> params = new ArrayList<ParamPair>();
        params.add(new ParamPair(1, 2));
        params.add(new ParamPair(1));
        NetworkTeacher netTeacher = new NetworkTeacher(net, filename, params);
        Swarm swarm = new Swarm(netTeacher, 100, 20, Config.NUM_OF_WEIGHTS);
        swarm.getMinimum();
    }
}
