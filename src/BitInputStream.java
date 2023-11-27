import java.io.IOException;
import java.io.InputStream;

class BitInputStream {

    private InputStream in;
    private int num = 0;
    private int count = 8;

    public BitInputStream(InputStream in) {
        this.in = in;
    }

    public boolean read() throws IOException {
        if (!hasNext()) {
            throw new IOException();
        }

        boolean x = (num & (1 << 7)) == (1 << 7);
        num <<= 1;
        this.count++;

        return x;
    }

    public boolean hasNext() throws IOException {
        if (this.count == 8){
            this.num = this.in.read();
            this.count = 0;
        }

        return this.num != -1;
    }

    public int readInt(int numBits) throws IOException {
        int output = 0;
        for (int i = numBits - 1; i >= 0; --i) {
            boolean bit = read();
            output += (bit ? Math.pow(2, i) : 0);
        }

        return output;
    }

    public void close() throws IOException {
        this.in.close();
    }

}