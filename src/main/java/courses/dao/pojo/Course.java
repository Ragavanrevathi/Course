package courses.dao.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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

   // private ArrayList<Test> listOfTests;



}
