package epitech.epiandroid;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by girard_s on 11/01/2016.
 */
public interface APIListener<T> {
    public void getResult(T object);
}
