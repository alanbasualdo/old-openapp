package com.opencars.netgo.users.dto;

import com.opencars.netgo.locations.entity.SubSector;

public class LineSubsector {

    private int line;
    private SubSector subSector;

    public LineSubsector() {
    }

    public LineSubsector(int line, SubSector subSector) {
        this.line = line;
        this.subSector = subSector;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public SubSector getSubSector() {
        return subSector;
    }

    public void setSubSector(SubSector subSector) {
        this.subSector = subSector;
    }
}
