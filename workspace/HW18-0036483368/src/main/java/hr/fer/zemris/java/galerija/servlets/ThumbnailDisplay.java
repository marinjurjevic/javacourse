package hr.fer.zemris.java.galerija.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for displaying thumbnail of picture whose name is recieved as
 * request's parameter.
 * 
 * @author Marin Jurjević
 */
@WebServlet(urlPatterns="/showthumbnail")
public class ThumbnailDisplay extends HttpServlet {

	/** serialUID */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String photoName = req.getParameter("name");
		if (photoName == null) {
			resp.sendError(400, "Picture name not provided.");
			return;
		}
		
		Path photoPath = Paths.get(req.getServletContext().getRealPath(ThumbnailProvider.RELATIVE_PATH_THUMBNAILS), photoName);
		BufferedImage image = ImageIO.read(photoPath.toFile());
		
		resp.setContentType("image/png");
		OutputStream os = resp.getOutputStream();
		ImageIO.write(image, "png", os);
		os.flush();
		os.close();
	}
}
