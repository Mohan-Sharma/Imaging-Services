package com.nzion.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.Location;
import com.nzion.domain.PasswordPolicy;
import com.nzion.domain.Practice;
import com.nzion.domain.PracticeUserAgreement;
import com.nzion.domain.UserLogin;
import com.nzion.repository.PracticeRepository;
import com.nzion.service.PasswordPolicyService;
import com.nzion.service.PracticeService;
import com.nzion.service.exceptions.ServiceException;
import com.nzion.view.PracticeViewObject;

@Transactional(propagation = Propagation.REQUIRED)
public class PracticeServiceImpl implements PracticeService {

    private PracticeRepository practiceRepository;
    private Set<String> defaultSecretQuestion;
    private PasswordPolicyService passwordPolicyService;

    private final List<TimeZone> timeZones = new ArrayList<TimeZone>();

    public PracticeServiceImpl() {
        for (String timeZoneId : TimeZone.getAvailableIDs()) {
            timeZones.add(TimeZone.getTimeZone(timeZoneId));
        }
        Collections.sort(timeZones, new Comparator<TimeZone>() {

            @Override
            public int compare(TimeZone o1, TimeZone o2) {
                return o1.getDisplayName().compareTo(o2.getDisplayName());
            }
        });
    }

    @Resource(name = "passwordPolicyService")
    public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
        this.passwordPolicyService = passwordPolicyService;
    }

    public List<TimeZone> getTimeZones() {
        return timeZones;
    }

    public void setDefaultSecretQuestion(Set<String> defaultSecretQuestion) {
        this.defaultSecretQuestion = defaultSecretQuestion;
    }

    public void updatePractice(PracticeViewObject practiceVo) throws ServiceException {
        Practice practice = practiceVo.getPractice();
        practiceRepository.merge(practice);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void save(PracticeViewObject practiceVo) throws ServiceException {
        Practice practice = practiceVo.getPractice();
        UserLogin userLogin = practiceVo.getUserLogin();
        Location loc = new Location();
        loc.setLocationCode("Main Location");
        loc.setContacts(practice.getContacts());
        practiceRepository.save(loc);
        userLogin.getPerson().setLocations(new HashSet(Arrays.asList(loc)));
        practice.setSecretQuestions(defaultSecretQuestion);
        practiceRepository.save(userLogin);
        practice.setAdminUserLogin(userLogin);
        practiceRepository.save(practice);
    }

    public Practice getPractice(Long practiceId) {
        return practiceRepository.findByPrimaryKey(Practice.class, practiceId);
    }

    public void save(Location location) {
        practiceRepository.save(location);
    }

    @Resource(name = "practiceRepo")
    public void setPracticeRepository(PracticeRepository practiceRepository) {
        this.practiceRepository = practiceRepository;
    }

    public Location getLocation(Long locationId) {
        return practiceRepository.findByPrimaryKey(Location.class, locationId);
    }

    public Location getLocation(String locationCode) {
        return practiceRepository.getLocation(locationCode);
    }

    public List<Practice> getAllPractice() {
        return practiceRepository.getAll(Practice.class);
    }

    public PracticeUserAgreement getTermsAndConditionsForPractice(Practice practice) {
        return practiceRepository.getTermsAndConditionsForPractice(practice);
    }

    public List<Location> getLocationsFor(Practice practice) {
        return practiceRepository.getLocationsFor(practice);
    }
}