package swndve;

import static java.util.logging.Level.INFO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class Problem {

  private static final Logger LOGGER = Logger.getLogger(Problem.class.getName());

  public Problem() {
  }

  public Problem(String fileName) {

    String line = null;
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

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
      LOGGER.log(INFO, String.format("Problem created with arguments: "));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
