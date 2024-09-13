package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseIntegrationTests {

  private MyFileDatabase db;
  private final String filePath = "./data.txt";

  @BeforeEach
  public void setUp() {
    db = new MyFileDatabase(0, filePath);
  }

  @Test
  public void testSaveAndLoad() throws IOException, ClassNotFoundException {
    HashMap<String, Department> testMap = new HashMap<>();
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("COMS4156", testCourse);
    testDepartment = new Department("CS", courses, "Prof. Gail Kaiser", 50);
    testMap.put("CS", testDepartment);
    db.setMapping(testMap);
    db.saveContentsToFile();

    // Reinitialize from the file
    MyFileDatabase newDb = new MyFileDatabase(0, filePath);
    HashMap<String, Department> resultMap = newDb.getDepartmentMapping();

    assertTrue(testMap.equals(resultMap));
  }
  public static Department testDepartment;
  public static Course testCourse;
}
