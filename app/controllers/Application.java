package controllers;

//import org.apache.;
import play.*;
import play.mvc.*;


import views.html.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application extends Controller {


    public static Result index() {
        String input = storeCSV();
        //String input = "yo";
        return ok(index.render(input));
    }

    public static String storeCSV() {

        String fileToParse = "HCAHPS_-_Hospital.csv";
        BufferedReader fileReader = null;
        String input = "got to this call: ";
        input += Play.application().path();
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        try
        {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader("/app/test.csv"));
            //Read the file line by line
            int s_num = 0;
            input += "right before loop ";
            while ((line = fileReader.readLine()) != null && s_num <= 10)
            {

                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                for(String token : tokens)
                {
                    input += token;
                }
                s_num++;
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
        return input;
    }
}

