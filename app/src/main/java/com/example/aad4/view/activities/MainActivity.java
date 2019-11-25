package com.example.aad4.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import com.example.aad4.R;
import com.example.aad4.databinding.ActivityMainBinding;
import com.example.aad4.entity.TouristAttraction;
import com.example.aad4.prefs.PrefsFragment;
import com.example.aad4.view.OnActionPerformedListener;
import com.example.aad4.view.fragments.AddFragment;
import com.example.aad4.view.fragments.InputDialogFragment;
import com.example.aad4.view.fragments.DetailsFragment;
import com.example.aad4.view.fragments.EditFragment;
import com.example.aad4.view.fragments.ListFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import static com.example.aad4.App.OBJECT_ID;

public class MainActivity
        extends BaseActivity
        implements OnActionPerformedListener {



    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private List<TouristAttraction> list;
    TouristAttraction touristAttraction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupToolbar();
        setupNavigationView();
        setupDrawer();

        list = dbRepository.getAll();

        fragmentTransaction(ListFragment.newInstance(list), HOME_FRAGMENT);

    }



    /* ************* TOOLBAR & MENU ************** */

    private void setupToolbar() {
        toolbar = (Toolbar) binding.drawerLayout.toolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item);
    }


    /* ************* DRAWER ************** */

    private void setupDrawer() {

        drawer = binding.drawerLayout.drawer;

        toggle = new ActionBarDrawerToggle(this,
                drawer,
                R.string.drawer_open,
                R.string.drawer_close);

        setupToolbarNav();
    }

    // when item is clicked on, close the drawer
    private void setupNavigationView() {

        binding.drawerLayout.navigationView
                .setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        drawer.closeDrawer(GravityCompat.START);

                        switch (menuItem.getItemId()) {
                            case R.id.show_all:
                                /* pop everything except first entry, which is list fragment
                                   when selecting "show all" I don't want anything on backstack,
                                   basically go back to"home page" */
                                getSupportFragmentManager().popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragmentTransaction(ListFragment.newInstance(list), HOME_FRAGMENT);
                                return true;

                            case R.id.menu_settings:
                                fragmentTransaction(new PrefsFragment(), null);
                                return true;

                            case R.id.menu_about:
                                showAboutDialog(MainActivity.this);
                                return true;
                        }

                        return false;
                    }
                });
    }

    /*
    show hamburger in first(list) fragment; show back arrow in others
    open drawer in first fragment; go back in others
     */
    private void setupToolbarNav() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // if other fragments, display back arrow
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    toggle.setDrawerIndicatorEnabled(false);
                    // if back arrow, navigate back
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });

                    // if list fragment, display hamburger
                } else {
                    toggle.setDrawerIndicatorEnabled(true);
                    drawer.addDrawerListener(toggle);
                    // if hamburger, open drawer
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!drawer.isDrawerOpen(GravityCompat.START)) {
                                drawer.openDrawer(GravityCompat.START);
                            }
                        }
                    });
                    toggle.syncState();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // if drawer open, first close on back pressed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            // if drawer closed
        } else {
            // if list fragment(first fragment), close app
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                // if other fragment, go back
            } else {
                super.onBackPressed();
            }
        }
    }



    /* ************************ ACTION LISTENER ************** */
    @Override
    public void onActionPerformed(Bundle bundle) {

        int id = -1;

        int bundleKey = bundle.getInt(BUNDLE_KEY, -1);

        switch (bundleKey) {

            case OPEN_DETAILS:
                id = bundle.getInt(OBJECT_ID);
                touristAttraction = dbRepository.getById(id);
                getSupportFragmentManager().popBackStack(HOME_FRAGMENT, 0);
                fragmentTransaction(DetailsFragment.newInstance(touristAttraction), DETAILS_FRAGMENT);
                break;

            case OPEN_ADD:
                fragmentTransaction(AddFragment.newInstance(), null);
                break;

            case OPEN_EDIT:
                touristAttraction = bundle.getParcelable(OBJECT_PARCELABLE);
                fragmentTransaction(EditFragment.newInstance(touristAttraction), null);
                break;

            case ACTION_SAVE:
                touristAttraction = bundle.getParcelable(OBJECT_PARCELABLE);
                if (dbRepository.insert(touristAttraction) != -1) {
                    checkPrefs(this,"Added ", touristAttraction.getName());
                }
                break;

            case ACTION_UPDATE:
                getSupportFragmentManager().popBackStack(DETAILS_FRAGMENT, 0);
                touristAttraction = bundle.getParcelable(OBJECT_PARCELABLE);
                if (dbRepository.modify(touristAttraction) != -1) {
                    checkPrefs(this,"Updated ", touristAttraction.getName());
                }
                break;

            case ACTION_DELETE:
                if (dbRepository.delete(touristAttraction) != -1) {
                    checkPrefs(this,"Deleted ", touristAttraction.getName());
                }
                getSupportFragmentManager().popBackStack();
                break;

            case ACTION_CONFIRM:
                touristAttraction = bundle.getParcelable(OBJECT_PARCELABLE);
                int action = bundle.getInt(CONFIRM_KEY, -1);
                showConfirmationDialog(this, action, touristAttraction);
                break;

            case ACTION_ADD_COMMENT:
                this.touristAttraction = bundle.getParcelable(OBJECT_PARCELABLE);
                InputDialogFragment customDialogFragment = new InputDialogFragment();
                customDialogFragment.getData(this.touristAttraction);
                customDialogFragment.show(getSupportFragmentManager(), "dialog");
                break;

            case ACTION_WEB:
                touristAttraction = bundle.getParcelable(OBJECT_PARCELABLE);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + touristAttraction.getWebAddress())));
                break;

            case ACTION_DIAL:
                touristAttraction = bundle.getParcelable(OBJECT_PARCELABLE);
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + touristAttraction.getPhone())));
                break;

            case ACTION_PICK_IMG:
                int reqCode = bundle.getInt(REQ_CODE);
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),reqCode);
                break;

            case ACTION_OPEN_IMG:
                TouristAttraction attraction = bundle.getParcelable(OBJECT_PARCELABLE);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(attraction.getImgUri())));
                break;

        }
    }





    /* ********************** LIFECYCLE ************************** */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbRepository = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        list = dbRepository.getAll();
    }


    /* ************************ GET IMG FROM GALLERY ******************** */
    private Uri uri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 2000:
                    uri = data.getData();
                    Fragment fr = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    ((AddFragment)fr).getData(uri);
                    break;

                case 2001:
                    uri = data.getData();
                    Fragment fr1 = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    ((EditFragment)fr1).getData(uri);
                    break;
            }
    }



}
