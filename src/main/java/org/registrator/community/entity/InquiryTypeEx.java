package org.registrator.community.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.registrator.community.enumLoader.MappedEnum;
import org.registrator.community.enumLoader.SystemDictionary;
import org.registrator.community.enumeration.InquiryType;


@Entity
@Table(name = "inquiryType")
@SequenceGenerator(name = "entityIdGenerator", sequenceName = "inquiry_type")
@MappedEnum(enumClass = InquiryType.class)
public class InquiryTypeEx extends SystemDictionary {

}
