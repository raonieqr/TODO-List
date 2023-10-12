package todo_list.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

public class JsonFormatter {
	private final ObjectMapper objectMapper;
	public JsonFormatter() {
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public String convertToJson(List<?> objects) {
		StringBuilder valueConverted = new StringBuilder();

		objects.forEach(object -> {
			try {
				String json = objectMapper.writeValueAsString(object);
				valueConverted.append(json).append("\n");
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		});
		return valueConverted.toString();
	}

}