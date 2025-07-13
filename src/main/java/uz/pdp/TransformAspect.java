package uz.pdp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class TransformAspect {

    @Bean
    public Transform transform(){
        return new Transform();
    }

    @Bean
    public Audience audience(){
        return new Audience();
    }
}
