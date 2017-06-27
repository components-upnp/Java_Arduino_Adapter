package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

/**
 * Created by mkostiuk on 26/06/2017.
 */

@UpnpService(
        serviceType = @UpnpServiceType(value = "ArduinoCommandService", version = 1),
        serviceId = @UpnpServiceId("ArduinoCommandService")
)
public class ArduinoCommandService {

    private final PropertyChangeSupport propertyChangeSupport;

    public ArduinoCommandService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(name = "ArduinoCommand")
    private String arduinoCommand = "";

    @UpnpAction(name = "SetArduinoCommand")
    public void setArduinoCommand(@UpnpInputArgument(name = "ArduinoCommand") String ac) {
        arduinoCommand = ac;
        getPropertyChangeSupport().firePropertyChange("ArduinoCommand", "", arduinoCommand);
    }
}
