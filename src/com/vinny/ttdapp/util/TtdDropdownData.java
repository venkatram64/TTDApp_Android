package com.vinny.ttdapp.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class TtdDropdownData {
	//http://localhost:8082/restSpring/villages/allMandals?districtId=4
//http://localhost:8082/restSpring/villages/allVillages?districtId=4&mandalId=5 
	private static final String ttdDataUrl = HostAddressEnum.MY_HOME.getType() + "/villages/";
			//"http://10.0.2.2:8082/restSpring/villages/"; //http://localhost:8082/restSpring/villages/allDistricts
		private static final int HTTP_STATUS_OK = 200;
		private static byte[] buff = new byte[1024];
		private static final String logTag = "TtdDropdownData";
		
		public static class ApiException extends Exception {
			private static final long serialVersionUID = 1L;

			public ApiException (String msg)
			{
				super (msg);
			}

			public ApiException (String msg, Throwable thr)
			{
				super (msg, thr);
			}
		}

		/**
		 * download most popular tracks in given metro.
		 * @param params search strings
		 * @return Array of json strings returned by the API. 
		 * @throws ApiException
		 */
		public static synchronized String downloadFromServer (String type,String... params)
		throws ApiException
		{
			String retval = null;
			//http://localhost:8082/restSpring/villages/searchCategories?category=BAJANAMANDALI
			//&distName=KHAMMAM&mandalName=YERRUPALEM&villageName=BANIGANDLAPADU
			String url = ttdDataUrl; 
			if(type == TtdTypeEnum.DISTRICT.toString()){
				url += "allDistricts";
			}else if(type == TtdTypeEnum.MANDAL.toString()){
				String id1 =  URLEncoder.encode(params[0]);
				url += "allMandals?districtId=" + id1;
			}else if(type == TtdTypeEnum.VILLAGE.toString()){
				String id1 =  URLEncoder.encode(params[0]);
				String id2 =  URLEncoder.encode(params[1]);
				url += "allVillages?districtId=" + id1 +"&mandalId="+id2;
			}else if(type == TtdTypeEnum.SEARCH.toString() || type == TtdTypeEnum.TEMPLE.toString()){
				String cat = URLEncoder.encode(params[0]);
				String dn =  URLEncoder.encode(params[1]);
				String mn =  URLEncoder.encode(params[2]);
				String vn =  URLEncoder.encode(params[3]);
				url += "searchCategories?category="+cat+"&distName="+dn+"&mandalName="+mn+"&villageName="+vn;
			}

			Log.d(logTag,"Fetching " + url);
			
			// create an http client and a request object.
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);

			try {

				// execute the request
				HttpResponse response = client.execute(request);
				StatusLine status = response.getStatusLine();
				if (status.getStatusCode() != HTTP_STATUS_OK) {
					// handle error here
					throw new ApiException("Invalid response from ttd data" + 
							status.toString());
				}

				// process the content. 
				HttpEntity entity = response.getEntity();
				InputStream ist = entity.getContent();
				ByteArrayOutputStream content = new ByteArrayOutputStream();

				int readCount = 0;
				while ((readCount = ist.read(buff)) != -1) {
					content.write(buff, 0, readCount);
				}
				retval = new String (content.toByteArray());

			} catch (Exception e) {
				throw new ApiException("Problem connecting to the server " + 
						e.getMessage(), e);
			}

			return retval;
		}
}
