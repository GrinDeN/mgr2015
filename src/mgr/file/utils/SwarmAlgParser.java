package mgr.file.utils;

import mgr.algorithm.SwarmEnum;

public class SwarmAlgParser {

    public static SwarmEnum parseIntToSwarmAlg(int number){
        SwarmEnum swarm = SwarmEnum.PSO;
        switch (number){
            case 1:
                swarm = SwarmEnum.PSO;
                break;
            case 2:
                swarm = SwarmEnum.WOLF;
                break;
            case 3:
                swarm = SwarmEnum.BAT;
                break;
            case 4:
                swarm = SwarmEnum.ANT;
                break;
            case 5:
                swarm = SwarmEnum.FIREFLY;
                break;
            case 6:
                swarm = SwarmEnum.BEE;
                break;
            case 7:
                swarm = SwarmEnum.WEED;
                break;
        }
        return swarm;
    }
}
