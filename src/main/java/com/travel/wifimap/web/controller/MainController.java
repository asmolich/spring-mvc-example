package com.travel.wifimap.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.travel.wifimap.domain.Feedback;
import com.travel.wifimap.service.FeedbackService;
import com.travel.wifimap.util.StringUtils;

@Controller
public class MainController {

	@Value("${locale.defaultLang}")
	private String defaultLang;

	@Value("${locale.langs}")
	private String langs;

	private List<String> langList;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private LocaleResolver localeResolver;

	@PostConstruct
	public void init() {
		if (!StringUtils.isEmpty(langs)) {
			String[] langsArray = langs.split(",");
			langList = new ArrayList<String>(langsArray.length);
			for (String lang : langsArray) {
				langList.add(lang.trim());
			}
		}
	}

	public String getDefaultLang() {
		return defaultLang;
	}

	public void setDefaultLang(String defaultLang) {
		this.defaultLang = defaultLang;
	}

	public List<String> getLangList() {
		return langList;
	}

	public void setLangList(List<String> langList) {
		this.langList = langList;
	}

	public String getLangs() {
		return langs;
	}

	public void setLangs(String langs) {
		this.langs = langs;
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String welcome(Locale locale) {
		if (locale != null
				&& CollectionUtils.containsInstance(getLangList(),
						locale.getLanguage())) {
			return "redirect:/" + locale.getLanguage() + "/";
		}
		System.out.println("defaultLang=" + getDefaultLang());
		return "redirect:/" + getDefaultLang() + "/";
	}

	@RequestMapping(value = { "/{lang}/" }, method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("lang") String lang) {
		System.out.println("lang = " + lang);
		if (StringUtils.isEmpty(lang)) {
			localeResolver
					.setLocale(request, response, new Locale(defaultLang));
		} else {
			localeResolver.setLocale(request, response, new Locale(lang));
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("langList", langList);
		mav.setViewName("mainPage");
		mav.addObject("feedback", new Feedback());
		return mav;
	}

	// @RequestMapping(value = { "/{lang}/feedback/add" }, method =
	// RequestMethod.POST)
	// public ModelAndView addFeedback(
	// @ModelAttribute("feedback") Feedback feedback, BindingResult result) {
	// ModelAndView mav = new ModelAndView();
	// mav.addObject("langList", langList);
	// mav.setViewName("mainPage");
	// // feedbackValidator.validate(feedback, result);
	// if (result.hasErrors()) {
	// mav.addObject("feedback", feedback);
	// } else {
	// mav.addObject("feedback", new Feedback());
	// System.out.println(feedback);
	// feedbackService.send(feedback);
	// }
	// return mav;
	// }

	@RequestMapping(value = { "/{lang}/" }, method = RequestMethod.POST)
	public ModelAndView addFeedback(
			@ModelAttribute("feedback") Feedback feedback,
			BindingResult result, @PathVariable("lang") String lang) {
		System.out.println("lang = " + lang);
		ModelAndView mav = new ModelAndView();
		mav.addObject("langList", langList);
		mav.setViewName("mainPage");
		// feedbackValidator.validate(feedback, result);
		if (result.hasErrors()) {
			mav.addObject("feedback", feedback);
		} else {
			mav.addObject("feedback", new Feedback());
			feedback.setLang(lang);
			System.out.println(feedback.toString());
			feedbackService.send(feedback);
		}
		return mav;
	}

	// @RequestMapping(value = { "/{lang}/feedback/add" }, method =
	// RequestMethod.GET)
	// public String addFeedbackRedirect(Model model,
	// @PathVariable("lang") String lang) {
	// return "redirect:/" + lang + "/";
	// }

	// FIXME TEST
	// @RequestMapping(method = RequestMethod.GET, value = "/test")
	// public String test() {
	// return "testPage";
	// }

	@RequestMapping(value = { "/{lang}/feedbacks" }, method = RequestMethod.GET)
	public ModelAndView feedbacks() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("langList", langList);
		mav.setViewName("mainPage");
		// feedbackValidator.validate(feedback, result);
		mav.addObject("feedback", new Feedback());
		List<Feedback> list = feedbackService.list();
		for (Feedback fb : list) {
			System.out.println(fb.toString());
		}
		// mav.addObject("feedbacks", list);
		return mav;
	}
}
