package com.digihealth.anesthesia.sysMng;

import java.util.Date;

import org.apache.log4j.Logger;

public class TestTask
{
    public final Logger log = Logger.getLogger(this.getClass());
    
    public void run() {
        for (int i = 0; i < 3; i++) {
            log.debug(" run......................................" + i + (new Date()));
        }

    }

    public void run1() {
        for (int i = 0; i < 5; i++) {
            log.debug(" run1......................................" + i + (new Date()));
        }
    }
}
