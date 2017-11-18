package yct.smartpolicefbchallenge.object_report;

/**
 * Created by mac on 10/12/17.
 */

public class report_model {

    public String type;
    public String date;
    public String location;
    public String information;
    public String image;


    public report_model(){
    }

    public report_model(String type, String date, String location, String information, String image) {
        this.type = type;
        this.date = date;
        this.location = location;
        this.information = information;
        this.image = image;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
