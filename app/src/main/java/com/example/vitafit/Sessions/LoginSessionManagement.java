package com.example.vitafit.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginSessionManagement {

    // SharedPreferences file name
    private static final String PREF_NAME = "login_session";

    // Keys for storing data
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";

    // SharedPreferences instance
    private SharedPreferences sharedPreferences;

    // Constructor
    public LoginSessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save login status and user email.
     *
     * @param email The user's email.
     */
    public void saveLogin(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true); // User is logged in
        editor.putString(KEY_USER_EMAIL, email);
        editor.apply(); // Save changes
    }

    /**
     * Check if the user is logged in.
     *
     * @return True if the user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Get the logged-in user's email.
     *
     * @return The user's email, or null if not found.
     */
    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    /**
     * Clear the login session.
     */
    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Remove all data
        editor.apply(); // Save changes
    }
}