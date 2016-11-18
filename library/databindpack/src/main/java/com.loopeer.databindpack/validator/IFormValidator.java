package com.loopeer.databindpack.validator;

public interface IFormValidator extends IValidator{
    /**
     * check whether any item is edited
     * @return true,the at least one of the items if edited.false,none of the items is edited
     */
    boolean isEdited();
}
