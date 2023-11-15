package courses.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue
    private Number id;
    private String courseName;
    private Date startDate;
    private Date endDate;
    private Number pendingTests;
    private Number completedTests;
    private Number numberOfTests;

}
