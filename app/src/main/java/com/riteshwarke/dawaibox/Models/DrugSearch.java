package com.riteshwarke.dawaibox.Models;

/**
 * Created by Ritesh Warke on 19/05/17.
 */

public class DrugSearch {
    private String drugId;
    private String drugName;
    private String drugType;
    private String pharmaCompName;
    private String compound;
    private String drugInteractions;
    private String drugIndications;
    private String drugContraIndications;
    private String expanded;



    public DrugSearch(String drugId, String drugName, String drugType, String pharmaCompName, String compound, String drugInteractions,String expanded) {
        this.drugId = drugId;
        this.drugType = drugType;
        this.drugName = drugName;
        this.pharmaCompName = pharmaCompName;
        this.compound = compound;
        this.drugInteractions = drugInteractions;
        this.expanded = expanded;
    }

    public String getExpanded() {
        return expanded;
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getPharmaCompName() {
        return pharmaCompName;
    }

    public void setPharmaCompName(String pharmaCompName) {
        this.pharmaCompName = pharmaCompName;
    }

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public String getDrugInteractions() {
        return drugInteractions;
    }

    public void setDrugInteractions(String drugInteractions) {
        this.drugInteractions = drugInteractions;
    }

    public String getDrugIndications() {
        return drugIndications;
    }

    public void setDrugIndications(String drugIndications) {
        this.drugIndications = drugIndications;
    }

    public String getDrugContraIndications() {
        return drugContraIndications;
    }

    public void setDrugContraIndications(String drugContraIndications) {
        this.drugContraIndications = drugContraIndications;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((drugId == null) ? 0 : drugId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DrugSearch other = (DrugSearch) obj;

        if (drugId == null) {
            if (other.drugId != null)
                return false;
        } else if (!drugId.equals(other.drugId))
            return false;
        return true;
    }
}
