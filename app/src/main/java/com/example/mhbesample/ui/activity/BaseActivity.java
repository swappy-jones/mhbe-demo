package com.example.mhbesample.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mhbesample.R;
import com.example.mhbesample.interfaces.IEssentialActivityFeature;

public class BaseActivity extends AppCompatActivity implements IEssentialActivityFeature {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getLayoutById() != 0)
            setContentView(getLayoutById());
        manageToolBar();
        getViewById();
    }

    public void showAlertDialog() {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(BaseActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(BaseActivity.this);
        }
//        builder.setIcon(R.drawable.app_icon);
        builder.setTitle("Info")
                .setMessage("Are you sure you want to exit without location update?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Exit from app
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Cancel
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public String getHeaderTitle() {
        return null;
    }

    @Override
    public void setHeaderTitle(String headerTitle) {
        if (!TextUtils.isEmpty(headerTitle)) {
            //TextView txtHeader = (TextView) findViewById(R.id.toolbar_title);
            /*if (txtHeader != null)
                txtHeader.setText(headerTitle);*/
        }
    }

    @Override
    public void removeOrReplaceHeader(View view) {

    }

    @Override
    public int getLayoutById() {
        return 0;
    }

    @Override
    public void getViewById() {

    }

    @Override
    public void manageToolBar() {

    }

    @Override
    public void handleDefaultToolbarBackClick(TextView txtBack) {

    }

    @Override
    public void handleToolbarNextClick(TextView txtNext) {

    }

    @Override
    public void updateBackButtonValue(String txtBackvalue) {

    }

    @Override
    public void updateNextButtonValue(String txtNextvalue) {

    }

    @Override
    public void hideToolbarNext() {

    }

    @Override
    public void showToolbarNext() {

    }

    @Override
    public void onNetworkChange(boolean networkStatus, String msg) {

    }

    @Override
    public void showSnackBarMessage(String message) {

    }

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public void setAppEnvironment(int environment) {

    }

    @Override
    public String getBearer() {
        return null;
    }

    @Override
    public String getHeader() {
        return null;
    }

    @Override
    public String getAppVersion() {
        return null;
    }

    @Override
    public int getUserID(Context context) {
        return 0;
    }

    @Override
    public String getFullname() {
        return null;
    }

    @Override
    public String getUserName(Context context) {
        return null;
    }

    @Override
    public boolean isSJMCLinked() {
        return false;
    }

    @Override
    public boolean isSubscribed() {
        return false;
    }

    @Override
    public String getUserEmail(Context context) {
        return null;
    }

    @Override
    public String getUserAvatar(Context context) {
        return null;
    }

    @Override
    public String getHomeHeaderText() {
        return null;
    }

    @Override
    public String getNextButtonText() {
        return null;
    }

    @Override
    public void openSJMCLinkingScreen(int SJMC_LINK_REQUEST_CODE) {

    }

    @Override
    public void openPaymentScreen() {

    }

    @Override
    public void removeSessionData() {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }
}
