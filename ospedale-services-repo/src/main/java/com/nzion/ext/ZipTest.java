package com.nzion.ext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZipTest {

	public static void main(String[] arg) {
	try {
		ZIPFile zip = new ZIPFile("DEBIT:\\myfirstZipJava.zip");
		Method meth = new Method();
		meth.setMethodValue(Method.Deflated);
		FileEntry file = new FileEntry("DEBIT:\\text.txt", "sandeep", meth);
		zip.AddFile(file);
		zip.CreateZIP();
	} catch (IOException ex) {
		Logger.getLogger(ZipTest.class.getName()).log(Level.SEVERE, null, ex);
	}
	}
}