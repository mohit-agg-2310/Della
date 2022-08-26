package persistence;

import java.io.*;
import com.thoughtworks.xstream.XStream;
/**
 * <p>
 * Title: DataManager
 * </p>
 *
 * <p>
 * Description: Read and write data to the persistent store, an xml file
 * </p>
 *
 * <p>
 * Copyright: Copyright � 2022
 * </p>
 *
 * @author Harry Sameshima, Mohit
 * @version 1
 * @version 1.01
 */
public class DataManager {
	private static final String storageFile = "Della.xml";

	public DataManager() {
	}

	/**
	 * Read our persistent store into an object
	 * @return Object
	 */
	public static Object readData() {

		// Does the persistent store exist?
		File file = new File(storageFile);
		if (!file.exists()) { 
						return null; 
			}

		// Yes, so let's deserialize the object
//		XStream x = new XStream();
//		x.addPermission(AnyTypePermission.ANY);
		Object result = null;
				
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
//			ObjectInputStream oin = x.createObjectInputStream(in);
//			result = oin.readObject();
//			oin.close();
			System.out.println("Read");
		}
		catch (IOException ex) {
			System.out.println("IO exception reading " + storageFile);
			System.exit(0);
		}
//		catch (ClassNotFoundException ex) {
//			System.out.println("Class not found exception while reading " + storageFile);
//			System.exit(0);
//		}
//		
		return result;
	}

	/**
	 * Write out an object to the persistent store so we can save data
	 * @param o Object
	 */
	public static void writeData(Object o) {

		try {
			System.out.println("write");
			XStream x = new XStream();
//			x.addPermission(AnyTypePermission.ANY);
			FileWriter fw = new FileWriter(storageFile);
			ObjectOutputStream out = x.createObjectOutputStream(fw);
			out.writeObject(o);
			out.close();
		}
		catch (IOException ex) {
			System.out.println("IO Exception writing " + storageFile);
			System.exit(0);
		}
	}
}