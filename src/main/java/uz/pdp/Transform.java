package uz.pdp;

import java.util.Random;

public class Transform {

    public void start(){
        if (new Random().nextBoolean()) {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Start returning"+i);
            }
        }else {
            throw new RuntimeException("Started bad");
        }
    }
}
