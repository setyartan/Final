package ua.nure.kortyak.test;

import org.junit.Test;
import org.mockito.Mockito;
import ua.nure.kortyak.FinalDBase.db.dbManager.DBManagerForPeriodicals;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.exception.AppException;
import ua.nure.kortyak.FinalDBase.web.Controller;
import ua.nure.kortyak.FinalDBase.web.command.AddPeriodicalCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;


public class AddPeriodicalTest extends Mockito {

    @Test(expected = AppException.class)
    public void execute()
            throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn(null);
        new AddPeriodicalCommand().execute(request, response);
    }

    @Test(expected = AppException.class)
    public void execute2()
            throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("price")).thenReturn(null);
        new AddPeriodicalCommand().execute(request, response);
    }

    @Test(expected = AppException.class)
    public void execute3()
            throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("theme")).thenReturn(null);
        new AddPeriodicalCommand().execute(request, response);
    }



    @Test()
    public void execute4()
            throws AppException, IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //when(request.getParameter("theme")).thenReturn("Love");
        when(request.getParameter("name")).thenReturn("Love");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("command")).thenReturn("addPeriodical");
        new Controller().doPost(request,response);

        DBManagerForPeriodicals managerForPeriodicals = DBManagerForPeriodicals.getInstance();
        Periodical periodicalIs = managerForPeriodicals.findPeriodicalByName("Love");
        Periodical periodicalExp = Periodical.createPeriodical("Love",100);


        assertEquals(periodicalExp , periodicalIs);
    }
}

