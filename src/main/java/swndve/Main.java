package swndve;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

  private static final Stopwatch mainStopwatch = Stopwatch.createUnstarted();


  private static final String[] INPUT_FILES = {
          "a_example.txt", "b_lovely_landscapes.txt", "c_memorable_moments.txt", "d_pet_pictures.txt", "e_shiny_selfies.txt"
  };

  public static void main(String[] args) {
    System.out.println("Started application");

    for (String fileName : INPUT_FILES) {
      mainStopwatch.reset().start();
      System.out.printf("Processing File: %s%n", fileName);
      Problem problem = new Problem(fileName);
      problem.output(problem.solve(), fileName + ".output");
      System.out.printf(
              "Processed File: %s in %d seconds%n",
              fileName, mainStopwatch.elapsed(TimeUnit.SECONDS));

    }
  }
}
