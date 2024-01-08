/**
 * 
 */
package in.ac.iitmandi.compl.pca;

import java.util.HashSet;
import java.util.Set;

import in.ac.iitmandi.compl.utils.CommonUtils;
import soot.SootClass;

/**
 * @author arjun
 *
 */
public class LinearType {

	private boolean isJavaPrimitive = false;
	private String javaPrimitiveTypeName;
	private static Set<LinearType> javaPrimitives;
	private SootClass classInstance;
	
	public LinearType(String javaPrimitiveTypeName) {
		if(CommonUtils.isNotNull(javaPrimitiveTypeName)) {
			this.isJavaPrimitive = true;
			this.javaPrimitiveTypeName = javaPrimitiveTypeName;
//			this.classInstance = null;
		}
	}
	
	public LinearType (SootClass primitiveClass) {
		if(null != primitiveClass) {
			this.classInstance = primitiveClass;
		}
	}
	
	/**
	 * @return the isJavaPrimitive
	 */
	public boolean isJavaPrimitive() {
		return isJavaPrimitive;
	}
	/**
	 * @param isJavaPrimitive the isJavaPrimitive to set
	 */
	public void setJavaPrimitive(boolean isJavaPrimitive) {
		this.isJavaPrimitive = isJavaPrimitive;
	}
	/**
	 * @return the classInstance
	 */
	public SootClass getClassInstance() {
		return classInstance;
	}
	/**
	 * @param classInstance the classInstance to set
	 */
	public void setClassInstance(SootClass classInstance) {
		this.classInstance = classInstance;
	}
	
	public static void initializeJavaPrimitives() {
		javaPrimitives = new HashSet<>();
		javaPrimitives.add(new LinearType("int"));
		javaPrimitives.add(new LinearType("double"));
		javaPrimitives.add(new LinearType("float"));
		javaPrimitives.add(new LinearType("long"));
		javaPrimitives.add(new LinearType("char"));
		javaPrimitives.add(new LinearType("byte"));
		javaPrimitives.add(new LinearType("short"));
		javaPrimitives.add(new LinearType("boolean"));
		javaPrimitives.add(new LinearType("java.lang.Integer"));
		javaPrimitives.add(new LinearType("java.lang.Double"));
		javaPrimitives.add(new LinearType("java.lang.Float"));
		javaPrimitives.add(new LinearType("java.lang.Long"));
		javaPrimitives.add(new LinearType("java.lang.Character"));
		javaPrimitives.add(new LinearType("java.lang.Byte"));
		javaPrimitives.add(new LinearType("java.lang.Short"));
		javaPrimitives.add(new LinearType("java.lang.Boolean"));
	}
	
	public static boolean isJavaPrimitiveType(String typeString) {
		if(CommonUtils.isNotNull(typeString) && CommonUtils.isNotNull(javaPrimitives)) {
			LinearType comparee = new LinearType(typeString);
			for(LinearType primitive : javaPrimitives) {
				if(primitive.equals(comparee)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "PrimitiveType [isJavaPrimitive=" + this.isJavaPrimitive  + ", javaPrimitiveTypeName=" + this.javaPrimitiveTypeName
				+ ", classInstance=" + this.classInstance.getName()+"]";
	}
	
	/**
	 * @param arg0
	 * @return
	 * @see java.util.Map#equals(java.lang.Object)
	 */
	public boolean equals(Object comparee) {
		if (comparee == this)
	        return true;
	    if (!(comparee instanceof LinearType))
	        return false;
	    
	    LinearType typeComparee = (LinearType)comparee;
	    
	    if(typeComparee.isJavaPrimitive) {
	    	return this.isJavaPrimitive && this.javaPrimitiveTypeName.equals(typeComparee.javaPrimitiveTypeName);
	    }
	    
	    return !this.isJavaPrimitive && this.classInstance.equals(typeComparee.classInstance);
	}

	/**
	 * @return
	 * @see java.util.Map#hashCode()
	 */
	public int hashCode() {
		int result = 17;
		if (this.isJavaPrimitive) {
	        result = 31 * result;
	    }
	    if (null != this.javaPrimitiveTypeName) {
	        result = 31 * result + this.javaPrimitiveTypeName.hashCode();
	    }
	    if (null != this.classInstance) {
	        result = 31 * result + this.classInstance.hashCode();
	    }
		return result;
	}
	
}
