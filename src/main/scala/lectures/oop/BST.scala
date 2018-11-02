package lectures.oop

/**
  * BSTImpl - это бинарное дерево поиска, содержащее только значения типа Int
  *
  * * Оно обладает следующими свойствами:
  * * * * * левое поддерево содержит значения, меньшие значения родителя
  * * * * * правое поддерево содержит значения, большие значения родителя
  * * * * * значения, уже присутствующие в дереве, в него не добавляются
  * * * * * пустые значения (null) не допускаются
  *
  * * Завершите реализацию методов кейс класс BSTImpl:
  * * * * * Трейт BST и BSTImpl разрешается расширять любым образом
  * * * * * Изменять сигнатуры классов и методов, данные в условии, нельзя
  * * * * * Постарайтесь не использовать var и мутабильные коллекции
  * * * * * В задаче про распечатку дерева, нужно раскомментировать и реализовать метод toString()
  *
  * * Генератор:
  * * * * * должен создавать дерево, содержащее nodesCount узлов.
  * * Для этой структуры нужно реализовать генератор узлов.
  * * * * * не должен использовать переменные или мутабильные структуры.
  *
  */
trait BST {
  val value: Int
  val left: Option[BST]
  val right: Option[BST]

  def add(newValue: Int): BST

  def find(value: Int): Option[BST]

  def height(): Int
}

case class BSTImpl(value: Int,
                   left: Option[BSTImpl] = None,
                   right: Option[BSTImpl] = None) extends BST {

  private def addToLeft(tree: BSTImpl, newValue: Int): BSTImpl = tree.left match {
    case Some(l) => tree.copy(left = Some(l.add(newValue)))
    case None => tree.copy(left = Some(BSTImpl(newValue)))
  }

  private def addToRight(tree: BSTImpl, newValue: Int): BSTImpl = tree.right match {
    case Some(r) => tree.copy(right = Some(r.add(newValue)))
    case None => tree.copy(right = Some(BSTImpl(newValue)))
  }

  override def add(newValue: Int): BSTImpl = {
    if (newValue < value) addToLeft(this, newValue)
    else if (newValue > value) addToRight(this, newValue)
    else this
  }

  private def findInSubtree(value: Int, subtree: Option[BST]): Option[BST] = subtree match {
    case Some(tree) => tree.find(value)
    case None => None
  }

  def find(foundValue: Int): Option[BST] = {
    if (foundValue < value) findInSubtree(foundValue, left)
    else if (foundValue > value) findInSubtree(foundValue, right)
    else Option(this)
  }

  override def height(): Int = {
    val leftHeight = left.map(tree => tree.height()).getOrElse(0)
    val rightHeight = right.map(tree => tree.height()).getOrElse(0)
    math.max(leftHeight, rightHeight) + 1
  }

  private def treeToSeq(acc: Seq[Option[BST]], remainElements: Int): Seq[Option[BST]] = {
    if (remainElements > 0) {
      acc.head match {
        case None => None +: treeToSeq(acc.tail ++ Seq(None, None), remainElements - 1)
        case Some(tree) => Some(tree) +: treeToSeq(acc.tail ++ Seq(tree.left, tree.right), remainElements - 1)
      }
    } else Seq.empty
  }

  private def valueToStr(bst: Option[BST]): String = bst match {
    case None => "."
    case Some(tree) => tree.value.toString
  }

  private def stringFromSeq(nodes: Seq[Option[BST]], curPow: Int, limit: Int): String = {
    if (curPow > limit) ""
    else {
      val (cur, remains) = nodes.splitAt(curPow)
      val spaceNum = 2 * limit / curPow
      val layerToStr = cur.foldLeft(" " * (spaceNum / 2))((prevStr, tree) => prevStr + valueToStr(tree) + " " * spaceNum)
      layerToStr + "\n\n" + stringFromSeq(remains, curPow * 2, limit)
    }
  }

   override def toString(): String = {
     val height = this.height()
     val elementsInFullTree = math.pow(2, height).toInt - 1
     val seqFromTree = treeToSeq(Seq(Option(this)), elementsInFullTree)
     stringFromSeq(seqFromTree, 1, elementsInFullTree)
   }

}

object NodeGenerator {
  val r = scala.util.Random

  private def fromRange(minNodeValue: Int, maxNodeValue: Int): Int = {
    if (minNodeValue >= maxNodeValue) minNodeValue
    else r.nextInt(maxNodeValue) % (maxNodeValue - minNodeValue) + minNodeValue
  }

  private def emptyNodeGen(minNodeValue: Int, maxNodeValue: Int) =
    BSTImpl(fromRange(minNodeValue, maxNodeValue), None, None)

  def generate(nodesCount: Int, minNodeValue: Int, maxNodeValue: Int): Option[BSTImpl] = {
    if (nodesCount < 2) Option(emptyNodeGen(minNodeValue, maxNodeValue))
    else {
      val value = fromRange(minNodeValue, maxNodeValue)
      Option(BSTImpl(value, generate(nodesCount / 2, minNodeValue, value - 1),
        generate(nodesCount - nodesCount / 2, value + 1, maxNodeValue)))
    }
  }
}

object TreeTest extends App {

  val sc = new java.util.Scanner(System.in)
  val maxValue = 110000
  val nodesCount = sc.nextInt()

  val markerItem = (Math.random() * maxValue).toInt
  val markerItem2 = (Math.random() * maxValue).toInt
  val markerItem3 = (Math.random() * maxValue).toInt

  // Generate huge tree
  val root: BST = BSTImpl(maxValue / 2)
  val tree: BST = NodeGenerator.generate(nodesCount, 0, maxValue)
    .getOrElse(root)// generator goes here

  // add marker items
  val testTree = tree.add(markerItem).add(markerItem2).add(markerItem3)

  // check that search is correct
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem2).isDefined)
  require(testTree.find(markerItem3).isDefined)

  println(testTree)

  val treeHW = new BSTImpl(100)
    .add(15)
    .add(190)
    .add(3)
    .add(91)
    .add(205)
    .add(171)
    .add(155)
    .add(303)
    .add(13)
    .add(17)

  println(treeHW)
}