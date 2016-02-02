package epitech.epiandroid;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by LouisAudibert on 26/01/2016.
 */

public class Event {
    @SerializedName("module_registered")
    boolean _moduleRegistered;

    @SerializedName("titlemodule")
    String _titleModule;

    @SerializedName("end")
    String _end;

    @SerializedName("codeevent")
    String _codeEvent;

    @SerializedName("total_students_registered")
    String _totalStudentsRegistered;

    @SerializedName("acti_title")
    String _actiTitle;

    @SerializedName("scolaryear")
    String _scolaryear;

    @SerializedName("allow_token")
    boolean _allowToken;

    @SerializedName("codemodule")
    String _codeModule;

    @SerializedName("start")
    String _start;

    @SerializedName("event_registered")
    boolean _eventRegistered;

    @SerializedName("allow_register")
    boolean _allowRegister;

    @SerializedName("codeinstance")
    String _codeInstance;

    @SerializedName("codeacti")
    String _codeActi;

    public boolean is_moduleRegistered() {
        return _moduleRegistered;
    }

    public String get_titleModule() {
        return _titleModule;
    }

    public Date get_end() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //"end": "2016-01-27 17:00:00"
        Date date = null;
        try {
            date = df.parse(_end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String get_codeEvent() {
        return _codeEvent;
    }

    public String get_totalStudentsRegistered() {
        return _totalStudentsRegistered;
    }

    public String get_actiTitle() {
        return _actiTitle;
    }

    public String get_scolaryear() {
        return _scolaryear;
    }

    public boolean is_allowToken() {
        return _allowToken;
    }

    public String get_codeModule() {
        return _codeModule;
    }

    public Date get_start() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //"end": "2016-01-27 17:00:00"
        Date date = null;
        try {
            date = df.parse(_start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public boolean is_eventRegistered() {
        return _eventRegistered;
    }

    public boolean is_allowRegister() {
        return _allowRegister;
    }

    public String get_codeInstance() {
        return _codeInstance;
    }

    public String get_codeActi() {
        return _codeActi;
    }

    public Event() {

    }
}

