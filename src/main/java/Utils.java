import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


public class Utils {

    public String createFileName (String dirPath){
        Date todaysDate = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM-dd-yyyy_HH-mm-SS");
        String fileName = "CuratedProducts_" + formattedDate.format(todaysDate);
        return dirPath + fileName + ".csv";
    }

    public CSVWriter createFileWriter (String fileName){
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fileName, true));
            return writer;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CSVReader createFileReader (String fileName){
        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            return reader;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void writeToCsvFile (CSVWriter writer, String rowString){

        String [] row = rowString.split("#");
        writer.writeNext(row);

    }

    public List<String[]> getAllRowsFromCsvFile (CSVReader reader){

        List<String[]> rows = new ArrayList<String[]>();
        try {
            rows = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;

    }

    public void closeWriter (CSVWriter writer){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static final String CURRENCY_SYMBOLS= "\\p{Sc}\u0024\u060B";

    public String[] returnPrices (String priceInfo){

        Pattern p = Pattern.compile("[" +CURRENCY_SYMBOLS + "][\\d,]+");
        Matcher m = p.matcher(priceInfo);

        int noOfPrices = 0;
        List<String> totalPrices = new ArrayList<String>();
        //String[] totalPrices = new String[6];
        String[] prices = new String[2];

        while (m.find()) {

            totalPrices.add(noOfPrices, m.group());
            noOfPrices++;

        }
        int priceArrayLength = totalPrices.size();
        if (priceArrayLength > 2){
            prices[1] = totalPrices.get(priceArrayLength - 1);
            prices[0] = totalPrices.get(priceArrayLength - 2);
            return prices;
        } else if (priceArrayLength == 2){
            prices[1] = totalPrices.get(1);
            prices[0] = totalPrices.get(0);
            return prices;
        } else if (priceArrayLength == 1) {
            prices[1] = totalPrices.get(0);
            prices[0] = "00";
            return prices;
        }

        prices[0] = "00";
        prices[1] = "00";
        return prices;

    }

}