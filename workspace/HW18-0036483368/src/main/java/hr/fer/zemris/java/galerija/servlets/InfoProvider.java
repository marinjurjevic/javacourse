package hr.fer.zemris.java.galerija.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.galerija.listener.Initializer.PhotoInfo;

/**
 * Servlet utilized for retrieving information, picture description and
 * associated tags, about photo whose name is served as parameter. Information
 * is returned as JSON object, whose first element is {@link String} object -
 * photo description, and whose second element is array of {@link String}
 * objects - tags associated with the photo.
 * 
 * @author Marin Jurjević
 */
@WebServlet(urlPatterns="/pictureinfo")
public class InfoProvider extends HttpServlet {

	/** serialUID */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		if (name == null) {
			resp.sendError(400, "Picture name not provided.");
			return;
		}
		
		List<PhotoInfo> photos = (List<PhotoInfo>) req.getServletContext().getAttribute("photos");
		PhotoInfo photo = null;
		for (PhotoInfo p : photos) {
			if (p.getName().equals(name)) {
				photo = p;
				break;
			}
		}
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(new Object[]{photo.getDescription(), photo.getTags()});
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
}
