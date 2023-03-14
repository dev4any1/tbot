package net.dev4any1.tbot.bot;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Helper {

	public static class Searcher {

		private static final Logger log = LoggerFactory.getLogger(Searcher.class);

		public static Collection<SearchResult> search(String query, int resultCount) {
			try {
				query = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
				Document document = Jsoup.connect("http://www.google.com/search?q=" + query + "&num=" + resultCount)
						.header("Accept-Language", "en-US,en;q=0.5")
						.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").get();
				return parseLinks(document);
			} catch (Exception e) {
				log.error("wtf jsoup: ", e);
				return Collections.emptyList();
			}
		}

		public static Collection<SearchResult> parseLinks(final Document doc) throws Exception {
			Map<String, SearchResult> links = new HashMap<String, SearchResult>();
			Element main = doc.getElementById("main");
			Elements searchResults = main.select("div");
			for (Element result : searchResults) {
				String title = result.select("h3").text();
				String link = normalizeLink(result.select("a").attr("href"));
				String description = result.select("div").text();
				if (!title.isEmpty() && !link.isEmpty() && !description.isEmpty() && !links.containsKey(link)) {
					SearchResult serchResult = new SearchResult().withDescribtion(description).withLink(link)
							.withTitle(title);
					links.put(link, serchResult);
					System.out.println(serchResult);
				}
			}
			return links.values();
		}

		private static String normalizeLink(String link) {
			if (!link.isEmpty()) {
				link = (!link.startsWith("/url?q=http")) ? "" : link.substring(7, link.indexOf("&sa="));
			}
			return link;
		}
	}
}