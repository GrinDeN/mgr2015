package mgr.file.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukasz on 2014-09-29.
 */
public class DataFileReader {

    private String filename;
    private ArrayList<ArrayList<Double>> values;
    private List<String> strValuesList;

    public DataFileReader(String filename){
        this.filename = filename;
        values = new ArrayList<ArrayList<Double>>();
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
        System.out.println("Rozmiar wczytanej listy: "+ dfr.getValueList().size());
        DataFileExtractor dfe = new DataFileExtractor(dfr.getValueList());
        System.out.println("Pierwszy element data: " + dfe.getDataValues().get(0));
        System.out.println("Pierwszy element demand: " + dfe.getDemandValues().get(0));
    }

}
