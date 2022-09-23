package tree

class BTreePrinter {
    companion object {
        fun printNode(root: Node?) {
            val maxLevel = maxLevel(root)
            printNodeInternal(listOf(root), 1, maxLevel)
        }

        private fun printNodeInternal(nodes: List<Node?>, level: Int, maxLevel: Int) {
            if (nodes.isEmpty() || isAllElementsNull(nodes)) return
            val floor = maxLevel - level
            val edgeLines = Math.pow(2.0, Math.max(floor - 1, 0).toDouble()).toInt()
            val firstSpaces = Math.pow(2.0, floor.toDouble()).toInt() - 1
            val betweenSpaces = Math.pow(2.0, (floor + 1).toDouble()).toInt() - 1
            printWhitespaces(firstSpaces)
            val newNodes = ArrayList<Node?>()
            for (node in nodes) {
                if (node != null) {
                    print(node.getKey())
                    newNodes.add(node.getLeftChild())
                    newNodes.add(node.getRightChild())
                } else {
                    newNodes.add(null)
                    newNodes.add(null)
                    print(" ")
                }
                printWhitespaces(betweenSpaces)
            }
            println()
            for (i in 1..edgeLines) {
                for (j in nodes.indices) {
                    printWhitespaces(firstSpaces - i)
                    if (nodes[j] == null) {
                        printWhitespaces(edgeLines + edgeLines + i + 1)
                        continue
                    }
                    if (nodes[j]!!.getLeftChild() != null) print("/")
                    else printWhitespaces(1)
                    printWhitespaces(i + i - 1)
                    if (nodes[j]!!.getRightChild() != null) print("\\")
                    else printWhitespaces(1)
                    printWhitespaces(edgeLines + edgeLines - i)
                }
                println()
            }
            printNodeInternal(newNodes, level + 1, maxLevel)
        }

        private fun printWhitespaces(count: Int) {
            for (i in 0 until count) print(" ")
        }

        private fun maxLevel(node: Node?): Int {
            if (node == null) return 0
            return Math.max(maxLevel(node.getLeftChild()), maxLevel(node.getRightChild())) + 1
        }

        private fun isAllElementsNull(list: List<Node?>): Boolean {
            for (element in list) {
                if (element != null) return false
            }
            return true
        }
    }
}