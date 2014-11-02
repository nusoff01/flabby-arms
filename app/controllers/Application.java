package controllers;

import models.Hospital;
import models.UserInput;
import models.Computed;
//import org.apache.;
import play.*;
import play.mvc.*;

import play.data.*;
import play.api.*;

import views.html.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;



public class Application extends Controller {

    private static final Form<UserInput> userForm = Form.form(UserInput.class);

    public static Result index() {
        return ok(index.render(userForm));
    }

    public static Result compute(){
        Form<UserInput> filled = userForm.bindFromRequest();
        Map<Long, Hospital> map = storeCSV();
        String response = "";

        Computed results = new Computed();
        UserInput created = filled.get();
        String procNum = created.procName;

        Integer fakeProc = Integer.parseInt(procNum);
        //int fakeProc = 20;
        int count = 0;
        for (Map.Entry<Long, Hospital> entry : map.entrySet()) {
            Hospital currH = entry.getValue();
            if(currH.prices[fakeProc] != null){
                count++;
            }
        }

        results.qualityScores = new Integer[count];
        results.priceScores = new Integer[count];
        results.hospitals = new String[count];
        results.addresses = new String[count];
        results.latitudes = new Double[count];
        results.longitudes = new Double[count];

        results.numHospitals = count;
        count = 0;
        for (Map.Entry<Long, Hospital> entry : map.entrySet()) {
            Hospital currH = entry.getValue();
            if(currH.prices[fakeProc] != null){
                results.hospitals[count] = currH.hospitalName;
                results.priceScores[count] = 110 + (1*currH.prices[fakeProc]);
                results.addresses[count] = currH.streetAddress;
                results.latitudes[count] = currH.latitude;
                results.longitudes[count] = currH.longitude;
                //results.hospitalName = currH.hospitalName;
                int num = 0;
                double sum = 0;
                if(created.q1 == true){
                    num++;
                    sum += currH.explainMed;
                }
                if(created.q2 == true){
                    num++;
                    sum += currH.soundIntol;
                }
                if(created.q3 == true){
                    num++;
                    sum += currH.nurseQual;
                }
                if(created.q4 == true){
                    num++;
                    sum += currH.bedsideMan;
                }
                if(created.q5 == true){
                    num++;
                    sum += currH.pain;
                }
                if(created.q6 == true){
                    num++;
                    sum += currH.bathroom;
                }
                if(created.q7 == true){
                    num++;
                    sum += currH.promptness;
                }
                double score = sum / num;
                results.qualityScores[count] = (int) score;

                count++;
            }
        }

        results.procName = created.procName;
        return ok(hospitals.render(results));
    }

    /*
     *  storeCSV gathers data from the two CSV files and computes
     *
     */

    public static Map storeCSV() {

        Map<Long, Hospital> map = new HashMap<Long, Hospital>();

        //QUALITY DATA


        String path = "data/qual_data.csv";
        BufferedReader fileReader = null;

        final String DELIMITER = ",";
        try
        {
            String line = "";

            fileReader = new BufferedReader(new InputStreamReader( new FileInputStream(path), "UTF-8"));

            //Read the file line by line
            int s_num = 0;
            //input += "right before loop ";
            int lineNum = 0;
            Hospital inUse = new Hospital(2);
            while ((line = fileReader.readLine()) != null )
            {

                String[] tokens = line.split(DELIMITER);
                int colNum = 0;


                for(String token : tokens) {

                    if((lineNum - 1) % 21 == 0){
                        if(colNum == 2){
                            inUse.hospitalName = token;
                        }
                        if(colNum == 1){
                            System.out.println(Long.parseLong(token));
                            inUse.hospitalID = Long.parseLong(token);
                        }
                        if(colNum == 3)
                            inUse.streetAddress = token;
                        if(colNum == 4)
                            inUse.streetAddress += " " + token + ", MA";
                        if(colNum == 15){
                            System.out.println("the damn line number: " + lineNum);
                            inUse.latitude = Double.parseDouble(token);
                        }
                        if(colNum == 16)
                            inUse.longitude = Double.parseDouble(token);
                    }

                    if((lineNum - 1) % 3 == 0 && colNum == 18){
                        double num = Double.parseDouble(token);
                        if((lineNum - 1) % 21 == 0)
                            inUse.explainMed = (int) (num * 100);
                        if((lineNum - 1) % 21 == 3)
                            inUse.soundIntol = (int) (num * 100);
                        if((lineNum - 1) % 21 == 6)
                            inUse.nurseQual = (int) (num * 100);
                        if((lineNum - 1) % 21 == 9)
                            inUse.bedsideMan = (int) (num * 100);
                        if((lineNum - 1) % 21 == 12)
                            inUse.pain = (int) (num * 100);
                        if((lineNum - 1) % 21 == 15)
                            inUse.bathroom = (int) (num * 100);
                        if((lineNum - 1) % 21 == 18)
                            inUse.promptness = (int) (num * 100);
                    }

                    colNum++;
                }
                if((lineNum + 1) % 21 == 0){
                    System.out.println(inUse.hospitalID);
                    map.put(inUse.hospitalID, inUse);
                 inUse = new Hospital(2);
                }
                lineNum++;
            }

            //PRICE DATA

            path = "data/Cleaned_Medical_Procedure_Data.csv";

            line = "";
            fileReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), "UTF-8"));

            // VirtualFile vf = VirtualFile.fromRelativePath(fileToParse);
            // File realFile = vf.getRealFile();
            // fileReader = new FileReader(realFile);

            lineNum = 0;
            int procNum = 0;
            long currNum = 0;
            long prevNum = 422;
            boolean firstLine = true;

            while ((line = fileReader.readLine()) != null )
            {
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                Hospital inUse2 = new Hospital(1);
                String[] tokens = line.split(DELIMITER);
                int colNum = 0;
                long idNum = 0;
                for(String token : tokens) {
                    if(colNum == 0){
                        currNum = Long.parseLong(token);
                    }

                    if(currNum - prevNum > 1){
                        procNum++;
                    }
                    if(colNum == 2){
                        idNum = Long.parseLong(token);
                        System.out.println("id num: " + idNum);
                    }
                    if(colNum == 12){
                        //if(procNum < 97){
                            Hospital response = map.get(idNum);

                            double dVal = (1.0 - Double.parseDouble(token));

                            System.out.println(procNum + " percent above or below val: " + dVal);
                            int val = (int)(200 * dVal);
                            System.out.println("final val: " + val);

                            response.prices[procNum] = val;
                            map.put(idNum, response);

                        //}
                    }
                    colNum++;
                    prevNum = currNum;
                }
                //if(procNum == 96) break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                if(fileReader != null) fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}

