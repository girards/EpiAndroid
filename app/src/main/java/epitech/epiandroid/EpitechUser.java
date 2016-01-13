package epitech.epiandroid;

import android.graphics.Bitmap;

/**
 * Created by girard_s on 12/01/2016 for Epitech.
 */
public class EpitechUser {
    private String _login;
    private String _mail;
    private String _title;
    private String _promo;
    private Integer _semester;
    private Integer _studentYear;
    private String _location;
    private Bitmap _profilePicture;
    private EpitechUser _currentUser;

    public EpitechUser currentUser() {
        return _currentUser;
    }



    public EpitechUser(){

    }

    public Bitmap getProfilePicture() {
        return _profilePicture;
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
