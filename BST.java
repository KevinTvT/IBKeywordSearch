class BST { 
    private String str = "";
    //node class that defines BST node
    class Node { 
        String key; 
        Node left, right; 
   
        public Node(String data){ 
            key = data; 
            left = right = null; 
        } 
    } 
    // BST root node 
    Node root; 
  
   // Constructor for BST =>initial empty tree
    BST(){ 
        root = null; 
    } 
    //delete a node from BST
    void deleteKey(String key) { 
        root = delete_Recursive(root, key); 
    } 
   
    //recursive delete function
    Node delete_Recursive(Node root, String key)  { 
        //tree is empty
        if (root == null)  return root; 
   
        //traverse the tree
        if (key.compareTo(root.key) < 0)     //traverse left subtree 
            root.left = delete_Recursive(root.left, key); 
        else if (key.compareTo(root.key) > 0)  //traverse right subtree
            root.right = delete_Recursive(root.right, key); 
        else  { 
            // node contains only one child
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 
   
            // node has two children; 
            //get inorder successor (min value in the right subtree) 
            root.key = minValue(root.right); 
   
            // Delete the inorder successor 
            root.right = delete_Recursive(root.right, root.key); 
        } 
        return root; 
    } 
   
    String minValue(Node root)  { 
        //initially minval = root
        String minval = root.key; 
        //find minval
        while (root.left != null)  { 
            minval = root.left.key; 
            root = root.left; 
        } 
        return minval; 
    } 
   
    // insert a node in BST 
    void insert(String key)  { 
        root = insert_Recursive(root, key); 
    } 
   
    //recursive insert function
    Node insert_Recursive(Node root, String key) { 
          //tree is empty
        if (root == null) { 
            root = new Node(key); 
            return root; 
        } 
        //traverse the tree
        if (key.compareTo(root.key) < 0)     //insert in the left subtree
            root.left = insert_Recursive(root.left, key); 
        else if (key.compareTo(root.key) > 0)    //insert in the right subtree
            root.right = insert_Recursive(root.right, key); 
          // return pointer
        return root; 
    } 
 
// method for inorder traversal of BST 
    String inorder() { 
        str = "";
        inorder_Recursive(root); 
        return str.substring(1);
    } 
   
    // recursively traverse the BST  
    void inorder_Recursive(Node root) { 
        if (root != null) { 
            inorder_Recursive(root.left);
            str = str + "," + root.key;
            inorder_Recursive(root.right); 
        } 
    } 
     
    boolean search(String key)  { 
        root = search_Recursive(root, key); 
        if (root!= null)
            return true;
        else
            return false;
    } 
   
    //recursive insert function
    Node search_Recursive(Node root, String key)  { 
        // Base Cases: root is null or key is present at root 
        if (root==null || root.key==key) 
            return root; 
        // val is greater than root's key 
        if (root.key.compareTo(key) > 0)
            return search_Recursive(root.left, key); 
        // val is less than root's key 
        return search_Recursive(root.right, key); 
    } 
}
class Main{
    public static void main(String[] args)  { 
       //create a BST object
        BST bst = new BST(); 
        /* BST tree example
              45 
           /     \ 
          10      90 
         /  \    /   
        7   12  50   */
        //insert data into BST
        bst.insert("f"); 
        bst.insert("e"); 
        bst.insert("c"); 
        bst.insert("a"); 
        bst.insert("b"); 
        bst.insert("d"); 
        //print the BST
        System.out.println("The BST Created with input data(Left-root-right):"); 
        bst.inorder(); 
        
        //delete leaf node  
        System.out.println("\nThe BST after Delete d(leaf node):"); 
        bst.deleteKey("d"); 
        System.out.println(bst.inorder()); 
        //delete the node with one child
        System.out.println("\nThe BST after Delete b (node with 1 child):"); 
        bst.deleteKey("b"); 
        System.out.println(bst.inorder()); 
                 
        //delete node with two children  
        System.out.println("\nThe BST after Delete 45 (Node with two children):"); 
        bst.deleteKey("c"); 
        System.out.println(bst.inorder()); 
        //search a key in the BST
        boolean ret_val = bst.search ("a");
        System.out.println("\nKey a found in BST:" + ret_val );
        ret_val = bst.search ("e");
        System.out.println("\nKey e found in BST:" + ret_val );
     } 
}