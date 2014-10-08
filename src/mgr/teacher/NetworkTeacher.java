package mgr.teacher;

import mgr.network.Network;

/**
 * Created by Lukasz on 2014-10-08.
 */
public class NetworkTeacher {

    private Network network;

    public NetworkTeacher(Network net){
        this.network = net;
    }

    public double getNetOutput(int t){
        return 0;
    }

    public double getDemandOutput(int t){
        return 0;
    }
}
