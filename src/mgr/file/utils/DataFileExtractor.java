package mgr.file.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukasz on 2014-09-30.
 */
public class DataFileExtractor {

    //singleton IMHO, tak samo moze z DataFileReader

    private ArrayList<Double> dataValues;
    private ArrayList<Double> demandValues;

    public DataFileExtractor(List<String> allData){
        dataValues = new ArrayList<Double>();
        demandValues = new ArrayList<Double>();
        extractData(allData);
    }

    private void extractData(List<String> allData){
        List<String> allDataList = allData;
        for (String eachLine : allDataList){
            String[] lineValues = eachLine.split(" ");
            setValuesToLists(lineValues);
        }
    }

    private void setValuesToLists(String[] valuesArray){
        dataValues.add(Double.valueOf(valuesArray[0]));
        demandValues.add(Double.valueOf(valuesArray[1]));
    }

    public ArrayList<Double> getDataValues(){
        return this.dataValues;
    }

    public ArrayList<Double> getDemandValues(){
        return this.demandValues;
    }

}
