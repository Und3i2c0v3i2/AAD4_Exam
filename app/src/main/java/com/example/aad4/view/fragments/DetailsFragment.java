package com.example.aad4.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aad4.R;
import com.example.aad4.databinding.FragmentDetailsBinding;
import com.example.aad4.entity.Comment;
import com.example.aad4.entity.TouristAttraction;
import com.example.aad4.view.OnActionPerformedListener;
import com.example.aad4.view.adapters.RVAdapterDetails;

import java.util.List;

import static com.example.aad4.view.OnActionPerformedListener.ACTION_CONFIRM;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_DELETE;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_WEB;
import static com.example.aad4.view.OnActionPerformedListener.BUNDLE_KEY;
import static com.example.aad4.view.OnActionPerformedListener.CONFIRM_KEY;
import static com.example.aad4.view.OnActionPerformedListener.OBJECT_PARCELABLE;
import static com.example.aad4.view.OnActionPerformedListener.OPEN_EDIT;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_OPEN_IMG;


public class DetailsFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARENT = "touristAttraction";

    private TouristAttraction touristAttraction;
    private OnActionPerformedListener listener;

    private FragmentDetailsBinding binding;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(TouristAttraction touristAttraction) {
        DetailsFragment fragment = new DetailsFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);
        binding.setClickHandler(this);
        binding.setVar(touristAttraction);

        setupAdapter(touristAttraction.getComments());

        return binding.getRoot();

    }


    /* ******* CHILD ADAPTER ******* */
    private void setupAdapter(List<Comment> list) {
        RecyclerView recyclerView = binding.recyclerLayout.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RVAdapterDetails adapter = new RVAdapterDetails(list);
        recyclerView.setAdapter(adapter);
    }


    /* ************* TOOLBAR & MENU ************** */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Bundle bundle = new Bundle();
        switch (item.getItemId()) {

            case R.id.menu_edit:
                bundle.putParcelable(OBJECT_PARCELABLE, touristAttraction);
                bundle.putInt(BUNDLE_KEY, OPEN_EDIT);
                listener.onActionPerformed(bundle);
                return true;

            case R.id.menu_delete:
                bundle.putInt(BUNDLE_KEY, ACTION_CONFIRM);
                bundle.putInt(CONFIRM_KEY, ACTION_DELETE);
                bundle.putParcelable(OBJECT_PARCELABLE, touristAttraction);
                listener.onActionPerformed(bundle);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();

        switch (v.getId()) {

            case R.id.btn_web:
                bundle.putInt(BUNDLE_KEY, ACTION_WEB);
                bundle.putParcelable(OBJECT_PARCELABLE, touristAttraction);
                listener.onActionPerformed(bundle);
                break;

            case R.id.btn_phone:
                bundle.putInt(BUNDLE_KEY, OnActionPerformedListener.ACTION_DIAL);
                bundle.putParcelable(OBJECT_PARCELABLE, touristAttraction);
                listener.onActionPerformed(bundle);
                break;

            case R.id.img:
                bundle.putInt(BUNDLE_KEY, ACTION_OPEN_IMG);
                bundle.putParcelable(OBJECT_PARCELABLE, touristAttraction);
                listener.onActionPerformed(bundle);
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


}
