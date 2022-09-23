package tree

class Node(private var key: Int,
           private var height: Int = 1,
           private var balanceFactor: Int = 0,
           private var leftChild: Node? = null,
           private var rightChild: Node? = null,) {

    fun find(value: Int): Node? = when {
        value > this.key -> this.rightChild?.find(value)
        value < this.key -> this.leftChild?.find(value)
        else -> this
    }

    fun getHeight(): Int {
        return this.height
    }

    fun getRightChild(): Node? {
        return this.rightChild
    }

    fun getLeftChild(): Node? {
        return this.leftChild
    }

    fun getKey(): Int {
        return this.key
    }

    

    fun getBalanceFactor(): Int {
        return this.balanceFactor
    }

    fun setLeftChild(node: Node?) {
        this.leftChild = node
    }

    fun setRightChild(node: Node?) {
        this.rightChild = node
    }
    

    fun insert(value: Int): Node? {
        if (this.find(value) == null) {
            var newNode: Node?
            newNode = if (value < this.key) {
                if (this.leftChild == null) {
                    newNode = Node(value)
                    this.leftChild = newNode
                    newNode
                } else {
                    this.leftChild!!.insert(value) 
                }
            } else {
                if (this.rightChild == null) {
                    newNode = Node(value)
                    this.rightChild = newNode
                    newNode
                } else {
                    this.rightChild!!.insert(value)
                }
            }
            this.updateBalance()
            this.updateTreeHeight()
            return newNode
        } else {
            return null
        }
    }

    fun delete(value: Int): Node? {
        if (this.find(value) == null) {
            return null
        }
        val newNode: Node? = if (value < this.key) {
            this.leftChild = this.leftChild?.delete(value)
            this
        } else if (value > this.key) {
            this.rightChild = this.rightChild?.delete(value)
            this
        } else {
            if (this.leftChild == null) {
                this.rightChild
            } else if (this.rightChild == null) {
                this.leftChild
            } else {
                val maxNode = this.leftChild!!.findMax()
                this.key = maxNode!!.key
                this.leftChild = this.leftChild!!.delete(maxNode.key)
                this
            }
        }
        this.updateBalance()
        this.updateTreeHeight()
        return newNode
    }

    fun balanceTree(): Node? {
        if (this.balanceFactor == 2) {
            if (this.leftChild!!.balanceFactor == -1) {
                this.leftChild = this.leftChild!!.rotateLeft()
            }

            return this.rotateRight()
        } else if (this.balanceFactor == -2) {
            if (this.rightChild!!.balanceFactor == 1) {
                this.rightChild = this.rightChild!!.rotateRight()
            }

            return this.rotateLeft()
        }

        return this
    }

    private fun rotateRight(): Node? {
        val newRoot = this.leftChild
        this.leftChild = newRoot!!.rightChild
        newRoot.rightChild = this
        this.updateBalance()
        this.updateTreeHeight()
        newRoot.updateBalance()
        newRoot.updateTreeHeight()

        return newRoot
    }

    private fun rotateLeft(): Node? {
        val newRoot = this.rightChild
        this.rightChild = newRoot!!.leftChild
        newRoot.leftChild = this
        this.updateBalance()
        this.updateTreeHeight()
        newRoot.updateBalance()
        newRoot.updateTreeHeight()

        return newRoot
    }

    private fun updateBalance() {
        val leftTreeHeight = this.leftChild?.getHeight() ?: 0
        val rightTreeHeight = this.rightChild?.getHeight() ?: 0

        this.balanceFactor = leftTreeHeight - rightTreeHeight
        if(this.balanceFactor >= 2 || this.balanceFactor <= -2) {


            val newNode = this.balanceTree()
            if (newNode != null) {
                if(newNode.getLeftChild() === this){
                    this.updateNodeReference(newNode,true)
                }
                else if(newNode.getRightChild()===this){
                    this.updateNodeReference(newNode,false)
                }
            }

        }
    }

    fun duplicateNode(): Node {
        val newNode = Node(this.key,  this.height, this.balanceFactor)
        newNode.leftChild = this.leftChild
        newNode.rightChild = this.rightChild
        return newNode
    }

    private fun updateNodeReference(newNode : Node?,equalsLeftNode : Boolean) {
        var duplicateMe = this.duplicateNode();
        this.key = newNode!!.getKey()
        
        this.height = newNode.getHeight()
        this.balanceFactor = newNode.getBalanceFactor()
        if(equalsLeftNode){
            this.leftChild = duplicateMe
            this.rightChild = newNode.getRightChild()
        }
        else{
            this.leftChild =  newNode.getLeftChild()
            this.rightChild = duplicateMe
        }


    }

    private fun updateTreeHeight() {
        val leftTreeHeight = this.leftChild?.getHeight() ?: 0
        val rightTreeHeight = this.rightChild?.getHeight() ?: 0

        this.height = leftTreeHeight.coerceAtLeast(rightTreeHeight) + 1
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Node) {
            return false
        }
        if (other.rightChild != this.rightChild) {
            return false
        }
        if (other.leftChild != this.leftChild) {
            return false
        }
        if (other.key != this.key) {
            return false
        }
       
        if (other.height != this.height) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = key
        result = 31 * result + height
        result = 31 * result + (leftChild?.hashCode() ?: 0)
        result = 31 * result + (rightChild?.hashCode() ?: 0)
        return result
    }

    private fun findMax(): Node? {
        return if (this.rightChild == null) {
            this
        } else {
            this.rightChild!!.findMax()
        }
    }

    private fun findMin(): Node? {
        return if (this.leftChild == null) {
            this
        } else {
            this.leftChild!!.findMin()
        }
    }

    fun preOrder(): List<Int> {
        val list = mutableListOf<Int>()
        list.add(this.key)
        if (this.leftChild != null) {
            list.addAll(this.leftChild!!.preOrder())
        }
        if (this.rightChild != null) {
            list.addAll(this.rightChild!!.preOrder())
        }
        return list
    }

    fun postOrder(): List<Int> {
        val list = mutableListOf<Int>()
        if (this.leftChild != null) {
            list.addAll(this.leftChild!!.postOrder())
        }
        if (this.rightChild != null) {
            list.addAll(this.rightChild!!.postOrder())
        }
        list.add(this.key)
        return list
    }

    fun inOrder(): List<Int> {
        val list = mutableListOf<Int>()
        if (this.leftChild != null) {
            list.addAll(this.leftChild!!.inOrder())
        }
        list.add(this.key)
        if (this.rightChild != null) {
            list.addAll(this.rightChild!!.inOrder())
        }
        return list
    }

    override fun toString(): String {
        return "Node\n(key=$key, height=$height, balanceFactor=$balanceFactor, leftChild=$leftChild, rightChild=$rightChild)\n"
    }

    
}