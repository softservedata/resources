package org.registrator.community.enumLoader;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;



public class EnumLoader implements SessionAction {
	

	
	private static final Configuration configuration = new Configuration().configure();
	
    @SuppressWarnings("unchecked")
    @Override
    public void run(Session session) {
        Iterator<PersistentClass> mappingList = configuration.getClassMappings();
        while (mappingList.hasNext()) {
            PersistentClass mapping = mappingList.next();

            Class<?> clazz = mapping.getMappedClass();
            if (!SystemDictionary.class.isAssignableFrom(clazz))
                continue;
            if (!clazz.isAnnotationPresent(MappedEnum.class))
                continue;

            MappedEnum mappedEnum = clazz.getAnnotation(MappedEnum.class);
            updateEnumIdentifiers(session, mappedEnum.enumClass(), (Class<SystemDictionary>) clazz);
        }
    }



	@SuppressWarnings("unchecked")
    private void updateEnumIdentifiers(
            Session session,
            @SuppressWarnings("rawtypes") Class<? extends Enum> enumClass,
            Class<? extends SystemDictionary> entityClass) {
        List<SystemDictionary> valueList =
            (List<SystemDictionary>) session.createCriteria(entityClass).list();

        int maxId = 0;
        @SuppressWarnings("rawtypes")
        Enum[] constants = enumClass.getEnumConstants();
        Iterator<SystemDictionary> valueIterator = valueList.iterator();
        while (valueIterator.hasNext()) {
            SystemDictionary value = valueIterator.next();

            int valueId = value.getId().intValue();
            setEnumOrdinal(Enum.valueOf(enumClass, value.getCode()), valueId);
            if (valueId > maxId)
                maxId = valueId;
        }

        Object valuesArray = Array.newInstance(enumClass, maxId + 1);
        for (@SuppressWarnings("rawtypes") Enum value : constants)
            Array.set(valuesArray, value.ordinal(), value);

        Field field;
        try {
            field = enumClass.getDeclaredField("$VALUES");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, valuesArray);
        } catch (Exception ex) {
            try {
				throw new Exception("Can't update values array: ", ex);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    private void setEnumOrdinal(@SuppressWarnings("rawtypes") Enum object, int ordinal) {
        Field field;
        try {
            field = object.getClass().getSuperclass().getDeclaredField("ordinal");
            field.setAccessible(true);
            field.set(object, ordinal);
        } catch (Exception ex) {
            try {
				throw new Exception("Can't update enum ordinal: " + ex);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}