package com.irit.upnp;

import com.irit.xml.GenerateurXml;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by mkostiuk on 26/06/2017.
 */
public class ArduinoAdapterServer implements Runnable {

    private LocalService<AdaptedCommandService> adaptaterCommandService;
    private LocalService<ArduinoCommandService> arduinoCommandService;

    @Override
    public void run() {
        try {

            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(
                    createDevice()
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    public LocalDevice createDevice()
            throws LocalServiceBindingException, IOException, org.fourthline.cling.model.ValidationException {

        DeviceIdentity identity =
                new DeviceIdentity(
                        UDN.uniqueSystemIdentifier("Arduino Adapter")
                );

        DeviceType type =
                new UDADeviceType("Vote", 1);

        DeviceDetails details =
                new DeviceDetails(
                        "Arduino Adapter",                    // Friendly Name
                        new ManufacturerDetails(
                                "UPS-IRIT",                                // Manufacturer
                                ""),                                // Manufacturer URL
                        new ModelDetails(
                                "ArduinoAdapter",                        // Model Name
                                "Composant qui adapte la communication entre Arduino et les autres composants",    // Model Description
                                "v1"                                // Model Number
                        )
                );
        arduinoCommandService =
                new AnnotationLocalServiceBinder().read(ArduinoCommandService.class);
        arduinoCommandService.setManager(
                new DefaultServiceManager(arduinoCommandService, ArduinoCommandService.class)
        );

        adaptaterCommandService =
                new AnnotationLocalServiceBinder().read(AdaptedCommandService.class);
        adaptaterCommandService.setManager(
                new DefaultServiceManager<>(adaptaterCommandService, AdaptedCommandService.class)
        );

        arduinoCommandService.getManager().getImplementation().getPropertyChangeSupport().addPropertyChangeListener(
                evt -> {
                    if (evt.getPropertyName().equals("ArduinoCommand")) {
                        try {
                            adaptaterCommandService.getManager().getImplementation()
                                    .setAdaptedCommand(new GenerateurXml().getDocXml(identity.getUdn().toString(),
                                            (String) evt.getNewValue()));
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (TransformerException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        return new LocalDevice(
                identity, type, details,
                new LocalService[]{arduinoCommandService, adaptaterCommandService}
        );
    }
}
