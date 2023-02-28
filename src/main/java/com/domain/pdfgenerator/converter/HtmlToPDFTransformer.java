package com.domain.pdfgenerator.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.itextpdf.html2pdf.HtmlConverter;
//import com.itextpdf.licensing.base.LicenseKey;

public class HtmlToPDFTransformer {
	
	/**
	 * The path to the resulting PDF file.
	 */
	public static final String DEST = "./target/htmlsamples/ch01/htmlToPDF.pdf";
	
	public static void main(String[] args) throws IOException {
//		try (FileInputStream license = new FileInputStream(System.getenv("ITEXT7_LICENSEKEY")
//				+ "/itextkey-html2pdf_typography.json")) {
//			LicenseKey.loadLicenseFile(license);
//		}
		
		String html = "./src/main/resources/htmlsamples/html/htmlTemplate.html";
		
		
		String htmlData = readFile(html);
		
//		generateFromDTO(htmlData);
		
		Map<String, String> mapData = new HashMap();

		mapData.put("<author>", "Gambardella, Matthew");
		mapData.put("<bookTitle>", "XML Developer's Guide");
		mapData.put("<genre>", "Computer");
		mapData.put("<price>", "44.95");
		mapData.put("<bookDate>", "2000-10-01");
		mapData.put("<description>", "An in-depth look at creating applications with XML.");
		
		generateFromMap(htmlData, mapData);
	}



	private static void generateFromDTO(String htmlData) throws IOException {
		AuthorDTO author = new AuthorDTO();

		author.setAuthor("Gambardella, Matthew");
		author.setBookTitle("XML Developer's Guide");
		author.setGenre("Computer");
		author.setPrice("44.95");
		author.setBookDate("2000-10-01");
		author.setDescription("An in-depth look at creating applications with XML.");
		
		String populatedHtmlData = replaceData(htmlData, author);
		
		File file = new File(DEST);
		file.getParentFile().mkdirs();

		new HtmlToPDFTransformer().createPdf(populatedHtmlData, DEST);
	}
	
	private static void generateFromMap(String htmlData, Map<String, String> mapData) throws IOException {
		
		
		String populatedHtmlData = replaceFromMap(htmlData, mapData);
		
		File file = new File(DEST);
		file.getParentFile().mkdirs();

		new HtmlToPDFTransformer().createPdf(populatedHtmlData, DEST);
	}
	
	
	private static String replaceData(String htmlData, AuthorDTO author) {

		//<bookTitle><genre><price><bookDate><description>
		htmlData = htmlData.replace("<author>", author.getAuthor());
		htmlData = htmlData.replace("<bookTitle>", author.getBookTitle());
		htmlData = htmlData.replace("<genre>", author.getGenre());
		htmlData = htmlData.replace("<price>", author.getPrice());
		htmlData = htmlData.replace("<bookDate>", author.getBookDate());
		htmlData = htmlData.replace("<description>", author.getDescription());
		
		return htmlData;
	}
	
	private static String replaceFromMap(String htmlData, Map<String, String> mapData) {

		Set<Entry<String, String>> entrySet = mapData.entrySet();
		for(Entry<String, String> entry: entrySet) {
			htmlData = htmlData.replace(entry.getKey(), entry.getValue());
		}
		
		return htmlData;
	}

	private static String readFile(String filePath)
    {
 
        // Declaring object of StringBuilder class
        StringBuilder builder = new StringBuilder();
 
        // try block to check for exceptions where
        // object of BufferedReader class us created
        // to read filepath
        try (BufferedReader buffer = new BufferedReader(
                 new FileReader(filePath))) {
 
            String str;
            while ((str = buffer.readLine()) != null) {
 
                builder.append(str);
//                builder.append("\n");
            }
        }
 
        catch (IOException e) {
            e.printStackTrace();
        }
 
        return builder.toString();
    }

	/**
	 * Creates the PDF file.
	 *
	 * @param html the HTML as a String value
	 * @param dest the path of the resulting PDF
	 * @throws IOException signals that an I/O exception has occurred.
	 */
	public void createPdf(String html, String dest) throws IOException {
		HtmlConverter.convertToPdf(html, new FileOutputStream(dest));
	}

}
