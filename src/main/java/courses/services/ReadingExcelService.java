package courses.services;

import courses.dao.entity.MCQ;
import courses.dao.entity.Questions;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;;
@ApplicationScoped
public class ReadingExcelService {

    private static final Logger LOGGER = Logger.getLogger(ReadingExcelService.class);
    private static final String pathToCourses = "C:/Project/Courses/";
    public List<MCQ> getQuestionAndAnswer(final String courseName,final String testName) throws IOException {
        String path = pathToCourses+courseName.split("_")[1]+"/"+testName.split("_")[1]+"/";
        File testFilePath = new File(path);
        if(!testFilePath.exists()){

        }
        String[] testFiles = testFilePath.list();
        List<MCQ> listOfMcqs = new ArrayList<>();
        for (String testFile :testFiles){
             MCQ mcq = new MCQ();
             mcq.setCategory(testFile);
             mcq.setType("MCQ");
             mcq.setListOfQuestions(readExcelFile(path+testFile));
             listOfMcqs.add(mcq);
        }
        return listOfMcqs;
    }

    public List<Questions> readExcelFile(final String testFilePath) throws IOException {
        File file = new File( testFilePath);
        FileInputStream fis = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> itr = sheet.iterator();
        List<Questions> listOfQuestions = new ArrayList<>();
        while (itr.hasNext())
        {
            Row row = itr.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Questions question = new Questions();
            while (cellIterator.hasNext())
            {
                Cell cell = cellIterator.next();
                if(cell.getColumnIndex()==0) {
                    question.setQuestion(getValueFromCell(cell));
                }else {
                    question.getOptions().add(getValueFromCell(cell));
                }
            }
            listOfQuestions.add(question);

        }
        return listOfQuestions;
    }

    public String getValueFromCell(final Cell cell){
        if ((cell.getCellType() + "").equals("STRING")) {
             return cell.getStringCellValue();
        } else {
             return cell.getNumericCellValue()+"";
        }
    }

}
