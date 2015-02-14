package in.ac.dtu.engifest;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by omerjerk on 25/12/13.
 */
public class Utils {

    public static boolean isNetworkConnected(Context context) {

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni == null) {
                // There are no active networks.
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);

        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    public static String[][] eventNamesDay = {
            {"anusthaan", "spandan",  "dirt"},
            {"bob", "natya", "stfu"},
            {"nukkad", "soundtrack" }
    };

    public static String[][] eventDesc = {
            {
                    "Dance in India comprises the varied styles of dances. Classical Indian dance manages to evoke a rasa by invoking a particular bhava (emotion). Classical dancer acts out a story almost exclusively through gestures. Folk dances are numerous in number and style, and vary according to the local tradition of the respective state, ethnic or geographic regions.\n" +
                            "Anushthaan houses two categories:\n" +
                            "1.  Solo\n" +
                            "2. Group",
                    "“Dance is the hidden language of the soul.”\n" +
                            "Dance is performed in many cultures as a form of emotional expression, social interaction, or exercise, in a spiritual or performance setting, and is sometimes used to express ideas or tell a story. Spandan houses three categories:\n" +
                            "1. Solo\n" +
                            "2.  Duet\n" +
                            "3. Group"},
            {
                    "B.O.B brings together rock lovers from all over the country for an electrifying experience, courtesy some of the most talented amateur rock bands in the land. It is a live music competition joined by bands from all over the country. It is open to bands of all music genres. With a combination of adrenaline charged performances, the event is a hot favorite amongst youngsters.",
                    "Natya represents Theatre and Theatricality is a powerful tool. Theatre is a collaborative form of fine art that uses live performers to present the experience of a real or imagined event before a live audience in a specific place. The right combination of “Lights”, “Camera” & most importantly “Action”. Various genres are Comedy, Farce, Satirical, Tragedy & Historical.",
                    "Street Dance refers to refers to dance styles - regardless of country of origin - that evolved outside of dance studios in any available open space such as streets, dance parties, block parties, parks, school yards etc.\n" +
                            "There are five categories in STFU:\n" +
                            "1. One on One Popping Battle\n" +
                            "2. One on One Breaking Battle\n" +
                            "3. Two on Two All Styles Battle\n" +
                            "4. Crew on Crew All Styles Battle\n" +
                            "5. Group Street Dance Showdown"},
            {
                    "Nukkad represents Street Theatre. It is a form of theatrical performance and presentation in outdoor public spaces without a specific paying audience. Street theatre is arguably the oldest form of theatre in existence: most mainstream entertainment mediums can be traced back to origins in street performing, including religious passion plays and many other forms.",
                    "“Music is a world within itself, with a language we all understand.” – Stevie Wonder\n" +
                            "No festival is complete without music. Soundtrack is the vocal music competition & it houses three categories:\n" +
                            "1. Indian Vocals (Solo)\n" +
                            "2. Western Vocals (Solo)\n" +
                            "3. Instrumentals (Solo)\n" +
                            "4. Indian (Group)\n" +
                            "5. Western (Group)"}
    };

    public static String[][] contacts = {
            {
                    "Swapna: +91-9999162121\n" +
                    "rendezvous.swapna@gmail.com\n" +
                    "Dharini: +91-9971950857\n" +
                    "dharinibaswal33@gmail.com",
                    "Akshay Verma: +91-9899562505\n" +
                            "akshayverma195@gmail.com"
            },
            {
                    "Parv Rustogi: +91-9971930089\n" +
                            "Harsh Choudhary: +91-9717359874\n" +
                            "arpeggio.2014@gmail.com",
                    "Arpit Vashist: +91-8860729255\n" +
                            "arpitvashist@gmail.com",
                    "Kshitiz Mehra: +91-9910558251\n" +
                            "kshitijmehra7@gmail.com"
            },
            {
                    "Akshay Gupta: +91-9911209191\n" +
                            "akshay101991@gmail.com",
                    "Shivam: +91-9013496087 (For Solo events)\n" +
                            "shivamdtu@gmail.com \n" +
                            "Mridul Mittal: +91-9818479628 (For Group events)\n" +
                            "mridul12july@gmail.com"
            }
    };

    public static Drawable getEventDrawable (Context context, String eventName) {
        eventName = eventName.toLowerCase();
        int eventDrawableId = context.getResources().getIdentifier(eventName, "drawable", context.getPackageName());
        Drawable eventDrawable = context.getResources().getDrawable(eventDrawableId);
        return eventDrawable;
    }
}
