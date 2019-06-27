package Crawler.URL;

import java.util.*;
import java.io.*;
import java.util.Collection;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

import Crawler.LiquidationPageParser;

public final class NumberOfAuctionsInDbForSeller {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
//		doIt("24kt");
//		doIt("98tatyana");
//		doIt("AAIDavid");
//		doIt("absqrd");
//		doIt("AHVIENT_WHOLESELLERS");
//		doIt("appliancesource2");
//		doIt("ashley6060");
//		doIt("Assetlenders");
		doIt("atacam");
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
		//Thread.sleep(200);

		LiquidationPageParser p = null;
		
		try {
			p = new LiquidationPageParser(new File("Liquidation - sellers 001 - 256 - temp copy.accdb"));
			Database db = p.getDatabase();
			
			Table t = db.getTable("AUCTION_ITEM");
			Column c = t.getColumn("AuctionID");
			
			Collection columnList = new ArrayList<String>(1);
			columnList.add("AuctionID");
			
			ArrayList<Integer> idsList = new ArrayList<Integer>(4000);
			
			Iterator<Map<String, Object>> i = t.iterator(columnList);
			
			while (i.hasNext()) {
				Map<java.lang.String, java.lang.Object> map = (Map<java.lang.String, java.lang.Object>) i
						.next();
				
				Integer id = (Integer) map.get("AuctionID");
				
				idsList.add(id);
			}
			Collections.sort(idsList);
			
			System.out.println(idsList.size());
			
			boolean result = idsList.contains(new Integer(4567084));
			System.out.println(result);
			result = idsList.contains(new Integer(	4703352	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703393	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703353	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703394	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703354	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703355	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703356	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703357	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703387	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703388	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703389	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703390	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703391	));	System.out.println(result);
			result = idsList.contains(new Integer(	4703350	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702291	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702292	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702293	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702294	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702295	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702296	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702289	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702290	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702119	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702120	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702118	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702121	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702122	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702123	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702124	));	System.out.println(result);
			result = idsList.contains(new Integer(	4702125	));	System.out.println(result);


			
//			Object result = Cursor.findValue(t, c, c, 4567084);
//			System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703352	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703393	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703353	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703394	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703354	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703355	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703356	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703357	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703387	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703388	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703389	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703390	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703391	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4703350	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702291	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702292	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702293	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702294	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702295	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702296	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702289	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702290	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702119	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702120	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702118	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702121	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702122	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702123	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702124	);	System.out.println(result);
//			result = Cursor.findValue(t, c, c, 	4702125	);	System.out.println(result);


			
			
			//SELLER_AUCTION_COUNT
			
			
			System.out.println("finished");
			
			
			
		} catch (Exception e) {
			System.err.println("Something went wrong :(");
			e.printStackTrace();
			
		} finally {
			if (p != null) {
				LiquidationPageParser.closeDB();
			}
		}

	}

}
