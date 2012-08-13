package com.travel.wifimap.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.travel.wifimap.foursquare.search.VenueParams;
import com.travel.wifimap.json.entity.WifiVenue;
import com.travel.wifimap.util.StringUtils;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompleteTip;
import fi.foyt.foursquare.api.entities.Location;
import fi.foyt.foursquare.api.entities.Recommendation;
import fi.foyt.foursquare.api.entities.RecommendationGroup;
import fi.foyt.foursquare.api.entities.Recommended;
import fi.foyt.foursquare.api.entities.TipGroup;

@Controller
public class VenueExploreController {

	@Autowired
	@Qualifier("foursquareApi")
	private FoursquareApi api;

	private Gson gson = buildGson();

	@Value(value = "${app.config.oauth.foursquare.oauthtoken}")
	private String oAuthToken;

	// @RequestMapping(value = "/4sqvenue/{venueId}/comments", method =
	// RequestMethod.GET)
	// public void getVenueComments(@PathVariable String venueId,
	// HttpServletRequest request, HttpServletResponse response) {
	// if (StringUtils.isEmpty(api.getOAuthToken())) {
	// api.setoAuthToken(oAuthToken);
	// }
	//
	// PrintWriter pw = null;
	// try {
	//
	// pw = response.getWriter();
	// // Result<Recommended> searchResult = api.venuesExplore(
	// api.setUseCallback(false);
	// // Result<List<Recommended>> searchResult = doSearch(props, query);
	// // List<Recommended> result = searchResult.getResult();
	// Result<TipGroup> searchResult = api.venuesTips(venueId, "recent",
	// 500, null);
	//
	// if (searchResult.getMeta().getCode() == 200) {
	// TipGroup tipGrpup = searchResult.getResult();
	// if (tipGrpup != null) {
	// printTipGroup(pw, tipGrpup);
	// } else {
	// pw.println("[]");
	// }
	// }
	// } catch (NumberFormatException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace(pw);
	// } catch (FoursquareApiException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace(pw);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// if (pw != null)
	// pw.close();
	// }
	// }

