package acme;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import lite.beans.BeanDescriptor;
import lite.beans.BeanInfo;
import lite.beans.Introspector;
import lite.beans.PropertyDescriptor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class EmptyTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void beanInfoTest() throws Exception {
        int expectedProperties = 0;
        BeanInfo beanInfo = Introspector.getBeanInfo(Empty.class, Object.class);

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        collector.checkThat(beanDescriptor, notNullValue());
        collector.checkThat(beanDescriptor.getName(), equalTo("Empty"));
        collector.checkThat((Class) beanDescriptor.getBeanClass(), equalTo(Empty.class));

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        collector.checkThat(propertyDescriptors, notNullValue());
        collector.checkThat(propertyDescriptors.length, equalTo(expectedProperties));
    }
}
