package courses.dao.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Course")
@Getter
@Setter
public class Course {

    @Id
    @Column(name = "Course_Id")
    private int id;
    @Column(name = "Course_Name")
    private String courseName;
    @Column(name = "Start_Date")
    private LocalDate startDate;
    @Column(name = "End_Date")
    private LocalDate endDate;
    @Column(name = "NO_OF_TEST")
    private int numberOfTests;

    /*@OneToMany   @JoinColumn(name = "Test_Course_Id")
    private List<Test> listOfTests ;*/

}
