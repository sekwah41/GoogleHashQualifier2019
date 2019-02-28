package swndve;

import static java.util.logging.Level.FINE;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

public class Image {

  private static final Logger LOGGER = Logger.getLogger(Image.class.getName());

  private final int id;
  private final boolean isVertical;
  private final SortedSet<String> tags;

  public Image(int id, boolean isVertical, SortedSet<String> tags) {
    this.id = id;
    this.isVertical = isVertical;
    this.tags = tags;
    LOGGER.log(
        FINE, String.format("Created Image: %d Tags: %s ", id, Arrays.toString(tags.toArray())));
  }

  static Image fromString(int id, String string) {
    String[] arguments = string.split(" ");
    boolean isVertical = arguments[0].equals("V");
    int tagCount = Integer.parseInt(arguments[1]);

    SortedSet<String> tags = new TreeSet<>();

    tags.addAll(Arrays.asList(arguments).subList(2, tagCount + 2));
    return new Image(id, isVertical, tags);
  }

  public int getId() {
    return id;
  }

  public boolean isVertical() {
    return isVertical;
  }

  public boolean isHorizontal() {
    return !isVertical;
  }

  public SortedSet<String> getTags() {
    return tags;
  }
}
