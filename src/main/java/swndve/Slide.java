package swndve;

import java.util.ArrayList;

public class Slide {

  public ArrayList<String> tagsTotal;
  public ArrayList<Photo> photos;

  public Slide(ArrayList<Photo> photos) throws Exception {
    if (photos.size() == 2) {
      for (Photo p : photos) {
        if (p.getType() == 'H') {
          System.out.println("Invalid photo combination");
          throw new Exception();
        }
      }
      this.photos = photos;
    } else if (photos.size() == 1) {
      if (photos.get(0).getType() == 'V') {
        System.out.println("Invalid photo combination");
        throw new Exception();
      }
      this.photos = photos;
    }
  }

  public void calcTags() {
    ArrayList<String> uniqueTags = new ArrayList<>();
    for (Photo p : photos) {
      for (String t : p.getTags()) {
        if (!uniqueTags.contains(t)) {
          uniqueTags.add(t);
        }
      }
    }
  }
}
