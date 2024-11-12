package schwender.ante.game_of_life;

public class GameLogic {
    // Game Rules

    // Each cell has 8 neighbours
    // 1. If a cell has 1 or 2 neighbours it dies
    // 2. If a cell has 4 or more neighbours it dies
    // 3. If a cell has 2 or 3 neighbours it survives
    // 4. If a unpopulated cell has 3 neighbours it becomes populated

    private static boolean populatedCellSurvives(int numPopulatedNeighbours) {
        return numPopulatedNeighbours > 1 && numPopulatedNeighbours < 4;
    }

    private static boolean unpopulatedCellBecomesPopulated(int numPopulatedNeighbours) {
        return numPopulatedNeighbours == 3;
    }


    private static int[] neighbourIndexes(int cellIndex, GameBoard board) {
        return new int[]{
                cellIndex - board.width() - 1,
                cellIndex - board.width(),
                cellIndex - board.width() + 1,
                cellIndex - 1,
                cellIndex + 1,
                cellIndex + board.width() - 1,
                cellIndex + board.width(),
                cellIndex + board.width() + 1
        };
    }

    private static int numPopulatedNeighbours(int[] neighbourIndexes, GameBoard board) {
        int numPopulatedNeighbours = 0;
        int boardSize = board.width() * board.height();

        for (int index: neighbourIndexes) {
            if (index >= 0 && index < boardSize && board.cells()[index]) {
                numPopulatedNeighbours += 1;
            }
        }
        return numPopulatedNeighbours;
    }

    public static GameBoard computeGameStep(GameBoard board) {
        var newBoard = new GameBoard(
                board.width(),
                board.height(),
                new boolean[board.width() * board.height()]
        );
        for (int cellIndex = 0; cellIndex < board.width() * board.height(); cellIndex++) {
            boolean populated = board.cells()[cellIndex];
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
