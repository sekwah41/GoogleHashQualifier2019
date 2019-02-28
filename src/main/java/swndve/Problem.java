package swndve;

import com.google.common.base.Stopwatch;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
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

  private List<List<Slide>> chains;

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

      Map<String, Long> tagFrequency =
          images.stream()
              .flatMap(image -> image.getTags().stream())
              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

      LinkedHashMap<String, Long> list =
          tagFrequency.entrySet().stream()
              .sorted(Map.Entry.comparingByValue())
              .collect(
                  Collectors.toMap(
                      Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

      System.out.println(list);

      System.out.printf(
          "Problem created in %d seconds with arguments: Images %d %n",
          problemStopwatch.elapsed(TimeUnit.SECONDS), imageCount);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Slide> solve() {

    List<Image> verticalImages =
        images.stream().filter(Image::isVertical).collect(Collectors.toList());

    List<Image> horizontalImages = images.stream().filter(Image::isHorizontal)
        .sorted(Comparator.comparingLong(this::getUniqueFactor)).collect(Collectors.toList());
    horizontalImages.stream().map(image -> new Slide(image));
    return null;
  }

  public void output(ArrayList<Slide> slides, String fileName) {
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

  private long getUniqueFactor(Image image) {
    Optional<Long> uniqueness = image.getTags().stream().map(s -> tagFrequency.get(s))
        .reduce(Long::sum);
    return uniqueness.orElse(0l);
  }
}
