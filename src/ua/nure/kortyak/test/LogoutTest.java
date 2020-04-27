package ua.nure.kortyak.test;

import org.junit.Test;
import org.mockito.Mockito;
import ua.nure.kortyak.FinalDBase.web.command.LogoutCommander;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static junit.framework.TestCase.assertNull;

public class LogoutTest extends Mockito {

    @Test()
    public void execute(){
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        new LogoutCommander().execute(request,response);

        assertNull(request.getSession());
    }

}
