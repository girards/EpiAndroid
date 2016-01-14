package epitech.epiandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

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

    private RequestManager(Context context) {
        _context = context;
        if (_requestQueue == null)
            _requestQueue = Volley.newRequestQueue(_context);
    }

    public String getLogin() {
        return _login;
    }

    public static RequestManager getInstance() {
        if (RequestInstance == null) {
            RequestInstance = new RequestManager(ApplicationExt.getContext());
        }
        return RequestInstance;
    }

    public void Login(final String login, String password, final APIListener<Boolean> listener) {
        String passwordEncoded = URLEncoder.encode(password);
        String finalRequest = REQUEST_URL + "/login?login=" + login + "&password=" + passwordEncoded;
        Log.d("URL", finalRequest);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
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

    public void getPhotoFromUrl(String url, final APIListener<Bitmap> listener) {
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap response) {
                listener.getResult(response);
            }
        }, 0, 0, null, null);
        _requestQueue.add(ir);
    }

    public void getUserData(String login, final APIListener<EpitechUser> listener)
    {
        String finalRequest = REQUEST_URL + "/user?token=" + _token + "&user=" + login;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            Gson gson = new GsonBuilder().create();
                            EpitechUser user = gson.fromJson(response.toString(), EpitechUser.class);
                            Log.d("USER", "User Login is "+ user.getLogin() + ", Title is " + user.getTitle() + ", email is " + user.getMail());
                            listener.getResult(user);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        _requestQueue.add(jsObjRequest);
    }

    public void getUserMessage(final APIListener<List<Message>> listener)
    {
        String finalRequest = REQUEST_URL + "/messages?token=" + _token;


        Log.d("PASSING", "Final Request is " + finalRequest);
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(
                Request.Method.GET, finalRequest, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Message>>(){}.getType();
                List<Message> messages = (List<Message>) gson.fromJson(array.toString(), listType);
                listener.getResult(messages);
            }
        }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        _requestQueue.add(jsArrayRequest);
    }

    public void getUserProject(final APIListener<List<ProjectOverview>> listener)
    {
        String finalRequest = REQUEST_URL + "/projects?token=" + _token;


        Log.d("PASSING", "Final Request is " + finalRequest);
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(
                Request.Method.GET, finalRequest, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ProjectOverview>>(){}.getType();
                List<ProjectOverview> projects = (List<ProjectOverview>) gson.fromJson(array.toString(), listType);
                listener.getResult(projects);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        _requestQueue.add(jsArrayRequest);
    }

    public void getPhotoUrl(String login, final APIListener<Bitmap> listener) {
        String finalRequest = REQUEST_URL + "/photo?token=" + _token + "&login=" + login;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getPhotoFromUrl(response.getString("url"), listener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.getMessage());
                    }
                });
        _requestQueue.add(jsObjRequest);
    }

    public void getPhotoUrlonly(String login, final APIListener<String> listener) {
        String finalRequest = REQUEST_URL + "/photo?token=" + _token + "&login=" + login;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            listener.getResult(response.getString("url"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.getMessage());
                    }
                });
        _requestQueue.add(jsObjRequest);
    }
}
