import org.jetbrains.annotations.Nullable;

public class HuffmanNode implements Comparable<HuffmanNode> {
    private boolean leaf;
    private char symbol;
    private float probability;
    private int code;

    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(char symbol, float probability, boolean leaf) {
        this.symbol = symbol;
        this.probability = probability;
        this.leaf = leaf;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isLeaf() { return leaf; }

    @Override
    public int compareTo(HuffmanNode o) {
        return Float.compare(probability, o.probability);
    }
}
