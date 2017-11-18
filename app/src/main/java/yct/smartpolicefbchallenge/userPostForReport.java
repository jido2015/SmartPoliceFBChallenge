package yct.smartpolicefbchallenge;

/**
 * Created by mac on 11/14/17.
 */

public class userPostForReport {

    private String image;
    private String video;
    private String audio;
    private String category;
    private String information;


    public userPostForReport() {
    }

    public userPostForReport(String image, String video, String audio, String category, String information) {
        this.image = image;
        this.video = video;
        this.audio = audio;
        this.category = category;
        this.information = information;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
