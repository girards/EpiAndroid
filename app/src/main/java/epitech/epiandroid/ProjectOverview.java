package epitech.epiandroid;

import com.google.gson.annotations.SerializedName;

/**
 * Created by girard_s on 14/01/2016 for Epitech.
 */
public class ProjectOverview {


    @SerializedName("registered")
    private int _registered;

    @SerializedName("scolaryear")
    private String _scolarYear;

    @SerializedName("codeinstance")
    private String _instanceCode;

    @SerializedName("codeacti")
    private String _activityCode;

    @SerializedName("title_module")
    private String _moduleTitle;

    @SerializedName("code_module")
    private String _moduleCode;

    @SerializedName("project")
    private String _projectName;

    @SerializedName("type_acti_code")
    private String _activityType;

    public ProjectOverview()
    {

    }

    public String getActivityType() {
        return _activityType;
    }

    public boolean isRegistered(){
        if (_registered == 1)
            return true;
        return false;
    }

    public String getScolarYear(){
        return _scolarYear;
    }

    public String getInstanceCode(){
        return _instanceCode;
    }

    public String getActivityCode() {
        return _activityCode;
    }

    public String getModuleTitle(){
        return _moduleTitle;
    }

    public String getModuleCode(){
        return _moduleCode;
    }

    public String getProjectName() {
        return _projectName;
    }
}