	private void printTipGroup(PrintWriter pw, TipGroup tipGrpup)
			throws IOException {
		JsonWriter writer = new JsonWriter(pw);
		writer.setHtmlSafe(false);
		writer.beginArray();
		for (CompleteTip tip : tipGrpup.getItems()) {
			gson.toJson(tip, CompleteTip.class, writer);
		}
		writer.endArray();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/venues/4sqsearch" }, method = RequestMethod.GET)
	public void search(HttpServletRequest request, HttpServletResponse response) {

		if (StringUtils.isEmpty(api.getOAuthToken())) {
			api.setoAuthToken(oAuthToken);
		}
		System.out.println("\n=============================\nOAUTH_TOKEN = "
				+ api.getOAuthToken());

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String, String> props = new HashMap<String, String>();
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (String paramKey : parameterMap.keySet()) {
			String[] paramVal = parameterMap.get(paramKey);
			if (paramVal != null && paramVal.length == 1) {
				props.put(paramKey, paramVal[0]);
			}
		}

		PrintWriter pw = null;
		if (props.get("debug") != null) {
			props.put(VenueParams.ll.toString(), "53.899994,27.566696");
			props.put(VenueParams.llAcc.toString(), "10000.0");
			props.put(VenueParams.radius.toString(), "20000");
		}
		// wifi, wi-fi, hotspot
		String[] query = { "wifi", "wi-fi" };
		try {

			pw = response.getWriter();
			// Result<Recommended> searchResult = api.venuesExplore(
			api.setUseCallback(false);
			// Result<List<Recommended>> searchResult = doSearch(props, query);
			// List<Recommended> result = searchResult.getResult();
			Result<List<CompleteTip[]>> searchResult = api.tipsMultiSearch(
					props.get(VenueParams.ll.toString()), 500, null, null,
					query);
			List<CompleteTip[]> result = searchResult.getResult();
			if (result != null && result.size() > 0) {

				// printRecommendedList(pw, result);
				printTipsList(pw, result);
			} else {
				pw.println("[]");
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(pw);
		} catch (FoursquareApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(pw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	@SuppressWarnings("unused")
	private Result<List<Recommended>> doSearch(Map<String, String> props,
			String[] query) throws FoursquareApiException {
		Result<List<Recommended>> searchResult = api
				.venuesMultiExplore(
						props.get(VenueParams.ll.toString()),
						props.get(VenueParams.llAcc.toString()) == null ? (Double) null
								: Double.valueOf(props.get(VenueParams.llAcc
										.toString())),
						props.get(VenueParams.alt.toString()) == null ? (Double) null
								: Double.valueOf(props.get(VenueParams.alt
										.toString())),
						props.get(VenueParams.altAcc.toString()) == null ? (Double) null
								: Double.valueOf(props.get(VenueParams.altAcc
										.toString())),
						props.get(VenueParams.radius.toString()) == null ? (Integer) null
								: Integer.valueOf(props.get(VenueParams.radius
										.toString())),
						props.get(VenueParams.section.toString()),
						query,
						props.get(VenueParams.limit.toString()) == null ? (Integer) null
								: Integer.valueOf(props.get(VenueParams.limit
										.toString())), props
								.get(VenueParams.basis.toString()));
		return searchResult;
	}

	@SuppressWarnings("unused")
	private void printRecommendedList(PrintWriter pw, List<Recommended> results)
			throws IOException, FoursquareApiException {
		JsonWriter writer = new JsonWriter(pw);
		writer.setHtmlSafe(false);
		writer.beginArray();
		Map<String, WifiVenue> map = new HashMap<String, WifiVenue>();
		for (Recommended result : results) {
			for (RecommendationGroup group : result.getGroups()) {
				for (Recommendation recommendation : group.getItems()) {
					if (recommendation != null) {
						WifiVenue wifiVenue = new WifiVenue(recommendation);
						String id = wifiVenue.getId();
						WifiVenue presents = map.get(id);
						if (presents != null) {
							presents.merge(wifiVenue);
						} else {
							map.put(id, wifiVenue);
						}
					}
				}
			}
		}

		for (String wifiId : map.keySet()) {
			gson.toJson((WifiVenue) map.get(wifiId), WifiVenue.class, writer);
		}

		writer.endArray();
	}

	private void printTipsList(PrintWriter pw, List<CompleteTip[]> results)
			throws IOException, FoursquareApiException {
		JsonWriter writer = new JsonWriter(pw);
		writer.setHtmlSafe(false);
		writer.beginArray();
		Map<String, WifiVenue> map = new HashMap<String, WifiVenue>();
		for (CompleteTip[] result : results) {
			for (CompleteTip tip : result) {
				if (tip != null && tip.getVenue() != null) {
					WifiVenue wifiVenue = new WifiVenue(tip);
					String id = wifiVenue.getId();
					WifiVenue presents = map.get(id);
					if (presents != null) {
						presents.merge(wifiVenue);
					} else {
						map.put(id, wifiVenue);
					}
				}
			}
		}

		for (String wifiId : map.keySet()) {
			gson.toJson((WifiVenue) map.get(wifiId), WifiVenue.class, writer);
		}

		writer.endArray();
	}

	private Gson buildGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder
				.registerTypeAdapter(Category.class,
						new TypeAdapter<Category>() {

							@Override
							public void write(JsonWriter out, Category value)
									throws IOException {
								// out.beginObject();
								// out.name("id");
								out.value(value.getId());
								// out.name("name");
								// out.value(value.getName());
								// out.endObject();
							}

							@Override
							public Category read(JsonReader in)
									throws IOException {
								return (Category) null;
							}
						})
				.registerTypeAdapter(Location.class,
						new TypeAdapter<Location>() {

							@Override
							public void write(JsonWriter out, Location value)
									throws IOException {
								out.beginObject();
								out.name("addr");
								out.value(value.getAddress());
								out.name("lat");
								out.value(value.getLat());
								out.name("lng");
								out.value(value.getLng());
								out.endObject();
							}

							@Override
							public Location read(JsonReader in)
									throws IOException {
								return (Location) null;
							}
						})
				.registerTypeAdapter(CompleteTip.class,
						new TypeAdapter<CompleteTip>() {

							@Override
							public void write(JsonWriter out, CompleteTip value)
									throws IOException {
								out.beginObject();
								out.name("id");
								out.value(value.getId());
								out.name("date");
								out.value(value.getCreatedAt());
								out.name("body");
								out.value(value.getText());
								out.name("author");
								out.value(StringUtils.formPersonName(value
										.getUser().getFirstName(), value
										.getUser().getLastName()));
								out.endObject();
							}

							@Override
							public CompleteTip read(JsonReader in)
									throws IOException {
								return (CompleteTip) null;
							}
						});
		return gsonBuilder.create();
	}
}
