package org.classes;

import java.util.List;

public class FlatGuy {
    private boolean isFrontArms;
    private List<FlatGuy> unlikedFlatGuys;
    public FlatGuy(){

    }
    public FlatGuy(boolean isFrontArms, List<FlatGuy> unlikedFlatGuys) {
        this.isFrontArms = isFrontArms;
        this.unlikedFlatGuys = unlikedFlatGuys;
    }

    public boolean isFrontArms() {
        return isFrontArms;
    }

    public void setFrontArms(boolean frontArms) {
        isFrontArms = frontArms;
    }

    public List<FlatGuy> getUnlikedFlatGuys() {
        return unlikedFlatGuys;
    }

    public void setUnlikedFlatGuys(List<FlatGuy> unlikedFlatGuys) {
        this.unlikedFlatGuys = unlikedFlatGuys;
    }
}
