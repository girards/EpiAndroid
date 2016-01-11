package epitech.epiandroid;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by girard_s on 11/01/2016.
 */
public class RequestManager {
    private static RequestManager RequestInstance = null;

    private String token;
    private static final String REQUEST_URL = "http://epitech-api.herokuapp.com";

    private RequestManager(){

    }

    public static RequestManager getInstance(){
        if (RequestInstance == null)
        {
            RequestInstance = new RequestManager();
        }
        return RequestInstance;
    }

    public void Login(String login, String password) {
        String finalRequest = REQUEST_URL+"/login?login="+login+"&password="+password;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
    }
}
