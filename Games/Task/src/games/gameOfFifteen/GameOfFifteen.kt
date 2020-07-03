package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */

class GameOfFifteen(
        private val initializer: GameOfFifteenInitializer = RandomGameInitializer(),
        private val board: GameBoard<Int?> = createGameBoard(4)) : Game, GameBoard<Int?> by board {

    override fun initialize() {
        getAllCells().zip(initializer.initialPermutation).forEach { (k, v) -> this[k] = v }
    }

    override fun canMove() = !hasWon()

    override fun hasWon() =
            (1..15).zip(getAllCells().map { this[it] }).all { (a, b) -> a == b }

    override fun processMove(direction: Direction) {
        val nullCells = board.getAllCells().filter { board[it] == null }
        if (!nullCells.isEmpty()) {
            nullCells.first().let { emptyCell ->
                emptyCell.getNeighbour(direction.reversed())?.let { cellToMove ->
                    this[emptyCell] = this[cellToMove]
                    this[cellToMove] = null
                }
            }
        }
    }

    override fun get(i: Int, j: Int) = this[Cell(i, j)]

}

fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()) = GameOfFifteen(initializer)