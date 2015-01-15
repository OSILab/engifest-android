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

import com.dtu.engifest.R;
import com.dtu.engifest.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

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
    SmoothProgressBar progressBar;
    private static String url = "http://engifesttest.comlu.com/events";

    ImageView eventImage;
    private int mPosition;
    private CardView cardView;


    public String loadJSONFromAsset() {

        String jsonString = "";
        try {
            String currentLine;
            File cacheFile = new File(getActivity().getFilesDir(), "events.json");

            BufferedReader br = new BufferedReader(new FileReader(cacheFile));
            while ((currentLine = br.readLine()) != null) {
                jsonString += currentLine + '\n';
            }
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();

        }

            return jsonString;
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

        eventImage =(ImageView) v.findViewById(R.id.eventImage);
        cardView =(CardView) v.findViewById(R.id.cardView);
        cardView.setPadding(30,30,30,30);
        
        if (NetworkUtil.isNetworkConnected(getActivity())){
            ImageView errorcloud = (ImageView) v.findViewById(R.id.errorcloud);
            TextView errortext = (TextView) v.findViewById(R.id.errortext);
            errorcloud.setVisibility(View.GONE);
            errortext.setVisibility(View.GONE);
        }
            



        if (mPosition==0){
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject anusthhan  = obj.getJSONObject("anusthaan");

                title = anusthhan.getString("title");
                description = anusthhan.getString("description");
                subdescription = anusthhan.getString("subtitle");
                contact = anusthhan.getString("contactname");
                number = anusthhan.getString("contactnumber");
                email = anusthhan.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.anusthaan);
           }
        if (mPosition==1) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject spandan  = obj.getJSONObject("spandan");

                title = spandan.getString("title");
                description = spandan.getString("description");
                subdescription = spandan.getString("subtitle");
                contact = spandan.getString("contactname");
                number = spandan.getString("contactnumber");
                email = spandan.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.spandan);


        }
        if (mPosition==2) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject natya  = obj.getJSONObject("natya");

                title = natya.getString("title");
                description = natya.getString("description");
                subdescription = natya.getString("subtitle");
                contact = natya.getString("contactname");
                number = natya.getString("contactnumber");
                email = natya.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.natya);


        }
        if (mPosition==3) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject nukkad  = obj.getJSONObject("nukkad");

                title = nukkad.getString("title");
                description = nukkad.getString("description");
                subdescription = nukkad.getString("subtitle");
                contact = nukkad.getString("contactname");
                number = nukkad.getString("contactnumber");
                email = nukkad.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.nukkad);

        }
        if (mPosition==4) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject arpeggio  = obj.getJSONObject("arpeggio");

                title = arpeggio.getString("title");
                description = arpeggio.getString("description");
                subdescription = arpeggio.getString("subtitle");
                contact = arpeggio.getString("contactname");
                number = arpeggio.getString("contactnumber");
                email = arpeggio.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.arpeggio);


        }
        if (mPosition==5) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject soundtrack  = obj.getJSONObject("soundtrack");

                title = soundtrack.getString("title");
                description = soundtrack.getString("description");
                subdescription = soundtrack.getString("subtitle");
                contact = soundtrack.getString("contactname");
                number = soundtrack.getString("contactnumber");
                email = soundtrack.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.purple_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.purple_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.soundtrack);


        }

        if (mPosition==6) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject switchthefunkup  = obj.getJSONObject("switchthefunkup");

                title = switchthefunkup.getString("title");
                description = switchthefunkup.getString("description");
                subdescription = switchthefunkup.getString("subtitle");
                contact = switchthefunkup.getString("contactname");
                number = switchthefunkup.getString("contactnumber");
                email = switchthefunkup.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.switchthefunkup);


        }

        if (mPosition==7) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject livewire  = obj.getJSONObject("livewire");

                title = livewire.getString("title");
                description = livewire.getString("description");
                subdescription = livewire.getString("subtitle");
                contact = livewire.getString("contactname");
                number = livewire.getString("contactnumber");
                email = livewire.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.livewire);


        }

        if (mPosition==8) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject paridhan  = obj.getJSONObject("paridhan");

                title = paridhan.getString("title");
                description = paridhan.getString("description");
                subdescription = paridhan.getString("subtitle");
                contact = paridhan.getString("contactname");
                number = paridhan.getString("contactnumber");
                email = paridhan.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.fashionp);


        }

        if (mPosition==9) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONObject sahitya  = obj.getJSONObject("sahitya");

                title = sahitya.getString("title");
                description = sahitya.getString("description");
                subdescription = sahitya.getString("subtitle");
                contact = sahitya.getString("contactname");
                number = sahitya.getString("contactnumber");
                email = sahitya.getString("contactemail");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            eventDescription.setText(description);
            eventTitle.setText(title);
            eventShortDescription.setText(subdescription);
            textContact.setText(contact);
            textNumber.setText(number);
            textEmail.setText(email);
            eventImage.setImageResource(R.drawable.sahitya);


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
