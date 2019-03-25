/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author admin
 */
public class MyTaskExecutor {
    ScheduledExecutorService excuExecutorService = Executors.newScheduledThreadPool(1);
    MyTask myTask;
    volatile boolean isStopped;

    public MyTaskExecutor(MyTask myTask) {
        this.myTask = myTask;
    }
    public void startExecutionAt(int targetHour, int targetMin, int targetSec){
        Runnable taskWrapper = new Runnable() {
            @Override
            public void run() {
                myTask.execute();
                startExecutionAt(targetHour, targetMin, targetSec);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        long delay = computeNextDelay(targetHour, targetMin, targetSec);
        excuExecutorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
    }
    private long computeNextDelay(int targetHour, int targetMin, int targetSec){
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zoneNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zoneNexttarget = zoneNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
        if (zoneNow.compareTo(zoneNexttarget) > 0){
            zoneNexttarget = zoneNexttarget.plusDays(1);
        }   
            Duration duration = Duration.between(zoneNow, zoneNexttarget);
            
        return duration.getSeconds();
    }
    public void stop(){
        excuExecutorService.shutdown();
        try {
            excuExecutorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
