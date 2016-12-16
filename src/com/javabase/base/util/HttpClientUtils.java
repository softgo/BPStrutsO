package com.javabase.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;

/**
 * @author admin
 */
public class HttpClientUtils {
	
	private static final String URL = "";
	private static boolean proxySet = false;
	private static String proxyHost = "127.0.0.1";
	private static int proxyPort = 101010 ;
	
	/**
	 * 解释优酷网站获取视频连接
	 * 某一用户的的专辑或用户所发表的视频
	 * 只支持以下两个地址
	 * http://i.youku.com/u/UMzU2MTg5MTIw/videos****
	 * http://www.youku.com/playlist_show/id_5576128***
	 * @param Set<String> 视频地址的集合
	 */
	public Set<String> toHtmlSet(String urlStr) {
		String html = Common.getInputHtmlUTF8(urlStr);
		html = parseTag0(html, Div.class, "class", "items").getStringText();
		Set<String> sets = new HashSet<String>();// 去除重复的
		if (html != null && !"".equals(html)) {
			// 取得视频连接内容
			List<LinkTag> linkTags = parseTags(html, LinkTag.class);
			for (LinkTag linkTag : linkTags) {
				if (!Common.isEmpty(linkTag.getLink()) && linkTag.getLink().indexOf("v_show/id_") > -1) {
					sets.add(linkTag.getLink());
				}
			}
		}
		return sets;
	}

	/**
	 * 视频标题
	 */
	String videoName = null;
	List<Map<String, String>> lists = null;

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	/**
	 * 返回解释后的连接地址 支持:土豆,优酷,我乐,新浪,百度,搜狐,Youtube,酷六,CCTV等8个主流视频网站的快速解析
	 * 
	 * @param urlStr
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getLinkTagsContent(String urlStr) throws Exception {

		urlStr = "http://www.flvcd.com/parse.php?kw=" + urlStr + "&flag=one&format=high";
		String html = Common.getInputHtmlGBK(urlStr);
		if (html != null) {
			if (html.indexOf("：</strong>") > -1) {
				// 获取script中的内容
				int beginLocal = html.indexOf("：</strong>") + 14;
				int endLocal = html.indexOf("<strong>（") - 4;
				// 取得视频标题
				videoName = html.substring(beginLocal, endLocal);

				// 取得视频连接内容
				String content = parseTag3(html, TableColumn.class, "class", "mn STYLE4").getStringText();

				List<LinkTag> linkTags = parseTags(content, LinkTag.class);
				List<String> list = new ArrayList<String>();
				for (LinkTag linkTag : linkTags) {
					list.add(linkTag.getLink());
				}

				return list;
			}
		}

		return null;
	}

	/**
	 * 返回标签的内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getHtmlTagsContent(String urlStr) throws Exception {

		Parser parser = new Parser();

		String html = Common.getInputHtmlGBK(urlStr);
		parser.setInputHTML(html);
		String filerStr = "li";
		NodeFilter filter = new TagNameFilter(filerStr);
		// 取得页面内容中标签为"dl"
		NodeList nodeList = parser.extractAllNodesThatMatch(filter);

		Tag tag = (Tag) nodeList.elementAt(0);

		return tag.toHtml();
	}

	/**
	 * 解释乐视网 MV音乐
	 * 
	 * @param urlStr
	 * @return List<String>
	 * @throws Exception
	 */
	public List<Map<String, String>> getYueShiMusic() {

		String html = Common.getInputHtmlUTF8("http://music.letv.com/");

		html = parseTag0(html, Div.class, "class", "musrigbox").getStringText();
		if (html != null && !"".equals(html)) {
			// 取得视频连接内容
			List<LinkTag> linkTags = parseTags(html, LinkTag.class);
			List<ImageTag> imageTags = parseTags(html, ImageTag.class);
			Map<String, String> map = new HashMap<String, String>();
			for (LinkTag linkTag : linkTags) {
				map.put(linkTag.getLinkText().trim(), linkTag.getLink().trim());

			}
			lists = new ArrayList<Map<String, String>>();
			Map<String, String> mapVideo = null;
			int sun = 0;
			for (ImageTag imageTag : imageTags) {
				if (sun < 10) {
					if (map.get(imageTag.getAttribute("alt")) != null && !"".equals(imageTag.getAttribute("alt"))) {
						mapVideo = new HashMap<String, String>();
						mapVideo.put("videoTypeId", "1");
						mapVideo.put("videoName", "" + imageTag.getAttribute("alt"));
						mapVideo.put("videoSmallImagesUrl", "" + imageTag.getAttribute("data-src"));
						mapVideo.put("videoUrl", "" + map.get(imageTag.getAttribute("alt")));
						mapVideo.put("videoLable", "" + imageTag.getAttribute("alt").substring(0, 2));
						mapVideo.put("videoProfile", "" + imageTag.getAttribute("alt"));

						lists.add(mapVideo);
					}
					sun += 1;
				}

			}

		}

		return lists;
	}

