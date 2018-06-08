package acme;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

import lite.beans.Introspector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class DecaptalizeTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void decaptalizeTest() {
        collector.checkThat(Introspector.decapitalize(null), nullValue());
        collector.checkThat(Introspector.decapitalize(""), equalTo(""));
        collector.checkThat(Introspector.decapitalize("foo"), equalTo("foo"));
        collector.checkThat(Introspector.decapitalize("Foo"), equalTo("foo"));
        collector.checkThat(Introspector.decapitalize("FOO"), equalTo("FOO"));
    }
}
