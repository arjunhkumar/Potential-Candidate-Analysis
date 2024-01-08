/**
 * 
 */
package in.ac.iitmandi.compl.pca;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import in.ac.iitmandi.compl.utils.CommonUtils;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootField;
import soot.util.Chain;

/**
 * @author arjun
 *
 */
public class PotentialCandidateAnalysis extends SceneTransformer{

	private Set<LinearType> linearDefinedTypes = new HashSet<>();
	
	@Override
	protected void internalTransform(String phaseName, Map<String, String> options) {
		LinearType.initializeJavaPrimitives();
		Chain<SootClass> classes = Scene.v().getClasses();
		analyzeClasses(new HashSet<SootClass>(classes));
		PrintUtils.printOutput(linearDefinedTypes);
	}

	private void analyzeClasses(HashSet<SootClass> hashSet) {
		boolean continueAnalysis = true;
		if(null != hashSet) {
			int linearTypesInitial = 0;
			while(continueAnalysis) {
				for(SootClass classToBeAnalyzed: hashSet) {
					analyzeClass(classToBeAnalyzed);
				}
				continueAnalysis = !(linearTypesInitial == linearDefinedTypes.size());
				linearTypesInitial = linearDefinedTypes.size();
			}
		}
	}

	private void analyzeClass(SootClass classToBeAnalyzed) {
		if(null != classToBeAnalyzed) {
			if(checkInstanceFieldTypes(classToBeAnalyzed)) {
				this.linearDefinedTypes.add(new LinearType(classToBeAnalyzed));
			}
//			validateStructure(classToBeAnalyzed);
		}
	}


	private boolean checkInstanceFieldTypes(SootClass classToBeAnalyzed) {
		Set<SootField> instanceFields = getInstanceFields(classToBeAnalyzed);
		if(CommonUtils.isNotNull(instanceFields)) {
			for(SootField instanceField : instanceFields) {
				if(!checkLinearType(instanceField)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkLinearType(SootField instanceField) {
		if(null != instanceField) {
			String typeString = instanceField.getType().toQuotedString();
			if(LinearType.isJavaPrimitiveType(typeString)) {
				return true;
			}else {
				return isLinearCustomType(typeString);
			}
		}
		return false;
	}

	private boolean isLinearCustomType(String typeString) {
		if(CommonUtils.isNotNull(typeString) && CommonUtils.isNotNull(this.linearDefinedTypes)) {
			SootClass typeSootClass = Scene.v().getSootClass(typeString);
			LinearType comparee = new LinearType(typeSootClass);
			for(LinearType typeInstance : this.linearDefinedTypes) {
				if(typeInstance.equals(comparee)) {
					return true;
				}
			}
		}
		return false;
	}

	public static Set<SootField> getInstanceFields(SootClass classToBeAnalyzed) {
		Set<SootField> instanceFields = new HashSet<>();
		if(null != classToBeAnalyzed && null != classToBeAnalyzed.getFields() 
				&& !classToBeAnalyzed.getFields().isEmpty()) {
			for(SootField field : classToBeAnalyzed.getFields()) {
				if(!field.isStatic()) {
					instanceFields.add(field);
				}
			}
		}
		return instanceFields;
	}

}
