package test.maksim.parcels.ws.service;

import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.ws.repository.ParcelsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest {

    @InjectMocks
    private RegisterService service;

    @Mock
    private ParcelsRepository repository;
    @Mock
    private Shipment shipment;

    @Test
    public void register() {
        service.register(shipment);

        verifyRepositoryCall();
    }

    // Util methods

    private void verifyRepositoryCall() {
        verify(repository).register(shipment);
    }
}