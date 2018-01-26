package de.veisen.cscbr;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.dfki.mycbr.core.ICaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;

/**
 * This class retrieves the best cameras from the myCBR Project 
 * and displays the results in the GUI.
 * 
 * @author Viktor Eisenstadt
 */
public class CamSearchResults extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CBRInit cbrinit;
	private Project prj;
	private ICaseBase defaultCB;
	private Concept concept;
	// private HashMap<String, ICaseBase> caseBases;
	private ArrayList<Hashtable<String, String>> resultTable;

	public CamSearchResults() throws Exception {
		this.cbrinit = new CBRInit();
		this.prj = cbrinit.createProjectFromPRJ();
		// this.caseBases = prj.getCaseBases();
		this.defaultCB = prj.getCB(cbrinit.getCaseBase());
		this.concept = prj.getConceptByID(CBRInit.getConceptName());
	}

	/**
	 * Retrieves the results from the myCBR Project and saves them into an ArrayList.
	 * 
	 * @param autoMode
	 * @param builtInFlash
	 * @param dustReduct
	 * @param effPix
	 * @param focusPoints
	 * @param ISOmax
	 * @param imageRatio
	 * @param imageStab
	 * @param liveView
	 * @param manufact
	 * @param multLang
	 * @param sensorType
	 * @param sensorClass
	 * @param storageType
	 * @param video
	 * @param viewfinder
	 * @param weight
	 * @param whiteBal
	 * @param wifi
	 * @param casesCount
	 * @return
	 */
	public ArrayList<Hashtable<String, String>> parseCSQuery(String autoMode, String builtInFlash, String dustReduct,
			int effPix, int focusPoints, int ISOmax, String imageRatio, String imageStab, String liveView,
			String manufact, String multLang, String sensorType, String sensorClass, String storageType, String video,
			String viewfinder, int weight, int whiteBal, String wifi, int casesCount) {

		int totalCasesCount = defaultCB.getCases().size();

		// Maximale Anzahl der gefundenen Fälle auf die Gesamtanzahl der Fälle
		// in der Fallbasis reduzieren, falls sie größer ist als die Gesamtanzahl
		if (casesCount > totalCasesCount) {
			casesCount = totalCasesCount;
		}

		SymbolDesc imageRatioSym = (SymbolDesc) concept.getAllAttributeDescs().get("Image ratio");
		SymbolDesc manufactSym = (SymbolDesc) concept.getAllAttributeDescs().get("Manufacturer");
		SymbolDesc sensorClassSym = (SymbolDesc) concept.getAllAttributeDescs().get("Sensor class (size)");
		SymbolDesc sensorTypeSym = (SymbolDesc) concept.getAllAttributeDescs().get("Sensor type");
		SymbolDesc storageTypeSym = (SymbolDesc) concept.getAllAttributeDescs().get("Storage type");
		SymbolDesc viewfinderSym = (SymbolDesc) concept.getAllAttributeDescs().get("Viewfinder");

		IntegerDesc effPixSym = (IntegerDesc) concept.getAllAttributeDescs().get("Effective pixels (million)");
		IntegerDesc focusPointsSym = (IntegerDesc) concept.getAllAttributeDescs().get("Focus points");
		IntegerDesc ISOmaxSym = (IntegerDesc) concept.getAllAttributeDescs().get("ISO max");
		IntegerDesc weightSym = (IntegerDesc) concept.getAllAttributeDescs().get("Weight (g)");
		IntegerDesc whiteBalSym = (IntegerDesc) concept.getAllAttributeDescs().get("White balance levels");

		SymbolDesc autoModeSym = (SymbolDesc) concept.getAllAttributeDescs().get("Automatic_modes");
		SymbolDesc builtInFlashSym = (SymbolDesc) concept.getAllAttributeDescs().get("Built_in_flash");
		SymbolDesc dustReductSym = (SymbolDesc) concept.getAllAttributeDescs().get("Dust_reduction");
		SymbolDesc imageStabSym = (SymbolDesc) concept.getAllAttributeDescs().get("Image_stabilization_(body)");
		SymbolDesc liveViewSym = (SymbolDesc) concept.getAllAttributeDescs().get("Live_view");
		SymbolDesc multLangSym = (SymbolDesc) concept.getAllAttributeDescs().get("Multiple_interface_languages");
		SymbolDesc videoSym = (SymbolDesc) concept.getAllAttributeDescs().get("Video");
		SymbolDesc wifiSym = (SymbolDesc) concept.getAllAttributeDescs().get("Wifi");

		// Retrieval initialisieren und Methode (sortiert absteigend nach Ähnlichkeit) festlegen
		Retrieval camRetrieval = new Retrieval(concept, defaultCB);
		camRetrieval.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);

		Instance queryInstance = camRetrieval.getQueryInstance();

		try {
			queryInstance.addAttribute(imageRatioSym, imageRatioSym.getAttribute(imageRatio));
			queryInstance.addAttribute(manufactSym, manufactSym.getAttribute(manufact));
			queryInstance.addAttribute(sensorClassSym, sensorClassSym.getAttribute(manufact));
			queryInstance.addAttribute(sensorTypeSym, sensorTypeSym.getAttribute(sensorType));
			queryInstance.addAttribute(storageTypeSym, storageTypeSym.getAttribute(storageType));
			queryInstance.addAttribute(viewfinderSym, viewfinderSym.getAttribute(viewfinder));
			// queryInstance.addAttribute(autoModeSym, autoModeSym.getAttribute(autoMode));
			// queryInstance.addAttribute(builtInFlashSym,
			// builtInFlashSym.getAttribute(builtInFlash));
			// queryInstance.addAttribute(dustReductSym,
			// dustReductSym.getAttribute(dustReduct));
			// queryInstance.addAttribute(imageStabSym,
			// imageStabSym.getAttribute(imageStab));
			// queryInstance.addAttribute(liveViewSym, liveViewSym.getAttribute(liveView));
			// queryInstance.addAttribute(multLangSym, multLangSym.getAttribute(multLang));
			// queryInstance.addAttribute(videoSym, videoSym.getAttribute(video));
			// queryInstance.addAttribute(wifiSym, wifiSym.getAttribute(wifi));
			queryInstance.addAttribute(effPixSym, effPixSym.getAttribute(effPix));
			queryInstance.addAttribute(focusPointsSym, focusPointsSym.getAttribute(focusPoints));
			queryInstance.addAttribute(ISOmaxSym, ISOmaxSym.getAttribute(ISOmax));
			queryInstance.addAttribute(weightSym, weightSym.getAttribute(weight));
			queryInstance.addAttribute(whiteBalSym, whiteBalSym.getAttribute(whiteBal));
			queryInstance.addAttribute(autoModeSym, autoModeSym.getAttribute(autoMode));
			queryInstance.addAttribute(builtInFlashSym, builtInFlashSym.getAttribute(builtInFlash));
			queryInstance.addAttribute(dustReductSym, dustReductSym.getAttribute(dustReduct));
			queryInstance.addAttribute(imageStabSym, imageStabSym.getAttribute(imageStab));
			queryInstance.addAttribute(liveViewSym, liveViewSym.getAttribute(liveView));
			queryInstance.addAttribute(multLangSym, multLangSym.getAttribute(multLang));
			queryInstance.addAttribute(videoSym, videoSym.getAttribute(video));
			queryInstance.addAttribute(wifiSym, wifiSym.getAttribute(wifi));
		} catch (ParseException ex) {
			Logger.getLogger(CamSearchResults.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Suche starten
		camRetrieval.start();

		// Get the results
		List<Pair<Instance, Similarity>> resultList = camRetrieval.getResult();
		if (resultList.size() > 0) {
			resultTable = new ArrayList<Hashtable<String, String>>();
			for (int i = 0; i < casesCount; i++) {
				resultTable.add(getAttributes(resultList.get(i), prj.getConceptByID(CBRInit.getConceptName())));
				System.out.println("liste " + resultTable.get(i).toString());
			}
		} else {
			resultTable = null;
		}

		return resultTable;
	}

	/**
	 * This method delivers a Hashtable which contains the Attributs names
	 * (Attributes of the case) combined with their respective values.
	 * 
	 * @author weber,koehler,namuth, Viktor Eisenstadt
	 * @param r
	 *            = An Instance.
	 * @param concept
	 *            = A Concept
	 * @return List = List containing the Attributes of a case with their values.
	 */
	public static Hashtable<String, String> getAttributes(Pair<Instance, Similarity> r, Concept concept) {

		Hashtable<String, String> resTable = new Hashtable<String, String>();
		ArrayList<String> categories = getCategories(r);
		// Add the similarity of the case
		resTable.put("Sim", String.valueOf(r.getSecond().getValue()));
		for (String category : categories) {
			// Add the Attribute name and its value into the Hashtable
			resTable.put(category,
					r.getFirst().getAttForDesc(concept.getAllAttributeDescs().get(category)).getValueAsString());
		}
		return resTable;
	}

	/**
	 * This Method generates an ArrayList, which contains all Categories of aa
	 * Concept.
	 * 
	 * @author weber,koehler,namuth, Viktor Eisenstadt
	 * @param r
	 *            = An Instance.
	 * @return List = List containing the Attributes names.
	 */
	public static ArrayList<String> getCategories(Pair<Instance, Similarity> r) {

		ArrayList<String> categories = new ArrayList<String>();
		// Read all Attributes of a Concept
		Set<AttributeDesc> categoryList = r.getFirst().getAttributes().keySet();
		for (AttributeDesc category : categoryList) {
			if (category != null) {
				// Add the String literals for each Attribute into the ArrayList
				categories.add(category.getName());
			}
		}
		return categories;
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// boolean automodesBoolean =
		// Boolean.parseBoolean(request.getParameter("automodes"));
		// boolean flashBoolean = Boolean.parseBoolean(request.getParameter("flash"));
		// boolean dustReductBoolean =
		// Boolean.parseBoolean(request.getParameter("dustreduct"));
		// boolean imageStabBoolean =
		// Boolean.parseBoolean(request.getParameter("imagestab"));
		// boolean liveViewBoolean =
		// Boolean.parseBoolean(request.getParameter("liveview"));
		// boolean multLangBoolean =
		// Boolean.parseBoolean(request.getParameter("multlang"));
		// boolean videoBoolean = Boolean.parseBoolean(request.getParameter("video"));
		// boolean wifiBoolean = Boolean.parseBoolean(request.getParameter("wifi"));

		int effPixInt = Integer.parseInt(request.getParameter("effpix"));
		int focusPointsInt = Integer.parseInt(request.getParameter("focuspoints"));
		int ISOmaxInt = Integer.parseInt(request.getParameter("isomax"));
		int weightInt = Integer.parseInt(request.getParameter("weight"));
		int whiteBalInt = Integer.parseInt(request.getParameter("whitebal"));

		ArrayList<Hashtable<String, String>> queryResult = parseCSQuery(request.getParameter("automodes"),
				request.getParameter("flash"), request.getParameter("dustreduct"), effPixInt, focusPointsInt, ISOmaxInt,
				request.getParameter("imageratio"), request.getParameter("imagestab"), request.getParameter("liveview"),
				request.getParameter("manufact"), request.getParameter("multlang"), request.getParameter("sensortype"),
				request.getParameter("sensorclass"), request.getParameter("storagetype"), request.getParameter("video"),
				request.getParameter("viewfinder"), weightInt, whiteBalInt, request.getParameter("wifi"), 5);

		// Ergebnisse ausgeben
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Search Results</title>");
			out.println("<link href='styles.css' rel='stylesheet' type='text/css' />");
			out.println("<link href='iconfont.css' rel='stylesheet' type='text/css' />");
			out.println("</head>");
			out.println("<body>");
			out.println("<div id='nav'><p>CamSearchCBR <span class='page-title'>/ Search Results</span></p></div>");
			// out.println("<br>used Case Bases: " + prjName);
			// out.println("<br>default Case Bases: " + defaultCB.toString());
			// out.println("<br>concept: " + concept.toString());
			out.println("<h3 id='new-query'><a href='CamSearchQuery'>New Query</a></h3>");

			// Anfrage in der Konsole ausgeben
			out.println("<br> automodes: " + request.getParameter("automodes") + " " + "<br> flash: "
					+ request.getParameter("flash") + " " + "<br> dust reduct: " + request.getParameter("dustreduct")
					+ " " + "<br> eff pix: " + effPixInt + " " + "<br>focus points: " + focusPointsInt + " "
					+ "<br> iso max: " + ISOmaxInt + " " + "<br> image ratio: " + request.getParameter("imageratio")
					+ " " + "<br> stab: " + request.getParameter("imagestab") + " " + "<br> lv: "
					+ request.getParameter("liveview") + " " + "<br> manufact: " + request.getParameter("manufact")
					+ " " + "<br> multlang: " + request.getParameter("multlang") + " " + "<br> stype: "
					+ request.getParameter("sensortype") + " " + "<br> sclass: " + request.getParameter("sensorclass")
					+ " " + "<br> storage: " + request.getParameter("storagetype") + " " + "<br> video: "
					+ request.getParameter("video") + " " + "<br> vf: " + request.getParameter("viewfinder") + " "
					+ "<br> weight: " + weightInt + " " + "<br> white balance: " + whiteBalInt + " " + "<br> wifi: "
					+ request.getParameter("wifi"));

			out.println("<div id='search-results'>");
			queryResult.stream().map((queryRes) -> {
				Hashtable<String, String> singleResult = new Hashtable<>();
				singleResult = queryRes;
				return singleResult;
			}).forEach((singleResult) -> {

				// Ausgabe der einzelnen Eigenschaften der Ergebnisse in einer Tabelle
				out.println("<div class='single-result'>" + "<table class='single-result-table'>"
						+ "<tr class='cam-name'><td><span class='icon-camera'></span></td>" + "<td>"
						+ singleResult.get("Manufacturer") + " " + singleResult.get("Model") + "</td></tr>");
				Enumeration<String> items = singleResult.keys();
				while (items.hasMoreElements()) {

					String key = (String) items.nextElement();

					if (!"Sim".equals(key)) {

						String value = singleResult.get(key);

						// Anpassen der Ergebnis-Strings: Umwandlung in die verständlichere Form für den Benutzer
						switch (value) {
						case "true":
							value = "Yes";
							break;
						case "false":
							value = "No";
							break;
						case "apsc":
							value = "APS-C (~23.6 x 15.7 mm)";
							break;
						case "apsh":
							value = "APS-H (28.7 x 19 mm)";
							break;
						case "fourthirds":
							value = "Four Thirds (17.3 x 13 mm)";
							break;
						case "fullframe":
							value = "Full Frame (36 x 24 mm)";
							break;
						case "ccd":
							value = "CCD";
							break;
						case "cmos":
							value = "CMOS";
							break;
						}
						value = value.replace("32;", "3:2 ");
						value = value.replace("43;", "4:3 ");
						value = value.replace("54;", "5:4 ");
						value = value.replace("169;", "16:9 ");
						value = value.replace("1610;", "16:10 ");
						value = value.replace("sd;", "SD/SDHC ");
						value = value.replace("xqd;", "XQD ");
						value = value.replace("compactflash;", "Compact Flash ");
						value = value.replace("memorystick;", "Memory Stick ");

						out.println("<tr class='td'><td>" + key + "</td><td><strong> " + value + "</strong></td></tr>");
					}
				}
				// Ausgabe der Buttons und des Ähnlichkeitswertes
				out.println("<tr class='buttons'><td>" + "SimScore: <strong>" + singleResult.get("Sim")
						+ "</strong></td>" + "</tr></table></div>");
			});
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
