package courses.controller;

import courses.dao.repo.CourseRepository;
import courses.dao.repo.TestRepository;
import courses.services.ReadingExcelService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Path("/Courses")
public class CoursesController {

    @Inject
    CourseRepository courseRepository;

    @Inject
    TestRepository testRepository;

     @Inject
    ReadingExcelService readingExcelService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(){
       return Response.ok(courseRepository.getListOfCourses()).build();
    }

    @GET
    @Path("{courseName}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTests(@PathParam("courseName") String courseName){
        String courseId = courseName.split("_")[1];
        System.out.println(courseId);
        return Response.ok(testRepository.getListOfTests(courseId)).build();
    }

    @GET
    @Path("{courseName}/{testName}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTestModules(
            @PathParam("courseName") String courseName,
            @PathParam("testName") String testName) throws IOException {
        return Response.ok(readingExcelService.getQuestionAndAnswer(courseName,testName)).build();
    }

}
