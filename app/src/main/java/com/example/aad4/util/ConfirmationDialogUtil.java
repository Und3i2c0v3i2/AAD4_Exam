package com.example.aad4.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.example.aad4.R;
import com.example.aad4.entity.TouristAttraction;
import com.example.aad4.view.OnActionPerformedListener;

import static com.example.aad4.App.OBJECT_ID;
import static com.example.aad4.view.OnActionPerformedListener.ACTION_UPDATE;
import static com.example.aad4.view.OnActionPerformedListener.BUNDLE_KEY;
import static com.example.aad4.view.OnActionPerformedListener.OBJECT_PARCELABLE;
import static com.example.aad4.view.OnActionPerformedListener.OPEN_DETAILS;

public class ConfirmationDialogUtil {


    /* for deciding if we should show dialog on rotation change */
    public static boolean isConfirmationShowing;

    public static AlertDialog showDialog(final Context context, final int action, final TouristAttraction touristAttraction) {

        isConfirmationShowing = true;

        AlertDialog.Builder builder =
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to proceed with this operation?")
                        .setTitle("Confirmation dialog")
                        .setIcon(R.drawable.ic_about)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                isConfirmationShowing = false;
                                Bundle bundle = new Bundle();
                                bundle.putInt(BUNDLE_KEY, action);
                                bundle.putParcelable(OBJECT_PARCELABLE, touristAttraction);
                                ((OnActionPerformedListener) context).onActionPerformed(bundle);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                isConfirmationShowing = false;
                                if(action == ACTION_UPDATE) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(BUNDLE_KEY, OPEN_DETAILS);
                                    bundle.putInt(OBJECT_ID, touristAttraction.getId());
                                    ((OnActionPerformedListener) context).onActionPerformed(bundle);
                                }
                            }
                        });


        return builder.create();
    }
}
