package zb2226;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

//constructor
public class HuffmanConverter{

        // ASCII number of characters
        public static final int NUMBER_OF_CHARACTERS = 256;

        private String contents;
        private HuffmanTree huffmanTree;
        private int count[];
        private String code[];

        // Construct using an input string.
        // Initialize count and code.
        public HuffmanConverter(String input) {
          this.contents = input;
          this.count = new int[NUMBER_OF_CHARACTERS];
          this.code = new String[NUMBER_OF_CHARACTERS];
        }

        // Record how often each character occurs and store in count.
        public void recordFrequencies() {
        	for(char c: contents.toCharArray()) {
        		count[(int)c]++;
        	}
        	
        	int distinct = 0;
        	for (int f : count) {
        		if (f > 0) {
        			distinct++;
        		}
        	}
            HuffmanNode[] nodes = new HuffmanNode[distinct];
        	
            int idx = 0;
        	for (int i = 0; i < count.length; i++) {
        		if(count[i]>0) {
        			String letter;
        			if(i == '\n') {
        				letter = "\\n";
        			}else {
        				letter = Character.toString((char)i);
        			}
        			HuffmanNode node = new HuffmanNode(letter, (double)count[i]);
        			nodes[idx++] = new HuffmanNode(letter, (double)count[i]);
        		}
        	}
        	BinaryHeap<HuffmanNode> heap = new BinaryHeap<>(nodes);
        	heap.printHeap();
        }

        // Construct a Huffman tree from count and 
        // store the tree in huffmanTree.
        public void frequenciesToTree() {
        	int distinct = 0;
        	for (int f : count) {
        		if (f > 0) {
        			distinct++;
        		}
        	}
            HuffmanNode[] nodes = new HuffmanNode[distinct];
            int idx = 0;
            for (int i = 0; i < count.length; i++) {
                if (count[i] > 0) {
                    String letter = Character.toString((char)i);
                    nodes[idx++] = new HuffmanNode(letter, (double)count[i]);
                }
            }
            BinaryHeap<HuffmanNode> heap = new BinaryHeap<>(nodes);
            this.huffmanTree = HuffmanTree.createFromHeap(heap);
        }

        // Construct code from huffmanTree.
        public void treeToCode() {
        	for (int i = 0; i < code.length; i++) {
                code[i] = "";
            }
        	treeToCode(huffmanTree.root, "");
        	printFormattedLegend(huffmanTree.root, "");
        }

        private void treeToCode(HuffmanNode t, String encoding) {
        	if (t == null) {
                return;
            }
        	if (t.letter.length() > 1) {
        		treeToCode(t.left,  encoding + "0");
        		treeToCode(t.right, encoding + "1");
        	}else {
        		char c = t.letter.charAt(0);
        		code[(int)c] = encoding;
        	}
        }
        
        //The original Part 1 printLegend method could not clearly handle quoting characters
        // Therefore, I add this new method.
        // printFormattedLegend in HuffmanConverter to customize the output format as required.
        private void printFormattedLegend(HuffmanNode node, String encoding) {
            if (node == null) return;
            if (node.left == null && node.right == null) {
                char c = node.letter.charAt(0);
                String display;
                if (c == '\n') {
                    display = "'\\n'";
                } else {
                    display = "'" + c + "'";
                }
                System.out.println(display + "=" + encoding);
            } else {
                printFormattedLegend(node.left,  encoding + "0");
                printFormattedLegend(node.right, encoding + "1");
            }
        }

        // Encode content using code.
        public String encodeMessage() {
        	StringBuilder sb = new StringBuilder();
            for (char c : contents.toCharArray()) {
                sb.append(code[(int)c]);
            }
            String encoded = sb.toString();
            
            return encoded;
        }
        
        // Decode a Huffman encoding.
        public String decodeMessage(String encodedStr) {
        	StringBuilder sb = new StringBuilder();
        	HuffmanNode node = huffmanTree.root;
        	for (char bit : encodedStr.toCharArray()) {
        		if (bit == '0') {
                    node = node.left;
                } else {
                    node = node.right;
                }
        		if (node.left == null && node.right == null) {
                    sb.append(node.letter);
                    node = huffmanTree.root;
                }
        	}
        	return sb.toString();
        }

        // Read an input file.
        public static String readContents(String filename) {
            String temp = "";
            try {
                File file = new File(filename);
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    temp += sc.nextLine();
                    temp += "\n";
                }
                sc.close();
                return temp;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return "";
        }

        public static void main(String args[]) {
	        	String input = HuffmanConverter.readContents(args[0]);
	            HuffmanConverter h = new HuffmanConverter(input);
	            h.recordFrequencies();
	            // Print a list of characters and frequencies here!
	            h.frequenciesToTree();
	            h.treeToCode();
	            // Print the Huffman encoding here!
	            String encoded = h.encodeMessage();
	            System.out.println();
	            System.out.println("Huffman Encoding:");
	            System.out.println(encoded+"\n");
	            System.out.println("Message size in ASCII encoding: "+h.contents.length()*8);
	            System.out.println("Message size in Huffman coding: "+encoded.length()+"\n");
	            System.out.println(h.decodeMessage(encoded));
        }
}
