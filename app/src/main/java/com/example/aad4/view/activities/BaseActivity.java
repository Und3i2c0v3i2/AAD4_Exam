package com.example.aad4.view.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.aad4.App;
import com.example.aad4.R;
import com.example.aad4.db.DBRepositoryImpl;
import com.example.aad4.entity.Comment;
import com.example.aad4.entity.TouristAttraction;
import com.example.aad4.prefs.PrefsRepository;
import com.example.aad4.util.AboutDialogUtil;
import com.example.aad4.util.ConfirmationDialogUtil;
import com.example.aad4.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.aad4.prefs.PrefsRepository.TOAST_KEY;
import static com.example.aad4.util.AboutDialogUtil.isAboutShowing;

public class BaseActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {


    /* for fragment transaction backstack so we can navigate */
    public static final String HOME_FRAGMENT = "home_fragment";
    public static final String DETAILS_FRAGMENT = "details_fragment";


    public DBRepositoryImpl dbRepository;
    protected PrefsRepository prefsRepository;

    protected AlertDialog aboutDialog;
    protected AlertDialog confirmationDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbRepository = App.getDbRepository();
        prefsRepository = App.getPrefsRepository();

//        insertDummyData();
    }


    /* ************* FRAGMENT TRANSACTION *********** */
    protected void fragmentTransaction(Fragment fragment, String addToBackStack) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(addToBackStack)
                .commit();
    }

    /* ********** CHECK WHETHER TO SHOW TOASTS ************* */
    protected void checkPrefs(Context context, String title, String text) {
        boolean b = prefsRepository.isAllowed(TOAST_KEY);
        if (b) {
            ToastUtil.showToast(context, title + ": " + text);
        }

    }



    /* ******************* ALERT DIALOG *************** */

    protected void showAboutDialog(Context context) {
        aboutDialog = AboutDialogUtil.showDialog(context);
        // prevent closing on back pressed
        aboutDialog.setCancelable(false);
        // prevent closing when clicked outside
        aboutDialog.setCanceledOnTouchOutside(false);
        aboutDialog.show();
    }


    protected void showConfirmationDialog(Context context, int code, TouristAttraction touristAttraction) {
        confirmationDialog = ConfirmationDialogUtil.showDialog(context, code, touristAttraction);
        // prevent closing on back pressed
        confirmationDialog.setCancelable(false);
        // prevent closing when clicked outside
        confirmationDialog.setCanceledOnTouchOutside(false);
        confirmationDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (isAboutShowing) {
            showAboutDialog(this);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // dismiss alert dialog if it is open so activity doesn't leak
        if (aboutDialog != null && aboutDialog.isShowing())
            aboutDialog.dismiss();

        if (confirmationDialog != null && confirmationDialog.isShowing())
            confirmationDialog.dismiss();
    }


    private void insertDummyData() {


        TouristAttraction attraction = new TouristAttraction();
        attraction.setName("Eiffel Tower");
        attraction.setDesc("The Eiffel Tower (/ˈaɪfəl/ EYE-fəl; French: tour Eiffel [tuʁ‿ɛfɛl] (About this soundlisten)) is a wrought-iron lattice tower on the Champ de Mars in Paris, France. It is named after the engineer Gustave Eiffel, whose company designed and built the tower.\n" +
                "\n" +
                "Constructed from 1887 to 1889 as the entrance to the 1889 World's Fair, it was initially criticised by some of France's leading artists and intellectuals for its design, but it has become a global cultural icon of France and one of the most recognisable structures in the world.[3] The Eiffel Tower is the most-visited paid monument in the world; 6.91 million people ascended it in 2015.\n" +
                "\n" +
                "The tower is 324 metres (1,063 ft) tall, about the same height as an 81-storey building, and the tallest structure in Paris. Its base is square, measuring 125 metres (410 ft) on each side. During its construction, the Eiffel Tower surpassed the Washington Monument to become the tallest man-made structure in the world, a title it held for 41 years until the Chrysler Building in New York City was finished in 1930. It was the first structure to reach a height of 300 metres. Due to the addition of a broadcasting aerial at the top of the tower in 1957, it is now taller than the Chrysler Building by 5.2 metres (17 ft). Excluding transmitters, the Eiffel Tower is the second tallest free-standing structure in France after the Millau Viaduct.\n" +
                "\n" +
                "The tower has three levels for visitors, with restaurants on the first and second levels. The top level's upper platform is 276 m (906 ft) above the ground – the highest observation deck accessible to the public in the European Union. Tickets can be purchased to ascend by stairs or lift to the first and second levels. The climb from ground level to the first level is over 300 steps, as is the climb from the first level to the second. Although there is a staircase to the top level, it is usually accessible only by lift.");
        attraction.setImgUri("https://images.pexels.com/photos/1780838/pexels-photo-1780838.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        attraction.setAddress("Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France");
        attraction.setPhone(892701239);
        attraction.setWebAddress("www.toureiffel.paris/en/rates-opening-times");
        attraction.setBusinessHours("09:30-23:45");
        attraction.setPrice(20);

        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setText("Amazing!");
        comment.setTouristAttraction(attraction);
        comments.add(comment);
        attraction.setComments(comments);


        dbRepository.insert(attraction);

        attraction.setName("Disneyland Paris");
        attraction.setDesc("Disneyland Paris, formerly Euro Disney Resort, is an entertainment resort in Marne-la-Vallée, France, a new town located 32 km (20 mi) east of the centre of Paris. It encompasses two theme parks, many resort hotels, Disney Nature Resorts, a shopping, dining, and entertainment complex, and a golf course, in addition to several additional recreational and entertainment venues. Disneyland Park is the original theme park of the complex, opening with the resort on 12 April 1992. A second theme park, Walt Disney Studios Park, opened in 2002, 10 years after the original park. Disneyland Paris celebrated its 25th anniversary in 2017. Within 25 years of opening, 320 million people visited Disneyland Paris making it the most visited theme park in Europe.[1] The Parisian resort is the second Disney park to open outside the United States following the opening of the Tokyo Disney Resort in 1983 and is the largest Disney resort to open outside of the United States. Disneyland Paris is also the only Disney resort outside of the United States to be completely owned by The Walt Disney Company.");
        attraction.setImgUri("https://images.pexels.com/photos/879844/pexels-photo-879844.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        attraction.setAddress("Disney Village, 77700 Chessy, France");
        attraction.setPhone(844800889);
        attraction.setWebAddress("www.disneylandparis.com");
        attraction.setBusinessHours("10:00-19:00");
        attraction.setPrice(150);

        comments = new ArrayList<>();
        comment = new Comment();
        comment.setText("Fun!");
        comment.setTouristAttraction(attraction);
        comments.add(comment);
        attraction.setComments(comments);


        dbRepository.insert(attraction);


        attraction.setName("Taj Mahal");
        attraction.setDesc("The Taj Mahal (/ˌtɑːdʒ məˈhɑːl, ˌtɑːʒ-/;[4] Urdu: تاج محل\u200E, lit. 'Crown of the Palace',[taːdʒ ˈmɛːɦ(ə)l])[5] is an ivory-white marble Islamic mausoleum on the south bank of the Yamuna river in the Indian city of Agra. It was commissioned in 1632 by the Mughal emperor Shah Jahan (reigned from 1628 to 1658) to house the tomb of his favourite wife, Mumtaz Mahal; it also houses the tomb of Shah Jahan himself. The tomb is the centrepiece of a 17-hectare (42-acre) complex, which includes a mosque and a guest house, and is set in formal gardens bounded on three sides by a crenellated wall.\n" +
                "\n" +
                "Construction of the mausoleum was essentially completed in 1643, but work continued on other phases of the project for another 10 years. The Taj Mahal complex is believed to have been completed in its entirety in 1653 at a cost estimated at the time to be around 32 million rupees, which in 2015 would be approximately 52.8 billion rupees (U.S. $827 million). The construction project employed some 20,000 artisans under the guidance of a board of architects led by the court architect to the emperor, Ustad Ahmad Lahauri.\n" +
                "\n" +
                "The Taj Mahal was designated as a UNESCO World Heritage Site in 1983 for being \"the jewel of Muslim art in India and one of the universally admired masterpieces of the world's heritage\". It is regarded by many as the best example of Mughal architecture and a symbol of India's rich history. The Taj Mahal attracts 7–8 million visitors a year and in 2007, it was declared a winner of the New7Wonders of the World (2000–2007) initiative.");
        attraction.setImgUri("https://images.pexels.com/photos/1603650/pexels-photo-1603650.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        attraction.setAddress("Agra, India");
        attraction.setPhone(844800889);
        attraction.setWebAddress("www.tajmahal.gov.in");
        attraction.setBusinessHours("06:00-18:30 pm");
        attraction.setPrice(15);

        comments = new ArrayList<>();
        comment = new Comment();
        comment.setText("Fantastic!");
        comment.setTouristAttraction(attraction);
        comments.add(comment);
        attraction.setComments(comments);


        dbRepository.insert(attraction);


    }


}
