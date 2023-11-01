package courses.controller;

import courses.dao.repo.CourseRepository;
import courses.dao.repo.TestRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Courses")
public class CoursesController {

    @Inject
    CourseRepository courseRepository;

    @Inject
    TestRepository testRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(){
       return Response.ok(courseRepository.getListOfCourses()).build();
    }

    @GET
    @Path("{CourseName}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTests(@PathParam("CourseName") String CourseName){

        return Response.ok(testRepository.getListOfTests(CourseName)).build();
    }

    @GET
    @Path("{CourseName}/{testName}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTestModules(@PathParam("testName") String testName){
        return Response.ok(testName).build();
    }

}
