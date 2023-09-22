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
        INode localNode = this.rootNode;

        word = word.toLowerCase();

        for(int i = 0; i < word.length(); i++){
            char curChar = word.charAt(i);
            int charIndex = curChar - 'a';
            INode[] children = localNode.getChildren();
            if (children[charIndex] == null) {
                children[charIndex] = new Node();
                nodeCount++;
            }
            localNode = children[charIndex];
        }

        localNode.incrementValue();
        if (localNode.getValue() == 1) wordCount++;
    }

    @Override
    public INode find(String word) {
        INode curNode = this.rootNode;

        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            char curChar = word.charAt(i);
            int charIndex = curChar - 'a';

            INode[] children = curNode.getChildren();
            if (children[charIndex] == null) return null;
            curNode = children[charIndex];
        }
        if(curNode.getValue() == 0) return null;
        return curNode;
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
        if(o == null) return false;
        //if not a trie return false
        if(!(o instanceof Trie)) return false;
        if(o == this) return true;
        //if wordcount not equal return false
        //if nodecount not equal return false
        Trie t = (Trie) o;
        if(this.wordCount !=  t.wordCount && this.nodeCount != t.nodeCount) return false;
        //equalsHelper(this.root, other.root)
        return equalsHelper(this.rootNode, ((Trie) o).rootNode);

    }

    private boolean equalsHelper(INode a, INode b) {
        if (a.getValue() != b.getValue()) return false;
        INode[] aChildren = a.getChildren();
        INode[] bChildren = b.getChildren();
        if (aChildren.length != bChildren.length) return false;
        for (int i = 0; i < aChildren.length; i++) {
            INode aNode = aChildren[i];
            INode bNode = bChildren[i];
//            if (myNode == null && otherNode == null) {
//                // that is ok
//            } else {
//                if (myNode.getValue() != otherNode.getValue()) return false;
//                if (myNode.getChildren() != otherNode.getChildren()) return false;
//            }
            equalsHelper(myNode, otherNode);
        }
        return true;//Compare a and b
    }
}
