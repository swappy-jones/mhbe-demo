package com.example.mhbesample.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public interface IEssentialActivityFeature {
    String getHeaderTitle();
    void setHeaderTitle(String headerTitle);
    void removeOrReplaceHeader(View view);
    int getLayoutById();
    void getViewById();

    /**
     * This is a method responsible to handle Activity toolbar
     */
    void manageToolBar();

    /**
     * This method will give you a default back screen feature.
     * You can also override its default functionality.
     * @param txtBack is the id of toolbar back button.
     */
    void handleDefaultToolbarBackClick(TextView txtBack);

    /**
     * This method will give you Activity specific click feature.
     * You can also override its default functionality.
     * @param txtNext is the id of toolbar next button.
     */
    void handleToolbarNextClick(TextView txtNext);


    /**
     * This method is responsible to apply given text value on Back button.
     * @param txtBackvalue is the text value applied on back button
     */
    void updateBackButtonValue(String txtBackvalue);

    /**
     * This method is responsible to apply given text value on Next button.
     * @param txtNextvalue is the text value applied on next button
     */
    void updateNextButtonValue(String txtNextvalue);

    /**
     * This method is responsible to hide next button
     */
    void hideToolbarNext();

    /**
     * This method is responsible to show next button
     */
    void showToolbarNext();

    void onNetworkChange(boolean networkStatus, String msg);
    void showSnackBarMessage(String message);
    /*// This method is commented as we only need header title in center
    void setToolbarTitleInCentre(TextView tstHeader);*/
//    LoginResponse getSessionLoginResponse(Context context);
    String getAccessToken();

    void setAppEnvironment(int environment);
    String getBearer();
    String getHeader();
    String getAppVersion();
    int getUserID(Context context);
    String getFullname();
//    String getEmail();
    String getUserName(Context context);
    boolean isSJMCLinked();
    boolean isSubscribed();
    String getUserEmail(Context context);
    String getUserAvatar(Context context);
    String getHomeHeaderText();
    String getNextButtonText();
    void openSJMCLinkingScreen(int SJMC_LINK_REQUEST_CODE);
    void openPaymentScreen();
    void removeSessionData();
    boolean isLoggedIn();
    //void logout();

}
