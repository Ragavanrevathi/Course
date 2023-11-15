package courses.dao.manager;

import courses.dao.entity.Course;
import courses.dao.entity.Module;
import courses.dao.entity.Test;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class CourseManager {

    @Transactional
    public List<Course> getListOfCourses(EntityManager em,long studentId){
        // return find("select courseName , count(Test.id) from Course join Test on id = Test.id group by Test.id").stream().toList();
        String nativeQuery = "SELECT " +
                "    c.Course_Id, " +
                "    c.Course_Name, " +
                "    c.Start_Date, " +
                "    c.End_Date, " +
                "    COALESCE(tc.PendingCount, 0) AS PendingCount, " +
                "    COALESCE(tc.CompletedCount, 0) AS CompletedCount, " +
                "    COUNT(t.Test_Id) AS TotalTests " +
                "FROM " +
                "    Course c " +
                "LEFT JOIN " +
                "    Test t ON c.Course_Id = t.Test_Course_Id " +
                "LEFT JOIN ( " +
                "    SELECT " +
                "        Student_Course_Id, " +
                "        CAST(SUM(CASE WHEN Test_Status = 'Pending' THEN 1 ELSE 0 END) AS DECIMAL) AS PendingCount, " +
                "        CAST(SUM(CASE WHEN Test_Status = 'Completed' THEN 1 ELSE 0 END) AS DECIMAL) AS CompletedCount " +
                "    FROM " +
                "        Student_Test_Status " +
                "    WHERE " +
                "        Student_Id = :studentId " +
                "    GROUP BY " +
                "        Student_Course_Id " +
                ") tc ON c.Course_Id = tc.Student_Course_Id " +
                "GROUP BY " +
                "    c.Course_Id, c.Course_Name, c.Start_Date, c.End_Date, tc.PendingCount, tc.CompletedCount";

        List<Object[]> resultList = em.createNativeQuery(nativeQuery)
                .setParameter("studentId", studentId)
                .getResultList();



        return resultList.stream()
                .map(result -> new Course(
                        ((Number) result[0]),
                        (String) result[1],
                        (Date) result[2],
                        (Date) result[3],
                        ((Number) result[4]),
                        ((Number) result[5]),
                        ((Number) result[6])))
                .collect(Collectors.toList());
    }

    public List<Test> getTests(long courseId,long studentId,EntityManager em){
        String query = "select Test_Id,Test_Name,Module_Id,Module_Name,Module_Type,No_Of_Questions,Mark_Per_Question,Duration_Of_Test,Student_Test_Status.Test_Status,Student_Id from Test\n" +
                "join TestModule on " +
                "Test.Test_Id = TestModule.Module_Test_Id " +
                "left join Student_Test_Status on " +
                "TestModule.Module_Test_Id = Student_Test_Status.Student_Test_Id " +
                "And Student_Id = :studentId "+
                "WHERE Test_Course_Id = :courseId ;" ;

        List<Object[]> resultList = em.createNativeQuery(query)
                .setParameter("studentId",studentId)
                .setParameter("courseId",courseId)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.groupingBy(
                        result -> (int) result[0], // Group by Test_Id
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                values -> {
                                    Test existingTest = null;
                                    for (Object value : values) {
                                        Object[] result = (Object[]) value;
                                        if (existingTest == null) {
                                            // If the Test doesn't exist, create a new one
                                            existingTest = new Test(
                                                    (int) result[0],
                                                    (String) result[1],
                                                    (result[8] != null) ? (String) result[8] : "Not Started",
                                                    new ArrayList<>()
                                            );
                                        }
                                        // Add the new Module to the existing Test
                                        existingTest.getListOfModules().add(
                                                new Module(
                                                        (int) result[2], (String) result[3], (String) result[4], (int) result[5], (int) result[6], (int) result[7]
                                                )
                                        );
                                    }
                                    return existingTest;
                                }
                        )
                ))
                .values() // Get the values from the map
                .stream()
                .collect(Collectors.toList());


    }


}
