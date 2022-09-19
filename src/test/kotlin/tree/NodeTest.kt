package tree

import exceptions.NodeException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

internal class NodeTest {
    private var tree: Node? = null
    @BeforeEach
    fun setup() {
        tree = Node(4)
    }

    @Test
    fun when_insert_new_node_should_found_same_key_in_the_structure() {

        tree?.insert(6)
        tree?.let { assertEquals(Node(6, 1), it.find(6), "Value not found") }

        tree?.insert(2)
        tree?.let { assertEquals(Node(2, 1), it.find(2), "Value not found") }

        tree?.insert(7)
        tree?.let { assertEquals(Node(7, 2), it.find(7), "Value not found") }

        tree?.insert(5)
        tree?.let { assertEquals(Node(5, 2), it.find(5), "Value not found") }

        tree?.insert(1)
        tree?.let { assertEquals(Node(1, 2), it.find(1), "Value not found") }

        tree?.insert(3)
        tree?.let { assertEquals(Node(3, 2), it.find(3), "Value not found") }
    }

    @Test
    fun when_insert_key_that_already_exists_should_throw_exception() {
        val block: () -> Unit = { tree?.insert(4) }

        val error = assertFailsWith<NodeException>(block = block)
        assertEquals(error.message, "Key 4 already exist")
    }

    @Test
    fun should_return_correct_string() {
        var excepted = "4"
        var found = tree?.toString()
        assertEquals(excepted, found)

        tree?.insert(2)
        found = tree.toString()
        excepted = "$excepted\n\t2"
        assertEquals(excepted, found)

        tree?.insert(5)
        found = tree.toString()
        excepted = "$excepted\n\t5"
        assertEquals(excepted, found)

        tree?.insert(6)
        found = tree.toString()
        excepted = "$excepted\n\t\t6"
        assertEquals(excepted, found)
    }

    @Test
    fun test_if_equals() {
        assertEquals(Node(5), Node(5))
    }

    @Test
    fun test_not_equals() {
        assertNotEquals(Node(5), Node(3), "Keys are different")
        assertNotEquals(Node(5), Node(5, 1), "Height is different")
        assertNotEquals(Node(5, leftChild = Node(3)), Node(5, leftChild = Node(2)), "Left children is different")
        assertNotEquals(Node(5, rightChild = Node(6)), Node(5, rightChild = Node(7)), "Right children is different")
    }

}