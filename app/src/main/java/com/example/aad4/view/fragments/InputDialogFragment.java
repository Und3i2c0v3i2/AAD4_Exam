package com.example.aad4.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;

import android.view.View;

import com.example.aad4.R;
import com.example.aad4.databinding.InputDialogLayoutBinding;
import com.example.aad4.entity.Comment;
import com.example.aad4.entity.TouristAttraction;

import java.util.Objects;

public class InputDialogFragment extends AppCompatDialogFragment {

    private InputDialogLayoutBinding binding;
    private Comment comment;
    private TouristAttraction touristAttraction;

    private OnCommentAdded onCommentAdded;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil
                .inflate(getActivity().getLayoutInflater(), R.layout.input_dialog_layout, null, false);

        comment = new Comment();

        return getDialog(binding.getRoot());
    }


    private Dialog getDialog(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setView(view)
                .setTitle("Add comment for attraction")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        comment.setText(binding.addComment.getEditText().getText().toString());
                        comment.setTouristAttraction(touristAttraction);

                        onCommentAdded.addComment(comment);
                    }
                });


        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            onCommentAdded = (OnCommentAdded) getTargetFragment();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onCommentAdded = null;
    }

    public void getData(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }


    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }


    // For fragment-to-fragment communications
    public interface OnCommentAdded {

        void addComment(Comment comment);
    }

}
