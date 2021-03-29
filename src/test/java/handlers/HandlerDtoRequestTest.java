package handlers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HandlerDtoRequestTest {

    private HandlerDtoRequest handlerDtoRequest;
    private ArrayList<String []> stringsTrue = new ArrayList<>();
    private ArrayList<String []> stringsFalse = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        handlerDtoRequest = new HandlerDtoRequest();
        this.stringsTrue.addAll(create());
        this.stringsFalse.addAll(createFalse());
    }

    @After
    public void tearDown() throws Exception {

    }


    public List<String[]> create() {

        String[] s1 = new String[]{"метр", "литр", "тонна"};
        String[] s2 = new String[]{"км", "куб", "центнер"};

        return new ArrayList<String[]>() {{ add(s1); add(s2);}};
    }

    public List<String[]> createFalse() {

        String[] s1 = new String[]{"метр", "литр", "тонна"};
        String[] s2 = new String[]{"км", "куб", "центнер","s"};

        return new ArrayList<String[]>() {{ add(s1); add(s2);}};
    }

    @Test
    public void checkConversionEnableWaitTrue() {
        boolean res = handlerDtoRequest.checkConversionEnable(stringsTrue);
        Assert.assertTrue(res);
    }

    @Test
    public void checkConversionEnableWaitFalse() {
        boolean res = handlerDtoRequest.checkConversionEnable(stringsFalse);
        Assert.assertFalse(res);
    }

}