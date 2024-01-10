/**
 * 
 */
package in.ac.iitmandi.compl.pca;

import soot.Scene;
import java.util.Set;

import in.ac.iitmandi.compl.utils.CommonUtils;

import java.util.HashSet;
import soot.SootClass;
import soot.SootField;

/**
 * @author arjun
 *
 */
public class FieldAnalysis {
	
	private static Set<LinearType> linearFieldTypes = new HashSet<>();

	public static void  analyzeClasses(Set<LinearType> linearTypesFound) {
		Set<SootClass> classes4Analysis = new HashSet<>(Scene.v().getClasses());
		for(SootClass classToBeAnalyzed: classes4Analysis) {
			analyzeClass(classToBeAnalyzed,linearTypesFound);
		}
		PrintUtils.printOutput(linearFieldTypes,"fields");
	}

	private static void analyzeClass(SootClass classToBeAnalyzed, Set<LinearType> linearTypesFound) {
		if(null != classToBeAnalyzed && CommonUtils.isNotNull(linearTypesFound)) {
			Set<SootField> instanceFields = PotentialCandidateAnalysis.getInstanceFields(classToBeAnalyzed);
			if(CommonUtils.isNotNull(instanceFields)) {
				for(SootField instanceField : instanceFields) {
					SootClass fieldClass = Scene.v().getSootClass(instanceField.getType().toQuotedString());
					for(LinearType linearType : linearTypesFound) {
						if(linearType.getClassInstance().equals(fieldClass)) {
							linearFieldTypes.add(linearType);
						}
					}
				}
			}
		}
	}
	
}
