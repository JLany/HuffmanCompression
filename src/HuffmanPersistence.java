import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HuffmanPersistence {
    public static void write(String outputFileName, String text, Map<Character, String> huffman) throws IOException {
        // Construct table (iterate over map)
        // Write table to file
        BitOutputStream out = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));

        // We need to write: 1 byte (number of symbols in table), 1 byte (nBitsForCodeLength)
        int nBitsForCodeLength = calculateNBitsForCode(huffman);
        out.writeInt(huffman.size(), 8);
        out.writeInt(nBitsForCodeLength, 8);

        for (Map.Entry<Character, String> entry : huffman.entrySet()) {
            // 1 byte (symbol), nBits (length = l), lBits (code)

            char symbol = entry.getKey();
            String code = entry.getValue();
            int codeLength = code.length();

            out.writeInt(symbol, 8);
            out.writeInt(codeLength, nBitsForCodeLength);

            for (char bit : code.toCharArray()) {
                out.write((bit == '1' ? true : false));
            }
        }

        // Encode text and write to file
        List<String> output = encode(text, huffman);
        for (String code : output) {
            for (char bit : code.toCharArray()) {
                out.write((bit == '1' ? true : false));
            }
        }

//        out.writeInt(Integer.MAX_VALUE, 32);

        out.close();
    }

    private static List<String> encode(String text, Map<Character, String> huffman) {
        var output = new ArrayList<String>();
        for (char c : text.toCharArray()) {
            output.add(huffman.getOrDefault(c, "BAD_BIT"));
        }

        return output;
    }

    private static int calculateNBitsForCode(Map<Character, String> huffman) {
        int maxLength = 0;
        for (Map.Entry<Character, String> entry : huffman.entrySet()) {
            if (entry.getValue().length() > maxLength) {
                maxLength = entry.getValue().length();
            }
        }

        return (int)Math.ceil(Math.log(maxLength) / Math.log(2));
    }
}
