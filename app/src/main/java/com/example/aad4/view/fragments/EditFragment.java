package com.example.aad4.view.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.aad4.R;
import com.example.aad4.databinding.FragmentDetailsBinding;
import com.example.aad4.databinding.FragmentEditBinding;
import com.example.aad4.entity.TouristAttraction;
import com.example.aad4.view.OnActionPerformedListener;

import java.io.IOException;

import static com.example.aad4.App.OBJECT_ID;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_CONFIRM;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_DELETE;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_PICK_IMG;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_UPDATE;
import static com.example.aad4.view.OnActionPerformedListener.BUNDLE_KEY;
import static com.example.aad4.view.OnActionPerformedListener.CONFIRM_KEY;
import static com.example.aad4.view.OnActionPerformedListener.OBJECT_PARCELABLE;
import static com.example.aad4.view.OnActionPerformedListener.OPEN_DETAILS;
import static com.example.aad4.view.OnActionPerformedListener.REQ_CODE;
import static com.example.aad4.view.activities.BaseActivity.DETAILS_FRAGMENT;
import static com.example.aad4.view.activities.BaseActivity.HOME_FRAGMENT;


public class EditFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARENT = "touristAttraction";

    private TouristAttraction touristAttraction;
    private OnActionPerformedListener listener;

    private FragmentEditBinding binding;


    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(TouristAttraction touristAttraction) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARENT, touristAttraction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            touristAttraction = getArguments().getParcelable(ARG_PARENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false);
        setHasOptionsMenu(true);
        binding.setVar(touristAttraction);
        binding.setClickHandler(this);
        return binding.getRoot();

    }



    /* ******* CLICK EVENTS ******* */
    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();

        switch (v.getId()) {

            case R.id.btn_update:
                bundle.putInt(BUNDLE_KEY, ACTION_CONFIRM);
                bundle.putInt(CONFIRM_KEY, ACTION_UPDATE);
                bundle.putParcelable(OBJECT_PARCELABLE, binding.getVar());
                listener.onActionPerformed(bundle);
                break;

            case R.id.btn_cancel:
                bundle.putInt(BUNDLE_KEY, OPEN_DETAILS);
                bundle.putInt(OBJECT_ID, binding.getVar().getId());
                listener.onActionPerformed(bundle);
                break;

            case R.id.btn_change_img:
                Bundle bundle1 = new Bundle();
                bundle1.putInt(BUNDLE_KEY, ACTION_PICK_IMG);
                bundle1.putInt(REQ_CODE, 2001);
                listener.onActionPerformed(bundle1);
                break;


        }
    }




    /* ******* LIFE CYCLE ******* */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionPerformedListener) {
            listener = (OnActionPerformedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActionPerformedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }



    public void getData(Uri uri) {

        binding.getVar().setImgUri(uri.toString());

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        binding.imgPreview.setImageBitmap(bitmap);
    }

}
