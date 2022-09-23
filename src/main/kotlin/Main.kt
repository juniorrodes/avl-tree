import tree.BTreePrinter
import tree.Node
import java.io.File
import java.util.Scanner

val reader = Scanner(System.`in`)
fun main(args: Array<String>) {
    
    val filePath = if (args.isNotEmpty()) args[0] else System.getProperty("user.dir")+"""\src\main\kotlin\tree.txt"""
    
    val file = File(filePath)
    if(file.createNewFile()){
        println("File created")
    }
    
   

    var tree = Node(readInt("Digite o valor do primeiro nó da árvore:"))

    while(true){
        println("Selecione uma opção do menu:");
        println("1 - Mostrar árvore")
        println("2 - Buscar  nó da árvore")
        println("3 - Inserir nó da árvore")
        println("4 - Remover nó da árvore")
        println("5 - Encaminhamento em pré-ordem")
        println("6 - Encaminhamento em pós-ordem")
        println("7 - Encaminhamento em ordem")
        println("8 - Carregar árvore de arquivo")
        println("9 - Salvar árvore em arquivo")
        println("10 - Sair")
        var option = reader.nextInt();
        when(option){
            1 -> BTreePrinter.printNode(tree)
            2 -> when(tree.find(readInt("Digite o valor do nó a ser encontrado:"))){
                null -> println("Nó não encontrado")
                else -> println("Nó encontrado")
            }
            3 -> when(tree.insert(readInt("Digite o valor do nó a ser inserido:"))){
                null -> println("Nó já existe")
                else -> println("Nó inserido")
            }
            4 -> when(tree.delete(readInt("Digite o valor do nó a ser removido:"))){
                null -> println("Nó não encontrado")
                else -> println("Nó removido")
            }
            5 -> println(tree.preOrder())
            6 -> println(tree.postOrder())
            7 -> println(tree.inOrder())
            8 -> tree = loadTreeFromFile(filePath)
            9 -> saveTreeToFile(tree, filePath)
            10 -> break;
            11 -> print(tree)
            else -> println("Opção inválida")
        }

    }
}

fun saveTreeToFile(tree: Node, filePath: String){
    val file = File(filePath)
    val treeArray = tree.preOrder()
    file.writeText("")

    for (i in treeArray) {
        if(i==treeArray[treeArray.lastIndex]){
            file.appendText("$i")
        }
        else{
            file.appendText("$i;")
        }

    }

}

fun loadTreeFromFile(filePath: String): Node {
    val file = File(filePath)
    val fileContent = file.bufferedReader().readLine()?.split(";")
    val numbers = fileContent?.map {it.toInt()}?.toIntArray()

    val tree = Node(numbers?.get(0) ?: 4)
    if (numbers != null) {
        for (i in numbers) {
            tree.insert(i)
        }
    }
    return tree
}

fun readInt(msg: String): Int {
    println(msg)
    return reader.nextInt()
}