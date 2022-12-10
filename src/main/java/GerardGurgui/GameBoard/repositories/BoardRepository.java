package GerardGurgui.GameBoard.repositories;

import GerardGurgui.GameBoard.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    boolean existsById(Long id);

}
