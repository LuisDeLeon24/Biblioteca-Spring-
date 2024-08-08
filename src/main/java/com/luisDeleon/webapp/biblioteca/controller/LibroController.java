package com.luisDeleon.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luisDeleon.webapp.biblioteca.model.Libro;
import com.luisDeleon.webapp.biblioteca.service.LibroService;

@Controller
@RestController
@RequestMapping(value = "")
public class LibroController {
    @Autowired
    LibroService libroService;

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> listarLibros(){
        try{
            return ResponseEntity.ok(libroService.listarLibros());
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/libro")
    public ResponseEntity<Libro> buscarLibroPorId(@RequestParam Long Id, @RequestParam String nombre){
        try{
            return ResponseEntity.ok(libroService.buscarLibroPorId(Id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/libro")
    public ResponseEntity<Map<String , String>> agregarLibro(@RequestBody Libro libro){
        Map<String, String> response = new HashMap<>();
        try{
            libroService.guardarLibro(libro);
            response.put("message", "Libro se a creado con exito");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message", "Error");
            response.put("err", "Hubo un erro al crear el libro");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/libro")
    public ResponseEntity<Map<String, String>> editarLibro(@RequestParam Long id, @RequestBody Libro libroNuevo){
        Map<String, String> response = new HashMap<>();
        try{
            Libro libro = libroService.buscarLibroPorId(id);
            libro.setIsbn(libroNuevo.getIsbn());
            libro.setNombre(libroNuevo.getNombre());
            libro.setSinopsis(libroNuevo.getSinopsis());
            libro.setAutor(libroNuevo.getAutor());
            libro.setEditorial(libroNuevo.getEditorial());
            libro.setDisponibilidad(libroNuevo.getDisponibilidad());
            libro.setNumeroEstanteria(libroNuevo.getNumeroEstanteria());
            libro.setCluster(libroNuevo.getCluster());
            libro.setCategoria(libroNuevo.getCategoria());
            libroService.guardarLibro(libro);
            response.put("message", "el libro a sido actualizado");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message", "la Libro no se puede eliminar");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/libro")
    public ResponseEntity<Map<String , String>> eliminarLibro(@RequestParam Long id){
        Map<String, String > response = new HashMap<>();
        try{
            Libro libro = libroService.buscarLibroPorId(id);
            libroService.eliminarLibro(libro);
            response.put("message", "Libro Eliminada");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message", "la Libro no se puede eliminar");
            return ResponseEntity.badRequest().body(response);
        }
    }

}