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
    private static final String pathToCourses = "Courses/";
    public List<MCQ> getQuestionAndAnswer(String pathTotests) throws IOException {
        pathTotests = pathToCourses +pathTotests;
        final File testFileDirectory = new File(pathTotests);
        List<MCQ> listOfMcqs = new ArrayList<>();

        if(!testFileDirectory.exists()){
            LOGGER.error("Path not found for the Courses Directory ->"+pathTotests);
           return listOfMcqs;
        }
        final String[] testFiles = testFileDirectory.list();
        if (testFiles==null){
            LOGGER.error("No test file found in this Path ->"+pathTotests);
            return listOfMcqs;
        }

        for (final String testFile :testFiles){
             MCQ mcq = new MCQ();
             mcq.setCategory(testFile);
             mcq.setType("MCQ");
             mcq.setListOfQuestions(readExcelFile(pathTotests+testFile));
             listOfMcqs.add(mcq);
        }
        return listOfMcqs;
    }

    public List<Questions> readExcelFile(final String testFilePath) throws IOException {
        final File file = new File( testFilePath);
        final FileInputStream fis = new FileInputStream(file);

        final XSSFWorkbook wb = new XSSFWorkbook(fis);
        final XSSFSheet sheet = wb.getSheetAt(0);
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
