package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Department class.
 * This class contains test methods to verify the behavior of the Department class
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("COMS4156", testCourse);
    testDepartment = new Department("CS", courses, "Prof. Gail Kaiser", 50);
  }

  @Test
  public void constructorTest() {
    assertEquals("CS", testDepartment.getDeptCode(), "Department code should be 'CS'.");
    assertEquals("Prof. Gail Kaiser", testDepartment.getDepartmentChair(),
        "Department chair should be 'Prof. Gail Kaiser'.");
    assertEquals(50, testDepartment.getNumberOfMajors(), "Number of majors should be 50.");
    assertEquals(testCourse, testDepartment.getCourseSelection().get("COMS4156"),
        "Course COMS4156 should be present.");
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(51, testDepartment.getNumberOfMajors(), "Number of majors should increase by 1.");
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(49, testDepartment.getNumberOfMajors(), "Number of majors should decrease by 1.");
  }

  @Test
  public void dropPersonFromMajorWhenNoneTest() {
    Department deptWithZeroMajors = new Department("CS", new HashMap<>(), "Aditi Mutagi", 0);
    deptWithZeroMajors.dropPersonFromMajor();
    assertEquals(0, deptWithZeroMajors.getNumberOfMajors(), "Number of majors should remain 0.");
  }

  @Test
  public void addCourseTest() {
    Course newCourse = new Course("Aditi Mutagi", "116th St.", "10:10-11:25", 30);
    testDepartment.addCourse("COMS1234", newCourse);
    assertEquals(newCourse, testDepartment.getCourseSelection().get("COMS1234"),
        "Course COMS1234 should be added.");
  }

  @Test
  public void createCourseTest() {
    testDepartment.createCourse("COMS1234", "Aditi Mutagi", "116th St.", "10:10-11:25", 30);
    Course createdCourse = testDepartment.getCourseSelection().get("COMS1234");
    assertEquals("Aditi Mutagi", createdCourse.getInstructorName(),
        "Instructor should be 'Aditi Mutagi'.");
    assertEquals("116th St.", createdCourse.getCourseLocation(), "Location should be '116th St.'");
    assertEquals("10:10-11:25", createdCourse.getCourseTimeSlot(),
        "Time slot should be '10:10-11:25'.");
  }

  @Test
  public void toStringTest() {
    String expectedResult = "CS COMS4156: \nInstructor: Griffin Newbold; Location: 417 IAB; Time:" +
        " 11:40-12:55\n";
    assertEquals(expectedResult, testDepartment.toString());
  }

  /** The test course instance used for testing. */
  public static Department testDepartment;
  public static Course testCourse;
}

