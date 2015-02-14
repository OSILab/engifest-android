package in.ac.dtu.engifest;

/**
 * Created by omerjerk on 31/12/13.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardAdapter;

/**
 * Created by omerjerk on 31/12/13.
 */
public class EventsCardAdapter extends CardAdapter<Card> {

    private final static String TAG = "EventsCardAdapter";
    int position;

    private Activity context;
    public EventsCardAdapter(Activity context, int position) {
        super(context, R.layout.card_event);

        this.context = context;
        this.position = position;
    }

    @Override
    protected boolean onProcessThumbnail(ImageView icon, Card card) {
        // Optional, you can modify properties of the icon ImageView here.
        return super.onProcessThumbnail(icon, card);
    }

    @Override
    protected boolean onProcessTitle(TextView title, Card card, int accentColor) {
        // Optional, you can modify properties of the title textview here.
        return super.onProcessTitle(title, card, accentColor);
    }

    @Override
    protected boolean onProcessContent(TextView content, Card card) {
        // Optional, you can modify properties of the content textview here.
        content.setTextColor(Color.DKGRAY);
        return super.onProcessContent(content, card);
    }

    @Override
    protected boolean onProcessMenu(View view, final Card card) {
        // Sets up a card's menu (custom_card.xml makes this a star)
        return super.onProcessMenu(view, card);
    }

    @Override
    public View onViewCreated(final int index, View recycled, Card item) {

        if(recycled == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            recycled = inflater.inflate(R.layout.card_event, null);
        }
        ImageView mImageView = (ImageView) recycled.findViewById(R.id.image_event);
        if(mImageView != null) {
            mImageView.setBackgroundDrawable(Utils.getEventDrawable(context, Utils.eventNamesDay[position][index - 1]));
        }

        return super.onViewCreated(index, recycled, item);
    }

}
