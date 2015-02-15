package mgr.algorithm;

import mgr.config.Config;
import mgr.file.utils.ConfigParser;
import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.Verifier;

import java.util.ArrayList;

import static mgr.config.Config.STATIC_NET;
import static mgr.config.Config.VERIFIER_ONLY;

public class MainTest {

    public static void main(String[] args) throws Exception{
//        String settings = "settings.txt";
        String settings = args[0];
        ConfigParser parser = new ConfigParser(settings);
        try {
            parser.parseSettings();
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        parser.saveSettingsToApp();
        Network net = new Network();
        String dataFilename;
        SwarmEnum swarmAlg = Config.swarmAlg;
        AlgTester algTester = new AlgTester();
        System.out.println("Program został uruchomiony, proszę czekać na wyniki...");
        if (STATIC_NET == false){
            ArrayList<ParamPair> params = new ArrayList<ParamPair>();
            params.addAll(Config.params);
//            params.add(new ParamPair(1, 2));
//            params.add(new ParamPair(2));
            if (VERIFIER_ONLY == true){
                String verifyFilename = "daneucz2000.txt";
//                String verifyFilename = "daneucz10000.dan";
                Verifier verifier = new Verifier(net, verifyFilename, params);
                double[] weights = new double[]{
                        -5.2740376633e-001,2.7135382652e-001,-7.0655302458e-001,-8.7158579532e-001,5.3968198808e-001,
                        2.4597523798e+000,1.5607815370e+000,-9.2384709429e-001,1.6830246479e+000,1.1148203308e+000,
                        -1.8876850398e+000,3.3095631413e+000,1.4756462913e+000,5.4160020749e+000,3.2871754722e+000,
                        -8.8840576255e-001,-1.5298878606e-001,1.1739839836e-002,2.0959552549e+000,-1.6510929355e+000,
                        2.3609068525e-001,-5.4276193263e-001,9.9407213723e-001,-2.6647345526e+000,1.4117756088e+000,
                        8.9973752710e-001,-4.5380753716e-002,-5.8211882247e-001,8.2481866281e-002,-1.6534435443e-001,
                        -8.5633864596e-002,4.1552714146e-001,-6.8373070355e-001,3.7882167902e-001,2.0569780378e-001,
                        1.4889850819e+000,-1.2238187540e+000,-1.5038233329e-001,5.6517152576e-002,2.4208385062e+000,1.5603895282e+000,-3.7242016363e-001,2.9730604126e+000
                };
                verifier.verify(weights);
                //dane weryfikacyjne
                String verifyFilename1 = "danewer2000.txt";
//                String verifyFilename1 = "danewer10000.dan";
                Verifier verifier1 = new Verifier(net, verifyFilename1, params);
                verifier1.verify(weights);
            } else {
                dataFilename = Config.dataFilename;
//                dataFilename = "daneucz2000.txt";
//                dataFilename = "daneucz10000.dan";
                algTester.testSwarm(net, swarmAlg, dataFilename, params);
//                String verifyFilename = "daneucz2000.txt";
//                String verifyFilename = "daneucz10000.dan";
//                Verifier verifier = new Verifier(net, verifyFilename, params);
//                verifier.verify(null);
//                String verifyFilename1 = "danewer2000.txt";
//                String verifyFilename1 = "danewer10000.dan";
//                Verifier verifier1 = new Verifier(net, verifyFilename1, params);
//                verifier1.verify(null);
//                algTester.testBFGS();
            }
        } else if(STATIC_NET == true){
            dataFilename = "daneucz8000_bezprzedostatniej.txt";
            algTester.testSwarm(net, swarmAlg, dataFilename, null);
//            String verDataFilename = "danewer8000_bezprzedostatniej.txt";
//            Verifier staticVerifier = new Verifier(net, verDataFilename, null);
//            staticVerifier.verify(null);
        }
    }
}
