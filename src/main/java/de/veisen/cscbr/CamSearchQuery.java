package de.veisen.cscbr;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Query for retrieval in the case base.
 * 
 * @author Viktor Eisenstadt
 */
public class CamSearchQuery extends HttpServlet {
	private static final long serialVersionUID = 4564048408781536527L;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws IOException
	 *             if an I/O error occurs
	 */
  private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet CamSearchQuery</title>");
			out.println("<link href='styles.css' rel='stylesheet' type='text/css' />");
			out.println("</head>");
			out.println("<body>");
			out.println("<div id='nav'><p>CamSearchCBR <span class='page-title'>/ Query</span></p></div>");
			out.println("<form method='POST' action='CamSearchResults' name='queryForm'>" + "<div class='query'>"
					+ "<fieldset id='cam-body'>" + "<h3>Body data</h3>" + "<div class='inputfield'>"
					+ "<label for='flash'>Built-in-flash</label>"
					+ "<input type='radio' name='flash' value='true' checked='checked'> Yes"
					+ "<input type='radio' name='flash' value='false'> No" + "</div>" + "<div class='inputfield'>"
					+ "<label for='dustreduct'>Dust reduction</label>"
					+ "<input type='radio' name='dustreduct' value='true' checked='checked'> Yes"
					+ "<input type='radio' name='dustreduct' value='false'> No" + "</div>" + "<div class='inputfield'>"
					+ "<label for='liveview'>Live view</label>"
					+ "<input type='radio' name='liveview' value='true' checked='checked'> Yes"
					+ "<input type='radio' name='liveview' value='false'> No" + "</div>" + "<div class='inputfield'>"
					+ "<label for='imagestab'>Image stabilization (body only)</label>"
					+ "<input type='radio' name='imagestab' value='true'> Yes"
					+ "<input type='radio' name='imagestab' value='false' checked='checked'> No" + "</div>"
					+ "<div class='inputfield'>" + "<label for='automodes'>Automatic modes</label>"
					+ "<input type='radio' name='automodes' value='true' checked='checked'> Yes"
					+ "<input type='radio' name='automodes' value='false'> No" + "</div>" + "<div class='inputfield'>"
					+ "<label for='multlang'>Multiple interface languages</label>"
					+ "<input type='radio' name='multlang' value='true' checked='checked'> Yes"
					+ "<input type='radio' name='multlang' value='false'> No" + "</div>" + "<div class='inputfield'>"
					+ "<label for='viewfinder'>Viewfinder</label>"
					+ "<input type='radio' name='viewfinder' value='optical' checked='checked'>optical "
					+ "<input type='radio' name='viewfinder' value='electronic'>electronic " + "</div>"
					+ "<div class='inputfield'>" + "<label for='wifi'>Wi-Fi</label>"
					+ "<input type='radio' name='wifi' value='true'> Yes"
					+ "<input type='radio' name='wifi' value='false' checked='checked'> No" + "</div>"
					+ "<div class='inputfield'>" + "<label for='video'>Video</label>"
					+ "<input type='radio' name='video' value='true'> Yes"
					+ "<input type='radio' name='video' value='false' checked='checked'> No" + "</div>"
					+ "<div class='inputfield'>" + "<label for='weight'>Weight (g)</label>"
					+ "<input type='text' name='weight' value='800'>" + "</div>" + "<div class='inputfield'>"
					+ "<label for='storagetype'>Storage type</label>" + "<select name='storagetype'>"
					+ "<option value='sd'>SD / SDHC</option>" + "<option value='compactflash'>Compact Flash</option>"
					+ "<option value='memorystick'>Memory Stick</option>" + "<option value='xqd'>XQD</option>"
					+ "</select>" + "</div>" + "</fieldset>" + "<fieldset id='imaging'>" + "<h3>Imaging</h3>"
					+ "<div class='inputfield'>" + "<label for='sensorclass'>Sensor Class</label>"
					+ "<select name='sensorclass'>" + "<option value='apsc'>APS-C (~23.6 x 15.7 mm)</option>"
					+ "<option value='apsh'>APS-H (28.7 x 19 mm)</option>"
					+ "<option value='fourthirds'>Four Thirds (17.3 x 13 mm)</option>"
					+ "<option value='fullframe'>Full Frame (36 x 24 mm)</option>" + "</select>" + "</div>"
					+ "<div class='inputfield'>" + "<label for='sensortype'>Sensor Type</label>"
					+ "<input type='radio' name='sensortype' value='ccd'>CCD "
					+ "<input type='radio' name='sensortype' value='cmos' checked='checked'>CMOS " + "</div>"
					+ "<div class='inputfield'>" + "<label for='imageratio'>Image ratio</label>"
					+ "<select name='imageratio'>" + "<option value='1610'>16:10</option>"
					+ "<option value='169'>16:9</option>" + "<option value='32'>3:2</option>"
					+ "<option value='43'>4:3</option>" + "<option value='54'>5:4</option>" + "</select>" + "</div>"
					+ "<div class='inputfield'>" + "<label for='effpix'>Effective pixels (million)</label>"
					+ "<input type='text' name='effpix' value='16'>" + "</div>" + "<div class='inputfield'>"
					+ "<label for='focuspoints'>Focus points</label>"
					+ "<input type='text' name='focuspoints' value='9'>" + "</div>"
					+ "<div class='inputfield'>" + "<label for='isomax'>ISO maximum</label>"
					+ "<input type='text' name='isomax' value='6400'>" + "</div>" + "<div class='inputfield'>"
					+ "<label for='whitebal'>White balance levels</label>"
					+ "<input type='text' name='whitebal' value='9'>" + "</div>" + "</fieldset>"
					+ "<button type='submit' id='send'>Search</button>"
					+ "<button type='reset' id='reset'>Reset</button>" + "</div>" + "</form>");
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
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
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
