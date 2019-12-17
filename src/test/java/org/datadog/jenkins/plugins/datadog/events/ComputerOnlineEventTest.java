package org.datadog.jenkins.plugins.datadog.events;

import hudson.model.*;
import org.datadog.jenkins.plugins.datadog.DatadogEvent;
import org.datadog.jenkins.plugins.datadog.DatadogUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DatadogUtilities.class})
public class ComputerOnlineEventTest {

    @Test
    public void testWithNothingSet() throws IOException, InterruptedException {
        PowerMockito.mockStatic(DatadogUtilities.class);
        when(DatadogUtilities.currentTimeMillis()).thenReturn(0l);
        when(DatadogUtilities.getHostname(any(String.class))).thenReturn(null);
        when(DatadogUtilities.getNodeName(any(Computer.class))).thenReturn(null);

        DatadogEvent event = new ComputerOnlineEventImpl(null, null, null, false);

        Assert.assertTrue(event.getHost() == null);
        Assert.assertTrue(event.getDate() == 0);
        Assert.assertTrue(event.getAggregationKey() == null);
        Assert.assertTrue(event.getTags() == null);
        Assert.assertTrue(event.getTitle().equals("Jenkins node null is online"));
        Assert.assertTrue(event.getText().contains("Jenkins node null is online"));
        Assert.assertTrue(event.getAlertType().equals(DatadogEvent.AlertType.SUCCESS));
        Assert.assertTrue(event.getPriority().equals(DatadogEvent.Priority.LOW));

        event = new ComputerOnlineEventImpl(null, null, null, true);

        Assert.assertTrue(event.getHost() == null);
        Assert.assertTrue(event.getDate() == 0);
        Assert.assertTrue(event.getAggregationKey() == null);
        Assert.assertTrue(event.getTags() == null);
        Assert.assertTrue(event.getTitle().equals("Jenkins node null is temporarily online"));
        Assert.assertTrue(event.getText().contains("Jenkins node null is temporarily online"));
        Assert.assertTrue(event.getAlertType().equals(DatadogEvent.AlertType.SUCCESS));
        Assert.assertTrue(event.getPriority().equals(DatadogEvent.Priority.LOW));
    }

    @Test
    public void testWithEverythingSet() throws IOException, InterruptedException {
        PowerMockito.mockStatic(DatadogUtilities.class);
        when(DatadogUtilities.currentTimeMillis()).thenReturn(System.currentTimeMillis());
        when(DatadogUtilities.getHostname(any(String.class))).thenReturn("hostname");
        when(DatadogUtilities.getNodeName(any(Computer.class))).thenReturn("computer");

        DatadogEvent event = new ComputerOnlineEventImpl(null, null, new HashMap<String, Set<String>>(), false);

        Assert.assertTrue(event.getHost().equals("hostname"));
        Assert.assertTrue(event.getDate() != 0);
        Assert.assertTrue(event.getAggregationKey().equals("computer"));
        Assert.assertTrue(event.getTags() != null);
        Assert.assertTrue(event.getTitle().equals("Jenkins node computer is online"));
        Assert.assertTrue(event.getText().contains("Jenkins node computer is online"));
        Assert.assertTrue(event.getAlertType().equals(DatadogEvent.AlertType.SUCCESS));
        Assert.assertTrue(event.getPriority().equals(DatadogEvent.Priority.LOW));

        event = new ComputerOnlineEventImpl(null, null, new HashMap<String, Set<String>>(), true);

        Assert.assertTrue(event.getHost().equals("hostname"));
        Assert.assertTrue(event.getDate() != 0);
        Assert.assertTrue(event.getAggregationKey().equals("computer"));
        Assert.assertTrue(event.getTags() != null);
        Assert.assertTrue(event.getTitle().equals("Jenkins node computer is temporarily online"));
        Assert.assertTrue(event.getText().contains("Jenkins node computer is temporarily online"));
        Assert.assertTrue(event.getAlertType().equals(DatadogEvent.AlertType.SUCCESS));
        Assert.assertTrue(event.getPriority().equals(DatadogEvent.Priority.LOW));
    }
}