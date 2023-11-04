package courses.dao.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @JoinColumn(name = "Test_Course_Id",
            referencedColumnName = "Course_Id",
            foreignKey = @ForeignKey(name = "Test_Course_Id",value = ConstraintMode.NO_CONSTRAINT)
              )
    @JsonbTransient
    private Course testCourse;

   @OneToMany   @JoinColumn(name = "Module_Test_Id",foreignKey = @ForeignKey(name = "Module_Test_Id",value = ConstraintMode.NO_CONSTRAINT))
   private List<Module> listOfModules ;


}
