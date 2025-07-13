package uz.pdp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class MainJavaAop {
    public static void main( String[] args ){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TransformAspect.class);
        Transform transform = context.getBean(Transform.class);
        transform.start();
    }
}
