package app;

import app.LoginUseCaseFactory;
import data_access.FileUserDataAccessObject;
import data_access.InMemorySelectModeAccessObject;
import entity.CommonUserFactory;
import interface_adaptors.login.LoginViewModel;
import interface_adaptors.logged_in.LoggedInViewModel;
import interface_adaptors.select_mode.SelectModeViewModel;
import interface_adaptors.signup.SignupViewModel;
import interface_adaptors.ViewManagerModel;
import use_case.login.LoginUserDataAccessInterface;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Trivia Cash");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();

        // Initialize SelectModeViewModel
        SelectModeViewModel selectModeViewModel = new SelectModeViewModel();

        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Initialize InMemoryDataAccessObject (for testing purpose); The actual Data Access Object is calling API
        InMemorySelectModeAccessObject selectModeAccessObject;
        selectModeAccessObject = new InMemorySelectModeAccessObject();

        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, userDataAccessObject);
        // views.add(signupView, signupView.viewName);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, loggedInViewModel, userDataAccessObject);
        views.add(loginView, loginView.viewName);

        LoggedInView loggedInView = new LoggedInView(loggedInViewModel);
        views.add(loggedInView, loggedInView.viewName);

        SelectModeView selectModeView = SelectModeUseCaseFactory.create(viewManagerModel, selectModeViewModel, selectModeAccessObject);
        views.add(selectModeView, selectModeView.viewName);

        viewManagerModel.setActiveView(signupView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}

