package hr.fer.zemris.java.galerija.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.galerija.listener.Initializer.PhotoInfo;

/**
 * 
 * Servlet for retrieving all tags currently used in all pictures of the gallery.
 * Tags are stored in array of String objects representing tags.
 * 
 * @author Marin Jurjević
 */
@WebServlet(urlPatterns="/tags")
public class TagProvider extends HttpServlet {

	/** serialUID */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Map<String, List<PhotoInfo>> tagsWithPhotoInfo = 
				(Map<String, List<PhotoInfo>>) req.getServletContext().getAttribute("tags");
		Set<String> tagsSet = tagsWithPhotoInfo.keySet();
		String[] tags = new String[tagsSet.size()];
		tags = tagsSet.toArray(tags);
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
}
