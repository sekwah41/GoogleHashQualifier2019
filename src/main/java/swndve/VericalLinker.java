package swndve;

import java.util.*;

public class VericalLinker {

    /*
    Given a list of vertical images and two chain of slides create the slide which best joins the two.
    Removes the vertical images taken.

    If null is returned, connect the two chains to each other with no join
     */
    public static Slide CreateJoiningSlide(List<Image> allVerticalImages, List<Slide> chain1, List<Slide> chain2){
        if(allVerticalImages.size() <= 1){
            return null;
        }
        Slide chain1Head = chain1.get(chain1.size()-1);
        Slide chain2Tail = chain2.get(0);

        int chain1Dif = Integer.MAX_VALUE;
        int chain2Dif = Integer.MAX_VALUE;
        Image chain1Joiner = null;
        Image chain2Joiner = null;
        for (Image img : allVerticalImages) {
            int chain1Similarity = similarity(img, chain1Head);
            int chain2Similarity = similarity(img, chain2Tail);
            if(chain1Similarity < chain1Dif){
                chain1Joiner = img;
            } else if(chain2Similarity < chain2Dif){
                chain2Joiner = img;
            }
        }
        if(chain2Joiner == null){
            chain2Joiner = allVerticalImages.get(0);
        }
        allVerticalImages.remove(chain1Joiner);
        allVerticalImages.remove(chain2Joiner);
        return new Slide(Arrays.asList(chain1Joiner, chain2Joiner));
    }

    private static int similarity(Image img, Slide chain1Head) {

        Set<String> differenceS1 = new HashSet<>(img.getTags());
        differenceS1.removeAll(chain1Head.getTags());

        Set<String> differenceS2 = new HashSet<>(img.getTags());
        differenceS2.removeAll(chain1Head.getTags());

        return Math.max(differenceS1.size(), differenceS2.size());
    }

}
