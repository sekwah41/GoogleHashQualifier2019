package swndve;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Slide {

  private final SortedSet<String> tags;
  private final List<Image> images;

  public Slide(List<Image> images) throws Exception {
    this.images = images;
    if (images.size() == 2) {
      for (Image p : images) {
        if (!p.isVertical()) {
          throw new Exception("Invalid image combination");
        }
      }

    } else if (images.size() == 1) {
      if (images.get(0).isVertical()) {
        throw new Exception("Invalid image combination");
      }
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
}
