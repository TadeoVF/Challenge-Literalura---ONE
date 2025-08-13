package com.Literalura.Literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer numeroDeDescargas
) {
    @Override
    public String toString() {
        return "---------- LIBRO ----------" + "\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.get(0).nombre() + "\n" +
                "Idioma: " + idiomas.get(0) + "\n" +
                "Numero de descargas: " + numeroDeDescargas + "\n" +
                "---------------------------" + "\n";
    }
}
