package com.jwplayer.opensourcedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import api.HttpTask;
import api.OnFeedListener;
import api.WooCommerceApi;

import static org.scribe.model.OAuthConstants.CONSUMER_KEY;
import static org.scribe.model.OAuthConstants.CONSUMER_SECRET;

public class FeedActivity extends AppCompatActivity implements OnFeedListener{

    ListView listItem;
    ArrayList<Post> posts;
    FeedAdapter adepter;
    //Post[] arraypost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        listItem = (ListView) findViewById(R.id.listView);
        posts = new ArrayList<>();
        adepter = new FeedAdapter(getApplicationContext(),R.layout.layout_feed);

        listItem.setAdapter(adepter);

        HttpTask task = new HttpTask(this);
        Log.d("err", "5555");
        task.execute("http://maxile.ddns.net:11111/wordpress/wp-json/wc/v2/products");


        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(FeedActivity.this, ItemActivity.class);
                Log.d("position", Integer.toString(position));
                //posts.get(position);
                intent.putExtra("title", posts.get(position).title);
                intent.putExtra("desc", posts.get(position).desc);
                intent.putExtra("price", posts.get(position).price);
                intent.putExtra("url", posts.get(position).thumbnail);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onFeed(JSONArray array) {
        int length = array.length();
        JSONObject imageobject = null;
        JSONArray imageArray;
        for(int i=0;i<length;++i)
        {
            JSONObject object = array.optJSONObject(i);
            Spanned dese = Html.fromHtml(object.optString("description"));

            try {
                imageArray = new JSONArray(object.optString("images"));
                imageobject = imageArray.optJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String price = object.optString("price");

            Post post = new Post(object.optString("name"),dese.toString(),imageobject.optString("src"),price+" บาท");
            Log.d("postname", post.thumbnail);
           // arraypost[i] = post;
            posts.add(post);
        }

        adepter.addAll(posts);
        adepter.notifyDataSetChanged();
    }

    public class FeedAdapter extends ArrayAdapter<Post>
    {
        int resource;
        public FeedAdapter(Context context,int resource) {
            super(context, resource);
            this.resource = resource;
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(null == convertView)
            {
                LayoutInflater inflater =LayoutInflater.from(getContext());
                convertView = inflater.inflate(resource, null);
            }

            Post post = getItem(position);

            TextView title = (TextView) convertView.findViewById(R.id.title);
           // TextView desc = (TextView) convertView.findViewById(R.id.desc);
            TextView price = (TextView) convertView.findViewById(R.id.price);
            ImageView image = (ImageView) convertView.findViewById(R.id.thumbnail);
            title.setText(post.title);
            price.setText(post.price);
            Glide.with(getContext()).load(post.thumbnail).into(image);

            return convertView;
        }
    }

    public class Post
    {
        public String title;
        public String desc;
        public String thumbnail;
        public String price;

        public Post(String title,String desc,String thumbnail,String price)
        {
            this.title = title;
            this.desc = desc;
            this.thumbnail = thumbnail;
            this.price = price;
        }
    }
}
