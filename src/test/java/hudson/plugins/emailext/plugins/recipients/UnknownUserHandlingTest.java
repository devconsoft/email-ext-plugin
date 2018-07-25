package hudson.plugins.emailext.plugins.recipients;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import hudson.plugins.emailext.ExtendedEmailPublisherDescriptor;
import jenkins.model.Jenkins;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
    UnknownUserHandling.class,
    Jenkins.class
})
public class UnknownUserHandlingTest {

    @Test
    public void getAlwaysSendToUnknownUsersSystemPropertyFlag() throws Exception {
        PowerMockito.mockStatic(UnknownUserHandling.class);

        Mockito.when(UnknownUserHandling.getAlwaysSendToUnkownUsersSystemPropertyFlag()).thenReturn(true);
        assertTrue(UnknownUserHandling.getAlwaysSendToUnkownUsersSystemPropertyFlag());

        Mockito.when(UnknownUserHandling.getAlwaysSendToUnkownUsersSystemPropertyFlag()).thenReturn(false);
        assertFalse(UnknownUserHandling.getAlwaysSendToUnkownUsersSystemPropertyFlag());
    }

    @Test
    public void sendToUnknownUserPatternMatchReturnTrueOnMatch() throws Exception {
        final Jenkins jenkins = PowerMockito.mock(Jenkins.class);
        final ExtendedEmailPublisherDescriptor extendedEmailPublisherDescriptor = PowerMockito.mock(ExtendedEmailPublisherDescriptor.class);
        PowerMockito.when(extendedEmailPublisherDescriptor.getSendToUnknownUserPattern()).thenReturn(".*");
        PowerMockito.when(jenkins.getDescriptorByType(ExtendedEmailPublisherDescriptor.class)).thenReturn(extendedEmailPublisherDescriptor);
        PowerMockito.mockStatic(Jenkins.class);
        PowerMockito.when(Jenkins.getActiveInstance()).thenReturn(jenkins);

    }

}
