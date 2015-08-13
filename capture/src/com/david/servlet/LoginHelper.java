package com.david.servlet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.david.util.LoginDetails;

public class LoginHelper {
	public UrlEncodedFormEntity createEntity(LoginDetails details) throws Exception{
		UrlEncodedFormEntity entity;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("__VIEWSTATE", 
				details.getViewState()));
		nvps.add(new BasicNameValuePair("txtUserName",
				details.getUserName()));
		nvps.add(new BasicNameValuePair("TextBox2", 
				details.getPassword()));
		nvps.add(new BasicNameValuePair("txtSecretCode",
				details.getIdentifyCode()));
		nvps.add(new BasicNameValuePair("RadioButtonList1", "ѧ��"));
		nvps.add(new BasicNameValuePair("Button1", ""));
		nvps.add(new BasicNameValuePair("lbLanguage", ""));
		nvps.add(new BasicNameValuePair("hidPdrs", ""));
		nvps.add(new BasicNameValuePair("hidsc", ""));
		entity = new UrlEncodedFormEntity(nvps,"gb2312");
		entity.setContentType("application/x-www-form-urlencoded;");
		return entity;
	}
	public void setHeader(HttpPost httpPost){
		httpPost.setHeader("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
		httpPost.setHeader("Referer","http://jwgl.gdut.edu.cn/default2.aspx");
		httpPost.setHeader("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8,zh-Hant-TW;q=0.5,zh-Hant;q=0.3");
		httpPost.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; Touch; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; Tablet PC 2.0; InfoPath.3; GWX:RESERVED; LCJB)");
		httpPost.setHeader("Accept-Encoding","gzip, deflate");
		httpPost.setHeader("Cache-Control","no-cache");
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;");
		httpPost.setHeader("Host","jwgl.gdut.edu.cn");
	}
	public void setHeader(HttpGet httpGet){
		httpGet.setHeader("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
		httpGet.setHeader("Referer","http://jwgl.gdut.edu.cn/default2.aspx");
		httpGet.setHeader("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8,zh-Hant-TW;q=0.5,zh-Hant;q=0.3");
		httpGet.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; Touch; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; Tablet PC 2.0; InfoPath.3; GWX:RESERVED; LCJB)");
		httpGet.setHeader("Accept-Encoding","gzip, deflate");
		httpGet.setHeader("Cache-Control","no-cache");
		httpGet.setHeader("Host","jwgl.gdut.edu.cn");
	}
}
