package swndve;

import static java.util.logging.Level.INFO;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  private static final Stopwatch mainStopwatch = Stopwatch.createUnstarted();
  private static final String[] INPUT_FILES = {};

  public static void main(String[] args) {

    LOGGER.setLevel(INFO);
    LOGGER.log(INFO, "Started application");

    for (String fileName : INPUT_FILES) {
      mainStopwatch.reset().start();
      LOGGER.log(INFO, String.format("Processing File: %s", fileName));

      LOGGER.log(
          INFO,
          String.format(
              "Processed File: %s in %d seconds",
              fileName, mainStopwatch.elapsed(TimeUnit.SECONDS)));
    }
  }

  public int[] convertToInt(String[] array) {
    int[] intArray = new int[array.length];
    for (int i = 0; i < array.length; i++) {
      intArray[i] = Integer.parseInt(array[i]);
    }
    return intArray;
  }
}
