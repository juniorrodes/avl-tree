import tree.Node
import exceptions.NodeException
import java.io.File

fun main(args: Array<String>) {
    val fileContent = File(args[0]).bufferedReader().readLine().split(";")
    val numbers = fileContent.map {it.toInt()}.toIntArray()

    val tree = Node(numbers[0])
    for (i in numbers) {
        try {
            tree.insert(i)
        } catch (e: NodeException) {
            continue
        }
    }

    print(tree)
}