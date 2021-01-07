package org.andasis.app;

import org.onosproject.cfg.ComponentConfigService;
import org.onosproject.net.Device;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortStatistics;
import org.onosproject.net.statistic.FlowStatisticService;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class DeviceStatistics extends Thread {
    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * Some configurable property.
     */
    private String someProperty;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ComponentConfigService cfgService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected DeviceService deviceService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected FlowStatisticService flowStatisticService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected PortStatistics portStatistics;


    @Override
    public void run() {
        super.run();
        long beforePacket = 0;
        long packetSize = 0;
        int counter = 0;
        Calculation calculation = new Calculation();

        while (true) {
            try {
                Iterable<Device> devices = getDeviceService().getDevices();

                for (Device d : devices) {

                    List<PortStatistics> portStatisticsList = getDeviceService().getPortStatistics(d.id());
                    for (PortStatistics p : portStatisticsList) {
                        packetSize = p.bytesReceived() / 1048576;
                        System.out.println("Before :" + packetSize);
                        packetSize = Math.abs(packetSize - beforePacket);
                        System.out.println("DATA : " + packetSize);
                        if (packetSize > 0 && packetSize < 80) {
                            calculation.addGroup1(packetSize);
                        } else if (packetSize >= 80 && packetSize < 160) {
                            calculation.addGroup2(packetSize);
                        } else if (packetSize >= 160 && packetSize < 320) {
                            calculation.addGroup3(packetSize);
                        } else if (packetSize >= 320 && packetSize < 640) {
                            calculation.addGroup4(packetSize);
                        } else if (packetSize >= 640 && packetSize < 1280) {
                            calculation.addGroup5(packetSize);
                        } else if (packetSize >= 1280 && packetSize < 2560) {
                            calculation.addGroup6(packetSize);
                        } else {
                            continue;
                        }
                        beforePacket = packetSize;
                    }
                }

                counter++;
                log.info("COUNTER:" + counter);
                if (counter == 5) {
                    log.info("***********************************");
                    log.info("***********************************");
                    log.info("" + calculation.setParameters());
                    log.info("***********************************");
                    log.info("***********************************");
                    counter = 0;
                }
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public PortStatistics getPortStatistics() {
        return portStatistics;
    }

    public void setPortStatistics(PortStatistics portStatistics) {
        this.portStatistics = portStatistics;
    }
}