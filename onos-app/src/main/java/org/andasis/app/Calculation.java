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
    public double calculateEntropy(ArrayList<Long> group) {
        for (int i = 0; i < group.size(); i++) {
            counter = 1;
            sumEntropy = 0;
            for (int j = i + 1; j < group.size(); j++) {
                if (group.get(i) == group.get(j)) {
                    counter++;
                }
            }

            value = (counter / Double.parseDouble(String.valueOf(group.size())));
            sumEntropy += (value) * (Math.log10(value) / Math.log10(2)) * (-1);
            System.out.println("SUMENTROPY:" + sumEntropy);
        }
        return sumEntropy;
    }

    public double setParameters() {
        sumGroupEntropy = 0;
        sumGroupEntropy += calculateEntropy(group1);
        sumGroupEntropy += calculateEntropy(group2);
        sumGroupEntropy += calculateEntropy(group3);
        sumGroupEntropy += calculateEntropy(group4);
        sumGroupEntropy += calculateEntropy(group5);
        sumGroupEntropy += calculateEntropy(group6);
        for (long data : group1) {
            System.out.println("GROUP 1 DATA : " + data);
        }
        for (long data : group2) {
            System.out.println("GROUP 2 DATA : " + data);
        }
        for (long data : group3) {
            System.out.println("GROUP 3 DATA : " + data);
        }
        for (long data : group4) {
            System.out.println("GROUP 4 DATA : " + data);
        }
        for (long data : group5) {
            System.out.println("GROUP 5 DATA : " + data);
        }
        for (long data : group6) {
            System.out.println("GROUP 6 DATA : " + data);
        }
        group1.clear();
        group2.clear();
        group3.clear();
        group4.clear();
        group5.clear();
        group6.clear();
        return sumGroupEntropy;
    }
}
