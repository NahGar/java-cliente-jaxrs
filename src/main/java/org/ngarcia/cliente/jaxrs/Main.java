package org.ngarcia.cliente.jaxrs;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ngarcia.cliente.jaxrs.models.Curso;

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

      response = rootUri.request(MediaType.APPLICATION_JSON).get();
      List<Curso> cursos = response.readEntity(new GenericType<List<Curso>>(){});

      System.out.println("Listar:");
      cursos.forEach(System.out::println);

   }
}
