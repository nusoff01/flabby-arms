package controllers;

import models.Hospital;
import models.UserInput;
import models.Computed;
//import org.apache.;
import play.*;
import play.mvc.*;

import play.data.*;



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
        //String input = storeCSV();
        //String input = "yo";
        return ok(index.render(userForm));
    }

// public int explainMed;
//     public int soundIntol;
//     public int nurseQual;
//     public int bedsideMan;
//     public int pain;
//     public int bathroom;
//     public int promptness;

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
            //response += currH.hospitalID + " ";
        }

        results.qualityScores = new Integer[count];
        results.priceScores = new Integer[count];
        results.hospitals = new String[count];
        results.addresses = new String[count];

        System.out.println("FINAL COUNT: " + count);
        results.numHospitals = count;
        count = 0;
        for (Map.Entry<Long, Hospital> entry : map.entrySet()) {
            Hospital currH = entry.getValue();
            if(currH.prices[fakeProc] != null){
                results.hospitals[count] = currH.hospitalName;
                results.priceScores[count] = currH.prices[fakeProc] * 5;
                results.addresses[count] = currH.streetAddress;
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
                System.out.println("Curr: " + currH.prices[fakeProc]);
            }
            //response += currH.hospitalID + " ";
        }

        results.procName = created.procName;
        return ok(hospitals.render(results));
    }



    public static Map storeCSV() {

         Map<Long, Hospital> map = new HashMap<Long, Hospital>();

        String fileToParse = Play.application().path() + "/data/Cleaned_Hospital_Data.csv";

        BufferedReader fileReader = null;
        String input = "";
        //input += Play.application().path();
        //Delimiter used in CSV file

        final String DELIMITER = ",";
        try
        {
            String line = "";
            //Create the file reader
            //fileReader = new BufferedReader(new FileReader("/app/test.csv"));
            //InputStream fis = game.getFileIO().readFile("test.csv");
            //fileReader = Play.classloader.getResourceAsStream("HCAHPS_-_Hospital.csv");
            fileReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileToParse), "UTF-8"));

            //Read the file line by line
            int s_num = 0;
            //input += "right before loop ";
            int lineNum = 0;
            Hospital inUse = new Hospital(2);
            while ((line = fileReader.readLine()) != null )
            {


                if((lineNum - 1) % 21 == 0){

                }
                //input += "@@@ Start of a line @@@";
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                int colNum = 0;
                //String hospitalData = "";
                //boolean addFlag = false;
                //input += "||| " + lineNum + " |||";

                for(String token : tokens) {
                    // if(colNum == 0){
                    //     input += token;
                    // }
                    //input += colNum + " " + token;

                    if((lineNum - 1) % 21 == 0){
                        if(colNum == 2){
                            //System.out.println("and here!");
                            inUse.hospitalName = token;
                            //System.out.println("but here?");
                        }
                        if(colNum == 1){
                            System.out.println(Long.parseLong(token));
                            inUse.hospitalID = Long.parseLong(token);
                        }
                        if(colNum == 3)
                            inUse.streetAddress = token;
                        if(colNum == 4)
                            inUse.streetAddress += token + ", MA";
                    }
                    System.out.println("check" + inUse.hospitalID);


                    if((lineNum - 1) % 3 == 0 && colNum == 19){
                        //input += token;
                        double num = Double.parseDouble(token);
                        //input += num;
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
                    // if(colNum == 4 && token.equals("MA")){
                    // if(colNum == 4){
                    //     addFlag = true;
                    //     //input += "" + colNum + ": " + token + "\n";
                    // } //else
                        //break;
                    // hospitalData += token + ", ";
                    colNum++;


                }
                if((lineNum + 1) % 21 == 0){
                    System.out.println(inUse.hospitalID);
                    map.put(inUse.hospitalID, inUse);
                 inUse = new Hospital(2);
                }
                lineNum++;
            }








            fileToParse = Play.application().path() + "/data/Cleaned_Medical_Procedure_Data.csv";

            //line = "";
            fileReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileToParse), "UTF-8"));

            lineNum = 0;
            int procNum = 0;
            long currNum = 0;
            long prevNum = 0;

            while ((line = fileReader.readLine()) != null )
            {
                Hospital inUse2 = new Hospital(1);
                //input += "@@@ Start of a line @@@";
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                int colNum = 0;
                long idNum = 0;
                for(String token : tokens) {
                    if(colNum == 0){
                        currNum = Long.parseLong(token);
                        //input += "*" + token + "*";
                    }

                    if(currNum - prevNum > 1){
                        procNum++;
                        input += procNum + " ";
                    }
                    if(colNum == 2){

                        idNum = Long.parseLong(token);
                        //input += " | " + Long.parseLong(token) + " | ";
                    }
                    //input += "*" + token + "*";

                    if(colNum == 12){
                        if(procNum < 97){
                            Hospital response = map.get(idNum);
                            double dVal = (1.0 - Double.parseDouble(token));
                            int val = (int)(100 * dVal);
                            System.out.println("val: " + val + "procNum: " + procNum);
                            response.prices[procNum] = val;
                            System.out.println("right after");
                            map.put(idNum, response);
                            if(procNum == 96) break;

                        }
                    }
                    colNum++;
                    prevNum = currNum;
                }
            }
        }
        catch (Exception e) {
            input += " exception caught";
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

        //String input = "";
        return map;
    }
}

