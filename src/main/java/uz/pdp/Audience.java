package uz.pdp;

import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    @Before("execution(* uz.pdp.Performance.perform())")
    public void silencingPhones(){
        System.out.println("Silencing Phones");
    }

    @Before("execution(* uz.pdp.Performance.perform())")
    public void takeSeats(){
        System.out.println("TAke seats");
    }

    @AfterReturning("execution(* uz.pdp.Performance.perform())")
    public void applause(){
        System.out.println("Clap clap \uD83D\uDC4F\uD83D\uDC4F");
    }

    @AfterThrowing("execution(* uz.pdp.Performance.perform())")
    public void refund(){
        System.out.println("Refund \uD83D\uDE08\uD83D\uDE08");
    }
}
