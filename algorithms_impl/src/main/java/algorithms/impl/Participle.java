package algorithms.impl;

import java.util.LinkedList;
import java.util.Queue;

public class Participle {

	public static void main(String[] args) {
		 
	  String[] d = {"西湖", "西湖博物馆", "西湖大学", "西藏", "藏龙岛", "西子湖畔"};

        Dictionary dic = new Dictionary(d);

        dic.print();

        String test = "我在杭州感受了西湖博大的水面，顺带去了西湖博物馆，还参观了西湖大学；最后我来到武汉藏龙岛，见到了毅哥，并和他一起开车去了西藏旅行。";

        dic.participle(test);

    }


    public static class Node {
        Character c;
        Node parent;
        HashMap<Character, Node> childs;
        boolean canEnd = false;

        public Node(Character c) {
            this.c = c;
            childs = new HashMap<>();
        }

        public Node(Character c, Node parent) {
            this.c = c;
            this.parent = parent;
            childs = new HashMap<>();
        }

        public boolean hasChild() {
            if (childs == null || childs.size() == 0) {
                return false;
            }
            return true;
        }
    }


    public static class Dictionary {
        HashMap<Character, Node> dic = new HashMap<>();

        public Dictionary(String[] words) {
            for (String w : words) {
                char[] chars = w.toCharArray();
                Node node = null;
                Node parent = null;
                HashMap<Character, Node> word = dic;
                int size = chars.length;
                for (int i = 0; i < size; i++) {
                    char c = chars[i];
                    if (word.containsKey(c)) {
                        Node n = word.get(c);
                        word = n.childs;
                        parent = n;
                    } else {
                        node = new Node(c, parent);
                        word.put(c, node);
                        word = node.childs;
                        parent = node;

                        if (i == size - 1) {
                            node.canEnd = true;
                        }
                    }
                }
            }
        }

        public void participle(String toParticiple){
            char[] tcs = toParticiple.toCharArray();
            int size = tcs.length;
            HashMap<Character, Node> root = dic;
            int start = 0;
            int end = 0;
            Node endNode = null;
            HashMap<Character, Node> dicm = root;
            for (int i = 0; i < size; i++) {
                if (dicm.containsKey(tcs[i])) {
                    Node n = dicm.get(tcs[i]);
                    if (start == 0) {
                        start = i;
                    }
                    if (n.hasChild()) {
                        dicm = n.childs;
                    }
                    if (n.canEnd) {
                        end = i;
                        endNode = n;
                    }
                } else if (start > 0) {
                    System.out.print("--");
//                for (int j = start; j <= end; j++)
//                    System.out.print(tcs[j]);
                    printWord(endNode);
                    System.out.print("--");
                    if (end < i) {
                        for (int j = end + 1; j <= i; j++)
                            System.out.print(tcs[j]);
                    }

                    start = 0;
                    end = 0;
                    dicm = root;
                    endNode = null;
                } else {
                    System.out.print(tcs[i]);
                }
            }
        }

        public void print() {
            print(dic.entrySet());
        }

        private void print(Set<Map.Entry<Character, Node>> entries) {
            for (Map.Entry<Character, Node> entry : entries) {
                Node n = entry.getValue();
                if (n.canEnd) {
                    printWord(n);
                    //System.out.println();

                }
                if (n.hasChild()) {
                    print(n.childs.entrySet());
                }
            }
        }

        private void printWord(Node node) {
            if (node.parent != null) {
                printWord(node.parent);
            }
            System.out.print(node.c);
        }

    }

}
