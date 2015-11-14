package com.david.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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

import com.david.util.LoginDetails;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UrlEncodedFormEntity entity = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost;
		HttpGet httpGet;
		String url;
		HttpResponse response;
		Document doc;
		Element element;
		HttpClientContext context = (HttpClientContext)req.getSession().getAttribute("context");
		String viewState = (String)req.getSession().getAttribute("_VIEWSTATE");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String identifyCode = req.getParameter("identity");
		System.out.println(viewState);
		System.out.println("logining:\ncoookieValue:"+
		(context.getCookieStore().getCookies()).get(0).getValue());
		LoginDetails details = new LoginDetails();
		details.setIdentifyCode(identifyCode);
		details.setUserName(userName);
		details.setViewState(viewState);
		details.setPassword(password);
		LoginHelper helper = new LoginHelper();
		try {
			url = "http://222.200.98.204/default2.aspx";
			httpPost = new HttpPost(url);
			entity = helper.createEntity(details);
			helper.setHeader(httpPost);
			System.out.println(httpPost.getAllHeaders()[0]);
			System.out.println(entity.getContentType());
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost,context);
			HttpEntity entity2 = response.getEntity();
			doc = Jsoup.parse(EntityUtils.toString(entity2));
			element =doc.select("a").get(0);
			String loginHref = element.attr("href");
			System.out.println(doc.toString());
			EntityUtils.consume(entity2);
			url = "http://222.200.98.204" + loginHref;
			System.out.println(url);
			httpGet = new HttpGet(url);
			helper.setHeader(httpGet);
			response = httpClient.execute(httpGet,context);
			HttpEntity entity3 = response.getEntity();
			
			doc = Jsoup.parse(EntityUtils.toString(entity3));
			System.out.println(doc.toString());
			for(Element el:doc.getElementsByAttributeValue("onclick","GetMc('个人信息');")){
				System.out.println(el);
			}
			element = doc.getElementsByAttributeValue("onclick","GetMc('个人信息');")
					.get(0);
			String MsgHref = element.attr("href");
			EntityUtils.consume(entity3);
			url = "http://222.200.98.204"+"/"+MsgHref;
			httpGet = new HttpGet(url);
			helper.setHeader(httpGet);
			response = httpClient.execute(httpGet,context);
			HttpEntity entity4 = response.getEntity();
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
			doc = Jsoup.parse(EntityUtils.toString(entity4));
			System.out.println(doc.toString());
//			System.out.println(element.attr("href"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
