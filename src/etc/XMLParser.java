package etc;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XMLParser{

	private XMLReader xmlReader = null;
	private SAXParser saxParser = null;
	
	public XMLParser(XMLHandler handler) throws ParserConfigurationException, SAXException{
		this.saxParser = SAXParserFactory.newInstance().newSAXParser();
		this.xmlReader = this.saxParser.getXMLReader();
		this.xmlReader.setContentHandler(handler);
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
	public void parse(String filename) throws IOException, SAXException{
		this.xmlReader.parse(convertToFileURL(filename));
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