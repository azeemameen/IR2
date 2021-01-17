import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CrawlerMain {

    private static String STORAGE_BASE_PATH = "crawler_data";
    private static String CRAWLER_DATA = "bbc-future.xml";
    private static int NUMBER_OF_CRAWLERS = 12;
    private static String SEED_URL = "https://www.bbc.com/future";

    public static void main(String[] args) throws IOException {

        // Add crawl storage
        File crawlStorage = new File(STORAGE_BASE_PATH);
        String crawlStoragePath = crawlStorage.getAbsolutePath();
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder(crawlStoragePath);

        // Add page fetcher
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotsTxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        // Add crawler statistics
        Statistics statistics = new Statistics();
        String dataFilePath = crawlStoragePath + File.separator + CRAWLER_DATA;
        File crawlerFile = new File(dataFilePath);
        if (crawlerFile.createNewFile()) {
            System.out.println("File created: " + crawlerFile.getName());
        } else {
            System.out.println("File already exists.");
        }

        FileWriter writer = new FileWriter(dataFilePath);
        CrawlController.WebCrawlerFactory<HtmlCrawler> factory = () -> new HtmlCrawler(SEED_URL, statistics, writer);

        try {
            // Add crawl controller
            CrawlController crawlController = new CrawlController(crawlConfig, pageFetcher, robotsTxtServer);
            crawlController.addSeed(SEED_URL);
            crawlController.start(factory, NUMBER_OF_CRAWLERS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            writer.close();
            System.out.println("Finish writing the file " + CRAWLER_DATA + ".");
        }

    }

}
