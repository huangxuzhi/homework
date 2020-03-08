import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Service;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author huangxz
 * @date ${date}
 * @description
 */
@Service
public class TestScanner {

    @Autowired
    private String val;

    @Test
    public void testScanner() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));

        for (BeanDefinition bd : scanner.findCandidateComponents("com.lagou.edu"))
            System.out.println(bd.getBeanClassName());
    }

    @Test
    public void testMethodAnnotation() {
        Method[] methods = TestScanner.class.getMethods();
        for (Method method : methods) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation a : declaredAnnotations) {
                System.out.println(a);
            }
        }
    }

    @Test
    public void testClassAnnotation() {
        Service annotation = TestScanner.class.getAnnotation(Service.class);
        if (annotation == null) {
            System.out.println("no annotation");
        } else {
            System.out.println(annotation);
        }
    }

    @Test
    public void testClassAnnotation2() {
        Annotation[] annotations = TestScanner.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }

    @Test
    public void testFieldAnnotation() throws NoSuchFieldException {
        Field val = TestScanner.class.getDeclaredField("val");
        Autowired annotation = val.getAnnotation(Autowired.class);
        System.out.println(annotation);
    }
}
