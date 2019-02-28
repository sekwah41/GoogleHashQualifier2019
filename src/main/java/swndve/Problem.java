package swndve;

import static java.util.logging.Level.INFO;

import com.google.common.base.Stopwatch;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Problem {

  private final Logger LOGGER = Logger.getLogger(Problem.class.getName());
  private final Stopwatch problemStopwatch = Stopwatch.createUnstarted();

  private int imageCount;
  private List<Image> images;
  private SortedSet<String> tags;
  private Map<String, Long> tagFrequency;

  public Problem() {}

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

      Map<String, Long> tagFrequency = images.stream().flatMap(image -> image.getTags().stream())
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

      LinkedHashMap<String, Long> list = tagFrequency.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
              LinkedHashMap::new));

      System.out.println(list);

      System.out.printf("Problem created in %d seconds with arguments: Images %d %n",
              problemStopwatch.elapsed(TimeUnit.SECONDS), imageCount);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public int computeChainScore(ArrayList<Slide> slides){
    int score = 0;
    for(int i = 0; i < slides.size(); i++){
      score += Slide.computeScore(slides.get(i).getTags(), slides.get(i).getTags());
    }
    System.out.println("Score: "+score);
    return score;
  }

  public void output(ArrayList<Slide> slides, String fileName) {
    computeChainScore(slides);
    ArrayList<String> lines = new ArrayList<>();
    for (int i = 0; i < slides.size() + 1; i++) {
      if (i == 0) {
        String l = Integer.toString(slides.size());
        lines.add(l);
      } else {
        lines.add(slides.get(i).toString());
      }
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      for (String s : lines) {
        writer.write(s);
        writer.newLine();
      }
    } catch (IOException e) {
      System.out.println("Write Error");
      e.printStackTrace();
    }
  }
}
