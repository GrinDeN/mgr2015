package mgr.algorithm;

import mgr.input.builder.ParamPair;

import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args) throws Exception{
        SwarmEnum swarmAlg = SwarmEnum.WOLF;
        AlgTester algTester = new AlgTester(swarmAlg);
        String filename = "daneucz2000.txt";
        ArrayList<ParamPair> params = new ArrayList<ParamPair>();
        params.add(new ParamPair(1, 2));
        params.add(new ParamPair(2));
        algTester.test(filename, params);
    }
}
