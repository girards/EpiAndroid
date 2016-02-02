package epitech.epiandroid;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.CompoundButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
                        Log.d("USER", "User Login is " + user.getLogin() + ", Title is " + user.getTitle() + ", email is " + user.getMail());
                        JSONArray gpa = null;
                        JSONObject stringGpa = null;
                        JSONObject log = null;
                        String logActive = null;
                        String logIdle = null;
                        try {
                            gpa = response.getJSONArray("gpa");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            stringGpa = gpa.getJSONObject(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setGpa(stringGpa.getString("gpa"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            log = response.getJSONObject("nsstat");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            logActive = log.getString("active");
                            logIdle = log.getString("idle");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        user.setLog(logActive.toString(), logIdle.toString());
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

    public void getUserGrades(final APIListener<List<Grade>> listener)
    {
        String finalRequest = REQUEST_URL + "/marks?token=" + _token;

        JsonObjectRequest jsObjectRequest = new JsonObjectRequest(
                Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                JSONArray array = null;
                try {
                    array = object.getJSONArray("notes");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Grade>>(){}.getType();
                List<Grade> grades = (List<Grade>) gson.fromJson(array.toString(), listType);
                Collections.reverse(grades);
                Iterator<Grade> it = grades.iterator();
                while (it.hasNext()) {
                    Grade g = it.next();
                    if (g.getComment() == null)
                        continue;
                    if (g.getComment().equals("[INTRA] not registered") == true) {
                        it.remove();
                    }
                }
                listener.getResult(grades);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getLocalizedMessage());
            }
        });
        _requestQueue.add(jsObjectRequest);
    }

    public void getProjectData(String scolarYear, String codeModule, String codeInstance, String codeActivity, final APIListener<Project> listener) {
        String finalRequest = REQUEST_URL + "/project?token=" + _token
                                            + "&scolaryear=" + scolarYear
                                            + "&codemodule=" + codeModule
                                            + "&codeinstance=" + codeInstance
                                            + "&codeacti=" + codeActivity;

        Log.d("GetPROJECTDATA", "Final Request is " + finalRequest);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new GsonBuilder().create();
                Project project = gson.fromJson(response.toString(), Project.class);
                listener.getResult(project);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error in on ErrorResponse", error.getMessage());
            }
        });
        _requestQueue.add(jsObjRequest);
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

    public void getUserModule(final int semester, final APIListener<List<ModuleOverview>> listener) {
        String finalRequest = REQUEST_URL + "/modules?token=" + _token;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, finalRequest, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        JSONArray array =  response.getJSONArray("modules");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<ModuleOverview>>() {
                        }.getType();
                        List<ModuleOverview> modules = (List<ModuleOverview>) gson.fromJson(array.toString(), listType);
                        Iterator<ModuleOverview> it = modules.iterator();
                        if (semester == -1);
                        else
                        {
                            while (it.hasNext()) {
                                ModuleOverview m = it.next();
                                if (m.getSemester() != semester) {
                                    it.remove();
                                }
                            }
                        }
                        listener.getResult(modules);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.getMessage());
                    }
                });
        _requestQueue.add(jsObjRequest);
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


    public void registerToProject(final Project project, final APIListener<Boolean> listener) {
        String finalRequest = REQUEST_URL + "/project";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalRequest,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        listener.getResult(true);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        listener.getResult(false);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("token", _token);
                params.put("scolaryear", project.get_scolarYear());
                params.put("codeinstance", project.get_codeInstance());
                params.put("codemodule", project.get_moduleCode());
                params.put("codeacti", project.get_codeActi());
                return params;
            }
        };
        _requestQueue.add(stringRequest);
    }

    /* public void enterToken(final Event event, final String tokenEntered, final APIListener<Boolean> listener) {
        String finalRequest = REQUEST_URL + "/token";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalRequest,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        listener.getResult(true);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        listener.getResult(false);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("token", _token);
                params.put("scolaryear", event.getScolarYear());
                params.put("codemodule", event.getCodeModule());
                params.put("codeinstance", event.getCodeInstance());
                params.put("codeactic", event.getCodeActi());
                params.put("codeevent", event.getCodeEvent());
                params.put("tokenvalidationcode", tokenEntered);
                return params;
            }
        };
        _requestQueue.add(stringRequest);
    } */

    public void registerToModule(final ModuleOverview module, final APIListener<Boolean> listener) {
        String finalRequest = REQUEST_URL + "/module";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalRequest,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        listener.getResult(true);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        listener.getResult(false);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("token", _token);
                params.put("scolaryear", module.getScolarYear());
                params.put("codemodule", module.getCodeModule());
                params.put("codeinstance", module.getCodeInstance());
                return params;
            }
        };
        _requestQueue.add(stringRequest);
    }


    public void getEvents(String begin, String end, final APIListener<List<Event>> listener)
    {
//        String finalRequest = REQUEST_URL + "/planning?token=" + _token + "&start=" + begin + "&end=" + end;//&start=2016-01-17&end=2016-01-22
        String finalRequest = REQUEST_URL + "/planning?token=" + _token + "&start=2016-02-01&end=2016-02-08";//&start=2016-01-17&end=2016-01-22

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, finalRequest, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Event>>(){}.getType();
                List<Event> events = (List<Event>) gson.fromJson(array.toString(), listType);
                listener.getResult(events);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        });
        _requestQueue.add(jsArrayRequest);
    }

    public void useToken(final Event e, final String mToken, final APIListener<Boolean> listener) {
        String finalRequest = REQUEST_URL + "/token";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalRequest,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        listener.getResult(true);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        listener.getResult(false);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("token", _token);
                params.put("scolaryear", e.get_scolaryear());
                params.put("codemodule", e.get_codeModule());
                params.put("codeinstance", e.get_codeInstance());
                params.put("codeacti", e.get_codeActi());
                params.put("codeevent", e.get_codeEvent());
                params.put("tokenvalidationcode", mToken);
                return params;
            }
        };
        _requestQueue.add(stringRequest);
    }
}
