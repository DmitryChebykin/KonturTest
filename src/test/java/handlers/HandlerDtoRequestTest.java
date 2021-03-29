package handlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public class HandlerDtoRequestTest {

    private HandlerDtoRequest handlerDtoRequest;

    @Before
    public void setUp() throws Exception {
        handlerDtoRequest = new HandlerDtoRequest();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Parameterized.Parameters
    public List<String[]> create() {

        String[] s1 = new String[]{"метр", "литр", "тонна"};
        String[] s2 = new String[]{"км", "куб", "центнер"};

        return new ArrayList<String[]>() {{ add(s1); add(s2);}};
    }

    @Test
    public void checkConversionEnable() {
        handlerDtoRequest.checkConversionEnable()
    }
}