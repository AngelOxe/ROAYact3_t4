package mx.ito.demoCrud.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ito.demoCrud.entity.Area;
import mx.ito.demoCrud.repository.AreaRepository;

@Service
@Transactional(readOnly = true)
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<Area> listar() {
        return areaRepository.findAll(Sort.by("nombre"));
    }

    public Area buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Debes seleccionar un área.");
        }

        return areaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El área seleccionada no existe."));
    }

    @Transactional
    public Area crear(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del área es obligatorio.");
        }

        String nombreLimpio = nombre.trim();

        if (nombreLimpio.length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre del área no puede superar los 100 caracteres.");
        }

        Area area = new Area();
        area.setNombre(nombreLimpio);

        return areaRepository.save(area);
    }
}