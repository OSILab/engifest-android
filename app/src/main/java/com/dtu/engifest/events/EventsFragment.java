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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

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



    ImageView eventImage;
    private int mPosition;
    private CardView cardView;



    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open("events.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

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
        View v = inflater.inflate(R.layout.events_fragment, null);

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

        if (mPosition==0){

            try{
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
            eventImage.setImageResource(R.drawable.anusthaan);}
        if (mPosition==1) {
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            eventDescription.setText("Dance is the hidden language of soul.Dance is performed in many cultures as a form of emotional expression,social interaction or exercise,in a spritual or performance setting,and is sometimes used to express ideas or tell a story.\nSpandan is a western dance competition and houses three categories.\n1.Solo\n2.Duet\n3.Group");
            eventTitle.setText("Spandan");
            eventShortDescription.setText("The Western dance competition");
            eventImage.setImageResource(R.drawable.spandan);

        }
        if (mPosition==2) {
            layout1.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            eventDescription.setText("Natya represents theatre and theatrically is a powerful tool.Theatre is a collaborative form of fine art that uses live performers to represent the experience of a real or imagined event before a live audience in a specific place.The right combination of Lights,Camera and most importantly Action.Various genres are Comedy, Farce, Satirical, Tragedy and historical. ");
            eventTitle.setText("Natya");
            eventShortDescription.setText("The stage play competition");
            eventImage.setImageResource(R.drawable.natya);

        }
        if (mPosition==3) {
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            eventDescription.setText("Nukkad represents Street Theatre. It is a form of theatrical performance and presentation in outdoor public spaces without a specific paying audience. Street theatre is arguably the oldest form of theatre in existence: most mainstream entertainment mediums can be traced back to origins in street performing, including religious passion plays and many other forms. Nukkad makes an effort for upliftment of society by spreading awareness and propagating the morals and ideals that society is in need of. Besides being an art of acting and drama, the street theatre is all about changes one can create in the society. ");
            eventTitle.setText("Nukkad");
            eventShortDescription.setText("The street play competition");
            eventImage.setImageResource(R.drawable.nukkad);

        }
        if (mPosition==4) {
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText("B.O.B brings together rock lovers from all over the country for an electrifying experience, Ncourtesy some of the most talented amateur rock bands in the land. It is a live music competition joined by bands from all over the country. It is open to bands of all music genres. With a combination of adrenaline charged performances, the event is a hot favorite amongst youngsters.");
            eventTitle.setText("Arpeggio");
            eventShortDescription.setText("Battle of Bands");
            eventImage.setImageResource(R.drawable.arpeggio);

        }
        if (mPosition==5) {
            layout1.setBackgroundColor(getResources().getColor(R.color.purple_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.purple_transparent));
            eventDescription.setText("\"Music is a world within itself, with a language we all understand.\" — Stevie Wonder \n\nNo festival is complete without music. Soundtrack is the vocal music competition & it houses three categories:\n1. Indian Vocals (Solo)\n2. Western Vocals (Solo)\n3. Instrumentals (Solo) ");
            eventTitle.setText("Soundtrack");
            eventShortDescription.setText("The Music competition");
            eventImage.setImageResource(R.drawable.soundtrack);

        }
        if (mPosition==6) {
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            eventDescription.setText("Switch The Funk Up is Street Dance. It refers to refers to dance styles—regardless of country of origin—that evolved outside of dance studios in any available open space such as streets, dance parties, block parties, parks, school yards etc. There are five categories in STFU:\n1. One on One Popping Battle\n2. One on One Breaking Battle\n3. Two on Two All Styles Battle\n4. Crew on Crew All Styles Battle\n5. Group Street Dance Showdown ");
            eventTitle.setText("Switch the funk up");
            eventShortDescription.setText("The dance competition");
            eventImage.setImageResource(R.drawable.switchthefunkup);

        }
        if (mPosition==7) {
            layout1.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            eventDescription.setText("Star Power- in its most tangible form. Bringing the Engifest to a spectacular end is Livewire. Famed personalities such as Mohit Chauhan, Hard Kaur, Harbhajan Mann, Surat Jagan, Judy D and Shibani Kashgap have graced the event, giving us an evening of star-studd splendour. It is the most antici-pated event of the fest, attracting huge crowds and addi g a generous splash of glamour. ");
            eventTitle.setText("Livewire");
            eventShortDescription.setText("The Star Night");
            eventImage.setImageResource(R.drawable.livewire);

        }
        if (mPosition==8) {
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            eventDescription.setText("The finest exposition showcasing the sartorial skills of budding designers across colleges, Pandhan creates new benchmarks and places the Delhi youth firmly in their rightful place as inhabitants of the fashion capital of India. The event continues to grow remarkably in stature, seeing ever-increasing participation and attendance, giving a tremendous platform for brand promotion. ");
            eventTitle.setText("Paridhan");
            eventShortDescription.setText("The Fashion Parade");
            eventImage.setImageResource(R.drawable.fashionp);

        }

        if (mPosition==9) {
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            eventDescription.setText("Words are a lens to focus one's mind... And this is what these competitions are all about! Parliamentary debate brings together strong debaters from all over the country for a very competitive experience. It provides a stage to showcase not only your speaking and debating skills but also time management and teamwork skills. Creative writing competitions let writers dance above the surface of the world and let their thoughts lift them into creativity that is not hampered by opinion. Mixed bag is a two-stage competition, with the prelims consisting of Word Games and Extempore and the final round being an extremely random buzzer round! interactive sessions with Brilliant minds like Pratyush Pushkar,Devapriya Roy and Ravinder Singh have been conducted in the past, and we will continue to conduct such events! We may surprise you with none other than the great Ruskin Bond and Chetan Bhagat! ");
            eventTitle.setText("Sahitya");
            eventShortDescription.setText("The literary competitions");
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
