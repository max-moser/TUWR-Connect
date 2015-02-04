package etc;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XMLParser{

	private final XMLReader xmlReader;
	private final SAXParser saxParser;
	private final XMLHandler xmlHandler;
	
	public XMLParser(XMLHandler handler) throws ParserConfigurationException, SAXException{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setValidating(true);
		this.saxParser = spf.newSAXParser();
		this.xmlReader = this.saxParser.getXMLReader();
		this.xmlHandler = handler;
		this.xmlReader.setContentHandler(handler);
		this.xmlReader.setErrorHandler(handler);
	}
	
	/**
	 * TODO write fancy description about parsing
	 * 
	 * Warning: filename should be an absolute path!
	 * Otherwise, the working directory will be used
	 * (this means, if you have a shortcut to the program on the Desktop,
	 *  the Desktop will also be your working directory!)
	 * 
	 * @param filename The name of the file which should be parsed (should be an absolute path!)
	 * @throws IOException
	 * @throws SAXException
	 */
	public Object parse(String filename) throws IOException, SAXException{
		this.xmlReader.parse(convertToFileURL(filename));
		return this.xmlHandler.getResult();
	}

	/**
	 * Converts file name to URL.
	 * Example:
	 * File "test.txt", in the directory C:\Temp\
	 * Output: "file:/C:/Temp/test.txt"
	 * 
	 * @param filename The filename to convert
	 * @return an URL crafted from the filename
	 */
	private static String convertToFileURL(String filename) {
		String path = new File(filename).getAbsolutePath();
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar, '/');
		}

		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return "file:" + path;
	}
}