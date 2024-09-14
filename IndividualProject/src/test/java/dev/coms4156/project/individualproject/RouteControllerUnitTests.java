package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for the MyFileDatabase class.
 * This class contains test methods to verify the behavior of the MyFileDatabase class
 */
@WebMvcTest(RouteController.class)
public class RouteControllerUnitTests {

  /**
   * Initializes testCourse, testDepartment, mockMapping before each test.
   */
  @BeforeEach
  public void setUp() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Map<String, Course> courses = new HashMap<>();
    courses.put("4156", testCourse);

    Map<String, Department> mockMapping = new HashMap<>();
    Department testDepartment = new Department("CS", courses, "Prof. Gail Kaiser", 50);
    mockMapping.put("CS", testDepartment);

    when(testMyFileDatabase.getDepartmentMapping()).thenReturn(mockMapping);
    IndividualProjectApplication.myFileDatabase = testMyFileDatabase;
  }

  @Test
  public void testRetrieveDepartmentWithExistingDeptCode() throws Exception {
    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "CS")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("CS")));
  }

  @Test
  public void testRetrieveDepartmentWithNonExistingDeptCode() throws Exception {
    when(testMyFileDatabase.getDepartmentMapping()).thenReturn(new HashMap<>());

    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "TEST")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }


  @Test
  public void testRetrieveCourseGivenExistingCourse() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "CS")
            .param("courseCode", "4156")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("417 IAB")));
  }

  @Test
  public void testRetrieveCourseGivenNonExistingCourse() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "CS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testIsCourseFullWhenCourseFull() throws Exception {
    testCourse.setEnrolledStudentCount(250);
    mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "CS")
            .param("courseCode", "4156")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  public void testIsCourseFullWhenCourseNotFull() throws Exception {
    mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "CS")
            .param("courseCode", "4156")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  public void testGetMajorCtFromDept() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "CS")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("50 majors")));
  }

  @Test
  public void testIdentifyDeptChair() throws Exception {
    mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "CS")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Prof. Gail Kaiser")));
  }

  @Test
  public void testFindCourseLocation() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "CS")
            .param("courseCode", "4156")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("417 IAB")));
  }

  @Test
  public void testFindCourseInstructorForExistingCourse() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "CS")
            .param("courseCode", "4156")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Griffin Newbold is the instructor")));
  }

  @Test
  public void testFindCourseInstructorForNonExistingCourse() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "CS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseTimeForExistingCourse() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "CS")
            .param("courseCode", "4156")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("The course meets at: 11:40-12:55")));
  }

  @Test
  public void testFindCourseTimeForNonExistingCourse() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "CS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testAddMajorToDeptForExistingDept() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
            .param("deptCode", "CS")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully"));
  }

  @Test
  public void testAddMajorToDeptForNonExistingDept() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
            .param("deptCode", "TEST")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRemoveMajorFromDeptForExistingDept() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "CS")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  public void testRemoveMajorFromDeptForNonExistingDept() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "TEST")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testDropStudentFromExistingCourse() throws Exception {
    testCourse.setEnrolledStudentCount(1);
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "CS")
            .param("courseCode", "4156")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void testDropStudentFromExistingCourseWithNoStudents() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "CS")
            .param("courseCode", "4156")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Student has not been dropped."));
  }

  @Test
  public void testDropStudentFromNonExistingCourse() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "CS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  public static Course testCourse;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MyFileDatabase testMyFileDatabase;

}