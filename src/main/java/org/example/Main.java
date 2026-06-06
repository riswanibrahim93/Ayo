package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String pathDir = System.getProperty("user.dir");
        String strPathDataOri = pathDir + "\\src\\main\\resources\\Data Compare\\Data Jadwal dan Harga.xlsx";
        String strPathDataDB = pathDir + "\\src\\main\\resources\\Data Compare\\Data Jadwal dan Harga Export DB.xlsx";

        ArrayList<ArrayList> arrDataOri = ExcelFactory.readDataExcel(strPathDataOri);
        ArrayList<ArrayList> arrDataDB = ExcelFactory.readDataExcel(strPathDataDB);

        //get Index date, start time, price DB
        ArrayList<String> arrHeaderDataDB = arrDataDB.get(0);
        int indexDateDB = getIndexArrayValue("date", arrHeaderDataDB);
        int indexStartTimeDB = getIndexArrayValue("Start_time", arrHeaderDataDB);
        int indexPriceDB = getIndexArrayValue("price", arrHeaderDataDB);

        //get Index date, start time, price DB
        ArrayList<String> arrHeaderData = arrDataOri.get(0);
        int indexStartTime = getIndexArrayValue("Start_time", arrHeaderData);
        int indexPrice = getIndexArrayValue("price", arrHeaderData);


        // 1. Validasi Duplicate (Date & Start Time)
        System.out.println("1. Validasi Duplicate (Date & Start Time)");
        ArrayList<String> arrTemp = new ArrayList<>();
        for(int i=1; i<arrDataDB.size(); i++){
            String strDate = ((String) arrDataDB.get(i).get(indexDateDB)).trim();
            String strStartTime = ((String) arrDataDB.get(i).get(indexStartTimeDB)).trim();
            String strKey = strDate + "-" + strStartTime;
            if(arrTemp.contains(strKey)){
                System.out.println("Data Duplicate: " + arrDataDB.get(i));
            }else{
                arrTemp.add(strKey);
            }
        }

        // 2. Validasi Price
        System.out.println("2. Validasi Price");
        //mapping data ori (key start, validate price)
        Map<String, String> mapDataOri = new HashMap<>();
        for(int i=1; i<arrDataOri.size(); i++){
            String strStartTime = ((String) arrDataOri.get(i).get(indexStartTime)).trim();
            String strPrice = ((String) arrDataOri.get(i).get(indexPrice)).trim();

            mapDataOri.put(strStartTime, strPrice);
        }
        //validate
        for(int i=1; i<arrDataDB.size(); i++){
            String strStartTimeDB = ((String) arrDataDB.get(i).get(indexStartTimeDB)).trim();
            String strPriceDB = ((String) arrDataDB.get(i).get(indexPriceDB)).trim();

            String strPriceOri = mapDataOri.get(strStartTimeDB);

            if(!strPriceOri.equalsIgnoreCase(strPriceDB)){
                System.out.println("Data Not Match: " + arrDataDB.get(i));
            }
        }

    }

    private static Integer getIndexArrayValue(String strValue, ArrayList<String> arrValue){
        int index = -1;
        for(int i=0; i<arrValue.size(); i++){
            if(arrValue.get(i).equalsIgnoreCase(strValue)){
                index = i;
                break;
            }
        }

        return index;
    }
}