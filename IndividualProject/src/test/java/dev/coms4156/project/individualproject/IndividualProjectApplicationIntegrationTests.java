package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the IndividualProjectApplication class.
 * This class contains test methods to verify the behavior of the IndividualProjectApplication class
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationIntegrationTests {

  @Test
  public void testSetupMode() {
    String[] args = {"setup"};
    IndividualProjectApplication.main(args);

    MyFileDatabase database = IndividualProjectApplication.myFileDatabase;
    assertNotNull(database, "Database should be initialized.");
    assertNotNull(database.getDepartmentMapping(), "Department mapping should not be null.");
    assertFalse(database.getDepartmentMapping().isEmpty(),
        "Department mapping should not be empty.");

    // Verify that some departments exist
    assertTrue(database.getDepartmentMapping().containsKey("COMS"),
        "COMS department should be present.");
    assertTrue(database.getDepartmentMapping().containsKey("ECON"),
        "ECON department should be present.");
  }

}
