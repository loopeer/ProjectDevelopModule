package com.loopeer.projectdevelopmodule.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.loopeer.projectdevelopmodule.R
import com.loopeer.projectdevelopmodule.validator.NamePhoneValidator2

class FormValidator2Activity : AppCompatActivity() {

    private lateinit var validator : NamePhoneValidator2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_validator2)
        validator = NamePhoneValidator2(this)
        validator.addEnableListener { invalidateOptionsMenu() }
        validator.notifyEnable()
    }

    fun onBtnClick(view: View) {
        Toast.makeText(this, validator.name + " : " + validator.phone, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_validator_submit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_validator_submit).isEnabled = validator.isEnable
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }


}
