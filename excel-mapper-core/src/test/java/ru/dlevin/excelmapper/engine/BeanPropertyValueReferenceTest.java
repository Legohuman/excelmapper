package ru.dlevin.excelmapper.engine;

import org.junit.Test;
import ru.dlevin.excelmapper.exceptions.EvaluationException;
import ru.dlevin.excelmapper.testbeans.ExceptionBean;
import ru.dlevin.excelmapper.testbeans.Person;
import ru.dlevin.excelmapper.testbeans.PersonFactory;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class BeanPropertyValueReferenceTest {

    @Test
    public void testGetProperties() {
        PersonFactory personFactory = new PersonFactory();
        Person john = personFactory.getJohn();
        Person mike = personFactory.getMike();
        BeanPropertyValueReference ref = new BeanPropertyValueReference("age");
        ref.setContext(john);
        assertEquals("age", ref.getProperty());
        assertEquals(int.class, ref.getType());
        assertEquals(20, (int)(Integer)ref.getValue());
        ref.setContext(mike);
        assertEquals("age", ref.getProperty());
        assertEquals(int.class, ref.getType());
        assertEquals(42, (int)(Integer)ref.getValue());
    }

    @Test
    public void testSetProperties() {
        PersonFactory personFactory = new PersonFactory();
        Person john = personFactory.getJohn();
        Person mike = personFactory.getMike();
        BeanPropertyValueReference ref = new BeanPropertyValueReference("age");
        ref.setContext(john);
        ref.setValue(25);
        assertEquals(int.class, ref.getType());
        assertEquals(25, (int)(Integer)ref.getValue());
        ref.setContext(mike);
        ref.setValue(45);
        assertEquals(int.class, ref.getType());
        assertEquals(45, (int)(Integer)ref.getValue());
    }

    @Test
    public void testGetPropertyNoSuchProperty() {
        try {
            testGetPropertyFromExceptionBean("undefinedProp");
            fail();
        } catch (Exception e) {
            assertEquals(EvaluationException.class, e.getClass());
            assertEquals(NoSuchMethodException.class, e.getCause().getClass());
        }
    }

    @Test
    public void testGetPropertyPrivateProperty() {
        try {
            testGetPropertyFromExceptionBean("privateProp");
            fail();
        } catch (Exception e) {
            assertEquals(EvaluationException.class, e.getClass());
            assertEquals(NoSuchMethodException.class, e.getCause().getClass());
        }
    }

    @Test
    public void testGetPropertyThrowExceptionProperty() {
        try {
            testGetPropertyFromExceptionBean("exceptionProp");
            fail();
        } catch (Exception e) {
            assertEquals(EvaluationException.class, e.getClass());
            assertEquals(InvocationTargetException.class, e.getCause().getClass());
        }
    }

    private void testGetPropertyFromExceptionBean(String property) {
        ExceptionBean bean = new ExceptionBean();
        BeanPropertyValueReference ref = new BeanPropertyValueReference(property);
        ref.setContext(bean);
        ref.getValue();
    }

    @Test
    public void testSetPropertyNoSuchProperty() {
        try {
            testSetPropertyToExceptionBean("undefinedProp", "test");
            fail();
        } catch (Exception e) {
            assertEquals(EvaluationException.class, e.getClass());
            assertEquals(NoSuchMethodException.class, e.getCause().getClass());
        }
    }

    @Test
    public void testSetPropertyPrivateProperty() {
        try {
            testSetPropertyToExceptionBean("privateProp", "test");
            fail();
        } catch (Exception e) {
            assertEquals(EvaluationException.class, e.getClass());
            assertEquals(NoSuchMethodException.class, e.getCause().getClass());
        }
    }

    @Test
    public void testSetPropertyThrowExceptionProperty() {
        try {
            testSetPropertyToExceptionBean("exceptionProp", "test");
            fail();
        } catch (Exception e) {
            assertEquals(EvaluationException.class, e.getClass());
            assertEquals(InvocationTargetException.class, e.getCause().getClass());
        }
    }

    private void testSetPropertyToExceptionBean(String property, String value) {
        ExceptionBean bean = new ExceptionBean();
        BeanPropertyValueReference ref = new BeanPropertyValueReference(property);
        ref.setContext(bean);
        ref.setValue(value);
    }

    @Test(expected = EvaluationException.class)
    public void testSetPropertyClassCastExceptionProperty() {
        PersonFactory personFactory = new PersonFactory();
        Person john = personFactory.getJohn();
        BeanPropertyValueReference ref = new BeanPropertyValueReference("age");
        ref.setContext(john);
        ref.setValue("42");
    }

    @Test
    public void testGetPropertyTypeNoSuchProperty() {
        try {
            testGetPropertyTypeToExceptionBean("undefinedProp");
            fail();
        } catch (Exception e) {
            assertEquals(EvaluationException.class, e.getClass());
            assertNull(e.getCause());
        }
    }

    @Test
    public void testGetPropertyTypePrivateProperty() {
        try {
            testGetPropertyTypeToExceptionBean("privateProp");
            fail();
        } catch (Exception e) {
            assertEquals(EvaluationException.class, e.getClass());
            assertNull(e.getCause());
        }
    }

    private void testGetPropertyTypeToExceptionBean(String property) {
        ExceptionBean bean = new ExceptionBean();
        BeanPropertyValueReference ref = new BeanPropertyValueReference(property);
        ref.setContext(bean);
        ref.getType();
    }
}
