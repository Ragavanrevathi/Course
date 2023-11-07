package courses.controller;

import courses.dao.entity.MCQ;
import courses.dao.entity.Test;
import courses.dao.repo.CourseRepository;
import courses.dao.repo.TestRepository;
import courses.services.ReadingExcelService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;

@Path("/Courses")
public class CoursesController {

    private static final Logger LOGGER = Logger.getLogger(CoursesController.class);

    private final CourseRepository courseRepository;

    private final TestRepository testRepository;

    private final ReadingExcelService readingExcelService;

    @Inject
    public CoursesController(CourseRepository courseRepository, TestRepository testRepository, ReadingExcelService readingExcelService) {
        this.courseRepository = courseRepository;
        this.testRepository = testRepository;
        this.readingExcelService = readingExcelService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(){
        try{
            return Response.status(Response.Status.OK)
                    .entity(courseRepository.getListOfCourses()).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }catch (Exception e){
            LOGGER.error("Error while retrieving list of Courses :" + e.getMessage());
            return Response.status(Response.Status.BAD_GATEWAY).
                    entity("").
                    build();
        }

    }

    @GET
    @Path("{courseName}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTests(@PathParam("courseName") String courseName){
        final String[] courseIdArray = courseName.split("_");
        if (courseIdArray.length<2){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Course Id is not exist in request").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        final String courseId = courseIdArray[1];
         try {
             List<Test> listOfTests = testRepository.getListOfTests(courseId);
             if (listOfTests.isEmpty()){
                 return Response.status(Response.Status.NOT_FOUND)
                         .entity("Test not found for this Course")
                         .type(MediaType.TEXT_PLAIN)
                         .build();
             }
             return Response.status(Response.Status.OK)
                     .entity(listOfTests)
                     .type(MediaType.APPLICATION_JSON)
                     .build();
         }catch (Exception e){
             LOGGER.error("Error while retrieving list of Tests :" + e.getMessage());
             return Response.status(Response.Status.BAD_REQUEST).
                     entity(e.getMessage()).
                     type(MediaType.TEXT_PLAIN).
                     build();
         }

    }

    @GET
    @Path("{courseName}/{testName}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTestModules(
            @PathParam("courseName") String courseName,
            @PathParam("testName") String testName) throws IOException {

        final String[] courseArray = courseName.split("_");
        final String[] testArray = testName.split("_");
        if (courseArray.length<2 || testArray.length<2){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Course or test Id is not exist in request").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        final String pathToTests = courseArray[1]+"/"+testArray[1]+"/";

        try {
            List<MCQ> listOfMcqs = readingExcelService.getQuestionAndAnswer(pathToTests);
            if (listOfMcqs.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Questions not found for this Test")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity(listOfMcqs)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }catch (Exception e){
            LOGGER.error("Error while retrieving reading the Excel files :" + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(e.getMessage()).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
    }

}
