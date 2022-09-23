package tree

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

internal class NodeTest {
    private var tree: Node? = null //6, 2, 7, 5, 1, 3
    private var insertionArray = arrayOf<Int>(6, 2, 7, 5, 1, 3)
    private var rootKey = 4

    @BeforeEach
    fun setup() {
        tree = Node(rootKey)
    }

    @Test
    fun when_insert_new_node_should_found_same_key_in_the_structure() {

        tree?.let { assertEquals(Node(insertionArray[0], 1, 1), it.insert(6), "Key already exists") }
        tree?.let { assertEquals(Node(insertionArray[0], 1, 1), it.find(6), "Value not found") }
        tree?.let { assertEquals(Node(rootKey, height =  2).getHeight(), it.find(4)?.getHeight(), "Height isn't correct") }

        tree?.let { assertEquals(Node(insertionArray[1], 1, 1), it.insert(2), "Key already exists") }
        tree?.let { assertEquals(Node(insertionArray[1], 1, 1), it.find(2), "Value not found") }

        tree?.let { assertEquals(Node(insertionArray[2], 2, 1), it.insert(7), "Key already exists") }
        tree?.let { assertEquals(Node(insertionArray[2], 2, 1), it.find(7), "Value not found") }
        tree?.let { assertEquals(Node(rootKey, height =  3).getHeight(), it.find(4)?.getHeight(), "Height isn't correct") }

        tree?.let { assertEquals(Node(insertionArray[3], 2, 1), it.insert(5), "Key already exists") }
        tree?.let { assertEquals(Node(insertionArray[3], 2, 1), it.find(5), "Value not found") }

        tree?.let { assertEquals(Node(insertionArray[4], 2, 1), it.insert(1), "Key already exists") }
        tree?.let { assertEquals(Node(insertionArray[4], 2, 1), it.find(1), "Value not found") }

        tree?.let { assertEquals(Node(insertionArray[5], 2, 1), it.insert(3), "Key already exists") }
        tree?.let { assertEquals(Node(insertionArray[5], 2, 1), it.find(3), "Value not found") }
    }

    @Test
    fun when_insert_key_that_already_exists_should_throw_exception() {
        val insertedNode = tree?.insert(4)

        assertEquals(null, insertedNode)
    }

    @Test
    fun test_if_equals() {
        assertEquals(Node(5), Node(5))
    }

    @Test
    fun test_not_equals() {
        assertNotEquals(Node(5), Node(3), "Keys are equal")
        assertNotEquals(Node(5), Node(5, 1), "Height is equal")
        assertNotEquals(Node(5, leftChild = Node(3)), Node(5, leftChild = Node(2)), "Left children is different")
        assertNotEquals(Node(5, rightChild = Node(6)), Node(5, rightChild = Node(7)), "Right children is different")
    }

}