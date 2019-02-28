package swndve;

import java.util.ArrayList;

public class Photo {


    private ArrayList<String> tags;
    private char type;

    public Photo(char type, ArrayList<String> tags){
        this.type = type;
        this.tags = tags;
    }

    public int compare(Photo p){
        int i = 0;
        for(String s : tags){

        }

        return 0;
    }

    public char getType(){ return this.type;}

    public ArrayList<String> getTags(){ return this.tags; }

}
