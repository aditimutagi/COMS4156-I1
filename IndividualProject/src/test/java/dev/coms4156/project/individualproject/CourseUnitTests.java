package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 * This class contains test methods to verify the behavior of the Course class, including
 * testing the toString() method.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(249);
    boolean result = testCourse.enrollStudent();
    assertEquals(true, result, "Student should be enrolled successfully.");
    assertEquals(true, testCourse.isCourseFull(), "Enrollment should be full.");
  }

  @Test
  public void enrollStudentWhenFullTest() {
    testCourse.setEnrolledStudentCount(250); // Set enrolled count to capacity
    boolean result = testCourse.enrollStudent();
    assertEquals(false, result, "Enrollment should fail when the course is full.");
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(250); // Set enrolled count to capacity
    boolean result = testCourse.dropStudent();
    assertEquals(true, result, "Student should be dropped successfully.");
    assertEquals(false, testCourse.isCourseFull(), "Enrollment should no longer be full.");
  }

  @Test
  public void dropStudentWhenNoneEnrolledTest() {
    testCourse.setEnrolledStudentCount(0); // Set enrolled count to 0
    boolean result = testCourse.dropStudent();
    assertEquals(false, result, "Dropping a student should fail when no students are enrolled.");
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Aditi Mutagi");
    assertEquals("Aditi Mutagi", testCourse.getInstructorName(), "Instructor should be reassigned.");
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("116th St.");
    assertEquals("116th St.", testCourse.getCourseLocation(), "Location should be reassigned.");
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

