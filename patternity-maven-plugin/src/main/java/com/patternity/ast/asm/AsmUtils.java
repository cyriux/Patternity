package com.patternity.ast.asm;

import com.patternity.ast.AnnotationElement;
import com.patternity.ast.Modifiers;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 *
 */
public class AsmUtils {

    public static AnnotationElement createAnnotationFromDesc(String desc, boolean visibleAtRuntime) {
        String annotationType = Type.getType(desc).getInternalName();
        AnnotationElement model = new AnnotationElement(annotationType, visibleAtRuntime);
        return model;
    }

    public static void applyModifiers(Modifiers model, int access) {
        applyFinalModifier(model, access);
        applyStaticModifier(model, access);
        applyVisibilityModifier(model, access);
    }

    public static void applyFinalModifier(Modifiers model, int access) {
        if (hasFlag(access, Opcodes.ACC_FINAL))
            model.markFinal();
    }

    public static void applyStaticModifier(Modifiers model, int access) {
        if (hasFlag(access, Opcodes.ACC_STATIC))
            model.markStatic();
    }

    public static void applyVisibilityModifier(Modifiers model, int access) {
        if (hasFlag(access, Opcodes.ACC_PUBLIC))
            model.markPublic();
        else if (hasFlag(access, Opcodes.ACC_PROTECTED))
            model.markProtected();
        else if (hasFlag(access, Opcodes.ACC_PRIVATE))
            model.markPrivate();
        else
            model.markDefault();
    }

    private static boolean hasFlag(int access, int flag) {
        return (access & flag) == flag;
    }
}
