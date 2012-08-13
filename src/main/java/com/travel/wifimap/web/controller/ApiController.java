package com.travel.wifimap.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.travel.wifimap.domain.Category;
import com.travel.wifimap.domain.Comment;
import com.travel.wifimap.domain.Venue;
import com.travel.wifimap.service.CategoryService;
import com.travel.wifimap.service.CommentService;
import com.travel.wifimap.service.VenueService;
import com.travel.wifimap.util.StringUtils;

@Controller
public class ApiController {

	private Gson gson = buildGson();

	@Autowired
	private VenueService venueService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/venue/add", method = RequestMethod.POST)
	public void addVenue(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();

			Venue venue = new Venue();
			String name = request.getParameter("name");
			if (name != null) {
				venue.setName(name);
			}
			venue.setSource("loca");
			venue.setSourceId(null);
			String latitude = request.getParameter("latitude");
			if (latitude != null) {
				venue.setLatitude(Double.valueOf(latitude));
			}
			String longitude = request.getParameter("longitude");
			if (longitude != null) {
				venue.setLongitude(Double.valueOf(longitude));
			}
			String address = request.getParameter("address");
			if (address != null) {
				venue.setAddress(address);
			}
			String postalCode = request.getParameter("postalCode");
			if (postalCode != null) {
				venue.setPostalCode(postalCode);
			}
			String city = request.getParameter("city");
			if (city != null) {
				venue.setCity(city);
			}
			String country = request.getParameter("country");
			if (country != null) {
				venue.setCountry(country);
			}
			String state = request.getParameter("state");
			if (state != null) {
				venue.setState(state);
			}
			String photoUrl = request.getParameter("photoUrl");
			if (photoUrl != null) {
				venue.setPhotoUrl(photoUrl);
			}
			String ssid = request.getParameter("ssid");
			if (ssid != null) {
				venue.setSsid(ssid);
			}
			String password = request.getParameter("password");
			if (password != null) {
				venue.setPassword(password);
			}
			String categories[] = request.getParameterValues("categories");
			if (categories != null && categories.length > 0) {
				List<Category> ctgrs = new ArrayList<Category>(
						categories.length);
				for (String categoryId : categories) {
					Category category = categoryService.findById(categoryId);
					if (category != null) {
						ctgrs.add(category);
					}
				}
				venue.setCategories(ctgrs);
			}
			venueService.saveOrUpdate(venue);
			String id = venue.getId();
			if (StringUtils.isEmpty(id)) {
				pw.print("{\"state\":\"error\",\"error_message\":\"Internal error\"}");
			} else {
				pw.print("{\"state\":\"ok\",\"id\":\"" + id + "\"}");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	@RequestMapping(value = "/venue/{venueId}", method = RequestMethod.GET)
	public void getVenue(@PathVariable String venueId,
			HttpServletResponse response) {

		PrintWriter pw = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();

			Venue venue = venueService.findById(venueId);
			if (venue != null) {
				gson.toJson(venue, Venue.class, pw);
			} else {
				pw.println("{}");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	@RequestMapping(value = "/venues/search", method = RequestMethod.GET)
	public void venueSearch(HttpServletResponse response,
			@RequestParam(value = "ll", required = true) String ll,
			@RequestParam(value = "radius", required = false) String radius,
			@RequestParam(value = "limit", required = false) String limit) {
		PrintWriter pw = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();

			String[] coords = ll.split(",");
			if (coords != null && coords.length == 2) {
				Double lat = Double.valueOf(coords[0]);
				Double lng = Double.valueOf(coords[1]);
				Double rad = StringUtils.isEmpty(radius) ? null : Double
						.valueOf(radius);
				Integer lim = StringUtils.isEmpty(limit) ? null : Integer
						.valueOf(limit);
				List<Venue> venues = venueService.search(lat, lng, rad, lim);
				if (venues != null && venues.size() > 0) {
					gson.toJson(venues, pw);
				} else {
					pw.println("[]");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	@RequestMapping(value = "/venues/categories", method = RequestMethod.GET)
	public void venuesCategories(HttpServletResponse response) {

		PrintWriter pw = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();

			List<Category> categories = categoryService.list();
			if (categories != null && categories.size() > 0) {
				gson.toJson(categories, pw);
			} else {
				pw.println("[]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	@RequestMapping(value = "/venue/{venueId}/categories", method = RequestMethod.GET)
	public void getVenueCategories(@PathVariable String venueId,
			HttpServletResponse response) {

		PrintWriter pw = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();

			StringBuilder sb = new StringBuilder();
			Venue venue = venueService.findById(venueId);
			if (venue != null) {
				List<Category> categories = venue.getCategories();
				if (categories != null && categories.size() > 0) {
					gson.toJson(categories, pw);
				} else {
					pw.println("[]");
				}
			}
			pw.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	@RequestMapping(value = "/venue/{venueId}/comment/add", method = RequestMethod.POST)
	public void addComment(@PathVariable String venueId,
			HttpServletRequest request, HttpServletResponse response) {

		PrintWriter pw = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();

			Comment comment = new Comment();
			String text = request.getParameter("text");
			if (text != null) {
				comment.setText(String.valueOf(text));
			}
			comment.setSource("loca");
			comment.setSourceId(null);
			comment.setCreatedAt(System.currentTimeMillis() / 1000L);
			String userId = request.getParameter("userId");
			if (text != null) {
				comment.setUserId(String.valueOf(userId));
			}
			if (venueId != null) {
				Venue venue = venueService.findById(String.valueOf(venueId));
				if (venue != null) {
					comment.setVenue(venue);
				}
				// comment.setVenueId(venueId);
			}
			commentService.saveOrUpdate(comment);

			String id = comment.getId();
			if (StringUtils.isEmpty(id)) {
				pw.print("{\"state\":\"error\",\"error_message\":\"Internal error\"}");
			} else {
				pw.print("{\"state\":\"ok\",\"id\":\"" + id + "\"}");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	@RequestMapping(value = "/venue/{venueId}/comments", method = RequestMethod.GET)
	public void getVenueComments(@PathVariable String venueId,
			HttpServletResponse response) {

		PrintWriter pw = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();

			List<Comment> comments = venueService.getVenueComments(venueId);
			if (comments != null && comments.size() > 0) {
				gson.toJson(comments, pw);
			} else {
				pw.print("[]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	private Gson buildGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder.registerTypeAdapter(Venue.class, new TypeAdapter<Venue>() {

			@Override
			public void write(JsonWriter out, Venue value) throws IOException {

				out.beginObject();
				out.name("id").value(value.getId());
				out.name("name").value(value.getName());
				out.name("lat").value(value.getLatitude());
				out.name("lng").value(value.getLongitude());
				out.name("address").value(value.getAddress());
				out.name("city").value(value.getCity());
				out.name("state").value(value.getState());
				out.name("country").value(value.getCountry());
				out.name("postalCode").value(value.getPostalCode());
				out.name("photoUrl").value(value.getPhotoUrl());
				Timestamp createdAt = value.getCreatedAt();
				out.name("createdAt").value(
						(createdAt == null) ? "" : String.valueOf(createdAt
								.getTime() / 1000)); // to UNIXRIMESTAMP
				out.name("categories");
				if (value.getCategories() != null
						&& value.getCategories().size() > 0) {
					gson.toJson(value.getCategories(), List.class, out);
				} else {
					out.beginArray().endArray();
				}
				out.endObject();
			}

			@Override
			public Venue read(JsonReader in) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
		});
		gsonBuilder.registerTypeAdapter(Comment.class,
				new TypeAdapter<Comment>() {

					@Override
					public void write(JsonWriter out, Comment value)
							throws IOException {
						out.beginObject();
						out.name("id");
						out.value(value.getId());
						out.name("text");
						out.value(value.getText());
						out.name("venueId");
						out.value(value.getVenue().getId());
						// out.value(value.getVenueId());
						out.name("userId");
						out.value("loca".equals(value.getSource()) ? value
								.getUserId() : ("4sq_" + value.getUserId()));
						out.name("createdAt");
						out.value(String.valueOf(value.getCreatedAt()));
						out.endObject();
					}

					@Override
					public Comment read(JsonReader in) throws IOException {
						// TODO Auto-generated method stub
						return null;
					}
				});
		return gsonBuilder.create();
	}
}
