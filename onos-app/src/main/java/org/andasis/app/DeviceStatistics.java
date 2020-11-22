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
        long packetCount = 0;
        int counter = 0;
        Calculation calculation = new Calculation();
        while (true) {
            try {
                Iterable<Device> devices = getDeviceService().getDevices();
                log.info("-----------------");
                log.info("| Cihaz sayisi:" + getDeviceService().getDeviceCount() + " |");
                log.info("-----------------");

                for (Device d : devices) {

                    log.info(String.valueOf(d.id()));
                    List<PortStatistics> portStatisticsList = getDeviceService().getPortStatistics(d.id());

                    for (PortStatistics p : portStatisticsList) {
                        log.info(String.valueOf(p.portNumber()));

                        log.info("cihaz:" + d.id() + " Port:" + p.portNumber() + " packetRec:" +
                                p.packetsReceived());
//                        log.info("FARK:" + (p.packetsReceived() - packetCount));
//                        packetCount = p.packetsReceived();
                        if (p.packetsReceived() > 0 && p.packetsReceived() < 80) {
                            calculation.addGroup1(p.packetsReceived());
                        } else if (p.packetsReceived() >= 80 && p.packetsReceived() < 160) {
                            calculation.addGroup2(p.packetsReceived());
                        } else if (p.packetsReceived() >= 160 && p.packetsReceived() < 320) {
                            calculation.addGroup3(p.packetsReceived());
                        } else if (p.packetsReceived() >= 320 && p.packetsReceived() < 640) {
                            calculation.addGroup4(p.packetsReceived());
                        } else if (p.packetsReceived() >= 640 && p.packetsReceived() < 1280) {
                            calculation.addGroup5(p.packetsReceived());
                        } else if (p.packetsReceived() >= 1280 && p.packetsReceived() < 2560) {
                            calculation.addGroup6(p.packetsReceived());
                        } else {
                            continue;
                        }
                    }
//                    log.info("\n");
                }

                counter++;
                log.info("COUNTER:" + counter);
                if (counter == 10) {
                    log.info("***********************************");
                    log.info("***********************************");
                    log.info("RESULT:" + calculation.setParameters());
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