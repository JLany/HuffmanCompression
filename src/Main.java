import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main {
    private static final int n = 1000000;
    public static void main(String[] args) throws IOException {
        UI ui = new UI();

//        System.out.println("Hello: " + false);
//
//        HuffmanCoder coder = new HuffmanCoder(new File("testing.txt"), "out.bin");
//        try {
//            coder.compress();
//
//        } catch (FileNotFoundException ignored) {
//
//        }

//
//        Random random = new Random();
//
//        //Generate array
//
//        boolean[] outputArray = new boolean[n];
//        for (int index = 0; index < n; index++){
//            outputArray[index] = random.nextBoolean();
//        }
//
//        //Write to file
//
//        BitOutputStream fout = new BitOutputStream(new BufferedOutputStream(new FileOutputStream("booleans.bin")));
//
//        for (int index = 0; index < n; index++){
//            fout.write(outputArray[index]);
//        }
//
//        fout.close();
//
//        //Read from file
//
//        BitInputStream fin = new BitInputStream(new BufferedInputStream(new FileInputStream("booleans.bin")));
//
//        boolean[] inputArray = new boolean[n];
//        for (int index = 0; index < n; index++){
//            inputArray[index] = fin.read();
//        }
//
//        fin.close();
//
//        //Delete file
//        new File("booleans.bin").delete();
//
//        //Check equality
//
//        boolean equal = true;
//        for (int index = 0; index < n; index++){
//            if (outputArray[index] != inputArray[index]){
//                equal = false;
//                break;
//            }
//        }
//
//        System.out.println("Input " + (equal ? "equals " : "doesn't equal ") + "output.");

    }
}