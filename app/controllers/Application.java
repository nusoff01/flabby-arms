package controllers;

import models.Hospital;
//import org.apache.;
import play.*;
import play.mvc.*;


import views.html.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import play.db.ebean.Model;





public class Application extends Controller {


    public static Result index() {
        String input = storeCSV();
        //String input = "yo";
        return ok(index.render(input));
    }

    public static String storeCSV() {

        String fileToParse = Play.application().path() + "/data/HCAHPS_-_Hospital.csv";

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
            long idNum = 0;
            long prevNum = 0;
            while ((line = fileReader.readLine()) != null && s_num < 100)
            {
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                int colNum = 0;
                String hospitalData = "";
                boolean addFlag = false;

                for(String token : tokens) {
                    if(colNum == 0){
                        input += token;
                    }
                    // if(colNum == 4 && token.equals("MA")){
                    if(colNum == 4){
                        addFlag = true;
                        //input += "" + colNum + ": " + token + "\n";
                    } //else
                        //break;
                    hospitalData += token + ", ";
                    colNum++;
                }
                if(addFlag == true){
                    //input += "START OF LINE: " + s_num + " " + hospitalData;
                    s_num++;
                    if(idNum != prevNum){
                        //Hospital currHospital = new Hospital(idNum);
                        //input += "About to save hospital";
                        //currHospital.save();
                    }
                    prevNum = idNum;
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
        return input;
    }
}

