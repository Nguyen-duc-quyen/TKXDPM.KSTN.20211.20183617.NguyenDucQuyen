package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
    	// TODO: your work
    	// check the length of the phone number
    	if(phoneNumber.length() != 10) return false;
    	
    	// check if the phone number start with "0"
    	if (!phoneNumber.startsWith("0")) return false;
    	
    	// check the format of the phone number
    	try {
    		Integer.parseInt(phoneNumber);
    	}catch(NumberFormatException e){
    		return false;
    	}
    	
    	return true;
    }
    
    public boolean validateName(String name) {
    	// TODO: your work
    	
    	//check if the name is null
    	if(name == null) return false;
    	
    	// check if the name contains any number or special character
    	String temp = name.replaceAll("[*a-zA-Z ]", "");
    	if(temp.length() != 0) return false;
    	
    	return true;
    }
    
    public boolean validateAddress(String address) {
    	// TODO: your work
    	
    	// check if the address is null
    	if(address == null) return false;
    	
    	// check if the address contains any number or special character
    	String temp = address.replaceAll("[*a-zA-Z0-9 ]", "");
    	if(temp.length() != 0) return false;
    	
    	return true;
    }
    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
