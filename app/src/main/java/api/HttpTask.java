package api;

import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by Harit Moolphunt on 18/7/2560.
 */

public class HttpTask extends AsyncTask<String, Void, JSONArray> {

    private OnFeedListener listener;

    public HttpTask(OnFeedListener listener)
    {
        this.listener = listener;
    }

    protected JSONArray doInBackground(String... urls)   {

        //restURL = "http://maxile.ddns.net:11111/wordpress/wp-json/wc/v2/products";
        OAuthService service = new ServiceBuilder()
                .provider(WooCommerceApi.class)
                .apiKey("ck_5e3b31f3effefacab6f8017696bff3e5049baf91") //Your Consumer key
                .apiSecret("cs_8ad3c4396de7458c28d5ec5acfcf1a9b315ac5c0") //Your Consumer secret
                .scope("API.Public") //fixed
                .signatureType(SignatureType.QueryString)
                .build();
        OAuthRequest request = new OAuthRequest(Verb.GET, urls[0]);
        Token accessToken = new Token("", ""); //not required for context.io
        service.signRequest(accessToken, request);
        Response response = request.send();
        Log.d("OAuthTask",response.getBody());
        String json = response.getBody();

        try {
            JSONArray array = new JSONArray(json);

            //Log.d("parse arr", object.toString());
            //JSONObject obj = object.getJSONObject(0);
            //JSONArray array = obj.optJSONArray("posts");
            Log.d("parse arr", array.toString());
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onProgressUpdate(Integer... values) {

    }
    protected void onPostExecute(JSONArray array)  {
        super.onPostExecute(array);

        if(null == array)
        {
            return ;
        }

        if(null != listener)
        {
            listener.onFeed(array);
        }

    }
}

