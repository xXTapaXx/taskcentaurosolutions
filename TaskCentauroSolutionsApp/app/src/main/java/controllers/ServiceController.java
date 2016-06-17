package controllers;

import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;



/**
 * Created by dcortess on 9/4/15.
 */
public class ServiceController {

    String url;
    int method;
    Map<String, String> params;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void jsonObjectRequest(String url, int method, Map<String, String> params, Response.Listener<JSONObject> response, Response.ErrorListener error) {
        this.url = url;
        this.method = method;
        this.params = params;
        String tag_json_obj = "json_obj_req";

        JsonObjectRequest objectRequest;

        if (this.getParams() != null) {
            objectRequest = new JsonObjectRequest(this.getMethod(), this.getUrl(), new JSONObject(this.getParams()), response, error);
        } else {
            objectRequest = new JsonObjectRequest(this.getMethod(), this.getUrl(), response, error);
        }

        // {}
        // []

        AppController.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    public void jsonArrayRequest(String url, int method, Map<String, String> params, Response.Listener<JSONArray> response, Response.ErrorListener error) {
        this.url = url;
        this.method = method;
        this.params = params;

        String tag_json_arry = "json_array_req";

        JsonArrayRequest arrayRequest;

        if (this.getParams() != null) {
            arrayRequest = new JsonArrayRequest(this.getMethod(), this.getUrl(), new JSONObject(this.getParams()), response, error);
        } else {
            arrayRequest = new JsonArrayRequest(this.getMethod(), this.getUrl(), response, error);
        }
        //Arreglo []
        AppController.getInstance().addToRequestQueue(arrayRequest, tag_json_arry);
    }

    public void stringRequest(String url, int method, Map<String, String> params, Response.Listener<String> response, Response.ErrorListener error) {
        this.url = url;
        this.method = method;
        this.params = params;

        String tag_string_req = "string_req";

        StringRequest strReq;

        strReq = new StringRequest(this.getMethod(), this.getUrl(), response, error);

        //String
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void stringRequest(String url, int method, Map<String, String> params, Response.Listener<String> response, Response.ErrorListener error,String tag) {
        this.url = url;
        this.method = method;
        this.params = params;

        StringRequest strReq;

        strReq = new StringRequest(this.getMethod(), this.getUrl(), response, error);

        //String
        AppController.getInstance().addToRequestQueue(strReq, tag);
    }

    public void imageRequest(String url, ImageView imageView, int icon_loading, int icon_error) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView, icon_loading, icon_error));
    }

}
