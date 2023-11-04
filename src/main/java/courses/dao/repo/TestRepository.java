package courses.dao.repo;

import courses.dao.entity.Test;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
@ApplicationScoped
public class TestRepository implements PanacheRepository<Test> {

    public List<Test> getListOfTests(String courseId){
        return find("testCourse.id = ?1", courseId).list();
    }


}
