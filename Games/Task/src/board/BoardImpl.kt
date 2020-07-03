package board

import board.Direction.*

class SquareBoardImpl(private val size: Int, private val cells: List<Cell>) : SquareBoard {

    constructor(width: Int) : this(width, createCells(width))

    override val width: Int
        get() = size


    override fun getCellOrNull(i: Int, j: Int) = cells.firstOrNull { (ci, cj) -> ci == i && cj == j }

    override fun getCell(i: Int, j: Int) =
            getCellOrNull(i, j) ?: throw IllegalArgumentException("Invalid position")

    override fun getAllCells() = cells

    override fun getRow(i: Int, jRange: IntProgression) =
            jRange.takeWhile { it <= size }.map { j -> getCell(i, j) }

    override fun getColumn(iRange: IntProgression, j: Int) =
            iRange.takeWhile { it <= size }.map { i -> getCell(i, j) }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(this.i - 1, this.j)
            DOWN -> getCellOrNull(this.i + 1, this.j)
            RIGHT -> getCellOrNull(this.i, this.j + 1)
            LEFT -> getCellOrNull(this.i, this.j - 1)
        }
    }

    companion object {
        private fun createCells(size: Int)
                = (1..size).flatMap { i -> (1..size).map { j -> Cell(i, j) } }
    }

}


class GameBoardImpl<T> private constructor(private val squareBoard: SquareBoard) : GameBoard<T>, SquareBoard by squareBoard {

    private val cells = mutableMapOf<Cell, T?>()

    constructor(width: Int) : this(createSquareBoard(width))

    override fun get(cell: Cell) = cells[cell]

    override fun set(cell: Cell, value: T?) {
        cells[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean) =
            squareBoard.getAllCells().filter { predicate.invoke(cells[it]) }

    override fun find(predicate: (T?) -> Boolean) =
            filter(predicate).firstOrNull()

    override fun any(predicate: (T?) -> Boolean) =
            squareBoard.getAllCells().any { predicate.invoke(get(it)) }

    override fun all(predicate: (T?) -> Boolean) =
            squareBoard.getAllCells().all { predicate.invoke(get(it)) }

}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