	/**
	 * 解释优酷搞笑视频
	 * 
	 * @param urlStr
	 * @return List<String>
	 * @throws Exception
	 */
	public List<Map<String, String>> getYouKuFunny() {

		String html = Common.getInputHtmlUTF8("http://fun.youku.com/index/focus");

		html = parseTag0(html, Div.class, "class", "body").getStringText();

		if (html != null && !"".equals(html)) {
			// 取得视频连接内容
			List<LinkTag> linkTags = parseTags(html, LinkTag.class);
			List<ImageTag> imageTags = parseTags(html, ImageTag.class);
			Map<String, String> map = new HashMap<String, String>();
			for (LinkTag linkTag : linkTags) {
				map.put(linkTag.getLinkText().trim(), linkTag.getLink().trim());

			}
			lists = new ArrayList<Map<String, String>>();
			Map<String, String> mapVideo = null;
			int sun = 0;
			for (ImageTag imageTag : imageTags) {
				if (sun < 10) {
					if (map.get(imageTag.getAttribute("alt").trim()) != null && !"".equals(imageTag.getAttribute("alt").trim())) {
						mapVideo = new HashMap<String, String>();
						mapVideo.put("videoTypeId", "2");
						mapVideo.put("videoName", "" + imageTag.getAttribute("alt").trim());
						mapVideo.put("videoSmallImagesUrl", "" + imageTag.getImageURL().trim());
						mapVideo.put("videoUrl", "" + map.get(imageTag.getAttribute("alt").trim()));
						mapVideo.put("videoLable", "" + imageTag.getAttribute("alt").trim().substring(0, 2));
						mapVideo.put("videoProfile", "" + imageTag.getAttribute("alt").trim());

						lists.add(mapVideo);
					}
					sun += 1;
				}

			}

		}

		return lists;
	}

	/**
	 * 解释新浪新闻
	 * 
	 * @param urlStr
	 * @return List<String> @
	 */
	public List<Map<String, String>> getXinLangNews() {

		String html = Common.getInputHtmlGBK("http://v.sina.com.cn/v/paike.html");

		html = parseTag0(html, Div.class, "class", "p_left").getStringText();
		if (html != null && !"".equals(html)) {
			// 取得视频连接内容
			// 获取script中的内容
			List<LinkTag> linkTags = parseTags(html, LinkTag.class);
			List<ImageTag> imageTags = parseTags(html, ImageTag.class);
			Map<String, String> map = new HashMap<String, String>();

			for (ImageTag imageTag : imageTags) {
				if (imageTag.getAttribute("alt").trim() != null && !"".equals(imageTag.getAttribute("alt"))) {
					map.put(imageTag.getAttribute("alt").trim(), imageTag.getAttribute("src"));
				}

			}
			lists = new ArrayList<Map<String, String>>();
			Map<String, String> mapVideo = null;
			for (LinkTag linkTag : linkTags) {
				if (map.get(linkTag.getLinkText()) != null && !"".equals(map.get(linkTag.getLinkText()))) {
					mapVideo = new HashMap<String, String>();
					mapVideo.put("videoTypeId", "3");
					mapVideo.put("videoName", "" + linkTag.getLinkText());
					mapVideo.put("videoSmallImagesUrl", "" + map.get(linkTag.getLinkText()));
					mapVideo.put("videoUrl", "" + linkTag.getLink());
					mapVideo.put("videoLable", "" + linkTag.getLinkText().substring(0, 2));
					mapVideo.put("videoProfile", "" + linkTag.getLinkText());

					lists.add(mapVideo);
				}

			}

		}

		return lists;
	}

