package com.ibm.academy.ruletaapi.controllers;

import com.ibm.academy.ruletaapi.entities.Apuesta;
import com.ibm.academy.ruletaapi.entities.Ruleta;
import com.ibm.academy.ruletaapi.exceptions.NotFoundException;
import com.ibm.academy.ruletaapi.services.ApuestaDAOImp;
import com.ibm.academy.ruletaapi.services.RuletaDAOImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ruleta")
public class RuletaController
{
    @Autowired
    private RuletaDAOImp ruletaDAO;

    @Autowired
    private ApuestaDAOImp apuestaDAO;

    /**
     * Endpoint que crea un objeto tipo Ruleta en la DB
     * @return Ruleta guardada
     * @author DYMS 15/05/2022
     */
    @PostMapping()
    public ResponseEntity<?> crearRuleta()
    {
        Ruleta ruleta = new Ruleta();
        ruleta.setEstadoRuleta(Boolean.FALSE);
        ruletaDAO.guardarRuleta(ruleta);
        return new ResponseEntity<Ruleta>(ruleta, HttpStatus.CREATED);
    }

    /**
     * Endpoint que cambia el estado del objeto Ruleta con el ID proporcionado
     * @param id identificador de la ruleta a modificar
     * @return Estado actualizado de el objeto Ruleta
     * @author DYMS 16/05/2022
     */
    @PutMapping("/open/id/{id}")
    public ResponseEntity<?> abrirRuleta(@PathVariable Integer id)
    {
        Optional<Ruleta> oRuleta = ruletaDAO.buscarPorId(id);
        if (!oRuleta.isPresent())
            throw new NotFoundException(String.format("Ruleta con ID %d no existente",id));
        if (oRuleta.get().getEstadoRuleta()==Boolean.TRUE)
            throw new NotFoundException(String.format("La ruleta ya estaba abierta"));
        Boolean respuesta = ruletaDAO.abrirRuleta(oRuleta.get());
        return new ResponseEntity<Boolean>(respuesta,HttpStatus.OK);
    }

    /**
     * Endpoint que genera un objeto Apuesta y se le asigna una ruleta con el ID proporcionado, siempre y cuando su estado sea TRUE
     * @param id identificador del objeto Ruleta que sera asignado a el objeto Apuesta
     * @param apuesta obheto Apuesta con la informacion de la apuesta
     * @param result lista de errores al momento de obtener la informacion del objeto tipo Apuesta por medio de validaciones
     * @return apuesta guardada en la DB
     * @author DYMS 16/05/2022
     */
    @PutMapping("/bet/id/{id}")
    public ResponseEntity<?> apostar(@PathVariable Integer id,@Valid @RequestBody Apuesta apuesta, BindingResult result)
    {
        Map<String,Object> validaciones = new HashMap<String,Object>();
        if(result.hasErrors())
        {
            List<String> listaErrores = result.getFieldErrors().stream().map(errores->"Campo: "+errores.getField()+" "+errores.getDefaultMessage()).collect(Collectors.toList());
            validaciones.put("Lista Errores",listaErrores);
            return new ResponseEntity<Map<String,Object>>(validaciones,HttpStatus.BAD_REQUEST);
        }

        Optional<Ruleta> oRuleta = ruletaDAO.buscarPorId(id);
        if (!oRuleta.isPresent())
            throw new NotFoundException(String.format("Ruleta con ID %d no existente",id));

        if (oRuleta.get().getEstadoRuleta() == Boolean.FALSE)
            throw new NotFoundException(String.format("La ruleta con ID %d esta cerrada",id));
        if (apuesta.getColorApuesta()!=null) {
            if (apuesta.getNumeroApuesta() != null) {
                throw new NotFoundException(String.format("Solo se puede apostar por un campo numero o color"));
            }
        }else if (apuesta.getNumeroApuesta()==null){
            throw new NotFoundException(String.format("Debe de apostar minimo por un campo"));
        }

        Apuesta apuestaGuardada = apuestaDAO.guardar(apuesta);
        apuestaDAO.asignarRuleta(apuestaGuardada,oRuleta.get());
        return new ResponseEntity<Apuesta>(apuestaGuardada,HttpStatus.OK);
    }

    /**
     * Endpoint que cierra la ruleta, mostrando una lista de los objetos  que han sido asociados a el objeto ruleta con el ID proporcionado
     * desde el momento en el que se abrio, ademas elimina las aouestas nostradas de la DB
     * @param id identificador de el objeto Ruleta a cerrar
     * @return lista de las apuestas asociadas a la ruleta
     * @author DYMS 16/05/2022
     */
    @PutMapping("/cls/id/{id}")
    public ResponseEntity<?> cerrarRuleta(@PathVariable Integer id)
    {
        Optional<Ruleta> oRuleta = ruletaDAO.buscarPorId(id);
        if (!oRuleta.isPresent())
            throw new NotFoundException(String.format("Ruleta con ID %d no existente",id));
        if (oRuleta.get().getEstadoRuleta()==Boolean.FALSE)
            throw new NotFoundException(String.format("La ruleta ya se encuentra cerrada"));
        oRuleta.get().setEstadoRuleta(Boolean.FALSE);
        ruletaDAO.guardarRuleta(oRuleta.get());
        Set<Apuesta> apuestas = oRuleta.get().getApuestas();
        if (apuestas.isEmpty())
            throw new NotFoundException(String.format("Ruleta cerrada pero se encontraba sin apuestas"));
        apuestas.forEach(item -> apuestaDAO.borrar(item.getId()));
        return new ResponseEntity<Set<Apuesta>>(apuestas,HttpStatus.OK);
    }

    /**
     * Endpoint que muestra una lista de las ruletas registradas y sus estados
     * @return Lista de los objetos Ruleta en la DB
     * @author DYMS 16/05/2022
     */
    @GetMapping("/lista/ruletas")
    public ResponseEntity<?> mostrarRuletas()
    {
        List<Ruleta> ruletas = (List<Ruleta>) ruletaDAO.mostrarTodos();
        if (ruletas.isEmpty())
            throw new NotFoundException(String.format("Lista vacia"));
        return new ResponseEntity<List<Ruleta>>(ruletas,HttpStatus.OK);
    }
}
