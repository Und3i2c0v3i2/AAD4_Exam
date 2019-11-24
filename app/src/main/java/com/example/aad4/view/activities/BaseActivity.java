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
import com.example.aad4.entity.TouristAttraction;
import com.example.aad4.prefs.PrefsRepository;
import com.example.aad4.util.AboutDialogUtil;
import com.example.aad4.util.ConfirmationDialogUtil;
import com.example.aad4.util.ToastUtil;

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

        if(isAboutShowing) {
            showAboutDialog(this);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // dismiss alert dialog if it is open so activity doesn't leak
        if(aboutDialog != null && aboutDialog.isShowing())
            aboutDialog.dismiss();

        if(confirmationDialog != null && confirmationDialog.isShowing())
            confirmationDialog.dismiss();
    }




}
