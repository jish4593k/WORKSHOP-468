import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkExtractor {
    private static final String USER_AGENT = "M 5";

    public static Map<String, List<String>> uploadSettings() {
        Map<String, List<String>> linkMap = new HashMap<>();

        

        return linkMap;
    }

    public static List<String> getLinks(String url, String tag, Map<String, String> filter) {
        List<String> links = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("User-Agent", USER_AGENT)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String content = response.body();

            Document doc = Jsoup.parse(content);
            Elements elements = doc.select(tag);

            for (Element element : elements) {
                String title = element.text();
                String link = element.attr("href");

                if (link.startsWith("/")) {
                    URI baseUri = new URI(url);
                    link = baseUri.getScheme() + "://" + baseUri.getHost() + link;
                }

                title = title.trim().split("\\n")[0];
                System.out.println("Link: " + link + ", Text: " + title);
                links.add(link);
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }

        return links;
    }

    public static void main(String[] args) {
        Map<String, List<String>> linkMap = uploadSettings();

        for (Map.Entry<String, List<String>> entry : linkMap.entrySet()) {
            String websiteName = entry.getKey();
            List<String> websiteUrls = entry.getValue();

            for (String url : websiteUrls) {
                List<String> links = getLinks(url, "h2[itemprop=headline]", Map.of());
              
            }
        }
    }
}
