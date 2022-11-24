package org.acme;

import model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/hello")
public class ExampleResource {
    private List<User> userList = new ArrayList<>();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allUser() {
        return Response.ok(userList).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User newUser){
        userList.add(newUser);
        return Response.ok(userList).build();
    }

    @PUT
    @Path("{id}/{tel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(
            @PathParam("id") int id,
            @PathParam("tel") String tel) {
        userList = userList.stream().map(user -> {
            if(user.getId() == id) {
                System.out.println("id => "+id+" is Found");
                user.setTel(tel);
            }
            return user;
        }).collect(Collectors.toList());
        return Response.ok(userList).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie(
            @PathParam("id") Long id) {
        Optional<User> userToDelete = userList.stream().filter(user -> user.getId() == id).findFirst();
        boolean removed = false;
        if(userToDelete.isPresent()){
            removed = userList.remove(userToDelete.get());
        }
        if(removed) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}