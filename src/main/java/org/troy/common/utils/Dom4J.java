package org.troy.common.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * xml dom解析
 * @author wangj
 * 2013-7-2
 */
public class Dom4J {
	
	private String outputEncoding="utf-8";    //DOM4J默认的编码格式为utf-8,所以含有中文时需要指定中文编码，不含中文倒不需要重写编码格式的
	/** xml文件路径 */
	private String filename = "";
	private  File xmlfile;
		
	public Dom4J() {
		super();
		File file = new File(filename);
	    this.xmlfile = file;
	}

	public String getOutputEncoding() {
		return outputEncoding;
	}
	public void setOutputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}
	public File getXmlfile() {
		return xmlfile;
	}
	public void setXmlfile(File xmlfile) {
		this.xmlfile = xmlfile;
	}
	
	/**
	 * 解析xml为Document，放入内存中
	 * @param filepath
	 * @return
	 * @throws DocumentException
	 */
	public Document parse(File xmlfile) {
        this.xmlfile = xmlfile;
		SAXReader reader = new SAXReader();
		Document document = null;
		try{
			document = reader.read(xmlfile);
		}catch(DocumentException e){
			e.printStackTrace();
		}
        return document;
    }
	/**
	 * 解析xml字符串为Document，放入内存中
	 * @param filepath
	 * @return
	 * @throws DocumentException
	 */
	public static Document parseXmldoc(String xmlDoc) {
		Document document = null;
		try{
			document = DocumentHelper.parseText(xmlDoc);
		}catch(DocumentException e){
			e.printStackTrace();
		}
        return document;
    }
	/**
	 * 修改一个xml文件的element值，根据element的位置及名称检索
	 * @param inputXml(或URL)
	 * @param elementpath:不含修改的那个element之name，只要到上一级即可
	 * @param nodename
	 * @param newtextvalue
	 */
	@SuppressWarnings("rawtypes")
	public void modifyDocumentByElement(String elementPath,String nodename,String newTextValue) {
		try {
			Document document = parse(xmlfile);			
			List list = new ArrayList();
			list = document.selectNodes(elementPath);  //
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Iterator iterator = element.elementIterator(nodename);
				while (iterator.hasNext()) {
					Element firstNameElement = (Element) iterator.next();
					firstNameElement.setText(newTextValue);
				}
			}			
			OutputFormat format = OutputFormat.createPrettyPrint(); 
            format.setEncoding(outputEncoding); 
			XMLWriter output = new XMLWriter(new FileWriter(xmlfile),format);
			output.write(document);
			output.close();
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * 修改一个xml文件的element的属性值，根据element的位置及属性名称检索
	 * @param inputXml(或URL)
	 * @param attributePath:规范："//catalog/journal/article/@level"：
	 * @param nodename
	 * @param newtextvalue
	 */
	@SuppressWarnings("rawtypes")
	public void modifyDocumentByAttribute(String attributePath,String newValue) {
		try {
			Document document = parse(xmlfile);
			List list = document.selectNodes(attributePath);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
				attribute.setValue(newValue);
			}
			
			OutputFormat format = OutputFormat.createPrettyPrint(); 
            format.setEncoding(outputEncoding); 
			XMLWriter output = new XMLWriter(new FileWriter(xmlfile),format);
			output.write(document);
			output.close();
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * 根据ELEMENT得到值
	 * @param elementPath
	 * @param nodename
	 * @param newTextValue
	 */
	@SuppressWarnings("rawtypes")
	public String getFirstElementValueByElement(String elementPath,String nodename) {
		String textValue = "";
		Document document = parse(xmlfile);
		List list = new ArrayList();
		list = document.selectNodes(elementPath); //
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			Iterator iterator = element.elementIterator(nodename);
			while (iterator.hasNext()) {
				Element element_temp = (Element) iterator.next();
				textValue = element_temp.getText();
				break;
			}
		}

		return textValue;
	}
	/**
	 * 根据ELEMENT的attribute得到属性值,带@符号
	 * @param elementPath
	 * @param nodename
	 * @param newTextValue
	 */
	@SuppressWarnings("rawtypes")
	public String getAttributeValueByElementAndAttribute(String attributePath) {
		String textValue = "";
		Document document = parse(xmlfile);
		List list = document.selectNodes(attributePath);
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			textValue = attribute.getValue();
			break;
		}

		return textValue;
	}
	/**
	 * 根据xml字符串和ELEMENT的attribute得到属性值,带@符号
	 * @param elementPath
	 * @param nodename
	 * @param newTextValue
	 */
	@SuppressWarnings("rawtypes")
	public static String getAttributeValueByElementAndAttribute(String xmlDoc,String attributePath) {
		String textValue = "";
		Document document = parseXmldoc(xmlDoc);
		List list = document.selectNodes(attributePath);
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			textValue = attribute.getValue();
			break;
		}

		return textValue;
	}
	/**
	 * 根据xml字符串和ELEMENT的attribute得到list结果集
	 * @param elementPath
	 * @param nodename
	 * @param newTextValue
	 */
	@SuppressWarnings("rawtypes")
	public static List selectNodes(String xmlDoc,String attributePath) {
		Document document = parseXmldoc(xmlDoc);
		List list = document.selectNodes(attributePath);
		return list;
	}
	public static void main(String[] args) {
		try{
			
			Dom4J dom1 = new Dom4J();
			dom1.modifyDocumentByElement("//config","overduetime","xcmm");
			//Dom4J dom2 = new Dom4J("catalog.xml");
			//dom2.modifyDocumentByAttribute("//catalog/journal/article/@level","xcmm");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
