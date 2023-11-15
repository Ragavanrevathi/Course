package courses.controller;

import courses.dao.entity.MCQ;
import courses.dao.manager.CourseManager;
import courses.dao.entity.Test;
import courses.services.ReadingExcelService;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;

@Path("/Courses")
public class CoursesController {

    private static final Logger LOGGER = Logger.getLogger(CoursesController.class);

    private final CourseManager courseManager;

    private final ReadingExcelService readingExcelService;



    @Inject
    public CoursesController(CourseManager courseManager, ReadingExcelService readingExcelService) {
        this.courseManager = courseManager;

        this.readingExcelService = readingExcelService;
    }

    @Inject
    @PersistenceUnit("portal")
    EntityManager portalEntityManager;

    @Inject
    @PersistenceUnit("vsb")
    EntityManager entityManager1;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(@QueryParam("studentId") long StudentId){

        try{
            return Response.status(Response.Status.OK)
                    .entity(courseManager.getListOfCourses(portalEntityManager,StudentId)).
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
    @Path("{courseId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTests(@PathParam("courseId") long courseId,@QueryParam("studentId") long studentId){


         try {
             List<Test> listOfTests = courseManager.getTests(courseId,studentId,portalEntityManager);
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
    @Path("{courseId}/{testId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTestModules(
            @PathParam("courseId") String courseId,
            @PathParam("testId") String testId) throws IOException {


        final String pathToTests = courseId+"/"+testId+"/";

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
