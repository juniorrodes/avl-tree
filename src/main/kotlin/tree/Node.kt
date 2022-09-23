package tree

class Node(private var key: Int,
           private var level: Int = 0,
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

    fun insert(value: Int): Node? {
        if (this.find(value) == null) {
            var newNode: Node?
            newNode = if (value < this.key) {
                if (this.leftChild == null) {
                    newNode = Node(value, this.level + 1)
                    this.leftChild = newNode
                    newNode
                } else {
                    this.leftChild!!.insert(value) 
                }
            } else {
                if (this.rightChild == null) {
                    newNode = Node(value, this.level + 1)
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

    private fun updateBalance() {
        val leftTreeHeight = this.leftChild?.getHeight() ?: 0
        val rightTreeHeight = this.rightChild?.getHeight() ?: 0

        this.balanceFactor = leftTreeHeight - rightTreeHeight
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
        if (other.level != this.level) {
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
                val minNode = this.rightChild!!.findMin()
                this.key = minNode!!.key
                this.rightChild = this.rightChild!!.delete(minNode.key)
                this
            }
        }
        this.updateBalance()
        this.updateTreeHeight()
        return newNode
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


    
}