	/**
	 * 解释凤凰 娱乐八挂
	 * 
	 * @param urlStr
	 * @return List<String> @
	 */
	public List<Map<String, String>> getFengHuangBaGua() {

		String html = Common.getInputHtmlUTF8("http://v.ifeng.com/ent/");

		if (html != null && !"".equals(html)) {
			// 取得视频连接内容
			// 获取script中的内容
			int beginLocal = html.indexOf("<div class=\"left\">");
			int endLocal = html.indexOf("<div class=\"ad660\">");
			String content = html.substring(beginLocal, endLocal);
			List<LinkTag> linkTags = parseTags(content, LinkTag.class);
			List<ImageTag> imageTags = parseTags(content, ImageTag.class);
			Map<String, String> map = new HashMap<String, String>();

			for (LinkTag linkTag : linkTags) {
				if (linkTag.getLink().trim().indexOf("ent") > -1) {
					map.put(linkTag.getLinkText().trim(), linkTag.getLink().trim());
				}

			}
			lists = new ArrayList<Map<String, String>>();
			Map<String, String> mapVideo = null;
			int sun = 0;
			for (ImageTag imageTag : imageTags) {
				if (sun < 10) {
					if (map.get(imageTag.getAttribute("alt").trim()) != null && !"".equals(imageTag.getAttribute("alt").trim())) {
						mapVideo = new HashMap<String, String>();
						mapVideo.put("videoTypeId", "4");
						mapVideo.put("videoName", "" + imageTag.getAttribute("alt").trim());
						mapVideo.put("videoSmallImagesUrl", "" + imageTag.getImageURL().trim());
						mapVideo.put("videoUrl", "" + map.get(imageTag.getAttribute("alt").trim()));
						mapVideo.put("videoLable", "" + imageTag.getAttribute("alt").trim().substring(0, 2));
						mapVideo.put("videoProfile", "" + imageTag.getAttribute("alt").trim());

						lists.add(mapVideo);
						sun += 1;
					}

				}
			}
		}

		return lists;
	}

