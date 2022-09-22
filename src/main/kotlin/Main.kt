import tree.Node
import java.io.File


fun main(args: Array<String>) {
    val filePath = if (args.isNotEmpty()) args[0] else System.getProperty("user.dir")+"""\src\main\kotlin\tree.txt"""
    println(filePath)

    val file = File(filePath)

    if(file.createNewFile()){
        println("File created")
    }
    val fileContent = file.bufferedReader().readLine()?.split(";")
    val numbers = fileContent?.map {it.toInt()}?.toIntArray()

    val tree = Node(numbers?.get(0) ?: -1)
    if (numbers != null) {
        for (i in numbers) {
            tree.insert(i)
        }
    }

    print(tree)
}