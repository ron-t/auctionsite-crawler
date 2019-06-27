package Crawler;

import java.io.IOException;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.frontier.DocIDServer;

/**
 * 
 */
@SuppressWarnings("all")
public class Controller {

	private static final int DEFAULT_DEPTH = 0;
	private static final String DEFULT_WORKING_PATH = "temp";

	/*
	 * usage: java CrawlController url output [flushdb?] [depth] [working]
	 * 
	 * url: url of site to crawl
	 * 
	 * output: path to place output files
	 * 
	 * flushdb?: y or n: y to delete all data in the target database before beginning crawl.
	 *                   n to leave as-is.
	 *                   (default is n)
	 * 
	 * depth: maximum depth to crawl (default 0)
	 * 
	 * working: path of working directory (default is .\temp\)
	 * 
	 */
	public static void main(String[] args) throws Exception {
		try {
			if (args.length < 2) {
				System.err.println("Please specify 'url' and 'output path'.");
				System.exit(-1);
			}

			int depth;
			String working;
			boolean flushDb;

			String url = args[0];
			String output = args[1];

			try {
				char input = args[2].trim().toLowerCase().toCharArray()[0];

				switch (input) {
				case 'n':
					flushDb = false;
					break;
				case 'y':
					flushDb = true;
					break;
				default:
					flushDb = false;
				}
			} catch (Exception e) {
				flushDb = false;
			}

			try {
				depth = Integer.parseInt(args[3]);
			} catch (Exception e) {
				depth = DEFAULT_DEPTH;
			}

			try {
				working = args[4];
			} catch (ArrayIndexOutOfBoundsException e) {
				working = DEFULT_WORKING_PATH;
			}

			try {
				LiquidationPageParser.initDB(output + (output.endsWith("\\") ? "" : "\\") + "Liquidation.accdb", flushDb);
				System.out.println("Database initialisation okay. Initialising crawler.");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}

			/*
			 * Instantiate the controller for this crawl. Note that if you want
			 * your crawl to be resumable (meaning that you can resume the crawl
			 * from a previously interrupted/crashed crawl) you can either set
			 * crawler.enable_resume to true in crawler4j.properties file or you
			 * can use the second parameter to the CrawlController constructor.
			 * 
			 * Note: if you enable resuming feature and want to start a fresh
			 * crawl, you need to delete the contents of rootFolder manually.
			 */
			// CrawlController controller = new CrawlController(rootFolder);

			CrawlController controller = new CrawlController(working);

			/*
			 * For each crawl, you need to add some seed urls. These are the
			 * first URLs that are fetched and then the crawler starts following
			 * links which are found in these pages
			 */
			//update crawl - should do all pages again.
			if (PageUtils.isOfType(url, LiquidationPageType.SEARCH_QUERY)) {
				int maxPageNumber = PageUtils.getAuctionListMaxPageNumber(url);
				for (int page = 1; page <= maxPageNumber; page++) {

					String seedURL = PageUtils.addPageSuffix(url, page);

					controller.addSeed(seedURL);
					PageUtils.addValidParentDocID(DocIDServer.getDocID(seedURL));

					// ***comment below to process all pages
					//break;
				}
			} else {
				controller.addSeed(url);
			}
			/*
			 * Be polite: Make sure that we don't send more than 5 requests per
			 * second (200 milliseconds between requests).
			 */
			controller.setPolitenessDelay(200);

			/*
			 * Optional: You can set the maximum crawl depth here. The default
			 * value is -1 for unlimited depth
			 */
			controller.setMaximumCrawlDepth(depth);

			/*
			 * Optional: You can set the maximum number of pages to crawl. The
			 * default value is -1 for unlimited depth
			 */
			controller.setMaximumPagesToFetch(Integer.MAX_VALUE);

			/*
			 * Do you need to set a proxy? If so, you can use:
			 * controller.setProxy("proxyserver.example.com", 8080); OR
			 * controller.setProxy("proxyserver.example.com", 8080, username,
			 * password);
			 */

			/*
			 * Note: you can configure several other parameters by modifying
			 * crawler4j.properties file
			 */

			/*
			 * Start the crawl. This is a blocking operation, meaning that your
			 * code will reach the line after this only when crawling is
			 * finished.
			 */
			System.out.println("Crawler initialisation okay. Starting crawl.");
			long start = System.currentTimeMillis();
			// only one crawler is used since this app crudely uses the same threads to insert into a MS Access db.
			// Access dbs are unable to natively handle concurrent access.
			controller.start(LiquidationCrawler.class, 1);
			
			long end = System.currentTimeMillis();
			System.out.println("" + (end - start) + " taken for " + url);

		} finally {
			if (!LiquidationPageParser.closeDB()) {
				System.err.println("Error closing database.");
				System.exit(-1);
			}
			System.out.println("Crawl completed.");
		}

	}

}
