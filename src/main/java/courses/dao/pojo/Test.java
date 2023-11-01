package courses.dao.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Entity
@Table(name = "Test")
@Getter
@Setter
public class Test {

    @Id
    @Column(name = "Test_Id")
    private int id;

    @Column(name = "Test_Name")
    private String testName;

    @ManyToOne
    @JoinColumn(name = "Test_Course_Id",referencedColumnName = "Course_Id")
    private Course testCourse;
   // private ArrayList<MCQ> mcqs;

}
