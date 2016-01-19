package epitech.epiandroid;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by girard_s on 15/01/2016 for Epitech.
 */
public class Project {
    @SerializedName("scolaryear")
    private String _scolarYear;

    @SerializedName("end")
    private String _dateEnd;

    @SerializedName("begin")
    private String _dateStart;

    @SerializedName("codemodule")
    private String _moduleCode;

    @SerializedName("codeinstance")
    private String _codeInstance;

    @SerializedName("codeacti")
    private String _codeActi;

    @SerializedName("module_title")
    private String _moduleTitle;

    @SerializedName("project_title")
    private String _projectTitle;

    @SerializedName("register")
    private boolean _isRegistrable;

    @SerializedName("description")
    private String _description;

    public String get_scolarYear() {
        return _scolarYear;
    }

    public String get_moduleCode() {
        return _moduleCode;
    }

    public String get_codeInstance() {
        return _codeInstance;
    }

    public String get_codeActi() {
        return _codeActi;
    }

    public String get_moduleTitle() {
        return _moduleTitle;
    }

    public Date get_dateEnd() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(_dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public float getTimePercentage() {
        long totalLength = Math.abs(get_dateEnd().getTime() - get_dateStart().getTime());
        long countingLength = Math.abs(get_dateEnd().getTime() - new Date().getTime());
        Log.d("Value of diff", String.valueOf(totalLength));
        Log.d("Value of count", String.valueOf(countingLength));
        float projecttime = (float)countingLength / (float)totalLength;
        return (100 - (projecttime * 100));
    }

    public Date get_dateStart() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(_dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public String get_projectTitle() {
        return _projectTitle;
    }

    public boolean is_Registrable() {
        return _isRegistrable;
    }

    public String get_description() {
        return _description;
    }

    public boolean is_Closed() {
        return _isClosed;
    }

    @SerializedName("closed")

    private boolean _isClosed;
}
