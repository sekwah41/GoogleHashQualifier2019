package swndve;

import static java.util.logging.Level.INFO;

import com.google.common.base.Stopwatch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Problem {

  private static final Logger LOGGER = Logger.getLogger(Problem.class.getName());
  private static final Stopwatch problemStopwatch = Stopwatch.createUnstarted();

  public Problem() {
  }

  public Problem(String fileName) {

    String line = null;
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      problemStopwatch.reset().start();
      line = reader.readLine();
      String[] arguments = line.split(" ");
      // TODO: Parse whole problem arguments here

      int idx = 0;
      while ((line = reader.readLine()) != null) {
        arguments = line.split(" ");
        // TODO: parse individual lines here

        idx++;
      }

      // TODO: Log problem details here
      LOGGER.log(
          INFO,
          String.format("Problem created in %d seconds with arguments: "),
          problemStopwatch.elapsed(TimeUnit.SECONDS));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
