package epitech.epiandroid;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by girard_s on 11/01/2016 for Epitech.
 */
public class RequestManager {
    private static RequestManager RequestInstance = null;

    private String _token;
    private RequestQueue _requestQueue;
    private static final String REQUEST_URL = "http://epitech-api.herokuapp.com";
    private static Context _context;
    private String _login;

    private RequestManager(Context context){
        _context = context;
        if (_requestQueue == null)
            _requestQueue = Volley.newRequestQueue(_context);
    }

    public String getLogin(){
        return _login;
    }

    public static RequestManager getInstance(Context context){
        if (RequestInstance == null) {
            RequestInstance = new RequestManager(context);
        }
        return RequestInstance;
    }

    public void Login(final String login, String password, final APIListener<Boolean> listener) {

        String finalRequest = REQUEST_URL+"/login?login="+login+"&password="+password;
        Log.d("URL", finalRequest);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            _token = response.getString("token");
                            Log.d("Token", _token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        _login = login;
                        listener.getResult(true);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.getResult(false);
                    }
                });
        _requestQueue.add(jsObjRequest);
    }

    public void getPhotoUrl(String login, final APIListener<String> listener)
    {
        String finalRequest = REQUEST_URL+"/photo?token="+_token+"&login="+login;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            listener.getResult(response.getString("url"));
                            Log.d("Token", _token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.getResult("NULL");
                    }
                });
        _requestQueue.add(jsObjRequest);
    }
}
