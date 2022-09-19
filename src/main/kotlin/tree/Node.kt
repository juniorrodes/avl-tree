package tree

import exceptions.NodeException

class Node(private val key: Int,
           private var height: Int = 0,
           private var leftChild: Node? = null,
           private var rightChild: Node? = null,) {

    fun find(value: Int): Node? = when {
        value > this.key -> this.rightChild?.find(value)
        value < this.key -> this.leftChild?.find(value)
        else -> this
    }

    fun insert(value: Int) {
        if (this.find(value) == null) {
            if (value < this.key) {
                if (this.leftChild == null) {
                    this.leftChild = Node(value, this.height + 1)
                } else {
                    this.leftChild!!.insert(value)
                }
            } else {
                if (this.rightChild == null) {
                    this.rightChild = Node(value, this.height + 1)
                } else {
                    this.rightChild!!.insert(value)
                }
            }
        } else {
            throw NodeException("Key $value already exist")
        }
    }

    override fun toString(): String {
        var indentation = ""
        for (i in 1..this.height) {
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
        if (other.height != this.height) {
            return false
        }
        return true
    }
}