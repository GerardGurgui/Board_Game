package GerardGurgui.GameBoard.services;


import java.util.List;

public interface Iservice<I,O> {

    void save(I input);

    List<O> getAll();

    O getOne(Long id);

    void update(I input, Long id);

    void delete(Long id);

}
