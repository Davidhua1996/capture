package com.david.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieStore;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;


public class Connection {
	@Test
	public void test(){
		HttpPost httpPost = new HttpPost();
		httpPost.setHeader("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
		httpPost.setHeader("Referer","http://jwgl.gdut.edu.cn/default2.aspx");
		httpPost.setHeader("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8,zh-Hant-TW;q=0.5,zh-Hant;q=0.3");
		httpPost.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; Touch; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; Tablet PC 2.0; InfoPath.3; GWX:RESERVED; LCJB)");
		httpPost.setHeader("Accept-Encoding","gzip, deflate");
		httpPost.setHeader("Cache-Control","no-cache");
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;");
		httpPost.setHeader("Host","jwgl.gdut.edu.cn");
		System.out.println(httpPost.getAllHeaders()[0]);
	}
	public HttpClientContext visitor(){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpClientContext context = new HttpClientContext();
		HttpClientContext context1 = new HttpClientContext();
		HttpPost httpPost;
		HttpResponse response;
		HttpEntity entity;
		String url;
		try{
			url = "http://jwgl.gdut.edu.cn/";
			httpPost = new HttpPost(url);
			response = httpClient.execute(httpPost,context);
			List<Cookie> cookies = context.getCookieStore().getCookies();
			BasicCookieStore cookieStore = new BasicCookieStore();
			if(null!=cookies){
				for(Cookie cookie:cookies){
					BasicClientCookie sendCookie = new BasicClientCookie(cookie.getName(), 
							cookie.getValue());
					sendCookie.setPath("/");
					sendCookie.setDomain("jwgl.gdut.edu.cn");
					cookieStore.addCookie(sendCookie);
//					System.out.println(cookie);
					System.out.println(cookie.getName()+"="+cookie.getValue());
				}
			}
			entity = response.getEntity();
			System.out.println(entity);
			EntityUtils.consume(entity);
			context1.setCookieStore(cookieStore);
		}catch(Exception e){
			e.printStackTrace();
		}
		return context1;
	}
	@Test
	public void getPic(){
		HttpClientContext context = visitor();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet;
		HttpEntity entity;
		HttpResponse response;
		InputStream in;
		FileOutputStream out;
		try{
			String url = "http://222.200.98.204/readimagexs.aspx?xh=3114004903";
			httpGet = new HttpGet(url);
			httpGet.setHeader("Accept","image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
//			httpGet.setHeader("Referer","http://jwgl.gdut.edu.cn/xsgrxx.aspx?xh=3114004903&xm=华德义&gnmkdm=N121501");
			httpGet.setHeader("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8,zh-Hant-TW;q=0.5,zh-Hant;q=0.3");
			httpGet.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; Touch; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; Tablet PC 2.0; InfoPath.3; GWX:RESERVED; LCJB)\r\n");
			httpGet.setHeader("Accept-Encoding","gzip, deflate");
			httpGet.setHeader("Host","jwgl.gdut.edu.cn");
			response = httpClient.execute(httpGet,context);
			entity = response.getEntity();
			Document content = Jsoup.parse(entity.toString());
			System.out.println(content.toString());
//			in = entity.getContent();
//			out = new FileOutputStream(new File("my.jpg"));
//			byte[] b = new byte[1024];
//			int buf = -1;
//			while((buf=in.read(b))!=-1){
//				out.write(b, 0, buf);
//			}
			EntityUtils.consume(entity);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void getIndentifyCard(){
		//获得Cookie
		HttpClientContext context = visitor();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpClientContext context1 = new HttpClientContext();
		HttpGet httpGet;
		HttpEntity entity;
		HttpResponse response;
		InputStream in = null;
		OutputStream out = null;
		String url;
		try{
			url = "http://jwgl.gdut.edu.cn/CheckCode.aspx";
			httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet,context);
			entity = response.getEntity();
			in = entity.getContent();
			out = new FileOutputStream(new File("identity.jpg"));
			byte[] b = new byte[1024];
			int buff = -1;
			while((buff =in.read(b))!=-1){
				out.write(b,0,buff);
			}
			EntityUtils.consume(entity);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(null!=in)
					in.close();
				if(null!=out)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
