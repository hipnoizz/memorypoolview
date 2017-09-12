/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodewerk.visualvm.memorypoolview;

import com.kodewerk.visualvm.memorypoolview.gc.arithmetic.Analytics;
import com.sun.tools.visualvm.tools.jmx.JmxModel;
import java.io.IOException;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;

/**
 *
 * @author kirk
 */
public class MemoryPoolAnalyticModel extends MemoryPoolModel {
    
    public static final int MOVING_AVERAGE_PERIOD = 10;
    
    private Analytics movingAverage;
    
    public MemoryPoolAnalyticModel(final ObjectName mbeanName, final JmxModel model, final MBeanServerConnection mbeanServerConnection) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
        super(mbeanName, model, mbeanServerConnection);
        movingAverage = new Analytics(MOVING_AVERAGE_PERIOD);
    }
    
    public long getMovingAverage() {
        return (long)movingAverage.getSimpleMomentum();
    }
    
    @Override
    protected void tickleListeners() {
        movingAverage.add(getUsed());
        super.tickleListeners();
    }    
}
