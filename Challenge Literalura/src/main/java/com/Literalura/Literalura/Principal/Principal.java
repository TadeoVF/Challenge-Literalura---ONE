package com.Literalura.Literalura.Principal;

import com.Literalura.Literalura.Model.*;
import com.Literalura.Literalura.Repository.AutorRepository;
import com.Literalura.Literalura.Repository.LibroRepository;
import com.Literalura.Literalura.Service.ConsumoAPI;
import com.Literalura.Literalura.Service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;


@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de su número:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosBuscados();
                    break;
                case 3:
                    listarAutoresBuscados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();

        // 1. Verificar si el libro ya existe en la base de datos usando la nueva consulta
        Optional<Libro> libroExistente = libroRepository.findByTituloContainsIgnoreCase(nombreLibro);
        if (libroExistente.isPresent()) {
            System.out.println("El libro '" + libroExistente.get().getTitulo() + "' ya se encuentra en la base de datos.");
            return; // Salir del método
        }

        // 2. Si no existe en la base de datos, buscar en la API
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));

        try {
            var datosBusqueda = conversor.obtenerDatos(json, ResultadoAPI.class);
            Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                    .findFirst();

            if (libroBuscado.isPresent()) {
                System.out.println("Libro encontrado en la API: " + libroBuscado.get());

                DatosAutor datosAutor = libroBuscado.get().autor().getFirst();

                // ... (el resto del código de guardado es el mismo que el que ya te había dado)
                Autor autor = autorRepository.findByNombre(datosAutor.nombre());
                if (autor == null) {
                    autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                }

                Libro libro = new Libro(libroBuscado.get());
                libro.setAutor(autor);
                libroRepository.save(libro);

                System.out.println("El libro ha sido guardado en la base de datos.");
            } else {
                System.out.println("Libro no encontrado en la API.");
            }
        } catch (Exception e) {
            System.out.println("Error al procesar los datos o guardar en la base de datos: " + e.getMessage());
        }
    }

    private void listarLibrosBuscados() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("Aún no has buscado ningún libro.");
        } else {
            System.out.println("--- Libros registrados en la base de datos ---");
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresBuscados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Aún no se han registrado autores en la base de datos.");
        } else {
            System.out.println("--- Autores registrados ---");
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Escribe el año:");
        try {
            var anio = teclado.nextInt();
            teclado.nextLine();

            // Usamos la consulta derivada para obtener los autores directamente de la base de datos
            List<Autor> autoresVivos = autorRepository.findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(anio, anio);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            } else {
                System.out.println("--- Autores vivos en el año " + anio + " ---");
                autoresVivos.forEach(System.out::println);
            }

        } catch (java.util.InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un año válido.");
            teclado.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Escriba el idioma para buscar los libros: ");
        System.out.println("es - Español");
        System.out.println("en - Inglés");

        var idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findByIdiomas(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros para el idioma: " + idioma);
        } else {
            System.out.println("--- Libros en el idioma " + idioma + " ---");
            libros.forEach(System.out::println);
        }
    }
}
