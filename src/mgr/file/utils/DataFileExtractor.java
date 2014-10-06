package mgr.file.utils;

import java.util.ArrayList;
import java.util.List;

public class DataFileExtractor {

    //singleton IMHO, tak samo moze z DataFileReader

    private List<String> allFileData;
    private ArrayList<ArrayList<Double>> dataValues;
    private ArrayList<Double> demandValues;
    private int numOfFileCols;

    public DataFileExtractor(List<String> allData){
        allFileData = allData;
        dataValues = new ArrayList<ArrayList<Double>>();
        demandValues = new ArrayList<Double>();
        numOfFileCols = getNumOfFileColumns();
        allocateInnerDataLists();
    }

    private void allocateInnerDataLists(){
        int correctSize = numOfFileCols-1;   //without last column - demand values
        for (int i = 0; i < correctSize; i++) {
            dataValues.add(new ArrayList<Double>());
        }
    }

    private int getNumOfFileColumns(){
        String[] firstLine = allFileData.get(0).split(" ");
        int numOfFileCols = firstLine.length;
        return numOfFileCols;
    }

    public void extractData(){
        int positionOfDemandValue = getPositionOfDemand(numOfFileCols);
        int numOfDataValues = getNumElementsOfData(numOfFileCols);
        for (String eachLine : allFileData){
            String[] lineValues = eachLine.split(" ");
            for (int elem = 0; elem < numOfDataValues; elem++){
                int listPosition = elem;
                addDataValues(listPosition, lineValues[elem]);
            }
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

    private void addDataValues(int numOfList, String data){
        Double eachValue = Double.valueOf(data);
        dataValues.get(numOfList).add(eachValue);
    }

    private void addDemandValue(String demand){
        Double demandVal = Double.valueOf(demand);
        demandValues.add(demandVal);
    }

    public ArrayList<ArrayList<Double>> getDataValues(){
        return this.dataValues;
    }

    public ArrayList<Double> getDemandValues(){
        return this.demandValues;
    }

}
