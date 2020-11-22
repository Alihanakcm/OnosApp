package org.andasis.app;


import java.util.ArrayList;

public interface ICalculation {

    void addGroup1(long packetReceived);

    void addGroup2(long packetReceived);

    void addGroup3(long packetReceived);

    void addGroup4(long packetReceived);

    void addGroup5(long packetReceived);

    void addGroup6(long packetReceived);

    void calculateEntropy(ArrayList<Long> group);

    double setParameters();
}
