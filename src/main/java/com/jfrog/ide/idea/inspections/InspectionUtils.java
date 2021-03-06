package com.jfrog.ide.idea.inspections;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import org.jfrog.build.extractor.scan.DependenciesTree;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author yahavi
 */
public class InspectionUtils {

    final static String SHOW_IN_DEPENDENCIES_TREE = "Show in dependencies tree";

    /**
     * Create the 'Show in dependencies tree' quickfix.
     *
     * @param problemsHolder - The "Show in dependencies tree" quickfix will be registered in this container
     * @param dependency     - The dependency tree node correlated to the element
     * @param elements       - The elements to apply the annotations
     */
    static void registerProblem(ProblemsHolder problemsHolder, DependenciesTree dependency, PsiElement[] elements, int dependenciesSize) {
        String description = getDescription(dependency, dependenciesSize);
        ShowInDependenciesTree quickFix = new ShowInDependenciesTree(dependency, description);
        Arrays.stream(elements)
                .filter(Objects::nonNull)
                .forEach(element -> problemsHolder.registerProblem(element, description, ProblemHighlightType.INFORMATION, quickFix));
    }

    /**
     * Get the description of the show in dependencies tree quickfix.
     *
     * @param dependency       - The dependencies tree node
     * @param dependenciesSize - The size of the dependencies tree list
     * @return the description of the show in dependencies tree quickfix
     */
    private static String getDescription(DependenciesTree dependency, int dependenciesSize) {
        if (dependenciesSize > 1) {
            return SHOW_IN_DEPENDENCIES_TREE + " (" + ((DependenciesTree) dependency.getParent()).getUserObject() + ")";
        }
        return SHOW_IN_DEPENDENCIES_TREE;
    }
}
