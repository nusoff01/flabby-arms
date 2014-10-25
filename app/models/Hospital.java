package models;

import javax.persistence.*;
import play.db.ebean.Model;
import java.util.*;
import javax.persistence.OneToMany;

@Entity
public class Hospital extends Model {
    @Id
    public long id;
    public long hospitalID;
    public String hospitalName;
    // public int[] qualityList;
    // public long[] priceList;
    public double latitude;
    public double longitude;
    public String streetAddress;

    public int explainMed;
    public int soundIntol;
    public int nurseQual;
    public int bedsideMan;
    public int pain;
    public int bathroom;
    public int promptness;




    //public List<Long> priceList = new ArrayList<Long>();


    //default constructor
    public Hospital(long hospitalID) {
        this.hospitalID = hospitalID;
        this.hospitalName = "";


        this.latitude = 0;
        this.longitude = 0;
        this.streetAddress = "";
    }
}