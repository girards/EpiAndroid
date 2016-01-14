package epitech.epiandroid;

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

    public MessageUser() {

    }

    public String getPicture() {
        return _picture;
    }

    public String getTitle() {
        return _title;
    }

    public String getUrl() {
        return _url;
    }
}
