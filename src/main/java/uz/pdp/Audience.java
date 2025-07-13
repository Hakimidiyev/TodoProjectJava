package uz.pdp;

import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    @Before("execution(* uz.pdp.Transform.start())")
    public void currencyBefore(){
        System.out.println("Currency Before \uD83D\uDCE5\uD83D\uDCE5\uD83D\uDCE5");
    }

    @After("execution(* uz.pdp.Transform.start())")
    public void returnTransform(){
        System.out.println("Returning transform ✅✅✅");
    }


    @AfterReturning("execution(* uz.pdp.Transform.start())")
    public void currencyAfter(){
        System.out.println("Currency After ⛏⛏⛏");
    }

    @AfterThrowing("execution(* uz.pdp.Transform.start())")
    public void throwingCurrency(){
        System.out.println("throw currency \uD83D\uDEB7\uD83D\uDEB7\uD83D\uDEB7");
    }

}
