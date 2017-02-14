package com.etkiproject.table;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private String[] meals,beverages,desserts,orders;
	private String[] quantityMeal={"1","2","3","4","5"};
	menuItem[] item;
	restaurantTable[] rTable,restTable;
	Button eksi,arti;
	String[] tableName=null,menuName = null,mealList=null,beveragesList=null,dessertList=null;
	PopupWindow popupWindow;
	menuItem[] mealsMenu,beveragesMenu,dessertsMenu;
	Spinner role,tableSpinner;
	ListView list,list1,list2,orderList;
	TextView nameOfItem;
	String tableNamee;
	tableOrders to;
	int[] tableOrderItemArray=new int[3];
	int tableID;
	tableOrder[] mItem;
	Bundle select = new Bundle();
    private static final String SOAP_ACTION_callWaiters = "http://tempuri.org/callWaiters";
    private static final String SOAP_ACTION_getMenuItemList = "http://tempuri.org/getMenuItemListName";
    private static final String SOAP_ACTION_getMenuItemData = "http://tempuri.org/getMenuItemListData";
    private static final String SOAP_ACTION_getMenuName = "http://tempuri.org/getMenuName";
    private static final String SOAP_ACTION_getTableName = "http://tempuri.org/getTableName";
    private static final String SOAP_ACTION_getTableOrder = "http://tempuri.org/getTableOrderData";
    private static final String SOAP_ACTION_responseWaiters = "http://tempuri.org/responseWaiters";
    private static final String SOAP_ACTION_takeReceipt = "http://tempuri.org/takeReceipt";
    private static final String SOAP_ACTION_addOrder = "http://tempuri.org/addOrder";
    private static final String SOAP_ACTION_getTableOrderID = "http://tempuri.org/getTableOrderIDControl";
    private static final String SOAP_ACTION_addOrderItem = "http://tempuri.org/addOrderItem";
    private static final String SOAP_ACTION_getTableOrders = "http://tempuri.org/getTableOrders";
    //private static final String SOAP_ACTION_getTableListData = "http://tempuri.org/getTableListData";
    
    
    private static final String OPERATION_NAME_callWaiters = "callWaiters";// your webservice web method name
    private static final String OPERATION_NAME_getMenuItemList = "getMenuItemListName";
    private static final String OPERATION_NAME_getMenuItemData = "getMenuItemListData";
    private static final String OPERATION_NAME_getMenuName = "getMenuName";
    private static final String OPERATION_NAME_getTableName = "getTableName";
    private static final String OPERATION_NAME_getTableOrder = "getTableOrderData";
    private static final String OPERATION_NAME_responseWaiters = "responseWaiters";
    private static final String OPERATION_NAME_takeReceipt = "takeReceipt";
    private static final String OPERATION_NAME_addOrder = "addOrder";
    private static final String OPERATION_NAME_addOrderItem = "addOrderItem";
    private static final String OPERATION_NAME_getTableOrderID = "getTableOrderIDControl";
    private static final String OPERATION_NAME_getTableOrders = "getTableOrders";
    //private static final String OPERATION_NAME_getTableListData = "getTableListData";
    
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    
    private static final String SOAP_ADDRESS = "http://etkiproject-001-site1.anytempurl.com/mywebservice.asmx";
    
    Bitmap[] bImage = new Bitmap[16];
    
    
    TabHost host;
    TabHost.TabSpec spec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //arkadan db verilerini çekiyo
        select=getIntent().getExtras();
		tableNamee=select.getString("tableName");
        String[] separated = tableNamee.split(" ");
        tableID=Integer.parseInt(separated[1]); 
        Log.i("tableID",""+tableID);
        bImage[0] = BitmapFactory.decodeResource(this.getResources(), R.drawable.hamburger);
        bImage[1] = BitmapFactory.decodeResource(this.getResources(), R.drawable.chickenburger);
        bImage[2] = BitmapFactory.decodeResource(this.getResources(), R.drawable.cheeseburger);
        bImage[3] = BitmapFactory.decodeResource(this.getResources(), R.drawable.meetball);
        bImage[4] = BitmapFactory.decodeResource(this.getResources(), R.drawable.schnitzel);
        bImage[5] = BitmapFactory.decodeResource(this.getResources(), R.drawable.beef);
        bImage[6] = BitmapFactory.decodeResource(this.getResources(), R.drawable.currychicken);
        bImage[7] = BitmapFactory.decodeResource(this.getResources(), R.drawable.chickengrill);
        bImage[8] = BitmapFactory.decodeResource(this.getResources(), R.drawable.cola);
        bImage[9] = BitmapFactory.decodeResource(this.getResources(), R.drawable.fanta);
        bImage[10] = BitmapFactory.decodeResource(this.getResources(), R.drawable.sprite);
        bImage[11] = BitmapFactory.decodeResource(this.getResources(), R.drawable.ayran);
        bImage[12] = BitmapFactory.decodeResource(this.getResources(), R.drawable.orangejuice);
        bImage[13] = BitmapFactory.decodeResource(this.getResources(), R.drawable.profiterol);
        bImage[14] = BitmapFactory.decodeResource(this.getResources(), R.drawable.tiramisu);
        bImage[15] = BitmapFactory.decodeResource(this.getResources(), R.drawable.baklava);
        
        	
        orderList=(ListView) findViewById(R.id.orderList);
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
        
    }
