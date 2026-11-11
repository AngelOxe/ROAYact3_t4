package mx.ito.demoCrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.ito.demoCrud.entity.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {
}