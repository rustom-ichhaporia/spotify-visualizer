package com.example;

/**
 * Public-facing version of ApiCredentials class. PLEASE RENAME THIS CLASS to ApiCredentials.java
 * and fill in the clientId and clientSecret fields for personal use.
 */
public class ApiCredentialsHull {
  private static String clientId = "";
  private static String clientSecret = "";
  private static String accessToken = "";

  /**
   * Returns application client ID.
   *
   * @return Client ID
   */
  public static String getClientId() {
    return clientId;
  }

  /**
   * Returns application client secret ID.
   *
   * @return Secret client ID
   */
  public static String getClientSecret() {
    return clientSecret;
  }

  /**
   * Returns temporary application client access token.
   *
   * @return Access token
   */
  public static String getAccessToken() {
    return accessToken;
  }

  /**
   * Sets temporary application client access token.
   *
   * @param input Access token to be set
   */
  public static void setAccessToken(String input) {
    accessToken = input;
  }
}
