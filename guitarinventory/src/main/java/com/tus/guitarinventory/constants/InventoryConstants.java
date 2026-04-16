package com.tus.guitarinventory.constants;

public final class InventoryConstants {

	/*
	 * private InventoryConstants() { // restrict instantiation }
	 */
    // Business Logic
    public static final String IN_STOCK = "In Stock";
    public static final String OUT_OF_STOCK = "Out of Stock";
    public static final String DISCONTINUED = "Discontinued";
    
    // Standard Response Codes & Messages
    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "Guitar added to inventory successfully";
    
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Request processed successfully";
    
    public static final String STATUS_417 = "417";
    public static final String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact Dev team";
    public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev team";
    
    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "An error occurred. Please try again or contact Dev team";
}