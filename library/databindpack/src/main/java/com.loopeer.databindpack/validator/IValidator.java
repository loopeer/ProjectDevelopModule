package com.loopeer.databindpack.validator;

public interface IValidator {

    /**
     * check whether all Items are empty
     * @return true,the button is enable.false,the button will be disable;
     */
    boolean checkEnable();

    /**
     * check whether all items are legal. And show Toast to remind .
     * @return true,the info is legal. false,the info is illegal.
     */
    boolean isValidated();
}
