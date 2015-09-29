package li.barlog;

import jdk.management.resource.ResourceContext;
import jdk.management.resource.ResourceContextFactory;
import jdk.management.resource.ResourceType;
import jdk.management.resource.SimpleMeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Function;

public final class App {
	private static final Logger log = LoggerFactory.getLogger(App.class);
	private static int TIMEOUT = 1000;

	public static void main(String... args) {
		measure(get, "http://ya.ru");
		measure(get, "https://ya.ru");
		measure(get, "http://google.com");
		measure(get, "https://google.com");
	}

	private static final Function<String, Boolean> get =  url -> {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			connection.setRequestMethod("GET");
			//connection.setRequestMethod("HEAD");

			log.info("URL: {}", url);
			log.debug("Code: {}, Message: {}",
				connection.getResponseCode(),
				connection.getResponseMessage());

			int responseCode = connection.getResponseCode();
			return (200 <= responseCode && responseCode <= 399);
		} catch (IOException e) {
			log.error("{}", e.getMessage(), e);
			return false;
		}
	};

	private static void measure(Function<String, Boolean> fn, String url) {
		ResourceContextFactory factory = ResourceContextFactory.getInstance();
		try (ResourceContext rc = factory.create("rc")) {
			SimpleMeter open = SimpleMeter.create(ResourceType.SOCKET_OPEN);
			rc.addResourceMeter(open);

			SimpleMeter in = SimpleMeter.create(ResourceType.SOCKET_READ);
			rc.addResourceMeter(in);

			SimpleMeter out = SimpleMeter.create(ResourceType.SOCKET_WRITE);
			rc.addResourceMeter(out);

			rc.bindThreadContext();

			log.info("Result: {}", fn.apply(url));

			log.info("Open: {}, In: {}, Out: {}",open.getValue(), in.getValue(), out.getValue());
		}
	}
}
