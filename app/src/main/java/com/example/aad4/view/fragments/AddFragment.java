package com.example.aad4.view.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aad4.R;
import com.example.aad4.databinding.FragmentAddBinding;
import com.example.aad4.entity.Comment;
import com.example.aad4.entity.TouristAttraction;
import com.example.aad4.view.OnActionPerformedListener;
import com.example.aad4.view.adapters.RVAdapterDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.aad4.view.OnActionPerformedListener.ACTION_PICK_IMG;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_SAVE;
import static com.example.aad4.view.OnActionPerformedListener.BUNDLE_KEY;
import static com.example.aad4.view.OnActionPerformedListener.OBJECT_PARCELABLE;
import static com.example.aad4.view.OnActionPerformedListener.REQ_CODE;
import static com.example.aad4.view.activities.BaseActivity.HOME_FRAGMENT;


public class AddFragment
        extends Fragment
        implements View.OnClickListener, InputDialogFragment.OnCommentAdded {


    private FragmentAddBinding binding;
    private OnActionPerformedListener listener;

    private TouristAttraction touristAttraction;
    private List<Comment> comments;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false);
        setHasOptionsMenu(true);


        touristAttraction = new TouristAttraction();
        comments = new ArrayList<>();
        touristAttraction.setComments(comments);

        binding.setClickHandler(this);
        binding.setVar(touristAttraction);

        return binding.getRoot();
    }


    /* ******* CLICK EVENTS ******* */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_add:
                TouristAttraction touristAttraction = binding.getVar();
                touristAttraction.setComments(comments);
                Bundle bundle = new Bundle();
                bundle.putInt(BUNDLE_KEY, ACTION_SAVE);
                bundle.putParcelable(OBJECT_PARCELABLE, touristAttraction);
                listener.onActionPerformed(bundle);
                getFragmentManager().popBackStack(HOME_FRAGMENT, 0);
                break;

            case R.id.btn_cancel:
                getFragmentManager().popBackStack(HOME_FRAGMENT, 0);
                break;

            case R.id.btn_img:
                Bundle bundle1 = new Bundle();
                bundle1.putInt(BUNDLE_KEY, ACTION_PICK_IMG);
                bundle1.putInt(REQ_CODE, 2000);
                listener.onActionPerformed(bundle1);
                break;

            case R.id.btn_comment:
                InputDialogFragment dialogFragment = new InputDialogFragment();
                dialogFragment.setTargetFragment(this, 1);
                dialogFragment.getData(this.touristAttraction);
                dialogFragment.show(getFragmentManager(), "dialog");
                break;


        }
    }



    private void setupAdapter(List<Comment> comments) {
        RecyclerView recyclerView = binding.recyclerLayout.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RVAdapterDetails adapter = new RVAdapterDetails(comments);
        recyclerView.setAdapter(adapter);

    }


    /* ******* LIFE CYCLE ******* */

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnActionPerformedListener) {
            listener = (OnActionPerformedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    // get img
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

    // get comment from dialog fragment
    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
        setupAdapter(comments);
    }




}
