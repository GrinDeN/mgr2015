package mgr.file.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Lukasz on 2014-09-30.
 */
public class DataFileExtractor {

    //singleton IMHO, tak samo moze z DataFileReader

    private ArrayList<ArrayList<Double>> dataValues;
    private ArrayList<Double> demandValues;

    public DataFileExtractor(List<String> allData){
        dataValues = new ArrayList<ArrayList<Double>>();
        demandValues = new ArrayList<Double>();
        extractData(allData);
    }

    private void extractData(List<String> allData){
        List<String> allDataList = allData;
        for (String eachLine : allDataList){
            String[] lineValues = eachLine.split(" ");
            int numOfLineElements = lineValues.length;
            int positionOfDemandValue = getPositionOfDemand(numOfLineElements);
            int numOfDataValues = getNumElementsOfData(numOfLineElements);
            ArrayList<Double> innerElementOfDataList = new ArrayList<Double>();
            for (int elem = 0; elem < numOfDataValues; elem++){
                Double dataValue = Double.valueOf(lineValues[elem]);
                innerElementOfDataList.add(dataValue);
            }
            addDataValues(innerElementOfDataList);
            String demandValue = lineValues[positionOfDemandValue];
            addDemandValue(demandValue);
        }
    }

    private int getPositionOfDemand(int numOfAllElements){
        return numOfAllElements-1;
    }

    private int getNumElementsOfData(int numOfAllElements){
        return numOfAllElements-1;
    }

    private void addDataValues(ArrayList<Double> innerDataList){
        dataValues.add(innerDataList);
    }

    private void addDemandValue(String demand){
        Double demandVal = Double.valueOf(demand);
        demandValues.add(demandVal);
    }

    public Iterator<ArrayList<Double>> getDataListIterator(){
        return dataValues.iterator();
    }

    public ArrayList<Double> getDemandValues(){
        return this.demandValues;
    }

}
