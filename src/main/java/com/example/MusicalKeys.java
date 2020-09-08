package com.example;

/**
 * Utility class to convert the user-given musical key to the corresponding integer for the API
 * call.
 */
public class MusicalKeys {
  /**
   * @param keyString String representation of the key, in forms such as "G", "Gsharp", or "Gflat".
   *     Does not allow double flats or sharps.
   * @return Int 0-11 representing key, otherwise -1.
   */
  public static int getKeyInt(String keyString) {
    switch (keyString) {
      case "C":
        return 0;
      case "Csharp":
        return 1;
      case "Dflat":
        return 1;
      case "D":
        return 2;
      case "Dsharp":
        return 3;
      case "Eflat":
        return 3;
      case "E":
        return 4;
      case "F":
        return 5;
      case "Fsharp":
        return 6;
      case "Gflat":
        return 6;
      case "G":
        return 7;
      case "Gsharp":
        return 8;
      case "Aflat":
        return 8;
      case "A":
        return 9;
      case "Asharp":
        return 10;
      case "Bflat":
        return 10;
      case "B":
        return 11;
    }
    return -1;
  }
}
