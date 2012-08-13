package com.travel.wifimap.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.travel.wifimap.json.entity.Point;

@Controller
public class GeolocationController {

	@Value(value = "${app.config.geodata.path}")
	private String filePath;

	@Value(value = "${app.config.geodata.filename}")
	private String fileName;

	private Gson gson = new Gson();

	@RequestMapping(value = { "/get-location" }, method = RequestMethod.GET)
	public void getLocation(HttpServletRequest request,
			HttpServletResponse response) {

		String address = getClientIpAddr(request);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			File file = null;
			file = new File(filePath + "/" + fileName);
			LookupService cl = new LookupService(file,
					LookupService.GEOIP_MEMORY_CACHE);
			Location loc = cl.getLocation(address);
			if (loc == null) {
				pw.println("{}");
			} else {
				Point point = new Point(loc.latitude, loc.longitude);
				JsonWriter writer = new JsonWriter(pw);
				writer.setHtmlSafe(false);
				writer.beginArray();
				gson.toJson(point, Point.class, writer);
				writer.endArray();
			}
			// String data = "gq=" + address;
			// String urlString = "http://www.getip.com/mapit/index.php";
			// URL url = new URL(urlString);
			// URLConnection conn = url.openConnection();
			// conn.setDoOutput(true);
			//
			// OutputStreamWriter writer = new OutputStreamWriter(
			// conn.getOutputStream());
			//
			// writer.write(data);
			// writer.flush();
			// String line;
			// InputStreamReader in = new
			// InputStreamReader(conn.getInputStream());
			// BufferedReader reader = new BufferedReader(in);
			// while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			// }
			// writer.close();
			// reader.close();

			// JsonObject obj = new JsonObject();
			// JsonReader json = new JsonReader(in);
			// Gson gson = new Gson();

			// if (in != null)
			// in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(pw);
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	public String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
