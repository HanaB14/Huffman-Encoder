A Java implementation of Huffman coding in two parts.

Part 1: Build the Huffman Tree
BinaryHeap: Generic min-heap.
HuffmanNode: Tree node with character(s) and frequency.
HuffmanTree: Creates a Huffman tree from a heap; prints <char>=code.
UnderflowException: Thrown on empty-heap operations.
Flow: Legend → BinaryHeap → merge min nodes → HuffmanTree → printLegend().

Part 2: Encode & Decode
HuffmanConverter:
recordFrequencies(): Count chars, batch-build heap, print <char, freq>.
frequenciesToTree(): Build tree from counts.
treeToCode(): Generate and print quoted codes ('a'=010).
encodeMessage(): Print bitstring and size comparison.
decodeMessage(): Restore original text.

Usage:
Import into Eclipse under Huffman_Encoder.
Add input .txt files to project root.
Run HuffmanConverter with filename as argument.
