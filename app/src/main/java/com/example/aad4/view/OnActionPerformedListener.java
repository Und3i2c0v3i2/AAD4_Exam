package com.example.aad4.view;


import android.os.Bundle;


public interface OnActionPerformedListener {


    String OBJECT_PARCELABLE = "object_parcelable";
    String BUNDLE_KEY = "bundle_key";
    String CONFIRM_KEY = "confirm_key";

    int OPEN_DETAILS = 101;
    int OPEN_ADD = 102;
    int OPEN_EDIT = 103;

    int ACTION_SAVE = 201;
    int ACTION_UPDATE = 202;
    int ACTION_DELETE = 203;
    int ACTION_CONFIRM = 204;
    int ACTION_ADD_COMMENT = 205;
    int ACTION_PICK_IMG = 206;
    int ACTION_DIAL = 207;
    int ACTION_WEB = 208;

    String REQ_CODE = "req_code";

    int ACTION_OPEN_IMG = 108;
    String IMG_KEY = "img_key";


    void onActionPerformed(Bundle bundle);
}
