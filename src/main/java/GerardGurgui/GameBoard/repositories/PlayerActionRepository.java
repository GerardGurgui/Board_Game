package GerardGurgui.GameBoard.repositories;

import GerardGurgui.GameBoard.entities.PlayerAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerActionRepository extends JpaRepository<PlayerAction,Long> {
}
