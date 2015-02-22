package com.dtu.engifest.events;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.toolbox.NetworkImageView;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.dtu.engifest.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by naman on 14/12/14.
 */

public class EventsFragment extends ScrollTabHolderFragment implements NotifyingScrollView.OnScrollChangedListener {

    private static final String ARG_POSITION = "position";


    private NotifyingScrollView mScrollView;
    private String title ;
    private String description ;
    private String subdescription ;
    private String contact ;
    private String number ;
    private String email ;
    private String image;

    TextView eventTitle;
    TextView eventShortDescription;
    TextView eventDescription;
    TextView textCall;
    TextView textSendEmail;
    TextView textContact;
    TextView textEmail;
    TextView textNumber;
    LinearLayout layout1;
    LinearLayout layout2;
    View v;


    JSONObject obj;
    NetworkImageView eventImage;
    private int mPosition;
    private CardView cardView;
    private CardView cardContact;
    private String URL_EVENTS = "http://engifest.dce.edu/api/events.php";

    public String loadJSONFRomCache() {

        String data = "";
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_EVENTS);
        if (entry != null) {
            // fetch the data from cache
            try {
                 data = new String(entry.data, "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

            return data;
    }


    public static Fragment newInstance(int position) {
        EventsFragment f = new EventsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.events_fragment, null);

        mScrollView = (NotifyingScrollView) v.findViewById(R.id.scrollview);
        mScrollView.setOnScrollChangedListener(this);
        layout1=(LinearLayout) v.findViewById(R.id.layout1);
        layout2=(LinearLayout) v.findViewById(R.id.layout2);
        eventDescription =(TextView) v.findViewById(R.id.eventDescription);
        eventShortDescription = (TextView) v.findViewById(R.id.eventShortDescription);
        eventTitle = (TextView) v.findViewById(R.id.eventTitle);
        textCall =(TextView) v.findViewById(R.id.textCall);
        textContact = (TextView) v.findViewById(R.id.textContact);
        textEmail = (TextView) v.findViewById(R.id.textEmail);
        textNumber =(TextView) v.findViewById(R.id.textNumber);
        textSendEmail = (TextView) v.findViewById(R.id.textSendEmail);
        cardContact = (CardView) v.findViewById(R.id.cardContact);


        textCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = textNumber.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(callIntent);
            }
        });

        textSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textEmail.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:"+email));
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(emailIntent);
            }
        });

        eventImage =(NetworkImageView) v.findViewById(R.id.eventImage);
        cardView =(CardView) v.findViewById(R.id.cardView);
        cardView.setPadding(30,30,30,30);
        
        if (NetworkUtil.isNetworkConnected(getActivity())){
            ImageView errorcloud = (ImageView) v.findViewById(R.id.errorcloud);
            TextView errortext = (TextView) v.findViewById(R.id.errortext);
            errorcloud.setVisibility(View.GONE);
            errortext.setVisibility(View.GONE);
        }
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_EVENTS);



        if (mPosition==0){
            try {

                 obj = new JSONObject(loadJSONFRomCache());
                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(0);

                title = obj2.getString("title");
                image = obj2.getString("image");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);

           }
        if (mPosition==1) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(1);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        if (mPosition==2) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(2);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        if (mPosition==3) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(3);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);

        }
        if (mPosition==4) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(4);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        if (mPosition==5) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(5);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.purple_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.purple_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }

        if (mPosition==6) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(6);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }

        if (mPosition==7) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(7);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }

        if (mPosition==8) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(8);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }

        if (mPosition==9) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(9);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }

        if (mPosition==10) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(10);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        if (mPosition==11) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(11);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        if (mPosition==12) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(12);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        if (mPosition==13) {
            try {

                    obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(13);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        if (mPosition==14) {
            try {

                obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(14);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        if (mPosition==15) {
            try {

                obj = new JSONObject(loadJSONFRomCache());

                JSONArray array  = obj.getJSONArray("content");
                JSONObject obj2 = array.getJSONObject(15);
                image = obj2.getString("image");
                title = obj2.getString("title");
                description = obj2.getString("description");
                subdescription = obj2.getString("subtitle");
                contact = obj2.getString("contactname");
                number = obj2.getString("contactnumber");
                email = obj2.getString("contactemail");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventImage.setImageUrl(image,AppController.getInstance().getImageLoader());
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            if (textContact.getText().equals("null"))
                cardContact.setVisibility(View.GONE);


        }
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY)
    {
        mScrollView.setScrollY(headerTranslationY - scrollHeight);
    }

    @Override
    public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt)
    {
        if (mScrollTabHolder != null)
            mScrollTabHolder.onScroll(view, l, t, oldl, oldt, mPosition);

    }



}
