package io.WINGS.providers.IP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public interface GetIP {
	public static String go() throws IOException, MalformedURLException {
		URL cip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(cip.openStream()));
		String ip = in.readLine();
		return ip;
	}
}
