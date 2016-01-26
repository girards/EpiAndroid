package epitech.epiandroid;

import com.google.gson.annotations.SerializedName;

/**
 * Created by girard_s on 25/01/2016 for Epitech.
 */
public class ModuleOverview {

    @SerializedName("codemodule")
    private String codeModule;

    @SerializedName("codeinstance")
    private String codeInstance;

    private String title;

    private String grade;

    private int credits;

    private int semester;


    public String getCodeModule() {
        return codeModule;
    }

    public String getCodeInstance() {
        return codeInstance;
    }

    public String getTitle() {
        return title;
    }

    public String getGrade() {
        return grade;
    }

    public int getCredits() {
        return credits;
    }

    public int getSemester() {
        return semester;
    }
}
