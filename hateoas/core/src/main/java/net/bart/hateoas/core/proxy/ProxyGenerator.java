package net.bart.hateoas.core.proxy;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ProxyGenerator implements Opcodes {

    private static final AtomicInteger CLASS_NUMBER = new AtomicInteger(0);
    private static final String CLASSNAME_PREFIX = "HateoasController_";
    private static final String HANDLER_NAME = "__handler";
    private static final Type INVOKER_TYPE = Type.getType(Invoker.class);

    public Class<?> generateProxyClass(final ClassLoader classLoader, final Class<?> proxyClass) {
        final Class<?> superclass = validateClass(proxyClass);
        final String proxyName = CLASSNAME_PREFIX + CLASS_NUMBER.incrementAndGet();
        final Method[] implementationMethods = getImplementationMethods(proxyClass);
        final String classFileName = proxyName.replace('.', '/');

        try {
            final byte[] proxyBytes = generateProxy(superclass, classFileName, implementationMethods);
            return loadClass(classLoader, proxyName, proxyBytes);
        } catch (final Exception e) {
            throw new FactoryException(e);
        }
    }

    private static boolean hasSuitableDefaultConstructor(Class<?> superclass) {
        for (Constructor<?> constructor : superclass.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 0
                    && (Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor
                    .getModifiers()))) {
                return true;
            }
        }
        return false;
    }

    private static Class<?> validateClass(Class<?> proxyClass) {
        if (Modifier.isFinal(proxyClass.getModifiers())) {
            throw new FactoryException("Proxy class cannot extend " + proxyClass.getName()
                    + " as it is final.");
        }
        if (!hasSuitableDefaultConstructor(proxyClass)) {
            throw new FactoryException("Proxy class cannot extend " + proxyClass.getName()
                    + ", because it has no visible \"default\" constructor.");
        }
        return proxyClass;
    }

    private Method[] getImplementationMethods(Class<?> proxyClass) {
        final Set<Method> implementationMethods = new HashSet<>();
        final Method[] methods = proxyClass.getMethods();
        for (Method method : methods) {
            if (!Modifier.isFinal(method.getModifiers())) {
                implementationMethods.add(method);
            }
        }
        return implementationMethods.toArray(new Method[implementationMethods.size()]);
    }

    private byte[] generateProxy(final Class<?> classToProxy, final String proxyName,
                                 final Method[] methods) throws FactoryException {

        final Type proxyType = Type.getObjectType(proxyName);
        final Type superType = Type.getType(classToProxy);

        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        cw.visit(V1_6, ACC_PUBLIC + ACC_SUPER, proxyType.getInternalName(), null, superType.getInternalName(),
                null);

        cw.visitField(ACC_FINAL + ACC_PRIVATE, HANDLER_NAME, INVOKER_TYPE.getDescriptor(), null, null).visitEnd();

        init(cw, proxyType, superType);

        for (final Method method : methods) {
            processMethod(cw, method, proxyType, HANDLER_NAME);
        }

        return cw.toByteArray();
    }

    private void init(final ClassWriter cw, final Type proxyType, Type superType) {
        final GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, new org.objectweb.asm.commons.Method("<init>",
                Type.VOID_TYPE, new Type[]{INVOKER_TYPE}), null, null, cw);
        // invoke super constructor:
        mg.loadThis();
        mg.invokeConstructor(superType, org.objectweb.asm.commons.Method.getMethod("void <init> ()"));

        // assign handler:
        mg.loadThis();
        mg.loadArg(0);
        mg.putField(proxyType, HANDLER_NAME, INVOKER_TYPE);
        mg.returnValue();
        mg.endMethod();
    }

    private void processMethod(final ClassWriter cw, final Method method, final Type proxyType,
                               final String handlerName) throws FactoryException {
        final Type sig = Type.getType(method);
        final Type[] exceptionTypes = getTypes(method.getExceptionTypes());

        // push the method definition
        final int access = (ACC_PUBLIC | ACC_PROTECTED) & method.getModifiers();
        final org.objectweb.asm.commons.Method m = org.objectweb.asm.commons.Method.getMethod(method);
        final GeneratorAdapter generatorAdapter = new GeneratorAdapter(access, m, null, getTypes(method.getExceptionTypes()), cw);

        final Label tryBlock = exceptionTypes.length > 0 ? generatorAdapter.mark() : null;

        generatorAdapter.push(Type.getType(method.getDeclaringClass()));

        generatorAdapter.push(method.getName());

        generatorAdapter.push(sig.getArgumentTypes().length);
        final Type classType = Type.getType(Class.class);
        generatorAdapter.newArray(classType);

        for (int i = 0; i < sig.getArgumentTypes().length; i++) {
            generatorAdapter.dup();

            generatorAdapter.push(i);
            generatorAdapter.push(sig.getArgumentTypes()[i]);
            generatorAdapter.arrayStore(classType);
        }

        generatorAdapter.invokeVirtual(classType, org.objectweb.asm.commons.Method
                .getMethod("java.lang.reflect.Method getDeclaredMethod(String, Class[])"));
        generatorAdapter.loadThis();

        generatorAdapter.getField(proxyType, handlerName, INVOKER_TYPE);
        generatorAdapter.swap();

        generatorAdapter.loadThis();
        generatorAdapter.swap();

        generatorAdapter.push(sig.getArgumentTypes().length);
        final Type objectType = Type.getType(Object.class);
        generatorAdapter.newArray(objectType);

        for (int i = 0; i < sig.getArgumentTypes().length; i++) {
            generatorAdapter.dup();

            generatorAdapter.push(i);

            generatorAdapter.loadArg(i);
            generatorAdapter.valueOf(sig.getArgumentTypes()[i]);
            generatorAdapter.arrayStore(objectType);
        }

        generatorAdapter.invokeInterface(INVOKER_TYPE, org.objectweb.asm.commons.Method
                .getMethod("Object invoke(Object, java.lang.reflect.Method, Object[])"));

        generatorAdapter.unbox(sig.getReturnType());

        generatorAdapter.returnValue();

        if (exceptionTypes.length > 0) {
            final Type caughtExceptionType = Type.getType(InvocationTargetException.class);
            generatorAdapter.catchException(tryBlock, generatorAdapter.mark(), caughtExceptionType);

            final Label throwCause = new Label();

            generatorAdapter.invokeVirtual(caughtExceptionType,
                    org.objectweb.asm.commons.Method.getMethod("Throwable getCause()"));

            for (Type exceptionType : exceptionTypes) {
                generatorAdapter.dup();
                generatorAdapter.push(exceptionType);
                generatorAdapter.swap();
                generatorAdapter.invokeVirtual(classType,
                        org.objectweb.asm.commons.Method.getMethod("boolean isInstance(Object)"));
                generatorAdapter.ifZCmp(GeneratorAdapter.NE, throwCause);
            }

            final int cause = generatorAdapter.newLocal(Type.getType(Exception.class));
            generatorAdapter.storeLocal(cause);
            final Type undeclaredType = Type.getType(UndeclaredThrowableException.class);
            generatorAdapter.newInstance(undeclaredType);
            generatorAdapter.dup();
            generatorAdapter.loadLocal(cause);
            generatorAdapter.invokeConstructor(undeclaredType, new org.objectweb.asm.commons.Method("<init>", Type.VOID_TYPE,
                    new Type[]{Type.getType(Throwable.class)}));
            generatorAdapter.throwException();

            generatorAdapter.mark(throwCause);
            generatorAdapter.throwException();
        }

        generatorAdapter.endMethod();
    }

    private Type[] getTypes(Class<?>... src) {
        final Type[] result = new Type[src.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Type.getType(src[i]);
        }
        return result;
    }

    private Class<?> loadClass(final ClassLoader loader, String className, byte[] b) {
        // override classDefine (as it is protected) and define the class.
        try {
            final Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class,
                    int.class, int.class);

            // protected method invocation
            final boolean accessible = method.isAccessible();
            if (!accessible) {
                method.setAccessible(true);
            }
            try {
                return (Class<?>) method
                        .invoke(loader, className, b, 0, b.length);
            } finally {
                if (!accessible) {
                    method.setAccessible(false);
                }
            }
        } catch (Exception e) {
            throw e instanceof RuntimeException ? ((RuntimeException) e) : new RuntimeException(e);
        }
    }


}