package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by Saikiran Chepuri on 26-Apr-16.
 */
@Entity
public class RCMPreference extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    private Set<SchedulingPreference> schedulingPreference;

    private Set<PatientCancellationPreference> patientCancellationPreferences;

    private Set<LabCancellationPreference> labCancellationPreferences;

    private Set<PatientReschedulingPreference> patientReschedulingPreferences;

    private Set<LabReschedulingPreference> labReschedulingPreferences;

    @OneToMany(targetEntity = SchedulingPreference.class, fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    public Set<SchedulingPreference> getSchedulingPreference(){
        return schedulingPreference;
    }
    public void setSchedulingPreference(Set<SchedulingPreference> schedulingPreference){
        this.schedulingPreference = schedulingPreference;
    }

    @OneToMany(targetEntity = PatientCancellationPreference.class,fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    public Set<PatientCancellationPreference> getPatientCancellationPreferences() {
        return patientCancellationPreferences;
    }

    public void setPatientCancellationPreferences(Set<PatientCancellationPreference> patientCancellationPreferences) {
        this.patientCancellationPreferences = patientCancellationPreferences;
    }

    @OneToMany(targetEntity = LabCancellationPreference.class, fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    public Set<LabCancellationPreference> getLabCancellationPreferences() {
        return labCancellationPreferences;
    }

    public void setLabCancellationPreferences(Set<LabCancellationPreference> labCancellationPreferences) {
        this.labCancellationPreferences = labCancellationPreferences;
    }

    @OneToMany(targetEntity = PatientReschedulingPreference.class, fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    public Set<PatientReschedulingPreference> getPatientReschedulingPreferences() {
        return patientReschedulingPreferences;
    }

    public void setPatientReschedulingPreferences(Set<PatientReschedulingPreference> patientReschedulingPreferences) {
        this.patientReschedulingPreferences = patientReschedulingPreferences;
    }

    @OneToMany(targetEntity = LabReschedulingPreference.class, fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    public Set<LabReschedulingPreference> getLabReschedulingPreferences() {
        return labReschedulingPreferences;
    }

    public void setLabReschedulingPreferences(Set<LabReschedulingPreference> labReschedulingPreferences) {
        this.labReschedulingPreferences = labReschedulingPreferences;
    }

    public static enum RCMVisitType {
        LAB_SERVICES("Lab Services"),HOME_PHLEBOTOMY("Home Phlebotomy");

        private String description;

        private RCMVisitType(String description){
            this.description = description;
        }

        public String getDescription(){
            return description;
        }

        public void setDescription(String description){
            this.description = description;
        }

    }
}
