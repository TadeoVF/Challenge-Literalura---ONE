package com.Literalura.Literalura.Repository;

import com.Literalura.Literalura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);
    List<Autor> findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(Integer anioNacimiento, Integer anioFallecimiento);

}
