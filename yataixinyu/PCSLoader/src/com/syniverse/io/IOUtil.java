package com.syniverse.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.syniverse.common.CommUtil;
import com.syniverse.common.MemoryUsage;
import com.syniverse.info.EachRowInfo;

public class IOUtil {
	private static final Log LOGGER = LogFactory.getLog(IOUtil.class);
	public static final String ENTER = "\n";

	public static BufferedReader createBufferedReader(String fullPath,
			String charsetName) {
		BufferedReader br = null;
		try {
			if (charsetName == null) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fullPath)));
			} else {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fullPath), charsetName));
			}
		} catch (Exception e) {
			LOGGER.error("Error when createBufferedReader", e);
			throw new RuntimeException("Cannot create BufferedReader", e);
		}
		return br;
	}

	/**
	 * Read first line, and convert it to uppercase
	 * 
	 * @param fullPath
	 * @param charsetName
	 * @return
	 */
	public static String readUppercaseFirstLine(String fullPath,
			String charsetName) {
		BufferedReader br = null;
		try {
			if (charsetName == null) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fullPath)));
			} else {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fullPath), charsetName));
			}
			String line = null;
			if ((line = br.readLine()) != null) {
				return line.toUpperCase();
			}
		} catch (Exception e) {
			LOGGER.error("Error when firstLine", e);
		} finally {
			closeReader(br);
		}
		return null;
	}

	public static void writeToFile(BufferedWriter bw, String content) {
		try {
			bw.write(content);
			bw.flush();
		} catch (IOException e) {
			LOGGER.error("Error when writeToFile", e);
		}
	}

	/**
	 * 
	 * 
	 * @param fullPath
	 * @param charsetName
	 * @return
	 */
	public static int getLineCount(String fullPath, String charsetName) {
		int count = 0;
		BufferedReader br = null;
		try {
			if (charsetName == null) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fullPath)));
			} else {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fullPath), charsetName));
			}
			while (br.readLine() != null) {
				count++;
			}
		} catch (Exception e) {
			LOGGER.error("Error when getLineCount", e);
		} finally {
			closeReader(br);
		}
		return count;
	}

	public static boolean close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (Throwable e) {
				LOGGER.error("Error when closeIgnoringException", e);
				return false;
			}
		}
		return true;
	}

	public static void closeReader(Reader br) {
		close(br);
	}

	public static void closeWriter(Writer bw) {
		close(bw);
	}

	public static void closeWriter(Writer[] array) {
		if (array != null) {
			for (Writer writer : array) {
				closeWriter(writer);
			}
		}
	}

	public static boolean ensureFolderExist(String folderFullPath) {
		File f = new File(folderFullPath);
		if (f.exists() == false) {
			return f.mkdirs();
		}
		return true;
	}

	public static boolean ensureParentFolderExist(String fullPath) {
		File f = new File(fullPath);
		File parentFolder = f.getParentFile();
		if (parentFolder.exists() == false) {
			return parentFolder.mkdirs();
		}
		return true;
	}

	public static boolean createNewFile(String fullPath) {
		new File(fullPath).delete();
		if (ensureParentFolderExist(fullPath) == true) {
			try {
				return new File(fullPath).createNewFile();
			} catch (Exception e) {
				LOGGER.error("Create new file fails, datafileFullPath:"
						+ fullPath, e);
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 1. Delete <code>to</code> first if exist
	 * 
	 * 2. Create parent folder of <code>to</code> if not exist
	 * 
	 * 3. Do rename
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean renameTo(String from, String to) {
		// source not exist, return false
		if (new File(from).exists() == false) {
			return false;
		}
		new File(to).delete();
		if (ensureParentFolderExist(to) == true) {
			boolean renameOK = new File(from).renameTo(new File(to));
			if (renameOK == false) {
				renameOK = cut2(from, to);
			}
			return renameOK;
		} else {
			return false;
		}
	}

	/**
	 * Precondition to invoke this method is:
	 * 
	 * <code>from</code> definitely exists, <code>to</code>'s parent folder
	 * definitely exists
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private static boolean cut2(String from, String to) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		int length = -1;
		byte[] buf = new byte[8 * 1024];
		try {
			fis = new FileInputStream(from);
			fos = new FileOutputStream(to);
			while ((length = fis.read(buf)) != -1) {
				fos.write(buf, 0, length);
				fos.flush();
			}
			IOUtil.close(fis);
			IOUtil.close(fos);
			new File(from).delete();

		} catch (Exception e) {
			LOGGER.error(
					CommUtil.format("Error when cut2: [{0}]-->[{1}]", from, to),
					e);
			return false;
		} finally {
			IOUtil.close(fis);
			IOUtil.close(fis);
		}
		return true;
	}

	public static boolean copy2(String from, String to) {
		// source not exist, return false
		if (new File(from).exists() == false) {
			return false;
		}
		new File(to).delete();
		if (ensureParentFolderExist(to) == true) {
			FileInputStream fis = null;
			FileOutputStream fos = null;
			int length = -1;
			byte[] buf = new byte[8 * 1024];
			try {
				fis = new FileInputStream(from);
				fos = new FileOutputStream(to);
				while ((length = fis.read(buf)) != -1) {
					fos.write(buf, 0, length);
					fos.flush();
				}
			} catch (Exception e) {
				LOGGER.error(CommUtil.format("Error when copy2: [{0}]-->[{1}]",
						from, to), e);
				return false;
			} finally {
				IOUtil.close(fis);
				IOUtil.close(fis);
			}
			return true;
		} else {
			LOGGER.error(CommUtil.format(
					"Cannot create parent folder when copy2: [{0}]-->[{1}]",
					from, to));
			return false;
		}
	}

	/**
	 * return the first filename we encounter when iterator
	 * <code>zipFullPath</code>, or null if no file exists
	 * 
	 * @param zipFullPath
	 * @return, filename we extracted, or null if not file is in
	 *          <code>zipFullPath</code>
	 */
	public static String extractWhat(String zipFullPath) {
		File f = new File(zipFullPath);
		if (f.exists() == false) {
			return null;
		}
		String filename = f.getName();
		// Yes, gz
		if (gz(filename) == true) {
			int pos = filename.lastIndexOf(".");
			String extractWhat = filename.substring(0, pos);
			LOGGER.info("file suffix is gz. extractWhat=" + extractWhat);
			return extractWhat;
		}

		long begin = System.currentTimeMillis();
		ZipInputStream zipIs = null;
		try {
			zipIs = new ZipInputStream(new BufferedInputStream(
					new FileInputStream(zipFullPath)));
			ZipEntry zEntry = null;
			while ((zEntry = zipIs.getNextEntry()) != null) {
				// we need file, NOT directory
				if (zEntry.isDirectory() == true) {
					continue;
				}
				// Since be here, this is a file
				String entryName = zEntry.getName();
				String extractWhat = sanitizeEntryName(entryName);
				LOGGER.info(CommUtil.format("Should extract [{0}-->{1}]",
						zipFullPath, extractWhat));
				return extractWhat;
			}
		} catch (Exception e) {
			LOGGER.error(CommUtil.format("Error when extractWhat() [{0}]",
					zipFullPath), e);
		} finally {
			close(zipIs);
			long end = System.currentTimeMillis();
			LOGGER.error("extractWhat() cost: " + (end - begin));
		}
		return null;
	}

	/**
	 * Return how many data files are there in this zip file
	 * <p>
	 * *.gz always has 1 data file
	 * <p>
	 * -1: original zip file does not exist
	 * <p>
	 * 0: original zip file contains no data file
	 * 
	 * @param zipFullPath
	 * @return
	 */
	public static int datafileCount(String zipFullPath) {
		File f = new File(zipFullPath);
		if (f.exists() == false) {
			return -1;
		}
		String filename = f.getName();
		// Yes, gz
		if (gz(filename) == true) {
			LOGGER.info("*.gz, datafileCount always 1, " + zipFullPath);
			return 1;
		}
		int containedCount = 0;
		long begin = System.currentTimeMillis();
		ZipInputStream zipIs = null;
		try {
			zipIs = new ZipInputStream(new BufferedInputStream(
					new FileInputStream(zipFullPath)));
			ZipEntry zEntry = null;
			while ((zEntry = zipIs.getNextEntry()) != null) {
				// we need file, NOT directory
				if (zEntry.isDirectory() == true) {
					continue;
				}
				// Since be here, this is a file
				containedCount++;
				String entryName = zEntry.getName();
				String extractWhat = sanitizeEntryName(entryName);
				LOGGER.info(CommUtil.format("Find one data file [{0}-->{1}]",
						zipFullPath, extractWhat));
			}
		} catch (Exception e) {
			LOGGER.error(CommUtil.format("Error when datafileCount() [{0}]",
					zipFullPath), e);
			containedCount = -1;
		} finally {
			close(zipIs);
			long end = System.currentTimeMillis();
			LOGGER.error("datafileCount() cost: " + (end - begin));
		}
		return containedCount;
	}

	private static final String[] zip_SUFFIX = { "zip" };
	private static final String[] gz_SUFFIX = { "gz", "gzip" };
	private static final String[] ALL_ZIP_SUFFIX = CommUtil.combineNewArray(
			zip_SUFFIX, gz_SUFFIX);

	private static boolean endsWithSuffix(String filename, String[] arraySuffix) {
		if (filename == null || filename.length() == 0) {
			return false;
		}
		int pos = filename.lastIndexOf(".");
		if (pos != -1) {
			String nowSuffix = filename.substring(pos + 1);
			for (String someSuffix : arraySuffix) {
				if (someSuffix.equalsIgnoreCase(nowSuffix)) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * we mean both zip and gz
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean isZip_by_LastestSuffix(String filename) {
		return endsWithSuffix(filename, ALL_ZIP_SUFFIX);
	}

	private static boolean gz(String filename) {
		return endsWithSuffix(filename, gz_SUFFIX);
	}

	public static boolean gunzip(String zipFullPath, String extractWhat) {
		if (extractWhat == null) {
			return false;
		}
		File f = new File(zipFullPath);
		if (f.exists() == false) {
			return false;
		}
		long begin = System.currentTimeMillis();
		String parentFolder = f.getParentFile().getAbsolutePath();
		String decompressFullPath = parentFolder + "/" + extractWhat;
		GZIPInputStream gis = null;
		FileOutputStream fos = null;
		boolean bSuccess = true;
		try {
			gis = new GZIPInputStream(new FileInputStream(zipFullPath));
			fos = new FileOutputStream(decompressFullPath);

			byte[] buffer = new byte[1024 * 4];
			int len = -1;
			while ((len = gis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			bSuccess = false;
			LOGGER.error(CommUtil.format("Error when gunzip [{0}-->{1}]",
					zipFullPath, extractWhat), e);
		} finally {
			close(fos);
			close(gis);
		}

		long end = System.currentTimeMillis();
		LOGGER.info(CommUtil.format("gunzip cost: {0} ,[{1}-->{2}]",
				MemoryUsage.human(end - begin), zipFullPath, extractWhat));
		return bSuccess;
	}

	public static String eliminate_from_firstDot(String filename) {
		if (filename == null || filename.length() == 0) {
			return filename;
		}
		int pos = filename.indexOf(".");
		if (pos != -1) {
			return filename.substring(0, pos);
		} else {
			return filename;
		}
	}

	public static boolean unzip(String zipFullPath, String extractWhat) {
		if (extractWhat == null) {
			return false;
		}
		File f = new File(zipFullPath);
		if (f.exists() == false) {
			return false;
		}
		if (gz(f.getName()) == true) {
			return gunzip(zipFullPath, extractWhat);
		}
		String parentFolder = f.getParentFile().getAbsolutePath();
		long begin = System.currentTimeMillis();
		boolean bSuccess = false;
		ZipInputStream zipIs = null;
		FileOutputStream fos = null;
		try {
			zipIs = new ZipInputStream(new BufferedInputStream(
					new FileInputStream(zipFullPath)));
			byte[] buf = new byte[8 * 1024];
			ZipEntry zEntry = null;
			while ((zEntry = zipIs.getNextEntry()) != null) {
				String zentryName = sanitizeEntryName(zEntry.getName());
				if (extractWhat.equals(zentryName)) {
					fos = new FileOutputStream(parentFolder + "/" + zentryName);
					int length = -1;
					while ((length = zipIs.read(buf)) != -1) {
						fos.write(buf, 0, length);
					}
					fos.flush();
					bSuccess = true;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error(CommUtil.format("Error when unzip [{0}-->{1}]",
					zipFullPath, extractWhat), e);
		} finally {
			close(fos);
			close(zipIs);
		}
		long end = System.currentTimeMillis();
		LOGGER.info(CommUtil.format("unzip cost: {0} ,[{1}-->{2}]",
				MemoryUsage.human(end - begin), zipFullPath, extractWhat));
		return bSuccess;
	}

	private static String sanitizeEntryName(String entryName) {
		int slash = entryName.lastIndexOf("/");
		if (slash != -1) {
			return entryName.substring(slash + 1);
		}
		// No slash
		else {
			// let's check BACKSLASH
			int backslash = entryName.lastIndexOf("\\");
			if (backslash != -1) {
				return entryName.substring(backslash + 1);
			}
			// No slash, No backslash
			else {
				return entryName;
			}
		}
	}

	public static boolean zip(String orgnFullPath, String zipFullPath) {
		long begin = System.currentTimeMillis();

		File orgn = new File(orgnFullPath);
		if (orgn.exists() == false) {
			return false;
		}
		File zip = new File(zipFullPath);
		if (zip.exists() == true) {
			zip.delete();
		}

		ZipOutputStream zipos = null;
		FileInputStream fis = null;
		try {
			zipos = new ZipOutputStream(new FileOutputStream(zipFullPath));
			zipos.putNextEntry(new ZipEntry(orgn.getName()));

			byte[] buf = new byte[8 * 1024];
			int length = -1;
			fis = new FileInputStream(orgnFullPath);
			while ((length = fis.read(buf)) != -1) {
				zipos.write(buf, 0, length);
			}
			zipos.flush();
			zipos.closeEntry();
		} catch (Exception e) {
			LOGGER.error(CommUtil.format("Error when zip [{0}-->{1}]",
					orgnFullPath, zipFullPath), e);
		} finally {
			close(fis);
			close(zipos);
		}
		long end = System.currentTimeMillis();
		LOGGER.info(CommUtil.format("zip cost: {0} ,[{1}-->{2}]",
				MemoryUsage.human(end - begin), orgnFullPath, zipFullPath));
		return true;
	}

	public static BufferedWriter createBufferedWriter(String fullPath,
			String charsetName, boolean append) {
		ensureParentFolderExist(fullPath);
		BufferedWriter bw = null;
		try {
			if (charsetName == null) {
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(fullPath, append)));
			} else {
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(fullPath, append), charsetName));
			}
		} catch (Exception e) {
			LOGGER.error("Error when createBufferedWriter", e);
			throw new RuntimeException("Cannot create BufferedWriter", e);
		}
		return bw;
	}

	public static boolean mergeTo(String destFullPath, String[] arraySrcFullPath) {
		new File(destFullPath).delete();
		BufferedWriter bw = createBufferedWriter(destFullPath, null, true);
		for (String srcFullPath : arraySrcFullPath) {
			BufferedReader br = createBufferedReader(srcFullPath, null);
			if (br == null) {
				continue;
			}
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					bw.write(line);
					bw.newLine();
				}
			} catch (Exception e) {
				LOGGER.error(
						CommUtil.format(
								"mergeTo Exception [desc={0}, src={1}]. Ignore and continue",
								destFullPath, srcFullPath), e);
			} finally {
				closeReader(br);
			}
		}
		closeWriter(bw);
		return true;
	}

	/**
	 * Get's the folder where <code>cls</code> stays
	 * <p>
	 * C:/work/workingdir/bin/com.syniverse.loader.Test.class-->C:/work/
	 * workingdir/bin
	 * <p>
	 * C:/work/workingdir/bin/test.jar(com.syniverse.loader.Test.class in this
	 * jar)-->C:/work/ workingdir/bin
	 * 
	 * @param cls
	 * @return
	 */
	public static String getJarStayFolder(Class<?> cls) {
		String jarSelf = cls.getProtectionDomain().getCodeSource()
				.getLocation().getFile();

		try {
			jarSelf = java.net.URLDecoder.decode(jarSelf, "UTF-8");
		} catch (Exception e) {
			LOGGER.error("Error when getClassStayFolder", e);
		}
		String jarStayFolder = new File(jarSelf).getParentFile()
				.getAbsolutePath();
		LOGGER.info(CommUtil.format(
				"getClassStayFolder: cls=[{0}], stayedFolder=[{1}]", cls,
				jarStayFolder));
		return jarStayFolder;
	}

	public static String getJarStayFolder_nologinfo(Class<?> cls) {
		String jarSelf = cls.getProtectionDomain().getCodeSource()
				.getLocation().getFile();
		try {
			jarSelf = java.net.URLDecoder.decode(jarSelf, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jarStayFolder = new File(jarSelf).getParentFile()
				.getAbsolutePath();
		return jarStayFolder;
	}

	public static void main(String[] args) throws Exception {
		// IOUtil.zip("/home/marvin/Desktop/a.log",
		// "/home/marvin/Desktop/aa.zip");
		PropertyConfigurator.configure("conf/log4j.properties");
		String zipfullpath = "C:\\a\\ARGTM_SUBSCRIBER.csv.gz";
		IOUtil.unzip(zipfullpath, extractWhat(zipfullpath));
		IOUtil.ensureFolderExist("c:/a/b////");
	}
}
