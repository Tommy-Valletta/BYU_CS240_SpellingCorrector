package spell;

public class Trie implements ITrie {
    private int wordCount = 0;
    private int nodeCount = 1;
    private INode rootNode = new Node();

    @Override
    public String toString() {
        StringBuilder word = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toStringHelper(rootNode, word, output);

        return output.toString();
    }

    private void toStringHelper(INode node, StringBuilder word, StringBuilder output) {
        if(node.getValue() > 0) {
            output.append(word.toString());
            output.append("\n");
        }

        INode [] children = node.getChildren();

        for(int i = 0; i < children.length; i++) {
            if(children[i] != null) {
                char childLetter = (char)('a' + i);
                word.append(childLetter);

                toStringHelper(children[i], word, output);

                word.deleteCharAt(word.length() - 1);
            }
        }
    }


    @Override
    public void add(String word) {
        INode localNode = rootNode;

        for(int i = 0; i < word.length(); i++){
            char currChar = word.charAt(i);
            int charIndex = currChar - 'a';
            INode[] children = localNode.getChildren();
            if (children[charIndex] == null) {
                children[charIndex] = new Node();
                nodeCount++;
            }
            localNode = children[charIndex];
        }

        localNode.incrementValue();
        wordCount++;
    }

    @Override
    public INode find(String word) {
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
    @Override
    public boolean equals(Object o) {
        //if null return false
        //if not a trie return false
        //if wordcount not equal return false
        //if nodecount not equal return false
        return false;//equalsHelper(this.root, other.root);
    }

    private boolean equalsHelper(INode a, INode b){
        return false;//Compare a and b
    }
}
