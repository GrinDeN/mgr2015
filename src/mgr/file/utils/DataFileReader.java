package mgr.file.utils;

import mgr.input.builder.InputBuilder;
import mgr.input.builder.ParamPair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataFileReader {

    private String filename;
    private List<String> strValuesList;

    public DataFileReader(String filename){
        this.filename = filename;
        strValuesList = new ArrayList<String>();
    }

    public void readData(){
        Path dataFilePath = FileSystems.getDefault().getPath(this.filename);
        try {
            strValuesList = Files.readAllLines(dataFilePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Niewłaściwa ścieżka pliku. Plik nie istnieje lub jest uszkodzony.");
        }
    }

    public List<String> getValueList(){
        return this.strValuesList;
    }

    public static void main(String[] args){
        String filename = "dane_spr_grad.txt";
        DataFileReader dfr = new DataFileReader(filename);
        dfr.readData();
        DataFileExtractor dfe = new DataFileExtractor(dfr.getValueList());
        dfe.extractData();
        ArrayList<ArrayList<Double>> dataValues = dfe.getDataValues();
        ArrayList<Double> networkOutputs = new ArrayList<Double>();
        networkOutputs.add(1.0);
        networkOutputs.add(2.0);
        networkOutputs.add(3.0);
        networkOutputs.add(4.0);
        ArrayList<ParamPair> params = new ArrayList<ParamPair>();
        params.add(new ParamPair(1, 3));
        params.add(new ParamPair(2));
        InputBuilder inputBuild = new InputBuilder(dataValues, params);
        inputBuild.build(4, networkOutputs);
        ArrayList<Double> input = inputBuild.getInput();
        for (int i = 0; i < input.size(); i++) {
            System.out.println(i+". element wektora wejsciowego: "+input.get(i));
        }
    }

}
