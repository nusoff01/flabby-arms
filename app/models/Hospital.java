package models;

import javax.persistence.*;
import play.db.ebean.Model;
import java.util.*;
import javax.persistence.OneToMany;

import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;



public class Hospital extends Model {
    @Id
    public long id;
    public long hospitalID;
    public String hospitalName;
    // public int[] qualityList;
    // public long[] priceList;

    public String streetAddress;

    public int explainMed;
    public int soundIntol;
    public int nurseQual;
    public int bedsideMan;
    public int pain;
    public int bathroom;
    public int promptness;
    public double latitude;
    public double longitude;

    public Integer[] prices = new Integer[101];



    // public int price0;
    // public int price1;
    // public int price2;
    // public int price3;
    // public int price4;
    // public int price5;
    // public int price6;
    // public int price7;
    // public int price8;
    // public int price9;
    // public int price10;
    // public int price11;
    // public int price12;
    // public int price13;
    // public int price14;
    // public int price15;
    // public int price16;
    // public int price17;
    // public int price18;
    // public int price19;
    // public int price20;
    // public int price21;
    // public int price22;
    // public int price23;
    // public int price24;
    // public int price25;
    // public int price26;
    // public int price27;
    // public int price28;
    // public int price29;
    // public int price30;
    // public int price31;
    // public int price32;
    // public int price33;
    // public int price34;
    // public int price35;
    // public int price36;
    // public int price37;
    // public int price38;
    // public int price39;
    // public int price40;
    // public int price41;
    // public int price42;
    // public int price43;
    // public int price44;
    // public int price45;
    // public int price46;
    // public int price47;
    // public int price48;
    // public int price49;
    // public int price50;
    // public int price51;
    // public int price52;
    // public int price53;
    // public int price54;
    // public int price55;
    // public int price56;
    // public int price57;
    // public int price58;
    // public int price59;
    // public int price60;
    // public int price61;
    // public int price62;
    // public int price63;
    // public int price64;
    // public int price65;
    // public int price66;
    // public int price67;
    // public int price68;
    // public int price69;
    // public int price70;
    // public int price71;
    // public int price72;
    // public int price73;
    // public int price74;
    // public int price75;
    // public int price76;
    // public int price77;
    // public int price78;
    // public int price79;
    // public int price80;
    // public int price81;
    // public int price82;
    // public int price83;
    // public int price84;
    // public int price85;
    // public int price86;
    // public int price87;
    // public int price88;
    // public int price89;
    // public int price90;
    // public int price91;
    // public int price92;
    // public int price93;
    // public int price94;
    // public int price95;
    // public int price96;
    // public int price97;
    // public int price98;
    // public int price99;




    //public List<Long> priceList = new ArrayList<Long>();


    //default constructor

    public Hospital(long hospitalID) {
        this.hospitalID = hospitalID;
        this.hospitalName = "";
        this.streetAddress = "";
    }


}