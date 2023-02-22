package com.icajobguarantee.ica;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class ProgressBar {
    private Context context;
    Dialog pDialog;

    public ProgressBar(Context context) {  // can take other params if needed
        this.context = context;
    }

    public void showProgress() {
        // TODO Auto-generated method stub
        pDialog = new Dialog(context, android.R.style.Theme_Translucent);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.yudala_progress);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    public void dimissProgress() {
        pDialog.dismiss();
    }
}
