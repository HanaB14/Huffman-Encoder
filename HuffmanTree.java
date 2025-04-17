package zb2226;

public class HuffmanTree {
	HuffmanNode root;
	
	public HuffmanTree(HuffmanNode huff) {
		this.root=huff;
	}
	
	public void printLegend() {
		printLegend(root, "");
	}
	private void printLegend(HuffmanNode t, String s) {
		if (t == null) {
            return;
		}else if(t.letter.length()>1) {
			printLegend(t.left, s+"0");
			printLegend(t.right, s+"1");
		}else{
			System.out.println(t.letter + "=" + s);
		}
	}
	
	public static BinaryHeap legendToHeap(String legend) {
		String[] tokens = legend.split(" ");
		int n = tokens.length / 2;
		HuffmanNode[] nodes = new HuffmanNode[n];
		int index = 0;
		for (int i = 0; i < tokens.length; i += 2) {
            String letter = tokens[i];
            Double freq = Double.parseDouble(tokens[i + 1]);
            nodes[index++] = new HuffmanNode(letter, freq);
        }
        return new BinaryHeap<>(nodes);
	}
	
	public static HuffmanTree createFromHeap(BinaryHeap b) {
		while (b.getSize() > 1) {
		HuffmanNode min1 = (HuffmanNode) b.deleteMin();
        HuffmanNode min2 = (HuffmanNode) b.deleteMin();
        HuffmanNode merged = new HuffmanNode(min1, min2);
        b.insert(merged);
		}
		return new HuffmanTree((HuffmanNode) b.deleteMin());
        
	}
	
	public static void main(String[] args) {
		String legend = "A 20 E 24 G 3 H 4 I 17 L 6 N 5 O 10 S 8 V 1 W 2";
		BinaryHeap<HuffmanNode> heap = legendToHeap(legend);
        System.out.println("Initial Heap:");
        heap.printHeap();
        HuffmanTree tree = createFromHeap(heap);
        System.out.println("Huffman Codes:");
        tree.printLegend();
	}

}
