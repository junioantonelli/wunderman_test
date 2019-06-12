package com.wundermancommerce.interviewtests.graph;

import java.util.Arrays;
import java.util.List;

public class Relationship {
    private String relationship;
    private List<String> emailPersons;

    public Relationship(List<String> emailPersons, String relationship) {
        this.relationship = relationship;
        this.emailPersons = emailPersons;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public List<String> getEmailPersons() {
        return emailPersons;
    }

    public void setEmailPersons(List<String> emailPersons) {
        this.emailPersons = emailPersons;
    }

    @Override
    public int hashCode() {
        if(this.emailPersons != null && this.relationship != null)
            return (this.emailPersons.get(0)+this.emailPersons.get(1)+this.relationship).hashCode();
        return super.hashCode();
    }

    @Override
    public String toString() {
        return this.getRelationship();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Relationship){
            Relationship r2 = (Relationship)obj;
            if(this.relationship.equals(r2.relationship)){
                for(String email:this.emailPersons){
                    if(Arrays.binarySearch(r2.emailPersons.toArray(),email) == -1)
                        return false;
                }
                return true;
            }
        }
        return false;
    }
}
