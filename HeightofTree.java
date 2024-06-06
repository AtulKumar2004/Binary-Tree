import java.util.*;

public class HeightofTree {
    static class Node {
        int data;
        Node left,right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    public static int height(Node root) { // TC = O(n)
        if(root == null) {
            return 0;
        }
        int lh = height(root.left);
        int rh = height(root.right);
        return Math.max(lh, rh) + 1;
    }
    public static int count(Node root) { // TC = O(n)
        if(root == null) {
            return 0;
        }
        int lc = count(root.left);
        int rc = count(root.right);
        return lc+rc+1;
    }

    public static int sum(Node root) { // TC = O(n)
        if(root == null) {
            return 0;
        }
        int leftSum = sum(root.left);
        int rightSum = sum(root.right);
        return leftSum + rightSum + root.data;
    }

    public static int diam(Node root) { // TC = O(n^2)
        if(root == null){
            return 0;
        }
        int ldiam = diam(root.left);
        int lh = height(root.left);
        int rdiam = diam(root.right);
        int rh = height(root.right);
        int selfdiam = lh + rh + 1;
        return Math.max(selfdiam, Math.max(rdiam, ldiam));
    }

    static class Info {
        int diam;
        int ht;

        public Info(int diam, int ht) {
            this.diam = diam;
            this.ht = ht;
        }
        
    }
    public static Info diameter(Node root) { // TC = O(n)
        if(root == null) {
            return new Info(0,0);
        }
        Info leftInfo = diameter(root.left);
        Info rightInfo = diameter(root.right);

        int diam = Math.max(Math.max(leftInfo.diam, rightInfo.diam), leftInfo.ht + rightInfo.ht + 1);
        int ht = Math.max(leftInfo.ht, rightInfo.ht) + 1;

        return new Info(diam, ht);
    }

    public static boolean isIdentical(Node node,Node SubRoot) {
        if(node == null && SubRoot == null) {
            return true;
        } else if(node == null || SubRoot == null || node.data != SubRoot.data) {
            return false;
        }

        if(!isIdentical(node.left, SubRoot.left)) {
            return false;
        }
        if(!isIdentical(node.right, SubRoot.right)) {
            return false;
        }
        return true;

    }
    public static boolean isSubTree(Node root,Node SubRoot) {
        if(root == null) {
            return false;
        }
        if(root.data == SubRoot.data) {
            if(isIdentical(root,SubRoot)){
                return true;
            }
        }

        return isSubTree(root.left, SubRoot) || isSubTree(root.right, SubRoot);
    }
    static class Infor {
        int hd;
        Node node;

        public Infor(int hd,Node node) {
            this.hd = hd;
            this.node = node;
        }
    }

    public static void topView(Node root) {
        // Level Order
        Queue<Infor> q = new LinkedList<>();
        HashMap<Integer,Node> map = new HashMap<>();
        int min = 0,max = 0;

        q.add(new Infor(0, root));
        q.add(null);

        while(!q.isEmpty()) {
            Infor curr = q.remove();
            if(curr == null) {
                if(q.isEmpty()) {
                    break;
                }
                else {
                    q.add(null);
                }
            }
            else {
                if(!map.containsKey(curr.hd)) { // first time my hd is occuring
                    map.put(curr.hd,curr.node);
                }
                if(curr.node.left != null) {
                    q.add(new Infor(curr.hd-1, curr.node.left));
                    min = Math.min(min,curr.hd-1);
                }
                if(curr.node.right != null) {
                    q.add(new Infor(curr.hd+1, curr.node.right));
                    max = Math.max(max,curr.hd+1);
                }
            }
        }
        for(int i=min;i<=max;i++) {
            System.out.print(map.get(i).data+" ");
        }
        System.out.println();
    }

    public static void Klevel(Node root,int level,int k) {
        if(root == null) {
            return;
        }
        if(level == k) {
            System.out.print(root.data+" ");
            return;
        }

        Klevel(root.left, level+1, k);
        Klevel(root.right, level+1, k);
    }

