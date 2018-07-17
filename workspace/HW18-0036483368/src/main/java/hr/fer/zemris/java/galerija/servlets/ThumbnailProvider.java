package hr.fer.zemris.java.galerija.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imgscalr.Scalr;

import com.google.gson.Gson;

import hr.fer.zemris.java.galerija.listener.Initializer.PhotoInfo;

/**
 * Servlet utilized for retrieving full picture names for pictures associated
 * with the tag passed as request's parameter. Information is returned in form
 * of JSON object containing array of {@link String} objects - full picture
 * names. Before returning all necessary information, servlet will ensure that
 * all pictures whose name is in resulting object have available thumbnails for
 * display. Thumbnails are prepared as pictures in 'png' format whose size is
 * 150 x 150.
 * 
 * @author Marin Jurjević
 */
@WebServlet(urlPatterns="/thumbnails")
public class ThumbnailProvider extends HttpServlet {
	
	/** Relative path to folder with pictures. */
	public static final String RELATIVE_PATH_PICTURES = "/WEB-INF/slike";
	/** Relative path to folder with thumbnails. */
	public static final String RELATIVE_PATH_THUMBNAILS = "/WEB-INF/thumbnails";
	/** Thumbnail width. */
	public static final Integer THUMBNAIL_WIDTH = 150;
	/** Thumbnail height. */
	public static final Integer THUMBNAIL_HEIGHT = 150;

	/** serialUID */
	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String tag = req.getParameter("tag");
		if (tag == null) {
			resp.sendError(400, "Tag not provided.");
			return;
		}
		
		Map<String, List<PhotoInfo>> tagsWithPhotoInfo = 
				(Map<String, List<PhotoInfo>>) req.getServletContext().getAttribute("tags");
		
		Path path = Paths.get(req.getServletContext().getRealPath(RELATIVE_PATH_THUMBNAILS));
		if (!Files.exists(path)) {
			Files.createDirectory(path);
		}		
		
		List<PhotoInfo> photos = tagsWithPhotoInfo.get(tag);
		List<String> presentThumbnails = new ArrayList<>();
		
		for (File t : path.toFile().listFiles()) {
			if (Files.isRegularFile(t.toPath())) {
				presentThumbnails.add(t.getName());
			}
		}
		
		for (PhotoInfo photo : photos) {
			if (!presentThumbnails.contains(photo.getName())) {
				createThumbnail(req, photo.getName());
			}
		}
		
		String[] fileNames = new String[photos.size()];
		fileNames = photos.stream().map(p -> p.getName()).collect(Collectors.toList()).toArray(fileNames);
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(fileNames);
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
	
	/**
	 * Creates picture thumbnail whose name is specified (thumbnail is
	 * in PNG format) and stores it in webapp's predefined directory used for
	 * thumbnail storage.
	 * 
	 * @param req servlet's request wrapper
	 * @param pictureName picture whose thumbnail is to be created
	 */
	private void createThumbnail (HttpServletRequest req, String pictureName) {
		
		Path originPath = Paths.get(req.getServletContext().getRealPath(RELATIVE_PATH_PICTURES), pictureName);
		Path destinationPath = Paths.get(req.getServletContext().getRealPath(RELATIVE_PATH_THUMBNAILS), pictureName);
		System.out.println(originPath);
		System.out.println(destinationPath);
		
		BufferedImage original = null;
		try {
			original = ImageIO.read(originPath.toFile());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception occurred during picture loading.");
		}
		
		double ratio = original.getHeight() / THUMBNAIL_HEIGHT;
		int width = (int) (original.getWidth() / ratio);
		int height = Math.min(original.getHeight(), THUMBNAIL_HEIGHT);
		
		BufferedImage scaled = Scalr.resize(original, width, height);
						
		try {
			ImageIO.write(scaled, "png", destinationPath.toFile());
		} catch (IOException e) {
			throw new RuntimeException("Exception occurred during thumbnail saving.");
		}
	}
}
