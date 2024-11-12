package schwender.ante.game_of_life;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApi {
    private static final int MAX_WIDTH_OR_HEIGHT = 2000;

    @PostMapping("/computeGameStep")
    public GameBoard computeGameStep(@RequestBody GameBoard board) {
        if (board.height() > MAX_WIDTH_OR_HEIGHT || board.width() > MAX_WIDTH_OR_HEIGHT) {
            throw new RuntimeException("Error: Board too big");
        }
        if (board.cells().length != board.height() * board.width()) {
            throw new RuntimeException("Error: number of cells does not equal height * width");
        }
        return GameLogic.computeGameStep(board);
    }
}
