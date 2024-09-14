package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the MyFileDatabase class.
 * This class contains test methods to verify the behavior of the MyFileDatabase class
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  /**
   * Initializes testMyFileDatabase, testCourse, and testDepartment before each test.
   */
  @BeforeEach
  public void setUp() {
    // Flag 1 (not loading from file)
    testMyFileDatabase = new MyFileDatabase(1, "test");

    // Create mock Department object
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Map<String, Course> courses = new HashMap<>();
    courses.put("COMS4156", testCourse);
    testDepartment = new Department("CS", courses, "Prof. Gail Kaiser", 50);
  }

  @Test
  public void setMappingAndGetMappingTest() {
    // Create a new department mapping
    Map<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("CS", testDepartment);

    // Set mapping in the database
    testMyFileDatabase.setMapping((HashMap<String, Department>) departmentMapping);

    // Get mapping and verify its contents
    Map<String, Department> retrievedMapping = testMyFileDatabase.getDepartmentMapping();

    assertNotNull(retrievedMapping);
    assertEquals(1, retrievedMapping.size());
    assertEquals(testDepartment, retrievedMapping.get("CS"));
  }

  @Test
  public void testToString() {
    Map<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("CS", testDepartment);
    testMyFileDatabase.setMapping((HashMap<String, Department>) departmentMapping);

    String expectedString = "For the CS department: \n" + testDepartment.toString();
    String actualString = testMyFileDatabase.toString();

    assertEquals(expectedString, actualString);
  }

  public static MyFileDatabase testMyFileDatabase;
  public static Course testCourse;
  public static Department testDepartment;
}
