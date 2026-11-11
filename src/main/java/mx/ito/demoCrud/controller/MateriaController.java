package mx.ito.demoCrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.ito.demoCrud.entity.Materia;
import mx.ito.demoCrud.service.AreaService;
import mx.ito.demoCrud.service.MateriaService;

@Controller
public class MateriaController {

    private final MateriaService materiaService;
    private final AreaService areaService;

    public MateriaController(
            MateriaService materiaService,
            AreaService areaService) {

        this.materiaService = materiaService;
        this.areaService = areaService;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/materias";
    }

    @GetMapping("/materias")
    public String listar(Model model) {
        model.addAttribute("materias", materiaService.listar());
        return "materias/lista";
    }

    @GetMapping("/materias/nueva")
    public String nueva(Model model) {
        model.addAttribute("materia", new Materia());
        model.addAttribute("areas", areaService.listar());
        return "materias/formulario";
    }

    @GetMapping("/materias/{id}/editar")
    public String editar(
            @PathVariable("id") Long id,
            Model model,
            RedirectAttributes redirect) {

        try {
            model.addAttribute("materia", materiaService.buscarPorId(id));
            model.addAttribute("areas", areaService.listar());
            return "materias/formulario";
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/materias";
        }
    }

    @PostMapping("/materias/guardar")
    public String guardar(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam(name = "areaId", required = false) Long areaId,
            Model model,
            RedirectAttributes redirect) {

        try {
            if (id == null) {
                materiaService.crear(nombre, areaId);
                redirect.addFlashAttribute(
                        "mensaje", "Materia registrada correctamente.");
            } else {
                materiaService.actualizar(id, nombre, areaId);
                redirect.addFlashAttribute(
                        "mensaje", "Materia actualizada correctamente.");
            }

            return "redirect:/materias";

        } catch (IllegalArgumentException ex) {
            Materia materia = new Materia();
            materia.setId(id);
            materia.setNombre(nombre);

            model.addAttribute("materia", materia);
            model.addAttribute("areas", areaService.listar());
            model.addAttribute("areaSeleccionada", areaId);
            model.addAttribute("error", ex.getMessage());

            return "materias/formulario";
        }
    }

    @PostMapping("/materias/{id}/eliminar")
    public String eliminar(
            @PathVariable("id") Long id,
            RedirectAttributes redirect) {

        try {
            materiaService.eliminar(id);
            redirect.addFlashAttribute(
                    "mensaje", "Materia eliminada correctamente.");
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/materias";
    }
}