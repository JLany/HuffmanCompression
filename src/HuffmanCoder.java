import java.io.*;
import java.util.*;

public class HuffmanCoder {

    private File inputFile;
    private String outputFilename;

    public HuffmanCoder(File inputFile, String outputFilename) {

        this.inputFile = inputFile;
        this.outputFilename = outputFilename;
    }

    public void compress() throws FileNotFoundException {
        StringBuilder input = new StringBuilder();
        Scanner inFile = new Scanner(inputFile);

        Map<Character, Integer> chars = new HashMap<>();
        while (inFile.hasNextLine()) {
            String line = inFile.nextLine();
            input.append(line);
            input.append("\n");

            for (char c : line.toCharArray()) {
                chars.put(c, chars.getOrDefault(c, 0) + 1);
            }

            chars.put('\n', chars.getOrDefault('\n', 0) + 1);
        }

        inFile.close();

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();

        HuffmanNode sentinel = new HuffmanNode((char)255, 0, true);
        pq.add(sentinel);
        input.append((char)255);

        for (Map.Entry<Character, Integer> entry : chars.entrySet()) {
            HuffmanNode node = new HuffmanNode(entry.getKey(), entry.getValue() / (float)chars.size(), true);
            pq.add(node);
        }

        while (pq.size() > 1) {
            HuffmanNode node1 = pq.remove();
            HuffmanNode node2 = pq.remove();

            node1.setCode(0);
            node2.setCode(1);

            HuffmanNode parent = new HuffmanNode(' ', node1.getProbability() + node2.getProbability(), false);
            parent.setLeft(node1);
            parent.setRight(node2);

            pq.add(parent);
        }

        Map<Character, String> huffmanCodes = new HashMap<>();
        HuffmanNode root = pq.remove();
        assignCodes(root, "", huffmanCodes);

        try {
            HuffmanPersistence.write(outputFilename, input.toString(), huffmanCodes);
        } catch(IOException e) {
            System.out.println("Error while writing to file.");
        }
    }

    public void decompress() throws IOException {
        BitInputStream in = new BitInputStream(new BufferedInputStream(new FileInputStream(inputFile)));

        // Construct the huffman codes table.
        int nSymbols = in.readInt(8);
        int nBitsForCodeLength = in.readInt(8);

        Map<String, Character> symbols = new HashMap<>();
        for (int i = 0; i < nSymbols; ++i) {
            char symbol = (char)in.readInt(8);
            int codeLength = in.readInt(nBitsForCodeLength);

            StringBuilder code = new StringBuilder();
            for (int j = 0; j < codeLength; ++j) {
                boolean next = in.read();
                code.append((next ? "1" : "0"));
            }

            symbols.put(code.toString(), symbol);
        }


        // Read stream and decode.
        StringBuilder output = new StringBuilder();
        StringBuilder current = new StringBuilder();
        while (in.hasNext()) {
            boolean next = in.read();
            current.append((next ? "1" : "0"));

            String currentString = current.toString();
//            if (binaryStringToInt(currentString) == Integer.MAX_VALUE) {

            if (symbols.containsKey(currentString)) {
                if (symbols.get(currentString) == (char)255) {
                    break;
                }

                output.append(symbols.get(currentString));
                current = new StringBuilder();
            }
        }

        PrintWriter printer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(outputFilename)));
        printer.print(output.toString());
        printer.close();
    }

    private void assignCodes(HuffmanNode node, String current, Map<Character, String> codes) {
        if (node.getProbability() == 0) {
            // Add sentinel.
            codes.put(node.getSymbol(), current + node.getCode());
            return;
        }

        if (node.isLeaf()) {
            codes.put(node.getSymbol(), current + node.getCode());
            return;
        }

        assignCodes(node.getLeft(), current + node.getCode(), codes);
        assignCodes(node.getRight(), current + node.getCode(), codes);
    }

    private int binaryStringToInt(String s) {
        int output = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            output += (s.charAt(i) == '1' ? Math.pow(2, s.length() - 1 - i) : 0);
        }

        return output;
    }
}
