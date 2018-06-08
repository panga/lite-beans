package acme;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import lite.beans.BeanDescriptor;
import lite.beans.BeanInfo;
import lite.beans.Introspector;
import lite.beans.PropertyDescriptor;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class PersonTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void beanInfoTest() throws Exception {
        int expectedProperties = 5;
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        collector.checkThat(beanDescriptor, notNullValue());
        collector.checkThat(beanDescriptor.getName(), equalTo("Person"));
        collector.checkThat((Class) beanDescriptor.getBeanClass(), equalTo(Person.class));

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        collector.checkThat(propertyDescriptors, notNullValue());
        collector.checkThat(propertyDescriptors.length, equalTo(expectedProperties));

        Map<String, PropertyDescriptor> propsByName = new TreeMap<>();
        for (PropertyDescriptor d : propertyDescriptors) {
            propsByName.put(d.getName(), d);
        }

        collector.checkThat(propsByName.size(), equalTo(expectedProperties));

        PropertyDescriptor clazz = propsByName.get("class");
        collector.checkThat(clazz, notNullValue());
        collector.checkThat((Class) clazz.getPropertyType(), equalTo(Class.class));
        collector.checkThat(clazz.getReadMethod(), equalTo(Person.class.getMethod("getClass")));
        collector.checkThat(clazz.getWriteMethod(), nullValue());

        PropertyDescriptor name = propsByName.get("name");
        collector.checkThat(name, notNullValue());
        collector.checkThat((Class) name.getPropertyType(), equalTo(String.class));
        collector.checkThat(name.getReadMethod(), equalTo(Person.class.getMethod("getName")));
        collector.checkThat(name.getWriteMethod(), equalTo(Person.class.getMethod("setName", String.class)));

        PropertyDescriptor age = propsByName.get("age");
        collector.checkThat(age, notNullValue());
        collector.checkThat((Class) age.getPropertyType(), equalTo(int.class));
        collector.checkThat(age.getReadMethod(), equalTo(Person.class.getMethod("getAge")));
        collector.checkThat(age.getWriteMethod(), equalTo(Person.class.getMethod("setAge", int.class)));

        PropertyDescriptor alive = propsByName.get("alive");
        collector.checkThat(alive, notNullValue());
        collector.checkThat((Class) alive.getPropertyType(), equalTo(boolean.class));
        collector.checkThat(alive.getReadMethod(), equalTo(Person.class.getMethod("isAlive")));
        collector.checkThat(alive.getWriteMethod(), equalTo(Person.class.getMethod("setAlive", boolean.class)));

        PropertyDescriptor country = propsByName.get("country");
        collector.checkThat(country, notNullValue());
        collector.checkThat((Class) country.getPropertyType(), equalTo(String.class));
        collector.checkThat(country.getReadMethod(), equalTo(Person.class.getMethod("getCountry")));
        collector.checkThat(country.getWriteMethod(), equalTo(Person.class.getMethod("setCountry", String.class)));
    }

    @Test
    public void cacheTest() throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        BeanInfo beanInfoCached = Introspector.getBeanInfo(Person.class);
        BeanInfo beanInfoNew = Introspector.getBeanInfo(Person.class, Object.class);

        collector.checkThat(beanInfoCached, equalTo(beanInfo));
        collector.checkThat(beanInfoCached, not(equalTo(beanInfoNew)));

        collector.checkThat(beanInfoCached.getPropertyDescriptors()[0].equals(beanInfoNew.getPropertyDescriptors()[0]),
            is(true));
        collector.checkThat(beanInfoCached.getPropertyDescriptors()[0].equals(beanInfoNew.getPropertyDescriptors()[1]),
            is(false));

        collector.checkThat(beanInfoCached.getPropertyDescriptors()[0].hashCode(),
            equalTo(beanInfoNew.getPropertyDescriptors()[0].hashCode()));
        collector.checkThat(beanInfoCached.getPropertyDescriptors()[0].hashCode(),
            not(equalTo(beanInfoNew.getPropertyDescriptors()[1].hashCode())));
    }
}
