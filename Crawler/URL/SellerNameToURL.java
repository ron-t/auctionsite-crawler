package Crawler.URL;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public final class SellerNameToURL {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		doIt("24kt");
//		doIt("98tatyana");
//		doIt("AAIDavid");
//		doIt("absqrd");
//		doIt("AHVIENT_WHOLESELLERS");
//		doIt("appliancesource2");
//		doIt("ashley6060");
//		doIt("Assetlenders");
//		doIt("atacam");
//		doIt("AuctioneersNet");
//		doIt("augietess");
//		doIt("austin04");
//		doIt("AustinSales01");
//		doIt("BABAGANOUSH");
//		doIt("BadabingCloseouts");
//		doIt("bdayparties");
//		doIt("bdexcess");
//		doIt("bdexcess2");
//		doIt("BestofBrands");
//		doIt("BestOverstockBuys");
//		doIt("BigCatSports");
//		doIt("BigCityWholesale");
//		doIt("bison");
//		doIt("blowoutsale");
//		doIt("BluetoothDist");
//		doIt("BluetoothGuys");
//		doIt("BM1962");
//		doIt("Bodypiercings");
//		doIt("bojyoung1");
//		doIt("boriler");
//		doIt("boutique101");
//		doIt("BridalsPlus");
//		doIt("bsmbuys");
//		doIt("CAL-Electronics");
//		doIt("carterlane");
//		doIt("chantilly");
//		doIt("ChoiceDecor");
//		doIt("ChoiceElectronics");
//		doIt("cindebride");
//		doIt("CKBargainsNC");
//		doIt("clearsales");
//		doIt("CLOSEOUTITEM");
//		doIt("Closeoutservices");
//		doIt("clothingoverstock");
//		doIt("compdepot");
//		doIt("cybertest");
//		doIt("DBECKER04");
//		doIt("dealfactory77");
//		doIt("DealMakersUSA");
//		doIt("denim219");
//		doIt("DeptStoreOverstock");
//		doIt("Designergalleria");
//		doIt("DesignerTies");
//		doIt("Designwarehouse");
//		doIt("difsnr_unlimited");
//		doIt("digitalcamerassales");
//		doIt("Discount-Electronics");
//		doIt("DIYCentral");
//		doIt("DKLA");
//		doIt("dougscompu");
//		doIt("dsgusa");
//		doIt("ebaybatteries");
//		doIt("edmimports");
//		doIt("einmaligedeals");
//		doIt("ElectronicDirect");
//		doIt("ElectronixExchange");
//		doIt("empiregallery");
//		doIt("EquinoxTrans");
//		doIt("EternityForever");
//		doIt("Etrade2010");
//		doIt("express5");
//		doIt("ExpressMedia");
//		doIt("exxoden");
//		doIt("fabian05");
//		doIt("FamousDeals");
//		doIt("FASHIONAVENUE-NY");
//		doIt("fashionbag");
//		doIt("FashionBliss");
//		doIt("FashionDepot");
//		doIt("faucetexcess");
//		doIt("fejoanna");
//		doIt("FESTIVAL2");
//		doIt("Force21");
//		doIt("foxtrot_l");
//		doIt("foxtrot_r");
//		doIt("FramedArt123");
//		doIt("GadgetLiquidator");
//		doIt("gar3663");
//		doIt("G-DC");
//		doIt("G-DC-MPDE");
//		doIt("GeneralOverstock");
//		doIt("gift4u12");
//		doIt("globalaccessory");
//		doIt("globalfashion");
//		doIt("goodvibe");
//		doIt("greatdealz32");
//		doIt("GrillMan");
//		doIt("GroceryStoreReturns");
//		doIt("GSMPhones101");
//		doIt("HDD-Source");
//		doIt("highendbags");
//		doIt("hitex");
//		doIt("HotFashions");
//		doIt("HULKSEVEN");
//		doIt("ikeregal");
//		doIt("ilderice");
//		doIt("IMPORT-LIQUIDATOR");
//		doIt("INNOVATIVECAPS");
//		doIt("Instylewarehouse");
//		doIt("intimateplace");
//		doIt("inventorysolutions");
//		doIt("IraqiAmericanBooks");
//		doIt("iuchamp95");
//		doIt("IZZYSALES");
//		doIt("jandrews1976");
//		doIt("jcbid_seller");
//		doIt("jcgo");
//		doIt("Jedwa");
//		doIt("jirah");
//		doIt("joemanias1");
//		doIt("joynow");
//		doIt("juliansgems");
//		doIt("kaikwando");
//		doIt("Koshka19");
//		doIt("LA3rdeye");
//		doIt("LabelsForLess");
//		doIt("lakeforest");
//		doIt("legendjade");
//		doIt("Lingerieplace");
//		doIt("liquidationgoods");
//		doIt("liquidationgoods");
//		doIt("lotsoflingerie");
//		doIt("lticam30");
//		doIt("Macguy");
//		doIt("mackinze");
//		doIt("magicwholesale72");
//		doIt("manifold");
//		doIt("MaribelJ");
//		doIt("marina");
//		doIt("mbminmo");
//		doIt("MediaOutletGroup");
//		doIt("Mexican_Pottery");
//		doIt("modagallerysale");
//		doIt("modern_tech");
//		doIt("mrpizza");
//		doIt("Mswemi");
//		doIt("MWAA");
//		doIt("natalie29");
//		doIt("NaturalCos");
//		doIt("neverendingstory14");
//		doIt("nfinn028");
//		doIt("NJAudioAndVideo");
//		doIt("OffPrice26");
//		doIt("OnlineAuctions");
//		doIt("onlinereturns");
//		doIt("OutdoorExpo");
//		doIt("overstocksales");
//		doIt("OverstockShopping");
//		doIt("pabuildpro");
//		doIt("palmjs");
//		doIt("ParadiseWholesale");
//		doIt("Pfdesigns");
//		doIt("PhoneGuys");
//		doIt("Planet-Lingerie");
//		doIt("plumpurple");
//		doIt("positiveattitude");
//		doIt("powercom");
//		doIt("premiergoods");
//		doIt("PremierRetail");
//		doIt("PremiumCosmetics");
//		doIt("ProductCareReturns");
//		doIt("PTFSupply");
//		doIt("Pvision");
//		doIt("Qualijetproducts");
//		doIt("quality1");
//		doIt("QualityWholesale2U");
//		doIt("raison_detrex");
//		doIt("Rangany");
//		doIt("Razfat");
//		doIt("Retailsurplus");
//		doIt("RetailWest1");
//		doIt("ReturnDepot");
//		doIt("Returnservices");
//		doIt("returnsmanager");
//		doIt("rich5");
//		doIt("ry11");
//		doIt("sbcd");
//		doIt("SEANHAWG");
//		doIt("sellerofstuff");
//		doIt("sgtbaker2112");
//		doIt("shirl2");
//		doIt("shoepallets");
//		doIt("shonagroup");
//		doIt("Shop-AHolics");
//		doIt("SkysTheLimit");
//		doIt("SLIMFANCY");
//		doIt("SouthJerseyWholesale");
//		doIt("Stephensonog");
//		doIt("str8swag");
//		doIt("sunvalleytek");
//		doIt("superdeal101");
//		doIt("surplus_bargains");
//		doIt("SurplusTile");
//		doIt("SurplusXchange");
//		doIt("svpfremont");
//		doIt("tahashafi");
//		doIt("TargetSurplus");
//		doIt("TechBuy");
//		doIt("techexcess");
//		doIt("TechnoSource");
//		doIt("TekDeals");
//		doIt("thbproducts");
//		doIt("thebest18");
//		doIt("TheFashionPlus");
//		doIt("TonyTigerSales");
//		doIt("topcosmetics");
//		doIt("TopFashionSurplus");
//		doIt("Top-QualityTools");
//		doIt("TopRetail");
//		doIt("topretail1");
//		doIt("TopRetail10");
//		doIt("TopRetail10");
//		doIt("TopRetail4");
//		doIt("TopRetail6");
//		doIt("TopRetail7");
//		doIt("TopRetail8");
//		doIt("TradeUps-125");
//		doIt("TradeUps-78");
//		doIt("trendyfashions4less");
//		doIt("trojan1");
//		doIt("unedheti");
//		doIt("UNIMARKET");
//		doIt("unorchea");
//		doIt("usawireless");
//		doIt("VNetSolutions");
//		doIt("wantfieldh");
//		doIt("WarehouseRetailer");
//		doIt("WarehouseSupply");
//		doIt("WarehouseVideo");
//		doIt("WestPortFurniture");
//		doIt("WheelDeals2");
//		doIt("WholesaleCE");
//		doIt("WholesaleFlorida");
//		doIt("wholesalegoods");
//		doIt("WholesalePosters");
//		doIt("WholesaleSwimwear");
//		doIt("wholesalez");
//		doIt("wilson3004");
//		doIt("wirelessusa");
//		doIt("wmoney");
//		doIt("worldexport");
//		doIt("wristbandsonline");
//		doIt("yeawehavethat");
//		doIt("YourWholesaleJeweler");
//		doIt("yvahora10");
//		doIt("ZSports");

	}

	private static void doIt(String arg) throws InterruptedException {
		Thread.sleep(200);

		OutputStreamWriter wr = null;
		BufferedReader rd = null;

		Pattern queryRegex = Pattern.compile(".*<a href='(\\?query=.+)'>" + arg.substring(arg.lastIndexOf("-") + 1)
				+ "</a>.*");
		// System.out.println(queryRegex.pattern());

		try {
			// Construct data
			String data = URLEncoder.encode("seller", "UTF-8") + "=" + URLEncoder.encode(arg, "UTF-8");
			data += "&" + URLEncoder.encode("closeFlag", "UTF-8") + "=" + URLEncoder.encode("2", "UTF-8");
			data += "&" + URLEncoder.encode("cmd", "UTF-8") + "=" + URLEncoder.encode("keyword", "UTF-8");

			// System.out.println("posting for data: " + data);

			// Send data
			URL url = new URL("http://www.liquidation.com/auction/search");
			URLConnection conn = url.openConnection();

			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; enâ€?US; rv:1.9.0.4) Gecko/2008102920 Firefox/3.0.4");

			conn.setDoOutput(true);
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// System.out.println("request sent");

			// Get the response
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			Matcher m;

			// System.out.println("processing reply");

			while ((line = rd.readLine()) != null) {
				m = queryRegex.matcher(line);

				if (m.matches()) {
					System.out.println(arg + "\t" + conn.getURL() + m.group(1));
					// System.out.println(line);
					break;
				}
			}
			wr.close();
			rd.close();

		} catch (Exception e) {
			System.err.println("Something went wrong :(");
			e.printStackTrace();
		} finally {

			try {
				if (wr != null) {
					wr.close();
				}
				if (rd != null) {
					rd.close();
				}
			} catch (IOException e) {
			}
		}

	}

}
