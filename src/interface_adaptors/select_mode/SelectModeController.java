package src.interface_adaptors.select_mode;

import src.use_case.select_mode.SelectModeInputBoundary;

import src.use_case.select_mode.SelectModeInputData;

public class SelectModeController {
    final SelectModeInputBoundary selectModeUseCaseInteractor;

    public SelectModeController(SelectModeInputBoundary userSignupUseCaseInteractor){
        this.selectModeUseCaseInteractor = userSignupUseCaseInteractor;
        }

    public void execute(String category, String difficultyLevel, int numOfQuestions){
        SelectModeInputData selectModeInputData = new SelectModeInputData(category, difficultyLevel, numOfQuestions);
        selectModeUseCaseInteractor.execute(selectModeInputData);
    }
}

