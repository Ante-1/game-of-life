package schwender.ante.game_of_life;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import schwender.ante.game_of_life.persistance.GameBoardEntity;
import schwender.ante.game_of_life.persistance.GameBoardRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
public class RestApi {

    private final GameBoardRepository repository;

    public RestApi(GameBoardRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/computeGameStep")
    public GameBoard computeGameStep(@Valid @RequestBody GameBoard board) {
        if (board.cells().length != board.height() * board.width()) {
            throw new RuntimeException("Error: number of cells does not equal height * width");
        }
        return GameLogic.computeGameStep(board);
    }

    public record SavedGameBoard(
            @NotEmpty String name,
            @NotNull @Max(2000) Integer width,
            @NotNull @Max(2000) Integer height,
            @NotNull int[] cells) {}

    @PostMapping("/saveGameBoard")
    public void saveGameBoard(@Valid @RequestBody RestApi.SavedGameBoard board) {
        if (board.cells().length != board.height() * board.width()) {
            throw new RuntimeException("Error: number of cells does not equal height * width");
        }
        var length = board.width()* board.height();
        byte[] cells = new byte[length];

        for (int i = 0; i < length; i++) {
            cells[i] = (byte) board.cells()[i];
        }
        var toSave = new GameBoardEntity(board.name, board.width(), board.height(), cells);
        repository.save(toSave);
    }

    @GetMapping("/gameBoards")
    public List<SavedGameBoard> getGameBoards() {
        var gameBoards = repository.findAll();

        var res = StreamSupport.stream(gameBoards.spliterator(), true);
        return res.map(entity -> {
            var length = entity.getHeight() * entity.getWidth();
            int[] cells = new int[length];
            for (int i = 0; i < length; i++) {
                cells[i] = entity.getCells()[i];
            }
            return new SavedGameBoard(entity.getName(), entity.getWidth(), entity.getHeight(), cells);
        }).toList();
    }
}
