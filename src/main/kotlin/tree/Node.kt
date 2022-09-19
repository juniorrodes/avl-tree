package tree

class Node(private val key: Int,
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

    public fun getHeight(): Int {
        return this.height
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

    override fun toString(): String {
        var indentation = ""
        for (i in 1..this.level) {
            indentation = "$indentation\t"
        }
        if ((this.leftChild === null) && (this.rightChild !== null)) {
            return "$indentation$key\n${rightChild}"
        }
        if ((this.leftChild !== null) && (this.rightChild === null)) {
            return "$indentation$key\n${leftChild}"
        }
        if ((this.leftChild === null) && (this.rightChild === null)) {
            return "$indentation$key"
        }
        return "$indentation$key\n$leftChild\n$rightChild"
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
}