package com.javabase.base.socket.test;
import java.io.Reader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
public class SchemaValidationHelper {
	 private Reader reader;
	 private Document document;
	 private boolean valid = true;

	 public SchemaValidationHelper(Reader reader) {
	    this.reader = reader;
	 }


	 public boolean isValid() {
	    SAXReader saxReader = new SAXReader();
	    try {
	     //TODO SchemaУ��
	     document = saxReader.read(reader);
	    } catch (DocumentException e) {
	     valid = false;
	    }
	    return valid;
	 }


	 public Document getDocument() {
	    return document;
	 }


	 public void setDocument(Document document) {
	    this.document = document;
	 }


	 public void setValid(boolean valid) {
	    this.valid = valid;
	 }

	 public Reader getReader() {
	    return reader;
	 }
}
