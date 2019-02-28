package swndve;

import com.google.common.base.Stopwatch;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
  private int acceptableLoss = 0;

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

      tagFrequency =
          images.stream()
              .flatMap(image -> image.getTags().stream())
              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

      tagFrequency =
          tagFrequency.entrySet().stream()
              .sorted(Map.Entry.comparingByValue())
              .collect(
                  Collectors.toMap(
                      Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

      System.out.printf(
          "Problem created in %d seconds with arguments: Images %d %n",
          problemStopwatch.elapsed(TimeUnit.SECONDS), imageCount);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int computeScore(Set<String> set1, Set<String> set2) {
    Set<String> union = new HashSet<>(set1);
    union.addAll(set2);

    Set<String> intersection = new HashSet<>(set1);
    intersection.retainAll(set2);

    Set<String> differenceS1 = new HashSet<>(set1);
    differenceS1.removeAll(set2);

    Set<String> differenceS2 = new HashSet<>(set2);
    differenceS2.removeAll(set1);

    return (Math.min(intersection.size(), Math.min(differenceS1.size(), differenceS2.size())));
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

  public List<Slide> solve() {

    List<Image> verticalImages =
        images.stream().filter(Image::isVertical).collect(Collectors.toList());

    List<Image> horizontalImages =
        images.stream()
            .filter(Image::isHorizontal)
            .sorted(Comparator.comparingLong(this::getUniqueFactor))
            .collect(Collectors.toList());
    List<Slide> slides =
        horizontalImages.stream()
            .map(image -> new Slide(image))
            .sorted(Comparator.comparingInt(Slide::maxScore))
            .collect(Collectors.toList());

    chains = new ArrayList<>();
    // First Pass
    long loss;
    for (acceptableLoss = 0; acceptableLoss < 16; acceptableLoss++) {
      while (slides.size() > 0) {
        Slide slide = slides.get(0);
        slides.remove(0);
        boolean foundChain = false;
        for (Iterator<Slide> iterator = slides.iterator(); iterator.hasNext(); ) {
          Slide successor = iterator.next();
          loss = getLoss(slide, successor);
          // System.out.println(
          //    String.format("MaxScore %d Score %d Loss %d", maxScore, potentialScore, loss));
          if (loss <= acceptableLoss) {
            List<Slide> chain = new ArrayList<>();
            chain.add(slide);
            chain.add(successor);
            chains.add(chain);
            iterator.remove();
            foundChain = true;
            break;
          }
        }
        if (!foundChain) {
          slides.add(slide);
        }
      }
    }

    for (acceptableLoss = 0; acceptableLoss < 16; acceptableLoss++) {
      while (chains.size() > 1) {
        List<Slide> chain = chains.get(0);
        chains.remove(0);
        boolean foundChain = false;
        for (Iterator<List<Slide>> iterator = chains.iterator(); iterator.hasNext(); ) {
          List<Slide> successorChain = iterator.next();
          long headHeadLoss = getLoss(chain.get(0), successorChain.get(0));
          long tailHeadLoss = getLoss(chain.get(chain.size() - 1), successorChain.get(0));
          long headTailLoss = getLoss(chain.get(0), successorChain.get(successorChain.size() - 1));
          long tailTailLoss = getLoss(chain.get(chain.size() - 1),
              successorChain.get(successorChain.size() - 1));

          long lowestLoss =
              Math.min(Math.min(headHeadLoss, tailHeadLoss), Math.min(headTailLoss, tailTailLoss));
          if (lowestLoss < acceptableLoss) {
            if (lowestLoss == headHeadLoss) {
              Collections.reverse(chain);
              chain.addAll(successorChain);
              iterator.remove();
              chains.add(chain);
              foundChain = true;
              break;
            } else if (lowestLoss == tailHeadLoss) {
              chain.addAll(successorChain);
              iterator.remove();
              chains.add(chain);
              foundChain = true;
              break;
            } else if (lowestLoss == headTailLoss) {
              successorChain.addAll(chain);
              iterator.remove();
              chains.add(successorChain);
              foundChain = true;
              break;
            } else {
              Collections.reverse(chain);
              successorChain.addAll(chain);
              iterator.remove();
              chains.add(successorChain);
              foundChain = true;
              break;
            }
          }
        }
        if (!foundChain) {
          chains.add(chain);
        }
      }
    }

    System.out.println(chains.size());
    return null;
  }

  private long getUniqueFactor(Image image) {
    Optional<Long> uniqueness =
        image.getTags().stream().map(s -> tagFrequency.get(s)).reduce(Long::sum);
    return uniqueness.orElse(0l);
  }

  private long getLoss(Slide slide, Slide successor) {
    int maxScore = Math.max(slide.maxScore(), successor.maxScore());
    int potentialScore = computeScore(slide.getTags(), successor.getTags());
    int loss = potentialScore - maxScore;
    return loss;
  }
}
