package board

import board.Direction.*
open class SquareBoardImpl(override val width: Int): SquareBoard {
    var cells: Array<Array<Cell>> = arrayOf(arrayOf())

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if(i == 0 || j == 0 || i > width || j > width)
            return null
        return getCell(i, j)
    }

    override fun getCell(i: Int, j: Int): Cell {
        // no i, j validation -> will throw exception on illegal values
        return cells[i - 1][j - 1]
    }

    override fun getAllCells(): Collection<Cell> {
        return (1..width).flatMap{ i: Int ->
            (1..width).map{j: Int -> getCell(i, j)}
        }.toList()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return when {
            // if the range is larger than the board limits and ignore other indexes,
            jRange.last > width -> (jRange.first..width).map{ j: Int -> getCell(i, j) }
            else -> jRange.map{ j -> getCell(i, j) }
        }.toList()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return when {
            iRange.last > width -> (iRange.first..width).map { i:Int -> getCell(i, j) }
            else -> iRange.map { i: Int -> getCell(i, j) }
        }.toList()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when(direction) {
            // consider null (given e.g. Direction.UP - null)
            UP -> getCellOrNull(i-1,j)
            DOWN -> getCellOrNull(i+1,j)
            RIGHT -> getCellOrNull(i,j+1)
            LEFT -> getCellOrNull(i,j-1)
        }

}

fun createSquareBoard(width: Int): SquareBoard {
    val board = SquareBoardImpl(width)
    board.cells = initializeBoard(width)
    return board
}

class GameBoardImpl<T> (override val width: Int) : SquareBoardImpl(width), GameBoard<T> {
    // You can store values separately, for instance, in a map from Cell to stored values type.
    val cellValuesMap : MutableMap<Cell, T?> = mutableMapOf()
    override fun get(cell: Cell): T? {
        return cellValuesMap[cell]
    }

    override fun set(cell: Cell, value: T?) {
        cellValuesMap[cell] = value
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellValuesMap.values.all{predicate(it)}
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellValuesMap.values.any{predicate(it)}
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellValuesMap.filterValues { predicate.invoke(it) }.keys.first()
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellValuesMap.filterValues { predicate.invoke(it) }.keys
    }
}

fun <T> createGameBoard(width: Int): GameBoard<T>  {
    val gameBoard = GameBoardImpl<T>(width)
    // initialize cells
    gameBoard.cells = initializeBoard(width)
    // initialize cells values map
    gameBoard.cells.forEach{ row -> row.forEach {cell: Cell -> gameBoard.cellValuesMap[cell] = null}}
    return gameBoard
}

private fun initializeBoard(width: Int): Array<Array<Cell>> {
    var board = arrayOf<Array<Cell>>()
    for (i in 1..width){
        var row = arrayOf<Cell>()
        for( j in 1..width){
            row += Cell(i,j)
        }
        board += row
    }
    return board
}
