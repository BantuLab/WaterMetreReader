package com.bantulogic.core.watermetrereader.helpers;

public interface Session {
    boolean isLoggedIn();

    void saveToken(String token);

    String getToken();

    void saveUsername();

    String getUsername();

    void savePassword(String password);

    String getPassword();

    void invalidate();
}
