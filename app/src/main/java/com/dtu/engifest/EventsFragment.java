package com.dtu.engifest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class EventsFragment extends ScrollTabHolderFragment implements NotifyingScrollView.OnScrollChangedListener {

    private static final String ARG_POSITION = "position";


    private NotifyingScrollView mScrollView;

    TextView eventTitle;
    TextView eventShortDescription;
    TextView eventDescription;
    ImageView eventImage;
    private int mPosition;
    private CardView cardView;

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

        eventDescription =(TextView) v.findViewById(R.id.eventDescription);
        eventShortDescription = (TextView) v.findViewById(R.id.eventShortDescription);
        eventTitle = (TextView) v.findViewById(R.id.eventTitle);
        eventImage =(ImageView) v.findViewById(R.id.eventImage);
        cardView =(CardView) v.findViewById(R.id.cardView);
        cardView.setPadding(30,30,30,30);

        if (mPosition==0){
            eventDescription.setText("Dance in India comprises of varied styles of dances.Classical Indian dance manages to evoke a rasa by invoking a particular bhava (emotion).Classical dancer acts out a story alost exclusively through gestures.Folk dances are numerous in number and styles,and vary according to the local tradition of the respective state,ethnic or geographic regions.\nAnusthaan houses two categories:\n1.Solo\n2.Group");
            eventTitle.setText("Anushthaan");
            eventShortDescription.setText("Classical and Folk dance competition");
            eventImage.setImageResource(R.drawable.anusthaan);}
        if (mPosition==1) {
            eventDescription.setText("Dance is the hidden language of soul.Dance is performed in many cultures as a form of emotional expression,social interaction or exercise,in a spritual or performance setting,and is sometimes used to express ideas or tell a story.\nSpandan is a western dance competition and houses three categories.\n1.Solo\n2.Duet\n3.Group");
            eventTitle.setText("Spandan");
            eventShortDescription.setText("The Western dance competition");
            eventImage.setImageResource(R.drawable.spandan);

        }
        if (mPosition==2) {
            eventDescription.setText("Natya represents theatre and theatrically is a powerful tool.Theatre is a collaborative form of fine art that uses live performers to represent the experience of a real or imagined event before a live audience in a specific place.The right combination of Lights,Camera and most importantly Action.Various genres are Comedy, Farce, Satirical, Tragedy and historical. ");
            eventTitle.setText("Natya");
            eventShortDescription.setText("The stage play competition");
            eventImage.setImageResource(R.drawable.natya);

        }
        if (mPosition==3) {
            eventDescription.setText("Nukkad represents Street Theatre. It is a form of theatrical performance and presentation in outdoor public spaces without a specific paying audience. Street theatre is arguably the oldest form of theatre in existence: most mainstream entertainment mediums can be traced back to origins in street performing, including religious passion plays and many other forms. Nukkad makes an effort for upliftment of society by spreading awareness and propagating the morals and ideals that society is in need of. Besides being an art of acting and drama, the street theatre is all about changes one can create in the society. ");
            eventTitle.setText("Nukkad");
            eventShortDescription.setText("The street play competition");
            eventImage.setImageResource(R.drawable.nukkad);

        }
        if (mPosition==4) {
            eventDescription.setText("B.O.B brings together rock lovers from all over the country for an electrifying experience, Ncourtesy some of the most talented amateur rock bands in the land. It is a live music competition joined by bands from all over the country. It is open to bands of all music genres. With a combination of adrenaline charged performances, the event is a hot favorite amongst youngsters.");
            eventTitle.setText("Arpeggio");
            eventShortDescription.setText("Battle of Bands");
            eventImage.setImageResource(R.drawable.arpeggio);

        }
        if (mPosition==5) {
            eventDescription.setText("\"Music is a world within itself, with a language we all understand.\" — Stevie Wonder \n\nNo festival is complete without music. Soundtrack is the vocal music competition & it houses three categories:\n1. Indian Vocals (Solo)\n2. Western Vocals (Solo)\n3. Instrumentals (Solo) ");
            eventTitle.setText("Soundtrack");
            eventShortDescription.setText("The Music competition");
            eventImage.setImageResource(R.drawable.soundtrack);

        }
        if (mPosition==6) {
            eventDescription.setText("Switch The Funk Up is Street Dance. It refers to refers to dance styles—regardless of country of origin—that evolved outside of dance studios in any available open space such as streets, dance parties, block parties, parks, school yards etc. There are five categories in STFU:\n1. One on One Popping Battle\n2. One on One Breaking Battle\n3. Two on Two All Styles Battle\n4. Crew on Crew All Styles Battle\n5. Group Street Dance Showdown ");
            eventTitle.setText("Switch the funk up");
            eventShortDescription.setText("The dance competition");
            eventImage.setImageResource(R.drawable.switchthefunkup);

        }
        if (mPosition==7) {
            eventDescription.setText("Star Power- in its most tangible form. Bringing the Engifest to a spectacular end is Livewire. Famed personalities such as Mohit Chauhan, Hard Kaur, Harbhajan Mann, Surat Jagan, Judy D and Shibani Kashgap have graced the event, giving us an evening of star-studd splendour. It is the most antici-pated event of the fest, attracting huge crowds and addi g a generous splash of glamour. ");
            eventTitle.setText("Livewire");
            eventShortDescription.setText("The Star Night");
            eventImage.setImageResource(R.drawable.livewire);

        }
        if (mPosition==8) {
            eventDescription.setText("The finest exposition showcasing the sartorial skills of budding designers across colleges, Pandhan creates new benchmarks and places the Delhi youth firmly in their rightful place as inhabitants of the fashion capital of India. The event continues to grow remarkably in stature, seeing ever-increasing participation and attendance, giving a tremendous platform for brand promotion. ");
            eventTitle.setText("Paridhan");
            eventShortDescription.setText("The Fashion Parade");
            eventImage.setImageResource(R.drawable.fashionp);

        }

        if (mPosition==9) {
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
