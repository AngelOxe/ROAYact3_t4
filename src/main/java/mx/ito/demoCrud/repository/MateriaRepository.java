package mx.ito.demoCrud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.ito.demoCrud.entity.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @EntityGraph(attributePaths = "area")
    List<Materia> findAllByOrderByIdAsc();

    @Override
    @EntityGraph(attributePaths = "area")
    Optional<Materia> findById(Long id);
}