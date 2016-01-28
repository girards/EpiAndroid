package epitech.epiandroid;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by LouisAudibert on 26/01/2016.
 */
public class Grade {
    @SerializedName("scolaryear")
    private String _scolaryear;

    @SerializedName("codemodule")
    private String _codemodule;

    @SerializedName("titlemodule")
    private String _titleModule;

    @SerializedName("title")
    private String _title;

    @SerializedName("correcteur")
    private String _correcteur;

    @SerializedName("date")
    private String _date;

    @SerializedName("final_note")
    private String _note;

    @SerializedName("comment")
    private String _comment;


    public Grade() {

    }

    public String getScolaryear() { return _scolaryear; }

    public String getCodemodule() { return _codemodule; }

    public String getTitleModule() { return _titleModule; }

    public String getTitle() { return _title; }

    public String getCorrecteur() { return _correcteur; }

    public String getDate() { return _date; }

    public String getNote() { return _note; }

    public String getComment() { return _comment; }

}