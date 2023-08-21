package de.veisen.cscbr;

import de.dfki.mycbr.core.Project;
import java.util.Objects;

/**
 * Contains methods for handling the myCBR project files.
 *
 * @author Viktor Eisenstadt
 */
class CBRInit {

  private final String data_path = Objects
      .requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();
  private static String projectName = "Cameras.prj";
  private static String conceptName = "Camera";
  private static String caseBase = "slr-base";

  String getCaseBase() {
    return caseBase;
  }

  public static void setCaseBase(String caseBase) {
    CBRInit.caseBase = caseBase;
  }

  public static String getProjectName() {
    return projectName;
  }

  public static void setProjectName(String projectName) {
    CBRInit.projectName = projectName;
  }

  static String getConceptName() {
    return conceptName;
  }

  public static void setConceptName(String conceptName) {
    CBRInit.conceptName = conceptName;
  }

  /**
   * This method loads a myCBR project from a .prj file and loads the cases in
   * this project. The specification of the project's location and according file
   * names has to be done at the beginning of this class.
   *
   * @return Project instance containing model, sims and cases (if available)
   * @author bach, hundt, sauer, Viktor Eisenstadt
   */
  Project createProjectFromPRJ() {

    Project project = null;

    try {
      System.out.println("data path: " + data_path);
      // load new project
      project = new Project(data_path + projectName);
      // create a concept and get the main concept of the project;
      // the name has to be specified at the beginning of this class
      while (project.isImporting()) {
        Thread.sleep(1000);
        System.out.print(". ");
      }
      System.out.println("\nProject imported successfully.");
    } catch (Exception ex) {
      System.out.println("Please move your Project files to " + data_path);
    }

    return project;
  }
}
