package com.honest.enterprise.core.utils;

import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.*;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Javassist 工具类
 * @author fanjie
 * @date 2022-07-17
 */
@Slf4j
@Data
public class JavassistUtils {
    public final static ClassPool CLASS_POOL = ClassPool.getDefault();


    /**
     * 获取类注解
     * @param classPath
     * @return
     */
    @SneakyThrows
    public static Set<String> getClassAnnotations(String classPath){
        Set<String> set=new HashSet<>();
        CtClass ctClass=getCtClass(classPath);
        ClassFile classFile=ctClass.getClassFile();

        AnnotationsAttribute attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
        Annotation[] annotations=attribute.getAnnotations();
        for (Annotation annotation:annotations){
            set.add(annotation.getTypeName());
        }

        ctClass.detach();//删除缓存

        return set;
    }

    /**
     * 获取父类
     * @param classPath
     * @return
     */
    @SneakyThrows
    public static Set<String> getSuperClass(String classPath){
        Set<String> set=new HashSet<>();
        CtClass ctClass=getCtClass(classPath);
        ClassFile classFile=ctClass.getClassFile();

        String superClass = classFile.getSuperclass();
        if (!"".equals(superClass) && superClass != null) {
            set.add(superClass);
        }

        ctClass.detach();//删除缓存

        return set;
    }

    /**
     * 获取接口
     * @param classPath
     * @return
     * @throws NotFoundException
     */
    @SneakyThrows
    public static Set<String> getInterface(String classPath){
        Set<String> set=new HashSet<>();
        CtClass ctClass=getCtClass(classPath);
        ClassFile classFile=ctClass.getClassFile();

        String[] interfaces = classFile.getInterfaces();
        if (interfaces != null) {
            for (String face : interfaces) {
                String className = getClassName(face);
                addClassName(set, className);
            }
        }

        ctClass.detach();//删除缓存

        return set;
    }

    /**
     * 获取字段类型
     * @param classPath
     * @return
     */
    @SneakyThrows
    public static Set<String> getFieldsType(String classPath){
        Set<String> set=new HashSet<>();
        CtClass ctClass=getCtClass(classPath);
        ClassFile classFile=ctClass.getClassFile();

        List<FieldInfo> fieldInfoList = classFile.getFields();
        if (fieldInfoList != null) {
            for (FieldInfo fieldInfo : fieldInfoList) {
                String descriptor = fieldInfo.getDescriptor();
                if (descriptor.startsWith("L") && descriptor.endsWith(";")) {
                    String className = descriptor.substring(1, descriptor.length() - 1);
                    className = getClassName(className);
                    addClassName(set, className);
                }

                if (descriptor.startsWith("[L") && descriptor.endsWith(";")) {
                    String className = descriptor.substring(2, descriptor.length() - 1);
                    className = getClassName(className);
                    addClassName(set, className);
                }
            }
        }

        ctClass.detach();//删除缓存

        return set;
    }


    /**
     * 获取方法声明的参数和返回值包含的所有类
     * @param classPath
     * @param methodName
     * @return
     */
    @SneakyThrows
    public static Set<String> getMethodSignatureClass(String classPath,String methodName) {
        Set<String> set=new HashSet<>();
        CtClass ctClass=getCtClass(classPath);
        ClassFile classFile=ctClass.getClassFile();

        MethodInfo methodInfo = classFile.getMethod(methodName);
        String descriptor = methodInfo.getDescriptor();
        extractClassNames(descriptor, set);

        ctClass.detach();//删除缓存

        return set;
    }

    /**
     * 通过接口类创建实现类
     * @param fullClassName
     * @param ctInc
     * @return
     */
    @SneakyThrows
    public static CtClass buildClassForInc(String fullClassName,CtClass ctInc){
        //接口CtClass
        // CtClass ctInc = CLASS_POOL.getCtClass(fullIncName);
        CtClass ctClassBack =CLASS_POOL.makeClass(fullClassName);

        //给类添加接口
        CtClass[] inc=new CtClass[]{CLASS_POOL.makeInterface(ctInc.getName())};
        ctClassBack.setInterfaces(inc);

        //给类动态追加接口的所有方法(空方法)
        makeInfMethodForClass(ctInc,ctClassBack);

        // ctClassBack.writeFile(classPath);
        return ctClassBack;
    }

    /**
     * 通过类名获取ctclass
     * @param fullClassName
     * @return
     */
    @SneakyThrows
    public static CtClass getCtClass(String fullClassName){
        CtClass ctClass =CLASS_POOL.getCtClass(fullClassName);
        //CLASS_POOL.insertClassPath(new ClassClassPath(Class.forName(fullClassName)));//为防止项目被打成jar包，请使用该语句
        return ctClass;
    }

