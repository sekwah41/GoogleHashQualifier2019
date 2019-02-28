package swndve;

import static java.util.logging.Level.INFO;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

  private static final Stopwatch mainStopwatch = Stopwatch.createUnstarted();
  private static final String[] INPUT_FILES = {
      "a_example.txt"/*, "b_lovely_landscapes.txt", "c_memorable_moments.txt", "d_pet_pictures.txt"*/
  };

  /*private static final String[] INPUT_FILES = {
          "a_example.txt", "b_lovely_landscapes.txt", "c_memorable_moments.txt", "d_pet_pictures.txt"
  };*/

  public static void main(String[] args) {
    System.out.println("Started application");

    for (String fileName : INPUT_FILES) {
      mainStopwatch.reset().start();
      System.out.printf("Processing File: %s%n", fileName);
      Problem problem = new Problem(fileName);
      System.out.printf(
              "Processed File: %s in %d seconds%n",
              fileName, mainStopwatch.elapsed(TimeUnit.SECONDS));
    }
  }
}
