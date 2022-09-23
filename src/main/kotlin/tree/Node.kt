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

    fun getKey(): Int = this.key

    fun getLeftChild(): Node? = this.leftChild

    fun getRightChild(): Node? = this.rightChild

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

    private fun balanceTree(): Node {
        if (this.balanceFactor == 2) {
            if (this.leftChild!!.balanceFactor == -1) {
                this.leftChild = this.leftChild!!.rotate(true)
            }

            return this.rotate(false)
        } else if (this.balanceFactor == -2) {
            if (this.rightChild!!.balanceFactor == 1) {
                this.rightChild = this.rightChild!!.rotate(false)
            }

            return this.rotate(true)
        }

        return this
    }

    private fun rotate(toLeft : Boolean): Node {
        val newRoot : Node
        if(toLeft){
            newRoot = this.rightChild!!
            this.rightChild = newRoot.leftChild
            newRoot.leftChild = this
        }
        else{
            newRoot = this.leftChild!!
            this.leftChild = newRoot.rightChild
            newRoot.rightChild = this
        }
        this.updateBalance()
        this.updateTreeHeight()
        newRoot.updateBalance()
        newRoot.updateTreeHeight()

        return newRoot
    }



    private fun updateBalance() {
        val leftTreeHeight = this.leftChild?.height ?: 0
        val rightTreeHeight = this.rightChild?.height ?: 0

        this.balanceFactor = leftTreeHeight - rightTreeHeight
        if(this.balanceFactor >= 2 || this.balanceFactor <= -2) {


            val newNode = this.balanceTree()
            if(newNode.leftChild === this){
                this.updateNodeReference(newNode,true)
            }
            else if(newNode.rightChild===this){
                this.updateNodeReference(newNode,false)
            }

        }
    }

    private fun duplicateNode(): Node {
        val newNode = Node(this.key,  this.height, this.balanceFactor)
        newNode.leftChild = this.leftChild
        newNode.rightChild = this.rightChild
        return newNode
    }

    private fun updateNodeReference(newNode : Node?,equalsLeftNode : Boolean) {
        val duplicateMe = this.duplicateNode()
        this.key = newNode!!.key
        
        this.height = newNode.height
        this.balanceFactor = newNode.balanceFactor
        if(equalsLeftNode){
            this.leftChild = duplicateMe
            this.rightChild = newNode.rightChild
        }
        else{
            this.leftChild =  newNode.leftChild
            this.rightChild = duplicateMe
        }


    }

    private fun updateTreeHeight() {
        val leftTreeHeight = this.leftChild?.height ?: 0
        val rightTreeHeight = this.rightChild?.height ?: 0

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