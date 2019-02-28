package swndve;

import static java.util.logging.Level.INFO;

import com.google.common.base.Stopwatch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

      int totalPhotos = 0;
      while ((line = reader.readLine()) != null) {
        arguments = line.split(" ");
        if (idx == 0) {
          totalPhotos = Integer.parseInt(arguments[0]);
        } else {

        }
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

  public static int compareScore(ArrayList<String> s1, ArrayList<String> s2){
    int scoreSim = 0;
    int scoreDiff1 = 0;
    int scoreDiff2 = 0;





    for(String r: s1){
      if(s2.contains(r)){
        scoreSim++;
      }
      if(!s2.contains(r)){
        scoreDiff1++;
      }
    }
    for(String r : s2){
      if(!s1.contains(r)){
        scoreDiff2++;
      }
    }

    ArrayList<Integer> scores = new ArrayList<>();
    scores.add(scoreDiff1);
    scores.add(scoreDiff2);
    scores.add(scoreSim);
    Collections.sort(scores);

    return scores.get(0);

  }
}