//popup ekranı çıkarıyo
    private void callPopup(final int position,String name,final int tableOrderID,final int menuItemID) {

    	  LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
    	    .getSystemService(LAYOUT_INFLATER_SERVICE);
    	  
    	  View popupView = layoutInflater.inflate(R.layout.popup2, null);
    	  
    	  
    	  popupWindow=new PopupWindow(popupView,
    	             LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT,        
    	    true);

    	  popupWindow .setTouchable(true);
    	  popupWindow .setFocusable(true);

    	  popupWindow .showAtLocation(popupView, Gravity.CENTER, 0, 0);
    	  role= (Spinner) popupView.findViewById(R.id.spinner);
    	  List<String> list = new ArrayList<String>();
    	  list.add("Owner");
    	  list.add("Member");
    	  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    	     android.R.layout.simple_spinner_item, quantityMeal);

    	 dataAdapter.setDropDownViewResource
    	                                   (android.R.layout.simple_spinner_dropdown_item);
    	 //yemek adı ekleme
    	 ((TextView) popupView.findViewById(R.id.Indexparameterheader)).setText(name);
    	 
    	 //yemek resmi ekleme
    	 ((ImageView) popupView.findViewById(R.id.imageView1)).setImageBitmap(bImage[menuItemID-1]);
    	 
    	   role.setAdapter(dataAdapter );
    	   //ordera yemek ekleme
    	   ((Button) popupView.findViewById(R.id.saveBtn))
    	    .setOnClickListener(new onClickListener() 
    	   {

    	        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    	         public void onClick(View arg0) 
    	        {
    	        	Log.i("0","0");
    	        	tableOrderItemArray[0]=tableOrderID;
    	        	tableOrderItemArray[1]=menuItemID;
    	        	tableOrderItemArray[2]=Integer.parseInt(role.getSelectedItem().toString());
    	        	Log.i("1","1");
    	        	AsyncCallAddOrder task1 = new AsyncCallAddOrder();
    	            task1.execute();
    	        	Toast.makeText(getApplicationContext(),"tableOrderID: "+tableOrderID+"menuItemID: "+menuItemID+"  quantity: "+
    	              role.getSelectedItem().toString(),
    	              Toast.LENGTH_LONG).show();
    	           popupWindow.dismiss();
    	     

    	     }

    	    });
    	   ((Button) popupView.findViewById(R.id.closeBtn))
   	    .setOnClickListener(new onClickListener() 
   	   {

   	        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
   	         public void onClick(View arg0) 
   	        {
   	           popupWindow.dismiss();

   	     }

   	    });
    	 }
	

	private menuItem[] getMenuItemList(int menuID){
		//menuItem Objelerimiz array halinde
		
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_getMenuItemData);
    	/* aşağıdakiler webservice method parametreleri
    	 * 
    	 */
    	request.addProperty("menuID", menuID);

    	
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE  androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
    	try {
    		
           androidHttpTransport.call(SOAP_ACTION_getMenuItemData, envelope);

        
        SoapObject a = (SoapObject) envelope.bodyIn;
        Object property = a.getProperty(0);
		Log.d("item........ ",property.toString()+"");
		// burdanda içine ulaşabiliyon uzunluk yada variableları görebilirsin
		SoapObject info = (SoapObject) property;
        int RCount=info.getPropertyCount();
        Log.d("property RCount",RCount+"");
        item = new menuItem[RCount+1];
            if (property instanceof SoapObject) {
                Object menuItemID =  info.getProperty(2);
                SoapObject infoID = (SoapObject) menuItemID;
                Object menuItemName =  info.getProperty(0);
                SoapObject infoItemName = (SoapObject) menuItemName;
                Object menuItemImageName =  info.getProperty(1);
                SoapObject infoItemImageName = (SoapObject) menuItemImageName;
                Object menuItemPrice =  info.getProperty(3);
                SoapObject infoItemPrice= (SoapObject) menuItemPrice;
                
        		
        		int j=0;
                int Count=infoID.getPropertyCount();
                Log.d("property Count",Count+"");
                item = new menuItem[Count+1];
                for (j = 0; j < Count; j++) {
                    if (property instanceof SoapObject) {
                        int sID = Integer.parseInt(infoID.getProperty(j).toString());
                        String sItemName = infoItemName.getProperty(j).toString();
                        String sItemImageName = infoItemImageName.getProperty(j).toString();
                        double sItemPrice = Double.parseDouble(infoItemPrice.getProperty(j).toString());
                        item[j] = new menuItem(sID,sItemName,sItemImageName,sItemPrice);
                       Log.d("itemName   ","ıd:"+item[j].getMenuItemID()+" name:"+item[j].getItemName()+" imageName"+item[j].getImageName());
                    }
                } 
            }
         } catch (Exception e) {
           e.printStackTrace();
           //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
           Log.i("", e.getMessage()+" ddd");
         }
		
		return item;     
    }

	private tableOrder[] getTableOrder(int tableID){
		//menuItem Objelerimiz array halinde
		tableOrder[] item=null;
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_getTableOrder);
    	/* aşağıdakiler webservice method parametreleri
    	 * 
    	 */
    	request.addProperty("tableID", tableID);

    	
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE  androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
    	try {
    		
           androidHttpTransport.call(SOAP_ACTION_getTableOrder, envelope);

        
        SoapObject a = (SoapObject) envelope.bodyIn;
        Object property = a.getProperty(0);
		Log.d("item........ ",property.toString()+"");
		// burdanda içine ulaşabiliyon uzunluk yada variableları görebilirsin
		SoapObject info = (SoapObject) property;
        int RCount=info.getPropertyCount();
        Log.d("property RCount",RCount+"");
        item = new tableOrder[RCount+1];
            if (property instanceof SoapObject) {
                Object itemName =  info.getProperty(0);
                SoapObject infoItemName = (SoapObject) itemName;
                Log.d("item........ ",infoItemName.toString()+"");
                Object itemQuantity =  info.getProperty(1);
                SoapObject infoItemQuantity = (SoapObject) itemQuantity;
                Object menuItemTotalPrice =  info.getProperty(2);
                SoapObject infoItemTotalPrice = (SoapObject) menuItemTotalPrice;
                
        		
        		int j=0;
                int Count=infoItemName.getPropertyCount();
                Log.d("property Count",Count+"");
                item = new tableOrder[Count+1];
                for (j = 0; j < Count; j++) {
                    if (property instanceof SoapObject) {
                        int sQuantity = Integer.parseInt(infoItemQuantity.getProperty(j).toString());
                        String sItemName = infoItemName.getProperty(j).toString();
                        double sItemTotalPrice = Double.parseDouble(infoItemTotalPrice.getProperty(j).toString());
                        item[j] = new tableOrder(sItemName,sQuantity,sItemTotalPrice);
                       Log.d("itemName   ","name: "+item[j].getItemName()+" quantity: "+item[j].getQuantity()+" totalPrice: "+item[j].getTotalPrice());
                    }
                }
            }
         } catch (Exception e) {
           e.printStackTrace();
           //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
           Log.i("", e.getMessage()+" ddd");
         }
		
		return item;     
    }
	
    private String[] getMenuName(){
		String[] itemName = null;
		try {
			 
			SoapSerializationEnvelope env = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
 
			env.dotNet = false;
			env.xsd = SoapSerializationEnvelope.XSD;
			env.enc = SoapSerializationEnvelope.ENC;

			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_getMenuName);
			env.setOutputSoapObject(request);
			env.implicitTypes= false;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
			androidHttpTransport.call(SOAP_ACTION_getMenuName, env);
			SoapObject a = (SoapObject) env.bodyIn;
			//burdan listenin kendini yi çekiyon ama içine ulaşamıyon
			Object property = a.getProperty(0);
			// burdanda içine ulaşabiliyon uzunluk yada variableları görebilirsin
			SoapObject info = (SoapObject) property;
			int i=0;
	        int RCount=info.getPropertyCount();
	        itemName = new String[RCount+1];
	        for (i = 0; i < RCount; i++) {
	            if (property instanceof SoapObject) {
	                String sDetail = info.getProperty(i).toString();
	               itemName[i] =sDetail;
	               //Log.i("menuName: ",sDetail);
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemName;
            
    }
    
	
	private void callWaiter(int tableID){
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_callWaiters);
    	/* aşağıdakiler webservice method parametreleri
    	 * 
    	 */
    	request.addProperty("tableID", tableID);

    	
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE  androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
    	try {
    		
           androidHttpTransport.call(SOAP_ACTION_callWaiters, envelope);
           SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
          
           //Toast.makeText(getBaseContext(), ""+response.toString(), Toast.LENGTH_LONG).show();
        Log.i("",response.toString());
         
         } catch (Exception e) {
           e.printStackTrace();
           //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
           Log.i("", e.getMessage()+" ddd");
         }
            
    }
	
	private void responseWaiter(int tableID){
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_responseWaiters);
    	/* webservice method parametreleri
    	 */
    	request.addProperty("tableID", tableID);

    	
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE  androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
    	try {
    		
           androidHttpTransport.call(SOAP_ACTION_responseWaiters, envelope);
           SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
          
           //Toast.makeText(getBaseContext(), ""+response.toString(), Toast.LENGTH_LONG).show();
        Log.i("",response.toString());
         
         } catch (Exception e) {
           e.printStackTrace();
           //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
           Log.i("", e.getMessage()+" ddd");
         }
	}
	
    private void addOrder(int tableID) {

    	SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_addOrder);
        /* webservice method parametreleri
    	 */
        Log.i("tableID: ",""+tableID);
        request.addProperty("tableID", tableID);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
        
        Log.i("1: ",""+tableID);
        try {
        	Log.i("2: ",""+tableID);
            androidHttpTransport.call(SOAP_ACTION_addOrder, envelope);
            Log.i("3: ",""+tableID);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.i("order oluşturma"," başarılı");
            //Toast.makeText(getBaseContext(), ""+response.toString(), Toast.LENGTH_LONG).show();
            Log.i("", response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
            Log.i("", e.getMessage() + " ddd");
        }
    }

    private void addOrderItem(int tableOrderID,int menuItemID,int quantity) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_addOrderItem);
        /* webservice method parametreleri
    	 */
        Log.i("tableID: ",""+tableID);
        request.addProperty("menuItemID", menuItemID);
        request.addProperty("tableOrderID", tableOrderID);
        request.addProperty("quantity", quantity);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
        
        Log.i("1: ",""+tableID);
        try {
        	Log.i("2: ",""+tableID);
            androidHttpTransport.call(SOAP_ACTION_addOrderItem, envelope);
            Log.i("3: ",""+tableID);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.i("order oluşturma"," başarılı");
            //Toast.makeText(getBaseContext(), ""+response.toString(), Toast.LENGTH_LONG).show();
            Log.i("", response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
            Log.i("", e.getMessage() + " ddd");
        }
    }
   
    private int getTableOrderID(int tableID){
    	int inta = 0;
    	String[] itemName=null;
		try {
			 
			SoapSerializationEnvelope env = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
 
			env.dotNet = false;
			env.xsd = SoapSerializationEnvelope.XSD;
			env.enc = SoapSerializationEnvelope.ENC;

			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_getTableOrderID);
			request.addProperty("tableID", tableID);
			Log.i("tableID",""+tableID);
			env.setOutputSoapObject(request);
			//env.implicitTypes= false;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
			androidHttpTransport.call(SOAP_ACTION_getTableOrderID, env);
			SoapObject a = (SoapObject) env.bodyIn;
			//burdan listenin kendini yi çekiyon ama içine ulaşamıyon
			Object property = a.getProperty(0);
			// burdanda içine ulaşabiliyon uzunluk yada variableları görebilirsin
			SoapObject info = (SoapObject) property;
			Log.i("aaaaaaaaaaaaa: ",a.toString());
			int i=0;
	        int RCount=info.getPropertyCount();
	        itemName = new String[RCount+1];
	        for (i = 0; i < RCount; i++) {
	            if (property instanceof SoapObject) {
	                String sDetail = info.getProperty(i).toString();
	               itemName[i] =sDetail;
	               Log.i("aaaaaaaaaaaaa: ",sDetail);
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inta;
    }
    private tableOrders getTableOrders(int tableID){
		//menuItem Objelerimiz array halinde
		
    	tableOrders item = null;
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_getTableOrders);
    	/* aşağıdakiler webservice method parametreleri
    	 * 
    	 */
		Log.d("item........ ","1");
    	request.addProperty("tableID", tableID);
    	Log.d("item........ ","2");
    	
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE  androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Log.d("item........ ","3");
    	try {
    		
           androidHttpTransport.call(SOAP_ACTION_getTableOrders, envelope);
           Log.d("item........ ","4");
        
        SoapObject a = (SoapObject) envelope.bodyIn;
        Log.d("item........ ","5");
        Object property = a.getProperty(0);
		Log.d("item........ ","içinde2");
		// burdanda içine ulaşabiliyon uzunluk yada variableları görebilirsin
		SoapObject info = (SoapObject) property;
        
            if (property instanceof SoapObject) {
            	
                Object helperName =  info.getProperty(0);
                
                SoapObject infoName = (SoapObject) helperName;
                Object helperSurname =  info.getProperty(1);
                SoapObject infoSurname = (SoapObject) helperSurname;
                Object helperPoints =  info.getProperty(2);
                SoapObject infoPoints = (SoapObject) helperPoints;
        		
        		
                    //if (property instanceof SoapObject) {
                        int tableIDs = Integer.parseInt(infoName.getProperty(0).toString());
                        int tableOrderIDs = Integer.parseInt(infoSurname.getProperty(0).toString());
                        boolean isPaids = Boolean.valueOf(infoPoints.getProperty(0).toString());
                        
                        item = new tableOrders(tableOrderIDs,tableIDs,isPaids);
                       Log.d("itemName   ","tableID:"+item.getTableID()+" tableOrderID:"+item.getTableOrderID()+" isPaid"+item.isPaid());                   
                      // Log.d("name",""+hName);
                       //}
          }
         } catch (Exception e) {
           e.printStackTrace();
           //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
           Log.i("", e.getMessage()+" ddd");
         }
		
		return item;     
    }

    
	private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
		ProgressDialog progDailog;
        protected Void doInBackground(Void... params) {
            Log.i("", "doInBackground");
            	//responseWaiter(1);
            
            //servis methodları data çekildi db den 
            addOrder(tableID);
            //int x = getTableOrderID(tableID);
            //get TableOrders to get tableorderid
            to=getTableOrders(tableID);
            menuName=getMenuName();
            mealsMenu = getMenuItemList(1);
            beveragesMenu = getMenuItemList(2);
            dessertsMenu = getMenuItemList(3);
            mItem = getTableOrder(tableID);
            
            runOnUiThread(new Runnable() {// viewde değişiklik için ana thread kullanman lazım bu şekilde kullandık.
			     @SuppressWarnings("deprecation")
				@Override
			     public void run() {
			    	 //tabların iç layoutları
			    	 nameOfItem = (TextView) findViewById(R.id.Indexparameterheader); 
			    	 int[] Rlayout = {R.id.meals,R.id.beverages,R.id.desserts};
			    	 meals = new String[mealsMenu.length];
			    	 int k=0;
			            while(k<mealsMenu.length-1){
			            	meals[k]=mealsMenu[k].getItemName()+" price: "+mealsMenu[k].getPrice();
			            	k++;
			            }
			            beverages = new String[beveragesMenu.length];
				    	 k=0;
				            while(k<beveragesMenu.length-1){
				            	beverages[k]=beveragesMenu[k].getItemName()+" price: "+beveragesMenu[k].getPrice();
				            	k++;
				            }
				            desserts = new String[dessertsMenu.length];
					    	k=0;
					            while(k<dessertsMenu.length-1){
					            	desserts[k]=dessertsMenu[k].getItemName()+" price: "+dessertsMenu[k].getPrice();
					            	k++;
					            }
					            orders = new String[mItem.length];
						    	k=0;
						            while(k<mItem.length-1){
						            	orders[k]=mItem[k].getItemName()+" #:"+mItem[k].getQuantity()+" price: "+mItem[k].getTotalPrice();
						            	k++;
						            }
			            Log.i("aaaaaa", "mustafa");
			            //önceki sayfadan hangi table olduğunun bilgisini aldık.
				            
			            TextView tableText = (TextView) findViewById(R.id.tableText);
			            tableText.setText(tableNamee);
			            //to get tableID tableNumber=tableID
			            
			        	Toast.makeText(getBaseContext(), menuName[0], Toast.LENGTH_SHORT).show();

			        	// tab lar böle oluşturuldu
			        	final TabHost host = (TabHost)findViewById(R.id.tabHost);
			            host.setup();			            
			            int i = 0;
			            while(i<menuName.length-1){
			            	//Log.d("whileda",""+i);
			            	spec = host.newTabSpec(menuName[i]);
			            	//Log.d("menuName.length",menuName[i]);
			            	spec.setContent(Rlayout[i]);
			            	spec.setIndicator(menuName[i]);
			            	host.addTab(spec);
			            	i++;
			            }
			            spec = host.newTabSpec("Order");
		            	//Log.d("menuName.length",menuName[i]);
		            	spec.setContent(R.id.orders);
		            	spec.setIndicator("Order");
		            	host.addTab(spec);
		            	
		            	 
		            	
		            	
		            	host.setOnTabChangedListener(new OnTabChangeListener() {

		            		@Override
		            		public void onTabChanged(String tabId) {

		            		int i = host.getCurrentTab();
		            		 Log.i("@@@@@@@@ ANN CLICK TAB NUMBER", "------" + i);

		            		    if (i == 3) {
		            		            Log.i("@@@@@@@@@@ Inside onClick tab 0", "onClick tab");
		            		            ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.single_row, R.id.textView1, orders);
		        			            orderList.setAdapter(adapter);

		            		    }

		            		  }
		            		});
			            //meals listesi
			            list=(ListView) findViewById(R.id.mealList);
			            ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.meals_row, R.id.textView1, meals);
			            list.setAdapter(adapter);
			            list.setOnItemClickListener(new onItemClickListener() {
			           	 
			                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			                	
			                	int id= mealsMenu[position].getMenuItemID();
			                	Toast.makeText(getApplicationContext(), "id: "+id, Toast.LENGTH_SHORT).show();
			                	//String cc=;
			                	
			                	callPopup(position,list.getItemAtPosition(position).toString(),to.getTableOrderID(),id);
			                	//Toast.makeText(getBaseContext(), ""+cc, Toast.LENGTH_SHORT).show();
			                }
			             });
			            
			            //beverages listesi
			            list1=(ListView) findViewById(R.id.beverageList);
			            ArrayAdapter<String> adapter1=new ArrayAdapter<String>(MainActivity.this,R.layout.beverages_row, R.id.textView1, beverages);
			            list1.setAdapter(adapter1);			           
			            list1.setOnItemClickListener(new onItemClickListener() {
			           	 
			                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			                	int id= beveragesMenu[position].getMenuItemID();
			                	Toast.makeText(getApplicationContext(), "id: "+id, Toast.LENGTH_SHORT).show();
			                	callPopup(position,list1.getItemAtPosition(position).toString(),to.getTableOrderID(),id);
			                	Toast.makeText(getBaseContext(), "ikinci", Toast.LENGTH_SHORT).show();
			                }
			             });
			            
			            //desserts listesi
			            list2=(ListView) findViewById(R.id.dessertList);
			            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(MainActivity.this,R.layout.desserts_row, R.id.textView1, desserts);
			            list2.setAdapter(adapter2);			           			            
			            list2.setOnItemClickListener(new onItemClickListener() {
			           	 
			                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			                	int id= dessertsMenu[position].getMenuItemID();
			                	Toast.makeText(getApplicationContext(), "id: "+id, Toast.LENGTH_SHORT).show();
			                	callPopup(position,list2.getItemAtPosition(position).toString(),to.getTableOrderID(),id);
			                	Toast.makeText(getBaseContext(), "üçüncü", Toast.LENGTH_SHORT).show();
			                }
			             });
			            
			            //garson çağırmak için button
			            Button callWaiterBt = (Button) findViewById(R.id.toggleButton1);
			            callWaiterBt.setOnClickListener(new onClickListener() {
			                public void onClick(View v) {
			                    	//garson çağır
			                	//AsyncCallWS task = new AsyncCallWS();
			                    //task.execute();
			                	callWaiter(tableID);
			                	//getMenuItemList(1);
			                	Toast.makeText(getBaseContext(), "butona basıldı", Toast.LENGTH_SHORT).show();
			                	//callWaiterBt.setText("Garson Geliyor");
			                	
			                }
			            });
			            
			     }
			});
            
            return null;
        }
        protected void onPreExecute() {
            Log.i("", "onPreExecute");
            progDailog = new ProgressDialog(MainActivity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        protected void onPostExecute(Void result) {
            Log.i("", "onPostExecute");
            progDailog.dismiss();
        }

        

        protected void onProgressUpdate(Void... values) {
            Log.i("", "onProgressUpdate");
        }


    }
	private class AsyncCallAddOrder extends AsyncTask<Void, Void, Void> {
		ProgressDialog progDailog;
        protected Void doInBackground(Void... params) {
            Log.i("", "doInBackground");
            	//responseWaiter(1);
            
            Log.i("2","2");
            addOrderItem(tableOrderItemArray[0],tableOrderItemArray[1],tableOrderItemArray[2]);
            mItem = null;
            mItem = getTableOrder(tableID);
            
            
            runOnUiThread(new Runnable() {// viewde değişiklik için ana thread kullanman lazım bu şekilde kullandık.
			     @SuppressWarnings("deprecation")
				@Override
			     public void run() {
			    	 orders=null;
			    	 orders = new String[mItem.length];
				    	int k=0;
				            while(k<mItem.length-1){
				            	orders[k]=mItem[k].getItemName()+" #:"+mItem[k].getQuantity()+" price: "+mItem[k].getTotalPrice();
				            	k++;
				            }
			    	 ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.single_row, R.id.textView1, orders);
			            orderList.setAdapter(adapter);
			     }
            });
			return null;
            }
        
        protected void onPreExecute() {
            Log.i("", "onPreExecute");
            progDailog = new ProgressDialog(MainActivity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        protected void onPostExecute(Void result) {
            Log.i("", "onPostExecute");
            progDailog.dismiss();
        }

        

        protected void onProgressUpdate(Void... values) {
            Log.i("", "onProgressUpdate");
        }


    }
}