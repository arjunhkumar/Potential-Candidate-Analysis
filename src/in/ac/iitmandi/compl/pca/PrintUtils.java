/**
 * 
 */
package in.ac.iitmandi.compl.pca;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import in.ac.iitmandi.compl.utils.CommonUtils;
import soot.SootClass;

/**
 * @author arjun
 *
 */
public class PrintUtils {

	public static final String OUT_FILE_PATH = "./candidates.out";
	
	public static void printOutput(Set<LinearType> linearDefinedTypes, String type) {
		if(CommonUtils.isNotNull(linearDefinedTypes)) {
			int size = 0;
			PrintUtils.writeToOutFile("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			PrintUtils.writeToOutFile("No. of Linear structured "+type+" found: ");
			for(LinearType foundType: linearDefinedTypes) {
				if(null !=foundType.getClassInstance() && isNonLibraryClass(foundType.getClassInstance()) 
						&& CommonUtils.isNotNull(PotentialCandidateAnalysis.getInstanceFields(foundType.getClassInstance()))) {
					writeToOutFile(foundType.getClassInstance());
					size++;
				}
			}
			PrintUtils.writeToOutFile("No. of Linear structured "+type+" size: "+size);
			PrintUtils.writeToOutFile("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		}
	}
	
	private static boolean isNonLibraryClass(SootClass classInstance) {
		if(null != classInstance){
			String packageName = classInstance.getPackageName();
			return !(packageName.startsWith("jdk.") 
					|| packageName.startsWith("java.")
					|| packageName.startsWith("javax.")
					|| packageName.startsWith("sun."));
		}
		return false;
	}

	public static void createOutFile() {
		Path path = Paths.get(OUT_FILE_PATH);
		File file = new File(OUT_FILE_PATH);
        if (file.exists()) {
        	try {
        		Files.delete(file.toPath());
			} catch (IOException e) {
				System.out.println("File: "+OUT_FILE_PATH+" could not be deleted.");
			}
        }
        try {
        	Files.createDirectories(path.getParent()); 
			Files.createFile(path);
		} catch (IOException e) {
			System.out.println("File: "+OUT_FILE_PATH+" could not be created.");
        }
	}
	
	public static void writeToOutFile(SootClass classMetadata) {
		File file = new File(OUT_FILE_PATH);
		try(BufferedWriter output = new BufferedWriter(new FileWriter(file,true))){
			try(PrintWriter writer = new PrintWriter(output, true)){
				writer.write("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
				writer.write("Class: "+classMetadata.getName()+"\n");
			}
		} catch (IOException e) {
			System.out.println("File: "+OUT_FILE_PATH+" could not be opened.");
		}
	}
	
	public static void writeToOutFile(String str) {
		File file = new File(OUT_FILE_PATH);
		try(BufferedWriter output = new BufferedWriter(new FileWriter(file,true))){
			try(PrintWriter writer = new PrintWriter(output, true)){
				writer.write(str+"\n");
			}
		} catch (IOException e) {
			System.out.println("File: "+OUT_FILE_PATH+" could not be opened.");
		}
	}
	
}
