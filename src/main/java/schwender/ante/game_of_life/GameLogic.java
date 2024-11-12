package schwender.ante.game_of_life;

public class GameLogic {
    // Game Rules

    // Each cell has 8 neighbours
    // 1. If a cell has 1 or 2 neighbours it dies
    // 2. If a cell has 4 or more neighbours it dies
    // 3. If a cell has 2 or 3 neighbours it survives
    // 4. If a unpopulated cell has 3 neighbours it becomes populated

    private static int populatedCellSurvives(int numPopulatedNeighbours) {
        if (numPopulatedNeighbours > 1 && numPopulatedNeighbours < 4) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int unpopulatedCellBecomesPopulated(int numPopulatedNeighbours) {
        if (numPopulatedNeighbours == 3) {
            return 1;
        } else {
            return 0;
        }
    }


    private static int[] neighbourIndexes(int cellIndex, GameBoard board) {
        // edge case handling (cell on edge): continue on the opposite board side
        int westOffset = -1;
        if (cellIndex % board.width() == 0) { // cell is on the left col of the board
            westOffset = board.width() - 1;
        }

        int eastOffset = 1;
        if (cellIndex % board.width() == board.width() -1) { // cell is on the right col of the board
            eastOffset = - board.width() + 1;
        }

        int lastRowFirstCell = (board.height() - 1) * board.height();

        int north = cellIndex - board.width();
        if (cellIndex < board.width()) { // cell is in first row
            north = lastRowFirstCell + cellIndex;
        }

        int south = cellIndex + board.width();
        if (cellIndex > lastRowFirstCell) { // cell is in last row
            south = cellIndex - lastRowFirstCell;
        }

        int northWest = north + westOffset;
        int northEast = north + eastOffset;
        int west = cellIndex + westOffset;
        int east = cellIndex + eastOffset;
        int southWest = south + westOffset;
        int southEast = south + eastOffset;

        return new int[]{
                northWest,
                north,
                northEast,
                west,
                east,
                southWest,
                south,
                southEast
        };
    }

    private static int numPopulatedNeighbours(int[] neighbourIndexes, GameBoard board) {
        int numPopulatedNeighbours = 0;
        int boardSize = board.width() * board.height();

        for (int index : neighbourIndexes) {
            // this only fixes top and bottom overflow, but not the sides
            if (index >= 0 && index < boardSize && board.cells()[index] == 1) {
                numPopulatedNeighbours += 1;
            }
        }
        return numPopulatedNeighbours;
    }

    public static GameBoard computeGameStep(GameBoard board) {
        var newBoard = new GameBoard(
                board.width(),
                board.height(),
                new int[board.width() * board.height()]
        );
        for (int cellIndex = 0; cellIndex < board.width() * board.height(); cellIndex++) {
            boolean populated = board.cells()[cellIndex] == 1;
            var neighbourIndexes = neighbourIndexes(cellIndex, board);
            int numPopulatedNeighbours = numPopulatedNeighbours(neighbourIndexes, board);
            if (populated) {
                newBoard.cells()[cellIndex] = populatedCellSurvives(numPopulatedNeighbours);
            } else {
                newBoard.cells()[cellIndex] = unpopulatedCellBecomesPopulated(numPopulatedNeighbours);
            }
        }
        return newBoard;
    }
}
