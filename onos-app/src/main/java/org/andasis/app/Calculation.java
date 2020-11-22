package org.andasis.app;

import java.util.ArrayList;

public class Calculation implements ICalculation {
    ArrayList<Long> group1 = new ArrayList<>();
    ArrayList<Long> group2 = new ArrayList<>();
    ArrayList<Long> group3 = new ArrayList<>();
    ArrayList<Long> group4 = new ArrayList<>();
    ArrayList<Long> group5 = new ArrayList<>();
    ArrayList<Long> group6 = new ArrayList<>();
    long sumPacketCount = 0;
    double sumEntropy = 0;
    double sumGroupEntropy = 0;
    double counter = 0.0;
    double value = 0.0;

    @Override
    public void addGroup1(long packetReceived) {
        this.group1.add(packetReceived);
    }

    @Override
    public void addGroup2(long packetReceived) {
        this.group2.add(packetReceived);
    }

    @Override
    public void addGroup3(long packetReceived) {
        this.group3.add(packetReceived);
    }

    @Override
    public void addGroup4(long packetReceived) {
        this.group4.add(packetReceived);
    }

    @Override
    public void addGroup5(long packetReceived) {
        this.group5.add(packetReceived);
    }

    @Override
    public void addGroup6(long packetReceived) {
        this.group6.add(packetReceived);
    }

    @Override
    public void calculateEntropy(ArrayList<Long> group) {
        for (int i = 0; i < group.size(); i++) {
            counter = 1;
            for (int j = i + 1; j < group.size(); j++) {
                if (group.get(i) == group.get(j)) {
                    counter++;
                }
            }

            value = (counter / Double.parseDouble(String.valueOf(group.size())));
            sumEntropy += (value) * Math.log(value) * (-1);
        }
        sumGroupEntropy += sumEntropy;
        sumEntropy = 0;
    }

    public double setParameters() {
        calculateEntropy(group1);
        calculateEntropy(group2);
        calculateEntropy(group3);
        calculateEntropy(group4);
        calculateEntropy(group5);
        calculateEntropy(group6);
        group1.clear();
        group2.clear();
        group3.clear();
        group4.clear();
        group5.clear();
        group6.clear();
        return sumGroupEntropy;
    }
}