    public static boolean getPath(Node root,int n,ArrayList<Node> path) {
        if(root == null) {
            return false;
        }
        
        path.add(root);

        if(root.data == n) {
            return true;
        }

        boolean foundLeft = getPath(root.left,n,path);
        boolean foundRight = getPath(root.right,n,path);

        if(foundLeft || foundRight) {
            return true;
        }

        path.remove(path.size()-1);
        return false;
    }

    public static Node lca(Node root,int n1,int n2) { // TC = O(n)
        ArrayList<Node> path1 = new ArrayList<>();
        ArrayList<Node> path2 = new ArrayList<>();

        getPath(root,n1,path1);
        getPath(root,n2,path2);

        // last common ancestor
        int i;
        for(i=0;i<path1.size() && i<path2.size();i++) {
            if(path1.get(i) != path2.get(i)) {
                break;
            }
        }
        // last equal node -> i-1th node
        Node lca = path1.get(i-1);
        return lca;
    }

    public static Node lca2(Node root,int n1,int n2) { // TC = O(n)
        if(root == null || root.data == n1 || root.data == n2) {
            return root;
        }
        Node leftLca = lca2(root.left, n1, n2);
        Node rightLca = lca2(root.right, n1, n2);

        if(leftLca == null) {
            return rightLca;
        } 
        if(rightLca == null) {
            return leftLca;
        } 

        return root;
    }
    public static int lcaDist(Node root,int n) {
        if(root == null) {
            return -1;
        }
        if(root.data == n) {
            return 0;
        }
        int leftDist = lcaDist(root.left, n);
        int rightDist = lcaDist(root.right, n);

        if(leftDist == -1 && rightDist == -1) {
            return -1;
        } else if(leftDist == -1) {
            return rightDist+1;
        } else {
            return leftDist+1;
        }
    }
    public static int minDist(Node root,int n1,int n2) {
        Node lca = lca2(root, n1, n2);
        int dist1 = lcaDist(lca,n1);
        int dist2 = lcaDist(lca,n2);

        return dist1+dist2;
    }

    public static int KAncestor(Node root,int n,int k) {
        if(root == null) {
            return -1;
        }
        if(root.data == n) {
            return 0;
        }

        int leftDist = KAncestor(root.left, n, k);
        int rightDist = KAncestor(root.right, n, k);

        if(leftDist == -1 && rightDist == -1) {
            return -1;
        }
        int max = Math.max(leftDist, rightDist);
        if(max+1 == k) {
            System.out.println(root.data);
        }
        return max+1;
    }
    
    public static int Nodesum(Node root) {
        if(root == null) {
            return 0;
        }
        int leftSubTreeSum = Nodesum(root.left);
        int rightSubTreeSum = Nodesum(root.right);
        int data = root.data;
        root.data = leftSubTreeSum + rightSubTreeSum;
        return leftSubTreeSum + rightSubTreeSum + data;
    }
    public static void preOrder(Node root) {
        if(root == null) {
            return;
        }
        System.out.print(root.data+" ");
        preOrder(root.left);
        preOrder(root.right);
    }
        
    public static void main(String[] args) {
        /*
                    1
                   / \
                  2   3
                 / \ / \
                4  5 6  7 
        */      
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        // System.out.println(height(root));
        // System.out.println(count(root));
        // System.out.println(sum(root));
        // System.out.println(diam(root));
        // System.out.println(diameter(root).ht);

        /*
             2
            / \
           4   5
        */
        Node subRoot = new Node(2);
        subRoot.left = new Node(4);
        subRoot.right = new Node(5);

        // System.out.println(isSubTree(root,subRoot));
        // topView(root);
        // Klevel(root, 1 ,3);
        // System.out.println(lca(root, 4, 6).data);
        // System.out.println(lca2(root, 4, 5).data);
        // System.out.println(minDist(root, 4, 6));
        // KAncestor(root, 6, 1);
        Nodesum(root);
        preOrder(root);

    }
}
