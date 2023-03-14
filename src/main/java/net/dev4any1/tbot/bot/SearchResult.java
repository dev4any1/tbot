package net.dev4any1.tbot.bot;

import java.util.Objects;

public class SearchResult {
	private String title;
	private String link;
	private String describtion;

	public SearchResult withTitle(String title) {
		this.title = title;
		return this;
	}

	public SearchResult withLink(String link) {
		this.link = link;
		return this;
	}

	public SearchResult withDescribtion(String describtion) {
		this.describtion = describtion;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(describtion, link, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResult other = (SearchResult) obj;
		return Objects.equals(describtion, other.describtion) && Objects.equals(link, other.link)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "SearchResult [title=" + title + ", link=" + link + ", describtion=" + describtion + "]";
	}

}
