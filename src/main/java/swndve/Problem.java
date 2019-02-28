package swndve;

import static java.util.logging.Level.INFO;

import com.google.common.base.Stopwatch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Problem {

  private final Logger LOGGER = Logger.getLogger(Problem.class.getName());
  private final Stopwatch problemStopwatch = Stopwatch.createUnstarted();

  private int imageCount;
  private List<Image> images;
  private SortedSet<String> tags;

  public Problem() {
  }

  public Problem(String fileName) {

    String line;
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      problemStopwatch.reset().start();
      line = reader.readLine();
      String[] arguments = line.split(" ");
      imageCount = Integer.parseInt(arguments[0]);
      images = new ArrayList<>(imageCount);
      tags = new TreeSet<>();
      int idx = 0;
      while ((line = reader.readLine()) != null) {
        Image image = Image.fromString(idx++, line);
        tags.addAll(image.getTags());
        images.add(image);
      }

      LOGGER.log(
          INFO,
          String.format(
              "Problem created in %d seconds with arguments: Images %d ",
              problemStopwatch.elapsed(TimeUnit.SECONDS), imageCount));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
