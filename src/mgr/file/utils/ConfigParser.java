package mgr.file.utils;

import mgr.config.Config;
import mgr.input.builder.ParamPair;

import java.util.ArrayList;
import java.util.List;

public class ConfigParser {

    private String filename;
    private DataFileReader reader;
    private List<String> settingsList;

    private int typeOfNetwork;
    private int numOfAlgorithm;
    private int inputSize;
    private int numOfNeurons;
    private int numOfIters;
    private String dataFileName;
    private ArrayList<Integer> delayParams;
    private ArrayList<ParamPair> params;

    public ConfigParser(String filepath){
        this.filename = filepath;
        this.reader = new DataFileReader(filename);
        this.settingsList = new ArrayList<String>();
        this.delayParams = new ArrayList<Integer>();
        this.params = new ArrayList<ParamPair>();
    }

    private void readSettings(){
        reader.readData();
        settingsList.addAll(reader.getValueList());
        typeOfNetwork = Integer.parseInt(settingsList.get(0));
        numOfAlgorithm = Integer.parseInt(settingsList.get(1));
        inputSize = Integer.parseInt(settingsList.get(2));
        numOfNeurons = Integer.parseInt(settingsList.get(3));
        numOfIters = Integer.parseInt(settingsList.get(4));
        dataFileName = settingsList.get(5);
    }

    private void getParamsFromSettingsList(String params){
        String[] paramsStrArray = params.split(" ");
        for (int i = 0; i < paramsStrArray.length; i++) {
            delayParams.add(Integer.parseInt(paramsStrArray[i]));
        }

    }

    public void parseSettings() throws Exception{
        try{
            readSettings();
        } catch(NumberFormatException numberEx){
            System.out.println("Podano niewłaściwy format danych - proszę sprawdzić konfigurację, czy wszystkie wartości są poprawnego formatu");
        } finally {
            if (settingsList.size() != 7 && typeOfNetwork == 1){
                throw new Exception("Niewłaściwa ilość ustawień - dla sieci rekurencyjnej powinno ich być 7 (6 linijek w pliku z ustawieniami)");
            }
            if (settingsList.size() != 6 && typeOfNetwork == 0){
                throw new Exception("Niewłaściwa ilość ustawień - dla sieci rekurencyjnej powinno ich być 6 (5 linijek w pliku z ustawieniami)");
            }
            if (numOfAlgorithm < 1 || numOfAlgorithm > 7){
                throw new Exception("Niewłaściwy numer algorytmu. Proszę wybrać numer od 1 do 7");
            }
            if (typeOfNetwork == 1 && delayParams.size()%2 == 0 && !delayParams.isEmpty()){
                throw new Exception("Niewłaściwa ilość parametrów - powinna ich być nieparzysta ilość (po 2 na każdy sygnał + 1 opóźnienie wyjścia sieci)");
            }
        }
    }

    public void saveSettingsToApp(){
        Config.setTypeOfNetwork(typeOfNetwork);
        Config.setSwarmAlg(SwarmAlgParser.parseIntToSwarmAlg(numOfAlgorithm));
        Config.setINPUT_SIZE(inputSize);
        Config.setHiddNeurons(numOfNeurons);
        Config.setNumOfIters(numOfIters);
        if (typeOfNetwork == 1){
            createParams();
            Config.setParams(params);
        }
        Config.setDataFilename(dataFileName);

    }

    private void createParams(){
        params.clear();
        getParamsFromSettingsList(settingsList.get(6));
        for (int i = 0; i < delayParams.size(); i++) {
            if(i==delayParams.size()-1) {
                params.add(new ParamPair(delayParams.get(i)));
            } else if(i%2==0){
                params.add(new ParamPair(delayParams.get(i), delayParams.get(i+1)));
            }
        }
    }

    public static void main(String[] args) throws Exception{
        String settings = "settings.txt";
        ConfigParser parser = new ConfigParser(settings);
        try {
            parser.parseSettings();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        parser.saveSettingsToApp();
        System.out.println(Config.swarmAlg);
        System.out.println(Config.INPUT_SIZE);
        System.out.println(Config.HIDD_NEURONS);
        System.out.println(Config.dataFilename);
        ArrayList<ParamPair> pars = Config.params;
        for (int i = 0; i < pars.size(); i++) {
            System.out.println(pars.get(i).getFirstValue() + " " + pars.get(i).getSecondValue());
        }
    }
}
