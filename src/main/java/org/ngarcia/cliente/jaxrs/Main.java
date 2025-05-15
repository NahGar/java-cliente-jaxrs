package org.ngarcia.cliente.jaxrs;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ngarcia.cliente.jaxrs.models.Curso;
import org.ngarcia.cliente.jaxrs.models.Instructor;

import java.util.List;

public class Main {
   public static void main(String[] args) {

      Client client = ClientBuilder.newClient();
      WebTarget rootUri = client.target("http://localhost:8080/webapp-jaxrs/api")
              .path("/cursos");

      //Curso curso = rootUri.path("/2").request(MediaType.APPLICATION_JSON).get(Curso.class);

      Response response = rootUri.path("/2").request(MediaType.APPLICATION_JSON).get();
      Curso curso = response.readEntity(Curso.class);
      System.out.println("Por id (2): " + curso);
      System.out.println("Status:"+response.getStatus());
      System.out.println("MediaType:"+response.getMediaType());

      //List<Curso> cursos = rootUri.request(MediaType.APPLICATION_JSON)
      //        .get(Response.class).readEntity(new GenericType<List<Curso>>(){});

      System.out.println("Listar:");
      listarCursos(rootUri);

      System.out.println("Crear:");
      Curso crearCurso = new Curso();
      crearCurso.setNombre("Linux");
      crearCurso.setDescripcion("Linux Redhat");
      //crearCurso.setInstructor("Bill Gates");
      Instructor instructor = new Instructor();
      instructor.setId(2L);
      crearCurso.setInstructor(instructor);

      crearCurso.setDuracion(500D);

      Entity<Curso> entityHeader = Entity.entity(crearCurso,MediaType.APPLICATION_JSON);
      curso = rootUri.request(MediaType.APPLICATION_JSON).post(entityHeader,Curso.class);
      System.out.println("Curso creado:"+curso);

      listarCursos(rootUri);

      System.out.println("Editar:");
      Curso editarCurso = curso;
      editarCurso.setNombre("Editado");

      entityHeader = Entity.entity(editarCurso,MediaType.APPLICATION_JSON);
      curso = rootUri.path("/"+curso.getId()).request(MediaType.APPLICATION_JSON)
              .put(entityHeader,Curso.class);
      System.out.println("Curso edoitado:"+curso);

      listarCursos(rootUri);

      System.out.println("Eliminar:");
      rootUri.path("/"+curso.getId()).request().delete();

      listarCursos(rootUri);

   }

   private static void listarCursos(WebTarget rootUri) {
      List<Curso> cursos;
      Response response;
      response = rootUri.request(MediaType.APPLICATION_JSON).get();
      cursos = response.readEntity(new GenericType<List<Curso>>(){});
      cursos.forEach(System.out::println);
   }
}
