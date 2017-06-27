package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

/**
 * Created by mkostiuk on 26/06/2017.
 */

@UpnpService(
        serviceId = @UpnpServiceId("AdaptedCommand"),
        serviceType = @UpnpServiceType(value = "AdaptedCommand", version = 1)
)
public class AdaptedCommandService {

    private final PropertyChangeSupport propertyChangeSupport;

    public AdaptedCommandService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(name = "AdaptedCommand")
    private String adaptedCommand = "";

    @UpnpAction(name = "SetAdaptedCommand")
    public void setAdaptedCommand(@UpnpInputArgument(name = "AdaptedCommand") String ac) {
        adaptedCommand = ac;
        getPropertyChangeSupport().firePropertyChange("AdaptedCommand", "", adaptedCommand);
    }
}
