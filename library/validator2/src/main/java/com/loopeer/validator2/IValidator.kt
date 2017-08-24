package com.loopeer.validator2

interface IValidator {

    /**
     * check whether all Items are empty
     * @return true,the button is enable.false,the button will be disable;
     */
    fun checkEnable(): Boolean

    /**
     * check whether all items are legal. And show Toast to remind .
     * @return true,the info is legal. false,the info is illegal.
     */
    fun isValidated(): Boolean
}
