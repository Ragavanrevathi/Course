package courses.dao.repo;

import courses.dao.entity.Course;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
@ApplicationScoped
public class CourseRepository implements PanacheRepository<Course> {

    public List<Course> getListOfCourses(){
        return listAll();
    }
}
