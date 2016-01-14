package epitech.epiandroid;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.google.gson.annotations.SerializedName;

/**
 * Created by girard_s on 13/01/2016 for Epitech.
 */
public class MessageUser {
    @SerializedName("picture")
    private String _picture;

    @SerializedName("title")
    private String _title;

    @SerializedName("url")
    private String _url;

    private Bitmap _photo;

    private boolean _done;

    public MessageUser() {

    }

    public String getPicture() {
        _done = false;
        RequestManager.getInstance().getPhotoUrlonly(_picture.substring(40).replace(".bmp", ""), new APIListener<String>() {
            @Override
            public void getResult(String object) {
                _picture = object;
            }
        });
        return _picture;
    }

    public String getTitle() {
        return _title;
    }

    public String getUrl() {
        return _url;
    }

    public String getPictureUrl() {
        return _picture;
    }
}
