package cn.wei.library.utils;

import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Stay
 * @version create time：Oct 11, 2014 2:29:31 PM 
 */
public class ZipUtil {
	private static final int BUFFER_SIZE = 1024;
	
	private ZipUtil(){
		
	}
	
	public static void decodeFileFromBase64(String base64, String zipFile) throws IOException {
		byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
		OutputStream stream = new FileOutputStream(zipFile);
		try {
			stream.write(bytes);
			stream.flush();
		}finally{
			stream.close();
		}
	}
	
	/**
	 * Unzip a zip file.  Will overwrite existing files.
	 * 
	 * @param zipFile Full path of the zip file you'd like to unzip.
	 * @param dir Full path of the directory you'd like to unzip to (will be created if it doesn't exist). null will create the unziped files 
	 * 			to the same directory
	 * @throws IOException
	 */
	public static void unzip(String zipFile, String dir) throws IOException {
	    int size;
	    if(dir == null || "".equals(dir.trim())){
	    	File file = new File(zipFile);
	    	dir = file.getParent();
	    }
	    byte[] buffer = new byte[BUFFER_SIZE];
	    try {
	        if (!dir.endsWith("/") ) {
	            dir += "/";
	        }
	        File f = new File(dir);
	        if(!f.isDirectory()) {
	            f.mkdirs();
	        }
	        ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));
	        try {
	            ZipEntry ze = null;
	            while ((ze = zin.getNextEntry()) != null) {
	                String path = dir + ze.getName();
	                File unzipFile = new File(path);

	                if (ze.isDirectory()) {
	                    if(!unzipFile.isDirectory()) {
	                        unzipFile.mkdirs();
	                    }
	                } else {
	                    // check for and create parent directories if they don't exist
	                    File parentDir = unzipFile.getParentFile();
	                    if ( null != parentDir ) {
	                        if (!parentDir.isDirectory() ) {
	                            parentDir.mkdirs();
	                        }
	                    }

	                    // unzip the file
	                    FileOutputStream out = new FileOutputStream(unzipFile, false);
	                    BufferedOutputStream fout = new BufferedOutputStream(out, BUFFER_SIZE);
	                    try {
	                        while ( (size = zin.read(buffer, 0, BUFFER_SIZE)) != -1 ) {
	                            fout.write(buffer, 0, size);
	                        }
	                        zin.closeEntry();
	                    }finally {
	                        fout.flush();
	                        fout.close();
	                    }
	                }
	            }
	        }finally {
	            zin.close();
	        }
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void zip(File[] files, String zipFile) throws IOException {
	    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
	    try { 
	        byte data[] = new byte[BUFFER_SIZE];
	        for (int i = 0; i < files.length; i++) {
	            FileInputStream fi = new FileInputStream(files[i]);    
	            try {
	                ZipEntry entry = new ZipEntry(files[i].getName());
	                out.putNextEntry(entry);
	                int count;
	                while ((count = fi.read(data)) > 0) {
	                    out.write(data, 0, count);
	                }
	            }finally {
	            	fi.close();
	            }
	        }
	    }finally {
	    	out.closeEntry();
	        out.close();
	    }
	}
	
}
