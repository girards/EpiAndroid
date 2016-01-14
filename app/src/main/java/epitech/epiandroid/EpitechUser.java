package epitech.epiandroid;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by girard_s on 12/01/2016 for Epitech.
 */
public class EpitechUser {
    @SerializedName("login")
    private String _login;

    @SerializedName("internal_email")
    private String _mail;

    @SerializedName("title")
    private String _title;

    @SerializedName("promo")
    private String _promo;

    @SerializedName("semester")
    private Integer _semester;

    @SerializedName("scolaryear")
    private Integer _studentYear;

    @SerializedName("location")
    private String _location;
    private Bitmap _profilePicture;

    public EpitechUser(){

    }

    public Bitmap getProfilePicture() {
        return _profilePicture;
    }

    public String getMail() {
        return _mail;
    }

    public String getTitle() {
        return _title;
    }

    public String getPromo() {
        return _promo;
    }

    public String getLocation() {
        return _location;
    }

    public String getLogin() {
        return _login;
    }

    public Integer getSemester() {
        return _semester;
    }

    public Integer getStudentYear() {
        return _studentYear;
    }

    public void accessProfilePicture() {
       RequestManager.getInstance().getPhotoUrl(_login, new APIListener<Bitmap>() {
           @Override
           public void getResult(Bitmap object) {
               _profilePicture = object;
           }
       });
    }
}
