package com.david.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


@SuppressWarnings("serial")
public class IdentityServlet extends HttpServlet{
	//先访问主页面
	private void visitor(HttpClientContext context,CloseableHttpClient httpClient
			,HttpServletRequest request)
		throws Exception{
		String url = "http://222.200.98.204/default2.aspx";
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = httpClient.execute(httpPost,context);
		HttpEntity entity = response.getEntity();
		if(null!=entity)
			System.out.println(entity.toString());
		Document doc = Jsoup.parse(EntityUtils.toString(entity));
		System.out.println(doc.toString());
		Element element = doc.select("input").get(0);
		String value = element.attr("value");
		request.getSession().setAttribute("_VIEWSTATE",value);
		EntityUtils.consume(entity);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpClientContext context = new HttpClientContext();
		HttpGet httpGet;
		HttpResponse response;
		HttpEntity entity;
		String url;
		InputStream in = null;
		OutputStream out = null;
		try{
			visitor(context, httpClient,req);
			System.out.println("identifying:\ncookieValue:"+
			(context.getCookieStore().getCookies()).get(0).getValue());
			//将context放在session中
			req.getSession().setAttribute("context", context);
			url = "http://222.200.98.204/CheckCode.aspx";
			httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet,context);
			entity = response.getEntity();
			in = entity.getContent();
			out = resp.getOutputStream();
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
