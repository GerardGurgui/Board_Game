package GerardGurgui.GameBoard.repositories;

import GerardGurgui.GameBoard.entities.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends JpaRepository<Box,Long> {

    Box findByNameIgnoreCase(String namePosition);

}
