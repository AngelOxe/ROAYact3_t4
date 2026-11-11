package mx.ito.demoCrud.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ito.demoCrud.entity.Area;
import mx.ito.demoCrud.entity.Materia;
import mx.ito.demoCrud.repository.MateriaRepository;

@Service
@Transactional(readOnly = true)
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final AreaService areaService;

    public MateriaService(
            MateriaRepository materiaRepository,
            AreaService areaService) {

        this.materiaRepository = materiaRepository;
        this.areaService = areaService;
    }

    public List<Materia> listar() {
        return materiaRepository.findAllByOrderByIdAsc();
    }

    public Materia buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Debes indicar el identificador de la materia.");
        }

        return materiaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La materia no existe."));
    }

    @Transactional
    public Materia crear(String nombre, Long areaId) {
        String nombreLimpio = validarNombre(nombre);
        Area area = areaService.buscarPorId(areaId);

        Materia materia = new Materia();
        materia.setNombre(nombreLimpio);
        materia.setArea(area);

        return materiaRepository.save(materia);
    }

    @Transactional
    public Materia actualizar(Long id, String nombre, Long areaId) {
        Materia materia = buscarPorId(id);
        String nombreLimpio = validarNombre(nombre);
        Area area = areaService.buscarPorId(areaId);

        materia.setNombre(nombreLimpio);
        materia.setArea(area);

        return materiaRepository.save(materia);
    }

    @Transactional
    public void eliminar(Long id) {
        Materia materia = buscarPorId(id);
        materiaRepository.delete(materia);
    }

    private String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la materia es obligatorio.");
        }

        String nombreLimpio = nombre.trim();

        if (nombreLimpio.length() > 150) {
            throw new IllegalArgumentException(
                    "El nombre de la materia no puede superar los 150 caracteres.");
        }

        return nombreLimpio;
    }
}