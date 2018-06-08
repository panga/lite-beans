package acme;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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

public class CarTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void beanInfoTest() throws Exception {
        int expectedProperties = 2;
        BeanInfo beanInfo = Introspector.getBeanInfo(Car.class, Object.class);

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        collector.checkThat(beanDescriptor, notNullValue());
        collector.checkThat(beanDescriptor.getName(), equalTo("Car"));
        collector.checkThat((Class) beanDescriptor.getBeanClass(), equalTo(Car.class));

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        collector.checkThat(propertyDescriptors, notNullValue());
        collector.checkThat(propertyDescriptors.length, equalTo(expectedProperties));

        Map<String, PropertyDescriptor> propsByName = new TreeMap<>();
        for (PropertyDescriptor d : propertyDescriptors) {
            propsByName.put(d.getName(), d);
        }

        collector.checkThat(propsByName.size(), equalTo(expectedProperties));

        PropertyDescriptor description = propsByName.get("description");
        collector.checkThat(description, notNullValue());
        collector.checkThat((Class) description.getPropertyType(), equalTo(String.class));
        collector.checkThat(description.getReadMethod(), equalTo(Car.class.getMethod("getDescription")));
        collector.checkThat(description.getWriteMethod(), nullValue());

        PropertyDescriptor owner = propsByName.get("owner");
        collector.checkThat(owner, notNullValue());
        collector.checkThat((Class) owner.getPropertyType(), equalTo(String.class));
        collector.checkThat(owner.getReadMethod(), nullValue());
        collector.checkThat(owner.getWriteMethod(), equalTo(Car.class.getMethod("setOwner", String.class)));

        collector.checkThat(description.equals(description), is(true));
        collector.checkThat(owner.equals(description), is(false));
    }
}
