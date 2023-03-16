package net.dev4any1.tbot.bot;

import java.io.Serializable;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

public class Helper {

	public static Optional<String> getJson(Serializable entity) {
		try {
			return Optional.of(new JSONObject(entity).toString(1));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

}