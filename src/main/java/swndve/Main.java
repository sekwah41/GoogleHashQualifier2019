package swndve;

import static java.util.logging.Level.INFO;

import java.util.logging.Logger;

public class Main {

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  private static final String[] INPUT_FILES = {};

  public static void main(String[] args) {

    LOGGER.setLevel(INFO);
    LOGGER.log(INFO, "Started application");

    for (String fileName : INPUT_FILES) {
      LOGGER.log(INFO, String.format("Processing File: %s", fileName));
    }
  }
}
