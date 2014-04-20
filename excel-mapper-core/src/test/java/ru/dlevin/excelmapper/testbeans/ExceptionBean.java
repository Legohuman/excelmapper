package ru.dlevin.excelmapper.testbeans;

/**
 * User: Dmitry Levin
 * Date: 03.04.14
 */
public class ExceptionBean {
    private String privateProp;
    private String exceptionProp;

    public ExceptionBean() {
    }

    private String getPrivateProp() {
        return privateProp;
    }

    private void setPrivateProp(String privateProp) {
        this.privateProp = privateProp;
    }

    public String getExceptionProp() {
        throw new UnsupportedOperationException("Can not get unsupported propert");
    }

    public void setExceptionProp(String exceptionProp) {
        throw new UnsupportedOperationException("Can not get unsupported propert");
    }
}
