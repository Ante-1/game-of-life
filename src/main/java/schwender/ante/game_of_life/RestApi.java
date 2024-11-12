package schwender.ante.game_of_life;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApi {
    @PostMapping("/computeGameStep")
    public GameBoard computeGameStep(@Valid @RequestBody GameBoard board) {
        if (board.cells().length != board.height() * board.width()) {
            throw new RuntimeException("Error: number of cells does not equal height * width");
        }
        return GameLogic.computeGameStep(board);
    }
}