    /**
     * 字节码保存
     * @param ctClass
     * @param classPath
     * @param createFlag 是否新创建类
     * @return
     */
    @SneakyThrows
    public static Class save(CtClass ctClass,String classPath,Boolean createFlag){
        try {
            ctClass.writeFile(classPath);
            if(createFlag) {
                return ctClass.toClass();
            }
        }catch (Exception e){
            log.error("save error:",e.getMessage(),e);

        }
        return null;

    }

    /**
     * 修改类注解
     * @param backClass
     * @param mapAnn
     * @param classPath
     * @return
     */
    @SneakyThrows
    public static CtClass modifyClassAnn(CtClass backClass, Map<String, Map<String, Object>> mapAnn,String classPath) {
        CtClass ctInc = backClass;
        ClassFile ccFile = ctInc.getClassFile();
        ConstPool constPool = ccFile.getConstPool();

        AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);

        for (Map.Entry<String, Map<String, Object>> entry : mapAnn.entrySet()) {
            //注解类全名
            String annClassName = entry.getKey();
            Map<String, Object> annValue = entry.getValue();
            javassist.bytecode.annotation.Annotation bodyAnnot = new javassist.bytecode.annotation.Annotation(annClassName, constPool);
            //循环注解值
            for (Map.Entry<String, Object> entryAnnValue : annValue.entrySet()) {
                MemberValue mv=getMemberValue(entryAnnValue.getValue(),constPool);
                if(mv!=null) {
                    bodyAnnot.addMemberValue(entryAnnValue.getKey(), mv);
                    bodyAttr.addAnnotation(bodyAnnot);
                }
            }
        }


        ccFile.addAttribute(bodyAttr);
        //ctInc.writeFile(classPath);
        return ctInc;
    }

    /**
     * 实现类生成接口定义的方法
     * @param ctInc
     * @param ctClass
     */
    @SneakyThrows
    public static void makeInfMethodForClass(CtClass ctInc,CtClass ctClass) {
        for(CtMethod m : ctInc.getDeclaredMethods()){
            String mname=m.getName();
            CtClass[] parameters=new CtClass[m.getParameterTypes().length];
            for(int i=0;i<m.getParameterTypes().length;i++){
                parameters[i]=CLASS_POOL.get(m.getParameterTypes()[i].getClass().getName());
            }
            CtClass returnType=CLASS_POOL.get(m.getReturnType().getName());
            CtMethod ctMethod = new CtMethod(returnType,mname,parameters,ctClass);
            String bodyInfo=ctInc.getName()+"_"+mname+" 服务熔断!";
            String body="{System.out.println(\""+bodyInfo+"\");return null;}";
            //String body="{log.info(\"服务熔断!\");return null;}";
            ctMethod.setBody(body);
            ctClass.addMethod(ctMethod);
        }

    }

    /**
     * 获取注解member 通过不同的类型返回不同的 member策略
     * @param annValue
     * @param constPool
     * @return
     */
    @SneakyThrows
    private static MemberValue getMemberValue(Object annValue,ConstPool constPool){
        if(annValue instanceof Class){
            String className=((Class) annValue).getTypeName();
            return new ClassMemberValue(className,constPool);
        }else if(annValue instanceof String){
            return new  StringMemberValue(annValue.toString(), constPool);
        }else if(annValue instanceof Boolean){
            return new BooleanMemberValue((Boolean.parseBoolean(annValue.toString())), constPool);
        }else if(annValue instanceof Byte){
            return new ByteMemberValue((Byte.parseByte(annValue.toString())), constPool);
        }else if(annValue instanceof Integer){
            return new IntegerMemberValue((Integer.parseInt(annValue.toString())), constPool);
        }else if(annValue instanceof Long){
            return new LongMemberValue((Long.parseLong(annValue.toString())), constPool);
        }else if(annValue instanceof Float){
            return new FloatMemberValue((Float.parseFloat(annValue.toString())), constPool);
        }else if(annValue instanceof Double){
            return new DoubleMemberValue((Double.parseDouble(annValue.toString())), constPool);
        }
        return null;
    }



    /**
     * 如果当前节点已经被访问过，不再将它添加到当前类的直接依赖中
     * @param set
     * @param className
     */
    private static void addClassName(Set<String> set, String className) {
        set.add(className);
    }

    /**
     * 路径替换
     * @param className
     * @return
     */
    private static String getClassName(String className) {
        return className.replaceAll("/", ".");
    }


    /**
     * 过滤类名
     * @param descriptor
     * @param set
     */
    private static void extractClassNames(String descriptor, Set<String> set) {
        String reg = "(L.+?;)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(descriptor);
        while (matcher.find()) {
            String className = matcher.group();
            className = className.substring(1, className.length() - 1);
            className = getClassName(className);
            addClassName(set, className);
        }
    }
}
