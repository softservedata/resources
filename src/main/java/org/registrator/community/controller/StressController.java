package org.registrator.community.controller;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import org.registrator.community.dao.AddressRepository;
import org.registrator.community.dao.AreaRepository;
import org.registrator.community.dao.CommunityRepository;
import org.registrator.community.dao.PassportRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.RoleRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.ResourceTypeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/stresstest/")
public class StressController {
    @Autowired
    Logger logger;
    @Autowired
    private ResourceTypeService resourceTypeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private RoleRepository roleRepository1;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private ResourceDiscreteValueRepository resourceDiscreteValueRepository;
    @Autowired
    private PolygonRepository polygonRepository;
    @Autowired
    private TomeRepository tomeRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PassportRepository passportRepository;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String addStressForm(Model model) {
        return "stress";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String startTesting(Model model) {

        TerritorialCommunity territorialCommunity = new TerritorialCommunity();
        territorialCommunity.setName("Тестова громада");
        communityRepository.save(territorialCommunity);
        Role userRole = roleRepository1.findRoleByType(RoleType.USER);
        Role registratorRole = roleRepository1.findRoleByType(RoleType.REGISTRATOR);

        User user = new User("user", "$2a$10$KJdq1wmP3MctLh.lEdAuseUCnSRdhJo8S7qwaZHFEUoGhfjOsOnrm", userRole, "Іван",
                "Головатий", "Сергійович", "userr@gmail.com", "ACTIVE");
        user.setDateOfAccession(new Date());
        user.setTerritorialCommunity(territorialCommunity);
        userRepository.save(user);
        User registrator = new User("registrator2", "$2a$10$KJdq1wmP3MctLh.lEdAuseUCnSRdhJo8S7qwaZHFEUoGhfjOsOnrm",
                registratorRole, "Євген", "Михалкевич", "Сергійович", "registratorr@gmail.com", "ACTIVE");
        registrator.setDateOfAccession(new Date());
        registrator.setTerritorialCommunity(territorialCommunity);
        userRepository.save(registrator);

        Address userAddress = new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31");
        PassportInfo userPassportInfo = new PassportInfo(user, "AA", "00000", "Народом України");
        Address registratorAddress = new Address(registrator, "00000", "Львівська", "Галицький", "Львів", "Вітовського",
                "48", "31");
        PassportInfo registratorPassportInfo = new PassportInfo(registrator, "AA", "00000", "Народом України");
        addressRepository.save(userAddress);
        passportRepository.save(userPassportInfo);
        addressRepository.save(registratorAddress);
        passportRepository.save(registratorPassportInfo);

        Tome tome = new Tome();
        tome.setIdentifier("332332");
        tome.setRegistrator(registrator);
        tomeRepository.save(tome);

        ResourceType resourceType = resourceTypeService.findByName("земельний");
        DiscreteParameter dis1 = resourceType.getDiscreteParameters().get(0);
        DiscreteParameter dis2 = resourceType.getDiscreteParameters().get(1);

        System.out.println("DIS 1" + dis1);
        System.out.println("DIS 2" + dis2);

        for (int i = 0; i < 20; i++) {
            Resource resource = new Resource();
            resource.setType(resourceType);
            resource.setIdentifier(String.valueOf(i)); // counter for IDENTIFIER
            resource.setDescription("desc");
            resource.setRegistrator(registrator);
            resource.setDate(new Date());
            resource.setStatus(ResourceStatus.ACTIVE);
            resource.setTome(tome);
            resource.setReasonInclusion("passport");
            resourceRepository.save(resource);

            System.out.println("RESOURCE" + resource.toString());

            ResourceDiscreteValue rdv1 = new ResourceDiscreteValue();
            rdv1.setDiscreteParameter(dis1);// PERIMETER!!
            rdv1.setResource(resource);
            rdv1.setValue(10.0);
            resourceDiscreteValueRepository.saveAndFlush(rdv1);
            System.out.println("RDV1" + rdv1.toString());// hashcode
            System.out.println(rdv1 != null);

            /*
             * ResourceDiscreteValue rdv2 = new ResourceDiscreteValue();
             * rdv1.setDiscreteParameter(dis2);// SQUARE
             * rdv2.setResource(resource); rdv2.setValue(10.0);
             * 
             * resourceDiscreteValueRepository.save(rdv2);
             */

            Polygon polygon = new Polygon();
            polygon.setMaxLat(50.0);
            polygon.setMaxLng(34.0);
            polygon.setMinLat(49.0);
            polygon.setMinLng(33.0);
            polygon.setResource(resource);
            polygonRepository.save(polygon);
            System.out.println("POLIGON" + polygon.toString());

            /*
             * change j
             */
            for (int j = 1; j < 4; j++) {
                Area area = new Area();

                Double min1 = 48.5;
                Double max1 = 51.16666666666667;
                Double min2 = 24.33333333333333;
                Double max2 = 34.33333333333333;
                Double randomLatitude = ThreadLocalRandom.current().nextDouble(min1, max1);
                Double randomLongitude = ThreadLocalRandom.current().nextDouble(min2, max2);

                area.setLatitude(randomLatitude);
                area.setLongitude(randomLongitude);

                System.out.println(randomLatitude);
                System.out.println(randomLongitude);

                area.setOrderNumber(j);
                area.setPolygon(polygon);

                System.out.println("AREA" + area.toString());
                System.out.println(area != null);
                areaRepository.save(area);
            }

        }
        return "stress";
    }
}