package com.qa.opencart.tests;

import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.listeners.ExtentReportListener;
import com.qa.opencart.listeners.AnnotationTransformer;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.Errors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic(" Epic no 10020 sample :Design Open Cart App- Login Page")
@Story("US 101 :Open Cart Login Design with multiple features")
@Listeners({AnnotationTransformer.class,ExtentReportListener.class,TestAllureListener.class})
public class LoginPageTest extends BaseTest {

	@Description("Login Page Title Test")
	@Severity(SeverityLevel.MINOR)
    @Test(priority = 1)
    public void loginPageTitleTest() {
        String actTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actTitle, Constants.LOGIN_PAGE_TITLE);
    }

	@Description("Login Page URL Test")
	@Severity(SeverityLevel.NORMAL)
    @Test(priority = 2)
    public void loginPageUrlTest() {
        String actualUrl = loginPage.getLoginPageUrl();
        Assert.assertTrue(actualUrl.contains(Constants.LOGIN_PAGE_URL_FRACTION));
    }

	@Description("Forgot pass link Test")
	@Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void forgotPasswordLinkTest() {
        Assert.assertTrue(loginPage.isForgotPasswordLinkExist());
    }


    @Description("Register link Test")
	@Severity(SeverityLevel.CRITICAL)
    @Test(priority = 4,enabled=true)
    public void isRegisterLinkExistTest() {
        Assert.assertTrue(loginPage.isRegisterLinkExist());
    }

    @Description("Login Test with correct credentials")
	@Severity(SeverityLevel.BLOCKER)
    @Test(priority = 5)
    public void doLoginTest() {
        accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
    Assert.assertEquals(accountsPage.getaccountPageTitle(),Constants.ACCOUNTS_PAGE_TITLE,Errors.ELEMENT_NOT_FOUND_ERROR_MESSG);
    }



}

