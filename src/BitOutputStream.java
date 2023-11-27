import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {

    private OutputStream out;
    private boolean[] buffer = new boolean[8];
    private int count = 0;

    public BitOutputStream(OutputStream out) {
        this.out = out;
    }

    public void write(boolean x) throws IOException {
        this.buffer[this.count] = x;
        this.count++;
        if (this.count == 8){
            int num = 0;
            for (int index = 0; index < 8; index++){
                num = 2*num + (this.buffer[index] ? 1 : 0);
            }

            this.out.write(num);

            this.count = 0;
        }
    }

    public void writeInt(int bits, int numBits) throws IOException {
        // Check if the number of bits to write is within a valid range (0 to 31)
        if (numBits < 0 || numBits > 32) {
            throw new IllegalArgumentException("Number of bits must be between 0 and 31");
        }

        // Write each bit to the buffer
        for (int i = numBits - 1; i >= 0; i--) {
            boolean bit = (((bits >> i) & 1) == 1);
            write(bit);
        }
    }

    public void close() throws IOException {
//        int num = 0;
//        for (int index = 0; index < 8; index++){
//            num = 2*num + (this.buffer[index] ? 1 : 0);
//        }
//
//        this.out.write(num - 128);

        while (this.count != 0) {
            this.write(false);
        }

        this.out.close();
    }

}