package com.domain.pdfgenerator.converter;

import java.io.OutputStream;

import java.io.FileOutputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class XmlToHtml {
	
	public static void main(String[] args) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			
			Source xslDoc = new StreamSource("./src/main/resources/htmlsamples/xml/stylesheet.xsl");
			Source xmlDoc = new StreamSource("./src/main/resources/htmlsamples/xml/report.xml");
			
			String outputFileName = "./target/htmlsamples/ch04/report.html";
			
			OutputStream htmlFile = new FileOutputStream(outputFileName);
			Transformer transform = tFactory.newTransformer(xslDoc);
			transform.transform(xmlDoc, new StreamResult(htmlFile));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
