package com.domain.pdfgenerator.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.activation.URLDataSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlToHtmlTransformer {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		String xml = "./src/main/resources/htmlsamples/xml/report.xml";
		String xslt = "./src/main/resources/htmlsamples/xml/stylesheet.xsl";
		String pdf = "./target/htmlsamples/ch04/report.html";
		
//		transform(xml, xslt, pdf);
		
		InputStream xmlData = new FileInputStream(xml);
		Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlData);
		Source src = new DOMSource(xmlDocument);
		transformFromDom(src, xslt, pdf);
	}
	
	public static void transformFromDom(final Source src, final String xslt, String outputFile) throws IOException,
		TransformerFactoryConfigurationError, TransformerException {
	
		URL xsltURL = new File(xslt).toURI().toURL();
		
		Source stylesource = new StreamSource(xsltURL.openStream(), xsltURL.toExternalForm());
		Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
		
		StringWriter stringWriter = new StringWriter();
		transformer.transform(src, new StreamResult(stringWriter));
		
		// write to file
		File file = new File(outputFile);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stringWriter.toString());
		bw.close();
	}

	public static void transform(final String xml, final String xslt, String outputFile) throws SAXException, IOException,
			ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {

		ClassLoader classloader = XmlToHtmlTransformer.class.getClassLoader();
		InputStream xmlData = new FileInputStream(xml);
		URL xsltURL = new File(xslt).toURI().toURL();

		Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlData);
		Source stylesource = new StreamSource(xsltURL.openStream(), xsltURL.toExternalForm());
		Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);

		StringWriter stringWriter = new StringWriter();
		transformer.transform(new DOMSource(xmlDocument), new StreamResult(stringWriter));

		// write to file
		File file = new File(outputFile);
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stringWriter.toString());
		bw.close();
	}

}