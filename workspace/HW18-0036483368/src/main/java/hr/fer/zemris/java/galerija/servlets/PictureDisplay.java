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
 * Servlet for displaying pictures in full resolution.
 * 
 * @author Marin Jurjević
 */
@WebServlet(urlPatterns="/showpicture")
public class PictureDisplay extends HttpServlet {

	/** serialUID */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		if (name == null) {
			resp.sendError(400, "Picture name not provided.");
			return;
		}
		
		Path path = Paths.get(req.getServletContext().getRealPath(ThumbnailProvider.RELATIVE_PATH_PICTURES), name);
		BufferedImage image = ImageIO.read(path.toFile());
		
		resp.setContentType("image/png");
		OutputStream os = resp.getOutputStream();
		ImageIO.write(image, "png", os);
		os.flush();
		os.close();
	}
}
