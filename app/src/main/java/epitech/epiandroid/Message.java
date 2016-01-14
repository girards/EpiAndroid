package epitech.epiandroid;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by girard_s on 13/01/2016 for Epitech.
 */
public class Message {
    @SerializedName("id")
    private String _id;

    @SerializedName("title")
    private String _title;

    @SerializedName("user")
    private MessageUser _user;

    @SerializedName("content")
    private String _content;

    @SerializedName("date")
    private String _textDate;

    private Date _date;

    public Message() {

    }

    public String getId()
    {
        return _id;
    }

    public String getTitle()
    {
        return _title;
    }

    public MessageUser getUser()
    {
        return _user;
    }

    public String getContent()
    {
        return _content;
    }

    public Date getDate() {
        return _date;
    }
}
