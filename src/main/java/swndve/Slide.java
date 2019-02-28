package swndve;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Slide {

  private final SortedSet<String> tags;
  private final List<Image> images;

  public Slide(List<Image> images) {
    this.images = images;
    switch (images.size()) {
      case 1:
        if (images.stream().allMatch(Image::isHorizontal)) {
          throw new IllegalArgumentException("Single image slides should be horizontal.");
        }
        break;
      case 2:
        if (images.stream().allMatch(Image::isVertical)) {
          throw new IllegalArgumentException("Multi image slides should be vertical.");
        }
        break;
      default:
        throw new IllegalArgumentException("Slides must have one or two images.");
    }


    this.tags = new TreeSet<>();
    for (Image image : images) {
      tags.addAll(image.getTags());
    }
  }

  public SortedSet<String> getTags() {
    return tags;
  }

  public List<Image> getImages() {
    return images;
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

  public int maxScore() {
    return tags.size() / 2;
  }
}
