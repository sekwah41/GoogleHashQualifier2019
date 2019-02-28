package swndve;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Slide {

  private SortedSet<String> tags;
  private List<Image> images;

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

  public Slide(Image image) {
    this.images = new ArrayList<>();
    images.add(image);

    this.tags = new TreeSet<>();
    for (Image img : images) {
      tags.addAll(img.getTags());
    }
  }

  public SortedSet<String> getTags() {
    return tags;
  }

  public List<Image> getImages() {
    return images;
  }

  public int maxScore() {
    return tags.size() / 2;
  }
}
