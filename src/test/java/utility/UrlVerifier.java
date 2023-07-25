package utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlVerifier {
	private boolean isalert, isbroken;
	private String message, url;

	public UrlVerifier(String component, String locale) {
		int responsecode = -1;
		isbroken = isalert = false;
		message = url = "";

		try {
			// getTestUrl() can return 1). Blank URL - handled  below. OR 2). two types of exception - catch below
			url = Utility.getTestUrl(component, locale);

			if (url.isEmpty()) {
				isbroken = true;
				message = "Failed to get URL for component: '" + component + "' and locale: '" + locale + "'";
			} else {
				// Below code can throw two types of exception - catch below
				URL objUrl = new URL(url);
				HttpURLConnection httpConnect = (HttpURLConnection) objUrl.openConnection();
				httpConnect.setConnectTimeout(5000);
				httpConnect.connect();
				responsecode = httpConnect.getResponseCode();

				if (responsecode > 200) {
					isbroken = true;
					message = "ResponseCode is '" + responsecode + "' for Url '" + url + "'";
				}
			}
		} catch (FileNotFoundException fnfex) {
			isbroken = true;
			message = "Failed to get status for Url '" + url + "'.\r\n FileNotFoundException:" + fnfex.getMessage();
			//System.out.println("FileNotFoundException: " + fnfex.getMessage());
			//fnfex.printStackTrace();
		} catch (MalformedURLException mfurlex) {
			isbroken = true;
			message = "Failed to get status for Url '" + url + "'.\r\n MalformedURLException: " + mfurlex.getMessage();
			//System.out.println("MalformedURLException: " + mfurlex.getMessage());
			//mfurlex.printStackTrace();
		} catch (IOException ioex) {
			// Note: Do not consider BROKEN in this case
			isalert = true;
			message = "IOException encountered while checking status of Url '" + url + "'. \tException message: " + ioex.getMessage();
			//System.out.println("IOException:" + ioex.getMessage());
			//ioex.printStackTrace();
		} catch (Exception ex) {
			isbroken = true;
			if (url.equals(""))
				message = "Failed to get URL for component: '" + component + "' and locale: '" + locale + "'";
			else
				message = "Failed to get status for Url '" + url + "'.\r\n Exception: " + ex.getMessage();	
			//System.out.println("Exception: " + ex.getMessage());
			//ex.printStackTrace();
		}
		if(isbroken) {
			Utility.addUrlStatus(message);
		}
		}

	public boolean isBroken() {
		return isbroken;
		}

	public boolean isAlert() {
		return isalert;
		}

	public String getMessage() {
		return message;
		}

	public String getURL() {
		return url;
		}
}
