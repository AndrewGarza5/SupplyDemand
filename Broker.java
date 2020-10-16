package SupplyDemand;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the "middle man" in the publisher-subscriber model. It takes the input from publishers and subscribers and processes it appropriately. It has all static attributes and operations so as to keep track of all the data without the need of creating and passing of a Broker object 
 */
public class Broker {

	/**
	 * an array list that contains all products, and each one of those product objects contains an arrayList of all the companies that subscribe to that product
	 */
	private static ArrayList<Product> prodAndSubList = new ArrayList<>();
	
	/**
	 * keeps track of the publichsed output messages
	 */
	private static List<String> output = new ArrayList<>();

	/**
	 * This method is called from a producer object. If the command is a publish, then it takes the information, processes it, and returns a String containing the published text
	 */
	public static void publishCommand(String producer, String prodCat, String brandName) {
		//begin
		
		int i = 0; 
		ArrayList<Retailer> subscriberList = new ArrayList<>();
		boolean foundProduct = false;
		//gets the subscriber list that are all subscribed to the product category
		while(i < prodAndSubList.size() && foundProduct == false) {
			Product tempProduct = prodAndSubList.get(i);
			if(prodCat.compareTo(tempProduct.getProdCat()) == 0) {
				foundProduct = true;
				subscriberList = tempProduct.getSubscriberList();
			}
			i++;
		}
		//adds the publish message to our output list
		if(foundProduct == true) {
			for(int j = 0; j < subscriberList.size(); j++) {
				Retailer tempRetailer = subscriberList.get(j);
				String publish = tempRetailer.getSubscriberName() + " notified of " + 
				brandName + " brand " + prodCat + " from " + producer;
				output.add(output.size(), publish);
				
			}
		}
		
		//end
	}

	/**
	 * This method is called from a Retailer object. If the input is a subscribe command, then the method will add the retailer as a subscriber to the product
	 */
	public static void subscribeCommand(String retailer, String prodCat) {
		//begin
		
		//checks if product already exists in our list and adds it if not
		int i = 0;
		boolean foundMatch = false;
		while(i < prodAndSubList.size() && foundMatch == false) {
			Product tempProduct = prodAndSubList.get(i);
			if(prodCat.compareTo(tempProduct.getProdCat()) == 0) {
				foundMatch = true;
				Retailer newRetailer = new Retailer(retailer);
				tempProduct.addSubscriber(newRetailer);
				prodAndSubList.set(i, tempProduct);
				
			}
			i++;
		}
		//if the product is not in our list then we add it along with the retailer subscription
		if(foundMatch == false) {
			Retailer newRetailer = new Retailer(retailer);
			Product newProduct = new Product(prodCat);
			newProduct.addSubscriber(newRetailer);
			prodAndSubList.add(newProduct);
		}
		//end
	}

	/**
	 * This method is called from a Retailer object. If the input is a unsubscribe command, the method will remove the subscription from the retailer to the product category
	 */
	public static void unsubscribeCommand(String retailer, String prodCat) {
		//begin
		int i = 0;
		boolean foundMatch = false;
		
		//finds the product type and removes the retailer from the subscription list
		while(i < prodAndSubList.size() && foundMatch == false) {
			Product tempProduct = prodAndSubList.get(i);
			if(prodCat.compareTo(tempProduct.getProdCat()) == 0) {
				foundMatch = true;
				tempProduct.removeSubscriber(retailer);
				prodAndSubList.set(i, tempProduct);
				
			}
			i++;
		}
		//end
	}
	
	/**
	 * Returns the output list which contains all of the publishing details
	 */
	public static List<String> getOutput() {
		return output;
		//return null;
	}
	/**
	 * method that is only called from the reset() method in SupplyDemand class. It resets all of the lists in here to start over
	 */
	public static void resetLists() {
		//begin
		output.clear();
		prodAndSubList.clear();
		//end
	}

}
