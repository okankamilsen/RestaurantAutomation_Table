package com.etkiproject.table;

public class tableOrders {

int tableID;
int tableOrderID;
boolean isPaid;
public tableOrders(int tableID, int tableOrderID, boolean isPaid) {
	super();
	this.tableID = tableID;
	this.tableOrderID = tableOrderID;
	this.isPaid = isPaid;
}
public int getTableID() {
	return tableID;
}
public void setTableID(int tableID) {
	this.tableID = tableID;
}
public int getTableOrderID() {
	return tableOrderID;
}
public void setTableOrderID(int tableOrderID) {
	this.tableOrderID = tableOrderID;
}
public boolean isPaid() {
	return isPaid;
}
public void setPaid(boolean isPaid) {
	this.isPaid = isPaid;
}
}
