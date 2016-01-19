package org.registrator.community.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.registrator.community.enumLoader.MappedEnum;
import org.registrator.community.enumLoader.SystemDictionary;
import org.registrator.community.enumeration.RoleType;

@Entity
@Table(name = "type")
@SequenceGenerator(name = "entityIdGenerator", sequenceName = "type_id")
@MappedEnum(enumClass = RoleType.class)
public class RoleTypeEx extends SystemDictionary {

}
