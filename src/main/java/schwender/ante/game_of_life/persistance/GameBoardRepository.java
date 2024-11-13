package schwender.ante.game_of_life.persistance;

import org.springframework.data.repository.CrudRepository;

public interface GameBoardRepository extends CrudRepository<GameBoardEntity, Long> {
}
