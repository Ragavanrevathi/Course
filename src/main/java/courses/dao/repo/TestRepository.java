package courses.dao.repo;

import courses.dao.pojo.Test;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
@ApplicationScoped
public class TestRepository implements PanacheRepository<Test> {

    public List<Test> getListOfTests(String CourseName){
        return list("testCourse.courseName = ?1", CourseName);
    }
}
