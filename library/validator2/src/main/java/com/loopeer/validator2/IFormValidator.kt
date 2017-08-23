package com.example.todou.testkotlinlibrary.validator

interface IFormValidator : IValidator {
    /**
     * check whether any item is edited
     * @return true,the at least one of the items if edited.false,none of the items is edited
     */
    val isEdited: Boolean
}