	/**
	 * 提取具有某个属性值的标签列表
	 * 
	 * @param <T>
	 * @param html
	 *            被提取的HTML文本
	 * @param tagType
	 *            标签类型
	 * @param attributeName
	 *            某个属性的名称
	 * @param attributeValue
	 *            属性应取的值
	 * @return 标签列表
	 */
	public static <T extends TagNode> List<T> parseTags(String html, final Class<T> tagType, final String attrbuteName, final String attrbutValue) {
		try {
			Parser parser = new Parser();
			parser.setInputHTML(html);
			NodeList list = parser.parse(new NodeFilter() {

				public boolean accept(Node node) {
					if (node.getClass() == tagType) {
						T tagNode = (T) node;
						if (attrbuteName == null) {
							return true;
						}
						String attrValue = tagNode.getAttribute(attrbuteName);
						if (attrValue != null && attrValue.equals(attrbutValue)) {
							return true;
						}
					}

					return false;
				}
			});
			List<T> tagsList = new ArrayList<T>();
			for (int i = 0; i < list.size(); i++) {
				T t = (T) list.elementAt(i);
				tagsList.add(t);
			}
			return tagsList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param <T>
	 *            需要查找的标签
	 * @param html
	 *            被提取的HTML文本
	 * @param tagType
	 *            标签类型
	 * @return 标签列表
	 */
	public static <T extends TagNode> List<T> parseTags(String html, final Class<T> tagType) {
		return parseTags(html, tagType, null, null);
	}

	/**
	 * 获取某一指定标签属性的内容
	 * 
	 * @param <T>
	 ** @param html
	 *            被提取的HTML文本
	 * @param tagType
	 *            标签类型
	 * @param attributeName
	 *            某个属性的名称
	 * @param attributeValue
	 *            属性应取的值
	 * @return 第一个标签
	 */
	public static <T extends TagNode> T parseTag0(String html, final Class<T> tagType, final String attrbuteName, final String attrbutValue) {
		List<T> tags = parseTags(html, tagType, attrbuteName, attrbutValue);
		if (tags != null && tags.size() > 0) {
			return tags.get(0);
		}
		return null;
	}

	/**
	 * 获取某一指定标签属性的内容
	 * 
	 * @param <T>
	 ** @param html
	 *            被提取的HTML文本
	 * @param tagType
	 *            标签类型
	 * @param attributeName
	 *            某个属性的名称
	 * @param attributeValue
	 *            属性应取的值
	 * @return 第3个标签
	 */
	public static <T extends TagNode> T parseTag3(String html, final Class<T> tagType, final String attrbuteName, final String attrbutValue) {
		List<T> tags = parseTags(html, tagType, attrbuteName, attrbutValue);
		if (tags != null && tags.size() > 2) {
			return tags.get(2);
		}
		return null;
	}

	/**
	 * 获取某一指定标签属性的内容
	 * 
	 * @param <T>
	 *            需要查找的标签
	 * @param html
	 *            被提取的HTML文本
	 * @param tagType
	 *            标签类型
	 * @return 第一个标签
	 */
	public static <T extends TagNode> T parseTag0(String html, final Class<T> tagType) {
		return parseTag0(html, tagType, null, null);
	}

	/**
	 * 获取某一指定标签属性的内容
	 * 
	 * @param <T>
	 *            需要查找的标签
	 * @param html
	 *            被提取的HTML文本
	 * @param tagType
	 *            标签类型
	 * @return 第3个标签
	 */
	public static <T extends TagNode> T parseTag3(String html, final Class<T> tagType) {
		return parseTag3(html, tagType, null, null);
	}
	

	/**
	 * http请求获取资源
	 * 
	 * @param url
	 * @return
	 */
	public static String httpRequest(String url) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL conUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			//连接.
			connection.connect();
			InputStream inStream = connection.getInputStream();
			InputStreamReader inReader = new InputStreamReader(inStream, "UTF-8");
			BufferedReader reader = new BufferedReader(inReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line+"\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		System.out.println(buffer.toString());
		return buffer.toString();
	}
	
	/**
	 * 向服务器发送请求获取信息
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendGetRequest(String url,String ... params) {
		StringBuffer buffer = new StringBuffer("");  
        BufferedReader in = null;  
        try {  
            URL realUrl = new URL(url);  
            //打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            //设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
            
            //建立实际的连接  
            conn.connect();  
            //获取所有响应头字段  
            Map < String, List< String >> map = conn.getHeaderFields();  
            //遍历所有的响应头字段  
            for (String key: map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }
            
            //定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;              
            while ((line = in .readLine()) != null) {  
            	buffer.append(line+"\n");
            }  
        } catch (Exception e) {  
            System.out.println("发送GET请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        
        //使用finally块来关闭输入流  
        finally {  
            try {  
                if ( in != null) { in .close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return buffer.toString();  
	}

	/**
	 * 向服务器发送请求获取信息
	 * 
	 * @param url
	 * @param isProxy
	 * @param params
	 * @return
	 */
	public static String sendPostRequest(String url,boolean isProxy,String ...params) {
		OutputStreamWriter out = null;  
        BufferedReader in = null;  
        StringBuffer buffer = new StringBuffer("");  
        try {  
            URL realUrl = new URL(url);  
            //打开和URL之间的连接  
            URLConnection conn = null;
            if (isProxy) {
				@SuppressWarnings("static-access")
				Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
				conn = (HttpURLConnection) realUrl.openConnection(proxy);
			}else {
				conn = (HttpURLConnection) realUrl.openConnection();
			}
            
            //设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            //发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);
            conn.connect();
            
            //获取URLConnection对象对应的输出流  
            out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");  
            
            //发送请求参数  
            out.write(params.toString());
            //flush输出流的缓冲  
            out.flush();  
            //定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in .readLine()) != null) {  
            	buffer.append(line+"\n");
            }  
        } catch (Exception e) {  
            System.out.println("发送POST请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        
        //使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if ( in != null) { in .close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return buffer.toString();
	}
	
	
}
