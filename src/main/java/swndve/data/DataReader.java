package swndve.data;

import javax.xml.crypto.Data;
import java.util.Scanner;

public interface DataReader<T> {
    public T readData(Scanner scanner);
}
