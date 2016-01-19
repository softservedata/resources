package org.registrator.community.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.registrator.community.enumLoader.MappedEnum;
import org.registrator.community.enumLoader.SystemDictionary;
import org.registrator.community.enumeration.ResourceStatus;


@Entity
@Table(name = "status")
@SequenceGenerator(name = "entityIdGenerator", sequenceName = "status_id")
@MappedEnum(enumClass = ResourceStatus.class)
public class ResourceStatusEx extends SystemDictionary {

}
