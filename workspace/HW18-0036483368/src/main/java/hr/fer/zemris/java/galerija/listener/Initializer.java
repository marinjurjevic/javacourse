package hr.fer.zemris.java.galerija.listener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * {@link WebListener} initializes necessary collections of information about
 * present gallery photos and user defined tags that are associated with them.
 * 
 * @author Marin Jurjević
 */
@WebListener
public class Initializer implements ServletContextListener {
	
	/** Relative path to file with informations about available photographs. */
	private static final String relativePathInfo = "/WEB-INF/opisnik.txt";
	
	/** Delimiter separating multiple tags in photo description. */
	private static final String tagDelimiter = "\\s*,\\s*";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Path path = Paths.get(sce.getServletContext().getRealPath(relativePathInfo));
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Exception occurred during photo info retrieval.");
		}
		
		List<PhotoInfo> photoInfo = parsePhotoInformation(lines);
		sce.getServletContext().setAttribute("photos", photoInfo);
		
		Map<String, List<PhotoInfo>> tags = parsePhotoTags(photoInfo);
		sce.getServletContext().setAttribute("tags", tags);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}
	
	/**
	 * Parses document lines retrieved from file with information about photos.
	 * Information about one photo needs to consist of three lines in following
	 * order and meaning:
	 * <ul>
	 * <li>full photo name with extension</li>
	 * <li>photo description</li>
	 * <li>tags associated with photos</li>
	 * </ul>
	 * 
	 * @param lines document lines retrieved from file with information about
	 *              photos
	 * @return retrieved information as collection of {@link PhotoInfo} objects
	 */
	private List<PhotoInfo> parsePhotoInformation(List<String> lines) {
		List<PhotoInfo> result = new ArrayList<>();
		
		for (int i = 0, j = lines.size(); i+2 < j; i+=3) {
			String photoName = lines.get(i);
			String photoDescription = lines.get(i + 1);
			List<String> photoTags = Arrays.asList(lines.get(i + 2).split(tagDelimiter));
			result.add(new PhotoInfo(photoName, photoDescription, photoTags));
		}
		
		return result;
	}
	
	/**
	 * Creates map whose key is photo tag and it's value is collection of all
	 * photos associated with aforementioned tag.
	 * 
	 * @param photoInfo list of photos
	 * @return map whose key is photo tag, and value collection of associated
	 *         photos
	 */
	private Map<String, List<PhotoInfo>> parsePhotoTags(List<PhotoInfo> photoInfo) {
		Map<String, List<PhotoInfo>> result = new HashMap<>();
		
		for (PhotoInfo photo : photoInfo) {
			for (String tag : photo.getTags()) {
				if (result.containsKey(tag)) {
					result.get(tag).add(photo);
				} else {
					List<PhotoInfo> photos = new ArrayList<>();
					photos.add(photo);
					result.put(tag, photos);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Encapsulates information of one photograph.
	 * 
	 * @author Hrvoje Bušić
	 */
	public static class PhotoInfo {
		
		/** Photograph's name. */
		private String name;
		/** Photograph's description. */
		private String description;
		/** Tags associated with photograph. */
		private List<String> tags;
		
		/**
		 * Creates new PhotoInfo object.
		 * @param name photograph's name
		 * @param description photograph's description
		 * @param tags tag tags associated with photograph
		 */
		public PhotoInfo(String name, String description, List<String> tags) {
			this.name = name;
			this.description = description;
			this.tags = tags;
		}

		/**
		 * Photograph's name.
		 * @return name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Photograph's description.
		 * @return description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * Tags associated with photograph.
		 * @return photograph tags
		 */
		public List<String> getTags() {
			return Collections.unmodifiableList(tags);
		}		
	}
}
