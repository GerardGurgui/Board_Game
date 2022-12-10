package GerardGurgui.GameBoard.repositories;

import GerardGurgui.GameBoard.entities.Dice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiceRepository extends JpaRepository<Dice,Long> {
}
