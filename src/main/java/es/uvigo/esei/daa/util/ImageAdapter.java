package es.uvigo.esei.daa.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Base64;


import es.uvigo.esei.daa.dao.EventDAO;

public class ImageAdapter {
	/**
	 * Returns a base64 String of the image that is obtained from the path
	 * 
	 * @param path the path where the image is saved.
	 * @return a String of the image that is obtained from the path coded in base64
	 */
	public static String pathToImage(String path) throws SQLException, IOException {
		byte[] imageData;	
		try (InputStream is = EventDAO.class.getResourceAsStream(path)) {
				imageData = new byte[0];
				byte[] buffer = new byte[4096];
				int length;
				
				while ((length = is.read(buffer)) != -1) {
					byte[] newImageData = new byte[imageData.length + length];
					System.arraycopy(imageData, 0, newImageData, 0, imageData.length);
					System.arraycopy(buffer, 0, newImageData, imageData.length, length);
					imageData = newImageData;
				}
			}
		
		return Base64.getEncoder().encodeToString(imageData);
	}
}